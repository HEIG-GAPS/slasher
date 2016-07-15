package ch.gaps.slasher.models.buttons;

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.models.treeItem.DatabaseTreeItem;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by leroy on 15.07.2016.
 */
public class DbCloseItem extends MenuItem {
    private DatabaseTreeItem databaseTreeItem;
    public DbCloseItem(DatabaseTreeItem databaseTreeItem){
        super();
        setText(databaseTreeItem.getValue().toString());
        addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {

        });
    }

}
