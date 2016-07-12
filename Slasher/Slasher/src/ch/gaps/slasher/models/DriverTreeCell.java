package ch.gaps.slasher.models;

import javafx.scene.control.TreeCell;

public class DriverTreeCell extends TreeCell<DriverTreeItem> {

    @Override
    public void updateItem(DriverTreeItem item, boolean empty){
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
