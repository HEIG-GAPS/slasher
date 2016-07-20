package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.*;

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
}
