package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.Table;

/**
 * Created by leroy on 13.07.2016.
 */
public class DatabaseTreeItem extends DbObjectTreeItem {

    public DatabaseTreeItem (Database db){
        super(db);

        for (Table t : db.getTables()){
            getChildren().add(new TableTreeItem(t));
        }


    }

    public String toString(){
        return "yolo2";
    }
}
