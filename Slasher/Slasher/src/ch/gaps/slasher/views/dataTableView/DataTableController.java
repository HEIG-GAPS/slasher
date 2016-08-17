package ch.gaps.slasher.views.dataTableView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by julien on 15.08.16.
 */
public class DataTableController
{
    @FXML
    private TableView <ObservableList<String>> tableView;
    @FXML
    private Label count;
    @FXML
    private Label displayZone;

    private ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();


    @FXML
    private void sort(){
        new Thread(() ->
        {
            tableView.getColumns().get(0).setSortType(TableColumn.SortType.ASCENDING);
        }).start();

    }





    public void display(ObservableList<ObservableList<String>> data, String[] colunmsName){

        tableView.getColumns().clear();


        int i = 0;
        for (String colunm: colunmsName){
            final int j = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(colunm);
            column.setCellValueFactory(tc -> new SimpleObjectProperty<>(tc.getValue().get(j)));
            tableView.getColumns().add(column);
            i++;
        }


        tableView.setOnSort(event ->
        {
            System.out.println("Sort");
        });
        count.setText(Integer.toString(data.size()));
        SortedList<ObservableList<String>> sortedList = data.sorted();
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

    }

}
