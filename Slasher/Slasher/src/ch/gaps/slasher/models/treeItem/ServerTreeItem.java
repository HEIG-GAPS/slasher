package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.*;
import ch.gaps.slasher.models.buttons.ServerDisconnectItem;
import ch.gaps.slasher.views.main.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * Created by leroy on 15.07.2016.
 */
public class ServerTreeItem extends DbObjectTreeItem {

    public ServerTreeItem (Server server){
        super(server);



        Database[] databases = server.getDatabases();

        if (databases != null)
        {
            for (Database database: databases){
                DbObjectTreeItem databaseItem = new DatabaseTreeItem(database);
                getChildren().add(databaseItem);
            }
        }
    }


    public void disconnect(){
        this.getParent().getChildren().remove(this);
        ((Server)getValue()).disconnect();
    }

    @Override
    public ContextMenu getContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeServer = new MenuItem("Disconnect");
        closeServer.setOnAction(event -> {
            MainController.getInstance().disconnectServer(this);
        });
        contextMenu.getItems().add(closeServer);

        return contextMenu;
    }



}
