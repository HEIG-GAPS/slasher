package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.Database;

/**
 * Created by leroy on 13.07.2016.
 */
public class DatabaseTreeItem extends DbObjectTreeItem {

    public DatabaseTreeItem (Database db){
        super(db);

        
    }

    public String toString(){
        return "yolo2";
    }
}
