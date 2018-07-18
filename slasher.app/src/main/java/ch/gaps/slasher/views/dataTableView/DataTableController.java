package ch.gaps.slasher.views.dataTableView;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.ResultSet;

/**
 * @author j.leroy
 */
public class DataTableController {
  @FXML
  private TableView<ObservableList<String>> tableView;
  @FXML
  private Label count;
  @FXML
  private Label displayZone;

  private ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();


  @FXML
  private void sort() {
    new Thread(() ->
                   tableView.getColumns().get(0).setSortType(TableColumn.SortType.ASCENDING)).start();

  }


  public void display(ResultSet resultSet) {

    ObservableList<ObservableList<String>> data = tableView.getItems();


    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {

        int columnCount = resultSet.getMetaData().getColumnCount();
        String columnName[] = new String[columnCount];

        for (int i = 0; i < columnCount; ++i) {
          columnName[i] = resultSet.getMetaData().getColumnName(i + 1);
        }

        int j = 0;
        for (String colunm : columnName) {
          final int k = j;
          TableColumn<ObservableList<String>, String> column = new TableColumn<>(colunm);
          column.setCellValueFactory(tc -> new SimpleObjectProperty<>(tc.getValue().get(k)));
          Platform.runLater(() -> tableView.getColumns().add(column));
          j++;
        }

        //ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        while (resultSet.next()) {
          ObservableList<String> row = FXCollections.observableArrayList();

          for (int i = 1; i <= columnCount; ++i) {
            row.add(resultSet.getString(i));
          }

          data.add(row);
        }

        Platform.runLater(() -> {
          //display(data, columnName);
        });

        return null;
      }
    };


    Thread th = new Thread(task);
    th.start();
  }


  public void display(ObservableList<ObservableList<String>> data, String[] colunmsName) {

    clear();


    int i = 0;
    for (String colunm : colunmsName) {
      final int j = i;
      TableColumn<ObservableList<String>, String> column = new TableColumn<>(colunm);
      column.setCellValueFactory(tc -> new SimpleObjectProperty<>(tc.getValue().get(j)));
      tableView.getColumns().add(column);
      i++;
    }


    tableView.setOnSort(event ->
                            System.out.println("Sort"));
    count.setText(Integer.toString(data.size()));
    SortedList<ObservableList<String>> sortedList = data.sorted();
    sortedList.comparatorProperty().bind(tableView.comparatorProperty());
    tableView.setItems(sortedList);

  }

  public void clear() {
    tableView.getColumns().clear();
    count.setText("0");
  }

}
