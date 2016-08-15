package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import javafx.scene.control.Tab;

import java.util.LinkedList;

/**
 * Created by julien on 15.08.16.
 */
public class DbComponentTreeItem extends DbObjectTreeItem
{
    private DatabaseTreeItem databaseTreeItem;


    public DbComponentTreeItem(DbObject dbObject, DatabaseTreeItem databaseTreeItem)
    {
        super(dbObject);
        this.databaseTreeItem = databaseTreeItem;
    }

    public DatabaseTreeItem getDatabase(){
        return databaseTreeItem;
    }

    @Override
    public void addTab(Tab tab)
    {
        
    }

    @Override
    public void removeTab(Tab tab)
    {

    }

    @Override
    public LinkedList<Tab> getTabs()
    {
        return null;
    }

    @Override
    public TreeItemType getType()
    {
        return null;
    }
}
