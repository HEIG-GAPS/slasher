package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import ch.gaps.slasher.database.driver.database.Server;

/**
 * Created by leroy on 15.07.2016.
 */
public class ServerTreeItem extends DbObjectTreeItem {
    public ServerTreeItem(Server server){
        super(server);
    }


    public void disconnect(){
        ((Server)getValue()).disconnect();
    }
}
