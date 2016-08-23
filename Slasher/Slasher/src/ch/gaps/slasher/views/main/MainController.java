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

import ch.gaps.slasher.DriverService;
import ch.gaps.slasher.Slasher;
import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.DbObject;
import ch.gaps.slasher.database.driver.database.Schema;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.models.buttons.ServerDisconnectItem;
import ch.gaps.slasher.models.treeItem.*;
import ch.gaps.slasher.views.connectServer.ConnectServerController;
import ch.gaps.slasher.views.editor.EditorController;
import ch.gaps.slasher.views.editor.EditorTab;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author leroy
 */
public class MainController {

    private static MainController instance;
    private static ResourceBundle bundle = ResourceBundle.getBundle("ch.gaps.slasher.bundle.Bundle", new Locale("en", "EN"));



    @FXML private MenuBar menu;
    @FXML private BorderPane borderPane;
    @FXML private TabPane tabPane;
    @FXML private TreeView<DbObject> treeView;
    @FXML private Menu closeServerButton;
    @FXML private MenuItem newEditorTab;

    private Tab structureTab = new Tab("Struct");

    private TreeItem<DbObject> rootTreeItem = new TreeItem<DbObject>();
    private DatabaseTreeItem currentDatabaseTreeItem;
    private LinkedList<Server> servers = new LinkedList<>();
    private BufferedWriter os;
    private LinkedList<EditorTab> tabs = new LinkedList<>();

    public MainController(){
        instance = this;
    }

    /**
     * To get the MainController instance
     * @return
     */
    public static MainController getInstance(){
        return instance;
    }

    /**
     * UI initializing method
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException
    {
        structureTab.setClosable(false);
        tabPane.getTabs().add(structureTab);

        menu.setUseSystemMenuBar(true);
        treeView.setRoot(rootTreeItem);
        treeView.setShowRoot(false);
        rootTreeItem.setExpanded(true);
        treeView.setCellFactory(param -> new DbObjectTreeCell());


        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
            {
                structureTab.setContent(null);
                DbObjectTreeItem dbObjectTreeItem = (DbObjectTreeItem) newValue;
                if (dbObjectTreeItem.getType() != TreeItemType.SERVER)
                {
                    newEditorTab.setDisable(false);
                } else
                {
                    newEditorTab.setDisable(true);
                }

                if (dbObjectTreeItem.getStructureTab() != null){
                    structureTab.setContent(dbObjectTreeItem.getStructureTab());

                }
            }

        });

        readSave();
        refreshTreeView();
    }

    /**
     * Called by the UI to close the app
     */
    @FXML
    private void close(){
        Platform.exit();
    }

    /**
     * Called by the UI to open a new tab
     * @throws IOException
     */
    @FXML
    public void newEditorTab(){

        Database database;
        DbObjectTreeItem dbObjectTreeItem = (DbObjectTreeItem)treeView.getSelectionModel().getSelectedItem();

        if ( dbObjectTreeItem.getType()== TreeItemType.DATABASE ){
            database = (Database) dbObjectTreeItem.getValue();
        }
        else if ( dbObjectTreeItem.getType()== TreeItemType.SCHEMA ){
            database = ((Schema)dbObjectTreeItem.getValue()).getDatabase();
        }

        else{
            database = (Database) ( (DbComponentTreeItem)dbObjectTreeItem ).getDatabase().getValue();
        }

        try
        {
            loadEditorTab(database, "");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        saveState();
    }

    /**
     * Load the fxml of the SQL Editor
     * @param database to link the query to
     * @param content   to initialise the content of the editor
     * @return  Loaded Tab
     * @throws IOException
     */
    private Tab loadEditorTab(Database database, String content) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(EditorController.class.getResource("EditorView.fxml"), Slasher.getBundle());
        Node node = loader.load();
        EditorController editorController = loader.getController();

        EditorTab newTab = new EditorTab("Editor on " + database.getDescritpion(), node, database, editorController);
        newTab.setOnClosed(event ->
        {
            tabs.remove(event.getSource());
        });

        if (content !=null){
            editorController.setContent(content);
        }

        editorController.setDatabase( database );
        tabs.add(newTab);

        tabPane.getTabs().add(newTab);

        return newTab;
    }

