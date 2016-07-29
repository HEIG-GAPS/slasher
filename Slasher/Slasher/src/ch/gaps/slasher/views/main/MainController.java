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

import ch.gaps.slasher.database.driver.database.DbObject;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.models.buttons.ServerDisconnectItem;
import ch.gaps.slasher.models.treeItem.DbObjectTreeCell;
import ch.gaps.slasher.models.treeItem.DbObjectTreeItem;
import ch.gaps.slasher.models.treeItem.ServerTreeItem;
import ch.gaps.slasher.views.editor.EditorController;
import ch.gaps.slasher.views.connectServer.ConnectServerController;
import java.io.IOException;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    private TreeItem<DbObject> rootTreeItem = new DbObjectTreeItem();


    private LinkedList<Server> servers = new LinkedList<>();

    public MainController(){
        instance = this;
    }

    public static MainController getInstance(){
        if (instance == null) instance = new MainController();
        return instance;
    }
    
    @FXML
    private void initialize(){
        menu.setUseSystemMenuBar(true);
        treeView.setRoot(rootTreeItem);
        treeView.setShowRoot(false);
        rootTreeItem.setExpanded(true);
        treeView.setCellFactory(param -> new DbObjectTreeCell());

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                newEditorTab.setDisable(false);
                tabPane.getTabs().clear();
                tabPane.getTabs().addAll(((DbObjectTreeItem)newValue).getTabs());
            }
            else {
                newEditorTab.setDisable(true);
            }
        });
    }
    
    @FXML
    private void close(){
        Platform.exit();
    }
    
    @FXML
    private void newEditorTab() throws IOException{
        FXMLLoader loader = new FXMLLoader(EditorController.class.getResource("EditorView.fxml"));
        Pane newPane = loader.load();
        Tab newTab = new Tab("Editor", newPane);
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
        int prevSize = servers.size();
        stage.showAndWait();
        refreshTreeView();
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


    public void addServer(Server server){

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

}
