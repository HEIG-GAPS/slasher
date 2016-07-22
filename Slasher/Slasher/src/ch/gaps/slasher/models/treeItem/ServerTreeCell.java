package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import javafx.scene.control.ContextMenu;

/**
 * Created by leroy on 22.07.2016.
 */
public class ServerTreeCell extends DbObjectTreeCell {

    public ContextMenu getContexMenu(){
        return new ContextMenu();
    }

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
            setContextMenu(getContexMenu());
        }
    }
}
