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
package ch.gaps.slasher.views.main;

import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.database.driver.MySql;
import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.DbObject;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.models.buttons.ServerDisconnectItem;
import ch.gaps.slasher.models.treeItem.*;
import ch.gaps.slasher.views.connectServer.ConnectServerController;
import ch.gaps.slasher.views.editor.EditorController;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author leroy
 */
public class MainController {

    private static MainController instance;


    @FXML private MenuBar menu;
    @FXML private BorderPane borderPane;
    @FXML private TabPane tabPane;
    @FXML private TreeView<DbObject> treeView;
    @FXML private Menu closeServerButton;
    @FXML private MenuItem newEditorTab;

    private TreeItem<DbObject> rootTreeItem = new TreeItem<DbObject>();
    private DatabaseTreeItem currentDatabaseTreeItem;
    private LinkedList<Server> servers = new LinkedList<>();
    private BufferedWriter os;
    private Map<Database, Tab> tabs = new LinkedHashMap<>();

    public MainController(){
        instance = this;
    }

    public static MainController getInstance(){
        return instance;
    }

    @FXML
    private void initialize(){

        readFromJson();
        refreshTreeView();

        menu.setUseSystemMenuBar(true);
        treeView.setRoot(rootTreeItem);
        treeView.setShowRoot(false);
        rootTreeItem.setExpanded(true);
        treeView.setCellFactory(param -> new DbObjectTreeCell());


        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
            {
                if (((DbObjectTreeItem) newValue).getType() != TreeItemType.SERVER)
                {
                    newEditorTab.setDisable(false);
                } else
                {
                    newEditorTab.setDisable(true);
                }
            }
        });
    }

    @FXML
    private void close(){
        Platform.exit();
    }

    @FXML
    private void newEditorTab() throws IOException{

        Database database;
        DbObjectTreeItem dbObjectTreeItem = (DbObjectTreeItem)treeView.getSelectionModel().getSelectedItem();

        if ( dbObjectTreeItem.getType()== TreeItemType.DATABASE ){
            database = (Database) dbObjectTreeItem.getValue();
        }
        else{
            database = (Database) ( (DbComponentTreeItem)dbObjectTreeItem ).getDatabase().getValue();
        }


        FXMLLoader loader = new FXMLLoader(EditorController.class.getResource("EditorView.fxml"));


        Tab newTab = new Tab("Editor on " + database.getDescritpion(), loader.load());
        EditorController editorController = loader.getController();
        editorController.setDatabase( database );
        tabs.put(database, newTab);

        ((DbObjectTreeItem)treeView.getSelectionModel().getSelectedItem()).addTab(newTab);
        tabPane.getTabs().add(newTab);
    }

    @FXML
    private void connectDB() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ConnectServerController.class.getResource("ConnectServerView.fxml"));
        stage.setTitle("Open a database");
        Pane pane = loader.load();
        ConnectServerController connectServerController = loader.getController();
        connectServerController.setController(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(pane));

        //Display the connection windows and wait undill the user close it by adding a server or by
        //cannceling
        stage.showAndWait();
        refreshTreeView();
        writeToJson();
    }

    public void refreshTreeView(){
        rootTreeItem.getChildren().forEach(dbObjectTreeItem -> {
            ((ServerTreeItem)dbObjectTreeItem).refresh();
        });
    }

    public  void disconnectServer(ServerTreeItem serverItem) {
        servers.remove(serverItem.getValue());
        serverItem.disconnect();
        closeServerButton.getItems().removeIf(menuItem -> ((ServerDisconnectItem) menuItem).getServerTreeItem() == serverItem);
    }


    /**
     * Called by the connection window to add a new server.
     * Rewrite the json too
     * @param server
     */
    public void addServer(Server server){
        addServerToSystem(server);
        writeToJson();
    }


    /**
     * Private method to add the server to application
     * @param server
     */
    private void addServerToSystem(Server server){
        servers.add(server);

        ServerTreeItem item = new ServerTreeItem(server);
        rootTreeItem.getChildren().add(item);

        ServerDisconnectItem serverDisconnectItem = new ServerDisconnectItem(item);

        serverDisconnectItem.setOnAction(event -> {
            disconnectServer(item);
            closeServerButton.getItems().remove(serverDisconnectItem);
        });

        closeServerButton.getItems().add(serverDisconnectItem);
    }

    public LinkedList<Server> getServersList(){
        return servers;
    }

    public void writeToJson(){
        try
        {
            os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("save.json")));
            Gson jsonEngine = new GsonBuilder().setPrettyPrinting().create();
            JsonArray mainArray = new JsonArray();

            for (Server s : servers)
            {
                JsonObject server = new JsonObject();

                server.addProperty("serverDescription", s.getDescription());
                server.addProperty("serverDriver", s.getDiverName());
                server.addProperty("serverHost", s.getHost());

                JsonArray databases = new JsonArray();

                for (Database db: s.getDatabases()){

                    JsonObject database = new JsonObject();

                    database.addProperty("databaseDescritpion", db.getDescritpion());
                    database.addProperty("databaseName", db.getName());
                    database.addProperty("databaseUsername", db.getUsername());

                    databases.add(database);

                    Tab tab = tabs.get(db);


                }
                server.add("databases", databases);

                mainArray.add(server);
            }

            os.write(jsonEngine.toJson(mainArray));
            os.flush();

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void readFromJson(){
        try
        {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("save.json"));
            JsonArray mainArray = gson.fromJson(reader, JsonArray.class);

            if (mainArray != null)
            {
                for (JsonElement e : mainArray)
                {
                    JsonObject server = e.getAsJsonObject();

                    Driver driver = new MySql();

                    Server s = new Server(driver, server.get("serverHost").getAsString(), server.get("serverDescription").getAsString());

                    JsonArray databases = server.get("databases").getAsJsonArray();

                    if (databases != null)
                    {
                        for (JsonElement d : databases)
                        {
                            JsonObject database = d.getAsJsonObject();

                            Driver dbDriver = new MySql();

                            Database db = new Database(dbDriver, database.get("databaseName").getAsString(),
                                    database.get("databaseDescritpion").getAsString(),
                                    s,
                                    database.get("databaseUsername").getAsString());
                            s.addDatabase(db);
                        }
                    }

                    addServerToSystem(s);
                }
            }

        int a = 0;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


    }

}
