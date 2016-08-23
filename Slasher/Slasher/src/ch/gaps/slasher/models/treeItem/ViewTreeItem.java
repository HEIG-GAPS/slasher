package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import javafx.scene.Node;

/**
 * Created by julien on 19.08.16.
 */
public class ViewTreeItem extends DbComponentTreeItem
{
    public ViewTreeItem(DbObject dbObject, DatabaseTreeItem databaseTreeItem)
    {
        super(dbObject, databaseTreeItem);
    }

    @Override
    public Node getStructureTab()
    {
        return null;
    }

    @Override
    public void buildContextMenu()
    {

    }
}
