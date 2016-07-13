package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.Table;

/**
 * Created by leroy on 13.07.2016.
 */
public class TableTreeItem extends DbObjectTreeItem {
    public TableTreeItem(Table table){
        super(table);
    }
}
