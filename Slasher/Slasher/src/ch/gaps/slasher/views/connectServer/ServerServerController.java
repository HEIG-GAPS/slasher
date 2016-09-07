/*
 * The MIT License
 *
 * Copyright 2016 leroy.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.gaps.slasher.views.connectServer;

import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.views.main.MainController;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class is used to connect databases who works with a server
 * @author j.leroy
 */
public class ServerServerController implements ServerController
{
    @FXML
    private TextField host;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField port;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button connect;
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, TextField> databaseDescription;
    @FXML
    private TableColumn<Item, CheckBox> databaseToOpen;
    @FXML
    private Label errorLabel;

    private BooleanProperty hostOk = new SimpleBooleanProperty(false);
    private BooleanProperty portOk = new SimpleBooleanProperty(false);
    private BooleanProperty usernameOk = new SimpleBooleanProperty(false);

    private BooleanProperty databaseNameConflict = new SimpleBooleanProperty(true);

    private BooleanProperty filedOk = new SimpleBooleanProperty(false);
    private BooleanProperty allOk = new SimpleBooleanProperty(false);



    private boolean newServer = false;

    private Driver driver;
    private Server server;

    private IntegerProperty dbCount = new SimpleIntegerProperty(0);
    private LinkedList<Database> databasesToOpen = new LinkedList<>();

    private MainController mainController;


    @FXML
    public void initialize()
    {
        errorLabel.setMaxWidth(380);
        errorLabel.setWrapText(true);


        mainController = MainController.getInstance();
        filedOk.bind(hostOk.and(usernameOk).and(portOk));
        allOk.bind(databaseNameConflict.and(dbCount.isNotEqualTo(0)));

        hostOk.bind(host.textProperty().isNotEmpty());
        portOk.bind(port.textProperty().isNotEmpty());
        usernameOk.bind(username.textProperty().isNotEmpty());

        connect.disableProperty().bind(filedOk.not());


        // force the field to be numeric only
        port.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!newValue.matches("\\d*")) {
                port.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        databaseDescription.setCellValueFactory(param ->
        {
            ObservableValue<TextField> tf = new SimpleObjectProperty<TextField>(new TextField());
            tf.getValue().disableProperty().bind(param.getValue().disable);
            tf.getValue().textProperty().addListener((observable, oldValue, newValue) ->
            {
                // We change the background to red and lock the connection
                // if the database description == to an already opened
                if (!checkDatabaseDesc(newValue) && !param.getValue().disable.getValue()){
                    tf.getValue().setStyle("-fx-background-color: red");
                    databaseNameConflict.setValue(false);
                }
                else {
                    tf.getValue().setStyle("");
                    databaseNameConflict.setValue(true);
                }
            });

            tf.getValue().textProperty().bindBidirectional(param.getValue().description);
            return tf;
        });


        databaseToOpen.setCellValueFactory(param ->
        {
            ObservableValue<CheckBox> cb = new SimpleObjectProperty<CheckBox>(new CheckBox());
            cb.getValue().disableProperty().bind(param.getValue().disable);
            cb.getValue().selectedProperty().bindBidirectional(param.getValue().selected);
            return cb;
        });


        databaseDescription.prefWidthProperty().bind(tableView.widthProperty().subtract(databaseToOpen.widthProperty()).subtract(2));

    }

    /**
     * Method called by the UI to load and display all the databases that the user have access to and is able to open
     */
    @FXML
    public void loadDatabases()
    {
        errorLabel.setText("");
        databasesToOpen.clear();
        tableView.getItems().clear();
        if (mainController.getServersList().stream()
                .filter(server1 -> server1.getHost().equals(host.getText()))
                .peek(server12 ->
                {
                    server = server12;
                })
                .count() == 0)
        {
            server = new Server(driver, host.getText(), Integer.parseInt(port.getText()), null);
            newServer = true;

        }
        LinkedList<Database> databases = server.getDatabases();

        // Look for the already opened databases and add them disabled with a checked checkbox
        databases.forEach(database ->
        {
            Item item = new Item(database);
            item.selected.set(true);
            item.disable.setValue(true);
            tableView.getItems().add(item);
        });


        LinkedList<Database> allDatabases = null;
        try
        {
            allDatabases = server.getAllDatabases(username.getText(), password.getText());


            //Add all the other database (the ones on the server that are not opened
            allDatabases.stream()
                    .filter(database -> databases.stream().noneMatch(database1 ->
                             database1.getName().equals(database.getName()) && database1.getUsername().equals(database.getUsername())
                            ))
                    .forEach(database ->
                    {
                        Item item = new Item(database);
                        item.onProperty().addListener((observable, oldValue, newValue) ->
                        {
                            if (newValue) {
                                server.addDatabase(item.database);
                                dbCount.set(dbCount.get() + 1);
                                databasesToOpen.add(item.database);
                            } else  {
                                server.removeDatabase(item.database);
                                dbCount.set(dbCount.get() - 1);
                                databasesToOpen.remove(item.database);
                            }
                        });
                        tableView.getItems().add(item);
                    });


        } catch (SQLException | ClassNotFoundException e)
        {
            errorLabel.setText(e.getMessage());
        }
    }

    @Override
    public Server getServer()
    {
        return server;
    }

    @Override
    public BooleanProperty getFieldValidation()
    {
        return allOk;
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
        port.setText(Integer.toString(driver.getDefaultPort()));
    }

    @Override
    public void connect() throws SQLException, ClassNotFoundException
    {
        for (Database database : databasesToOpen){
            //Add "(username@database)" at the end of the description if it isn't standard
            if (!database.getDescritpion().equals(database.getUsername() + "@" + database.getName())){
                database.setDescription( database.getDescritpion() + " (" + database.getUsername() + "@" + database.getName() + ")");
            }

            database.connect(password.getText());
        }
    }

    @Override
    public boolean newServer()
    {
        return newServer;
    }


    /**
     * Method to check if the description already exist on this server
     * @param description
     * @return true if doesn't exist
     */
    private boolean checkDatabaseDesc(String description){
        for (Database database : server.getDatabases()){
            if (database.getDescritpion().equals(description))
                return false;
        }

        return true;
    }


    class Item
    {
        private Database database;
        private BooleanProperty selected;
        private StringProperty description;
        private BooleanProperty disable = new SimpleBooleanProperty(false);

        Item(Database database)
        {
            this.selected = new SimpleBooleanProperty(false);
            this.database = database;
            description = new SimpleStringProperty(database.getDescritpion());

            description.addListener((observable, oldValue, newValue) ->
            {
                if (checkDatabaseDesc(newValue))
                {
                    database.setDescription(description.getValue());
                }
            });

        }

        public BooleanProperty onProperty()
        {
            return selected;
        }

        public String toString()
        {
            return database.toString();
        }
    }

}
