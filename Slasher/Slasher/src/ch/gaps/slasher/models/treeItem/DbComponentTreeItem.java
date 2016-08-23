package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import ch.gaps.slasher.views.main.MainController;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

/**
 * Created by julien on 15.08.16.
 */
public abstract class DbComponentTreeItem extends DbObjectTreeItem
{
    private DatabaseTreeItem databaseTreeItem;

    protected TabPane structureTab;


    public DbComponentTreeItem(DbObject dbObject, DatabaseTreeItem databaseTreeItem)
    {
        super(dbObject);
        this.databaseTreeItem = databaseTreeItem;
        buildContextMenu();
    }

    public DatabaseTreeItem getDatabase()
    {
        return databaseTreeItem;
    }

    @Override
    public TreeItemType getType()
    {
        return null;
    }

    @Override
    public abstract Node getStructureTab();

    @Override
    public void buildContextMenu(){
        MenuItem sqlEditor = new MenuItem("new SqlEditor");
        sqlEditor.setOnAction(event -> MainController.getInstance().newEditorTab());

        contextMenu.getItems().add(sqlEditor);
    }
}
