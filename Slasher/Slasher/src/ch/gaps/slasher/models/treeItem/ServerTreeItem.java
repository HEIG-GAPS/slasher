package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.views.main.MainController;
import ch.gaps.slasher.views.structure.StructureTabController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by leroy on 15.07.2016.
 */
public class ServerTreeItem extends DbObjectTreeItem {
    Tab structureTab;

    public ServerTreeItem (Server server){
        super(server);

        FXMLLoader loader = new FXMLLoader(StructureTabController.class.getResource("StructureTabView.fxml"));
        try
        {
            AnchorPane pane = new AnchorPane();
            pane = loader.load();
            structureTab = new Tab("Structure");

            structureTab.setContent(pane);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

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
    public void addTab(Tab tab)
    {

    }

    @Override
    public void removeTab(Tab tab)
    {

    }

    @Override
    public LinkedList<Tab> getTabs() {
        LinkedList<Tab> tabs = new LinkedList<>();
        tabs.add(structureTab);
        return tabs;
    }

    @Override
    public TreeItemType getType()
    {
        return TreeItemType.SERVER;
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
