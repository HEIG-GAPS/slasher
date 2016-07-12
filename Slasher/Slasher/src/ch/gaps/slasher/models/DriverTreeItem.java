package ch.gaps.slasher.models;

import ch.gaps.slasher.database.driver.Driver;
import javafx.scene.control.TreeItem;


/**
 * Created by leroy on 12.07.2016.
 */
public class DriverTreeItem extends TreeItem {
    private Driver driver;

    public DriverTreeItem(Driver driver){
        this.driver = driver;
    }

    public DriverTreeItem(){}

    public String toString()
    {
        return driver.type();
    }


}
