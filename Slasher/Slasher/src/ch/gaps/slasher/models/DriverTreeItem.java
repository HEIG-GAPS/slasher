package ch.gaps.slasher.models;

import ch.gaps.slasher.database.driver.Driver;
import javafx.scene.control.TreeItem;


public class DriverTreeItem extends TreeItem {
    private Driver driver;

    public DriverTreeItem(Driver driver){
        this.driver = driver;
    }

    public DriverTreeItem(){}

    @Override
    public String toString()
    {
        return "naom";
    }


}
