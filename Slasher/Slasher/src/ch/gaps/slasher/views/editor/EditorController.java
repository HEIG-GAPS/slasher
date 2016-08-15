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
package ch.gaps.slasher.views.editor;

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.views.dataTableView.DataTableController;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leroy
 */
public class EditorController {
    @FXML
    private TextArea request;
    @FXML
    private AnchorPane tableViewPane;
    @FXML
    private Button execute;
    @FXML
    private ProgressIndicator progress;

    private Database database;

    private DataTableController dataTableController;


    @FXML
    private void initialize(){
        progress.setVisible(false);

        FXMLLoader loader = new FXMLLoader(DataTableController.class.getResource("DataTableView.fxml"));
        try
        {
            TableView tableView = loader.load();
            AnchorPane.setTopAnchor(tableView, 10.);
            AnchorPane.setBottomAnchor(tableView, 10.);
            AnchorPane.setLeftAnchor(tableView, 10.);
            AnchorPane.setRightAnchor(tableView, 10.);
            dataTableController = loader.getController();

            tableViewPane.getChildren().add(tableView);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }



    @FXML
    private void execute(){


        Task<Void> task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                final ResultSet filanRs;
                try
                {
                    ResultSet rs = database.executeQuarry(request.getText());
                    dataTableController.display(rs);

                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        };
        progress.visibleProperty().bind(task.runningProperty());

        new Thread(task).start();
        
    }

    public void setDatabase(Database database){
        this.database = database;
        execute.disableProperty().bind(database.disabledProperty().not());
    }
}
