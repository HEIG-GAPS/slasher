package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import javafx.scene.control.TreeItem;

/**
 * Created by leroy on 13.07.2016.
 */
public class DbObjectTreeItem extends TreeItem <DbObject> {
    public DbObjectTreeItem(){}
    public DbObjectTreeItem(DbObject dbObject){super(dbObject);}

}
