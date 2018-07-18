package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DBObject;
import ch.gaps.slasher.views.main.MainController;
import javafx.scene.control.MenuItem;

/**
 * @author j.leroy
 */
public abstract class DbComponentTreeItem extends DbObjectTreeItem {
  private final DatabaseTreeItem databaseTreeItem;


  DbComponentTreeItem(DBObject dbObject, DatabaseTreeItem databaseTreeItem) {
    super(dbObject);
    this.databaseTreeItem = databaseTreeItem;
    contextMenu.getItems().clear();
    buildContextMenu();
  }

  /**
   * @return the mother databaseTreeItem
   */
  public DatabaseTreeItem getDatabase() {
    return databaseTreeItem;
  }


  @Override
  public abstract TreeItemType getType();


  @Override
  public void buildContextMenu() {
    MenuItem sqlEditor = new MenuItem("new SqlEditor");
    sqlEditor.setOnAction(event -> MainController.getInstance().newEditorTab());

    contextMenu.getItems().add(sqlEditor);
  }
}
