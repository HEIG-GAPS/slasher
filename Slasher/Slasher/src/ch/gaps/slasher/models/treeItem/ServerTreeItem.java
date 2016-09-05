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

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.views.main.MainController;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author j.leroy
 */
public class ServerTreeItem extends DbObjectTreeItem {

    public ServerTreeItem (Server server){
        super(server);
        addAllServerDb();
        buildContextMenu();
    }

    /**
     * Create all the databases linked to this server
     */
    public void addAllServerDb(){
        LinkedList<Database> databases = ((Server)getValue()).getDatabases();

        databases.stream()
                .filter(database -> getChildren().stream()
                        .noneMatch(dbObjectTreeItem -> ((Database)dbObjectTreeItem.getValue()).getDescritpion().equals(database.getDescritpion()))
        ).forEach(database -> {
            DbObjectTreeItem databaseItem = null;
            try
            {
                databaseItem = new DatabaseTreeItem(database);
                getChildren().add(databaseItem);

            } catch (SQLException e)
            {
                MainController.getInstance().addToUserCommunication(e.getMessage());
            }
        });
    }

    /**
     * Used by the UI to disconnect nicely a server and all linked to it
     * @throws SQLException
     */
    public void disconnect() throws SQLException
    {
        this.getParent().getChildren().remove(this);
        ((Server)getValue()).disconnect();
    }

    @Override
    public TreeItemType getType()
    {
        return TreeItemType.SERVER;
    }

    @Override
    public void buildContextMenu()
    {
        contextMenu = new ContextMenu();
        MenuItem closeServer = new MenuItem("Disconnect");
        closeServer.setOnAction(event -> MainController.getInstance().disconnectServer(this));
        contextMenu.getItems().add(closeServer);
    }
}
