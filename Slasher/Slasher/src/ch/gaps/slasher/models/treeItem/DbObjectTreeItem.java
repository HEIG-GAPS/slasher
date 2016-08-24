/*
 * The MIT License
 *
 * Copyright 2016 leroy.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ch.gaps.slasher.models.treeItem;

import ch.gaps.slasher.database.driver.database.DbObject;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;

/**
 * @author jfleroy
 */
public abstract class DbObjectTreeItem extends TreeItem <DbObject> {

    protected ContextMenu contextMenu = new ContextMenu();
    protected TabPane structureTab;

    public DbObjectTreeItem(DbObject dbObject){
        super(dbObject);
    }

    /**
     * @return the type of the tree item
     */
    public abstract TreeItemType getType();

    /**
     * @return the structure tab for the item
     */
    public Node getStructureTab(){
        return structureTab;
    }

    /**
     * @return the context menu
     */
    public ContextMenu getContextMenu(){
        return contextMenu;
    }

    /**
     * build the context menu
     */
    public abstract void buildContextMenu();

}