    /**
     * Load the connection window
     * @throws IOException
     */
    @FXML
    private void connectDB() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(ConnectServerController.class.getResource("ConnectServerView.fxml"), bundle);
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
        saveState();
    }

    /**
     * To Refresh the left tree view
     */
    public void refreshTreeView(){
        rootTreeItem.getChildren().forEach(dbObjectTreeItem -> {
            ((ServerTreeItem)dbObjectTreeItem).addAllServerDb();
        });
    }

    /**
     * Disconnect "nicly" a server.
     * @param serverItem
     */
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
        saveState();
    }


    /**
     * Private method to add the server to application
     * @param server
     */
    private ServerTreeItem addServerToSystem(Server server){
        servers.add(server);

        ServerTreeItem item = new ServerTreeItem(server);
        rootTreeItem.getChildren().add(item);

        ServerDisconnectItem serverDisconnectItem = new ServerDisconnectItem(item);

        serverDisconnectItem.setOnAction(event -> {
            disconnectServer(item);
            closeServerButton.getItems().remove(serverDisconnectItem);
        });

        closeServerButton.getItems().add(serverDisconnectItem);

        return item;
    }

    /**
     * Return the registered servers
     * @return registered servers list
     */
    public LinkedList<Server> getServersList(){
        return servers;
    }

    /**
     * Tosave the state of the software, the tab, the servers and the databases.
     */
    public void saveState(){
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


                    JsonArray tabsJson = new JsonArray();

                    tabs.forEach(editorTab ->
                    {
                        if (editorTab.getDatabase() == db){

                            JsonObject tabJson = new JsonObject();
                            tabJson.addProperty("tabName", "name");
                            tabJson.addProperty("moduleName", editorTab.getModuleName());
                            tabJson.addProperty("content", editorTab.getEditorController().getContent());
                            tabsJson.add(tabJson);
                        }
                    });

                    database.add("tabs", tabsJson);

                    databases.add(database);

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

    /**
     * Read the save file and laod all the data in the software
     * @throws IOException
     */
    private void readSave() throws IOException
    {
        try
        {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader("save.json"));
            JsonArray mainArray = gson.fromJson(reader, JsonArray.class);

            LinkedList<Driver> drivers = DriverService.instance().getAll();

            if (mainArray != null)
            {
                for (JsonElement e : mainArray)
                {
                    JsonObject server = e.getAsJsonObject();

                    Driver driver = null;

                    String serverDriver = server.get("serverDriver").getAsString();

                    for (Driver d: drivers){
                        if (d.toString().equals(serverDriver)){
                            driver = d.getClass().newInstance();
                        }
                    }

                    Server s = new Server(driver, server.get("serverHost").getAsString(), server.get("serverDescription").getAsString());
                    ServerTreeItem serverTreeItem = new ServerTreeItem(s);
                    servers.add(s);

                    JsonArray databases = server.get("databases").getAsJsonArray();

                    if (databases != null)
                    {
                        for (JsonElement d : databases)
                        {
                            driver = driver.getClass().newInstance();
                            JsonObject database = d.getAsJsonObject();


                            Database db = new Database(driver, database.get("databaseName").getAsString(),
                                    database.get("databaseDescritpion").getAsString(),
                                    s,
                                    database.get("databaseUsername").getAsString());
                            s.addDatabase(db);


                            JsonArray tabs = database.get("tabs").getAsJsonArray();

                            for (JsonElement t : tabs){
                                JsonObject tab = t.getAsJsonObject();

                                if (tab.get("moduleName").getAsString().equals("Editor")){
                                    loadEditorTab(db, tab.get("content").getAsString());
                                }

                            }

                        }
                    }
                    rootTreeItem.getChildren().add(serverTreeItem);
                }
            }

        int a = 0;
        } catch (FileNotFoundException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }

    }

    public static ResourceBundle getBundle(){return bundle;}
}
