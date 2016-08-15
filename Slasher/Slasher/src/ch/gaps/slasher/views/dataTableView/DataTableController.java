package ch.gaps.slasher.views.dataTableView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by julien on 15.08.16.
 */
public class DataTableController
{
    @FXML
    private TableView <ObservableList<String>> tableView;

    private ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();


    public void display(ResultSet resultSet){

        try
        {
            int colunms = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < colunms; ++i){
                final int j = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i+1));
                column.setCellValueFactory(tc -> new SimpleObjectProperty<>(tc.getValue().get(j)));
                tableView.getColumns().add(column);

            }


            while (resultSet.next()){
                ObservableList<String> row = FXCollections.observableArrayList();

                for (int i = 1 ; i <= colunms; ++i )
                {
                    row.add(resultSet.getString(i));
                }

                data.add(row);
            }

            tableView.setItems(data);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
