package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import javafx.scene.control.TreeCell;

/**
 * Created by leroy on 13.07.2016.
 */
public class DbObjectTreeCell extends TreeCell<DbObject> {

    @Override
    public void updateItem(DbObject dbObject, boolean empty){
        super.updateItem(dbObject, empty);

        if (empty){
            setText(null);
            setGraphic(null);
        }
        else{
            setText(getItem() == null ? "" : getItem().toString());
            setGraphic(getTreeItem().getGraphic());
        }
    }
}
