package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.*;
import ch.gaps.slasher.views.main.MainController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.LinkedList;

/**
 * Created by leroy on 15.07.2016.
 */
public class ServerTreeItem extends DbObjectTreeItem {

    public ServerTreeItem (Server server){
        super(server);

        refresh();
    }

    public void refresh(){
        LinkedList<Database> databases = ((Server)getValue()).getDatabases();

        databases.stream().filter(database -> getChildren().stream().noneMatch(dbObjectTreeItem -> ((Database)dbObjectTreeItem.getValue()).getDescritpion().equals(database.getDescritpion()))
        ).forEach(database -> {
            DbObjectTreeItem databaseItem = new DatabaseTreeItem(database);
            getChildren().add(databaseItem);
        });
    }


    public void disconnect(){
        this.getParent().getChildren().remove(this);
        ((Server)getValue()).disconnect();
    }

    @Override
    public ContextMenu getContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeServer = new MenuItem("Disconnect");
        closeServer.setOnAction(event -> {
            MainController.getInstance().disconnectServer(this);
        });
        contextMenu.getItems().add(closeServer);

        return contextMenu;
    }
}
