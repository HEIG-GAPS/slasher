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

import ch.gaps.slasher.Slasher;
import ch.gaps.slasher.database.driver.database.Table;
import ch.gaps.slasher.views.dataTableView.DataTableController;
import ch.gaps.slasher.views.main.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;


public class TableTreeItem extends DbComponentTreeItem {

    DatabaseTreeItem databaseTreeItem;

    public TableTreeItem(Table table, DatabaseTreeItem databaseTreeItem){
        super(table, databaseTreeItem);
        this.databaseTreeItem = databaseTreeItem;
    }

    @Override
    public TreeItemType getType()
    {
        return TreeItemType.TABLE;
    }

    @Override
    public Node getStructureTab()
    {
        if (structureTab == null){
            loadStructureTab();
        }

        return structureTab;
    }


    public void loadStructureTab() {
        structureTab = new TabPane();

        Tab data = new Tab(Slasher.getBundle().getString("u.data"));
        DataTableController tableController = null;


        FXMLLoader loader = new FXMLLoader(DataTableController.class.getResource("DataTableView.fxml"), Slasher.getBundle());
        Pane pane = null;
        AnchorPane anchorPane = new AnchorPane();

        Button refresh = new Button(Slasher.getBundle().getString("u.refresh"));

        AnchorPane.setLeftAnchor(refresh, 10.);
        AnchorPane.setTopAnchor(refresh, 5.);

        anchorPane.getChildren().add(refresh);

        try
        {
            pane = loader.load();

            AnchorPane.setTopAnchor(pane, 25.);
            AnchorPane.setLeftAnchor(pane, 0.);
            AnchorPane.setRightAnchor(pane, 0.);
            AnchorPane.setBottomAnchor(pane, 0.);
            anchorPane.getChildren().add(pane);

            tableController = loader.getController();
            final DataTableController dataTableController = tableController;
            refresh.setOnAction(event -> {
                try
                {
                    dataTableController.display(((Table)getValue()).getAllData());
                } catch (SQLException e)
                {
                    MainController.getInstance().addToUserCommunication(e.getMessage());
                }
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            tableController.display(((Table)getValue()).getAllData());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        data.setContent(anchorPane);
        structureTab.getTabs().add(data);
    }


}
