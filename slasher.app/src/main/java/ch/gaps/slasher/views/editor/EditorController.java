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

import ch.gaps.slasher.Slasher;
import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.views.dataTableView.DataTableController;
import ch.gaps.slasher.views.main.MainController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

/**
 * @author j.leroy
 */
public class EditorController {
  @FXML
  private CodeArea request;
  @FXML
  private AnchorPane tableViewPane;
  @FXML
  private Button execute;
  @FXML
  private ProgressIndicator progress;

  private Database database;

  private DataTableController dataTableController;

  // used for the asynchronous code highlighting
  private Executor executor;


  @FXML
  private void initialize() {
    progress.setVisible(false);
    String path = DataTableController.class.getResource("DataTableView.fxml").toExternalForm();
    FXMLLoader loader = new FXMLLoader(DataTableController.class.getResource("DataTableView.fxml"), Slasher.getBundle());
    try {
      Pane pane = loader.load();
      AnchorPane.setTopAnchor(pane, 10.);
      AnchorPane.setBottomAnchor(pane, 10.);
      AnchorPane.setLeftAnchor(pane, 10.);
      AnchorPane.setRightAnchor(pane, 10.);
      dataTableController = loader.getController();

      tableViewPane.getChildren().add(pane);
    } catch (IOException e) {
      e.printStackTrace();
    }

    executor = Executors.newSingleThreadExecutor();
    request.setParagraphGraphicFactory(LineNumberFactory.get(request));

    Subscription cleanupWhenDone = request.multiPlainChanges()
            .successionEnds(Duration.ofMillis(100))
            .supplyTask(this::computeHighlightingAsync)
            .awaitLatest(request.multiPlainChanges())
            .filterMap(t -> {
              if (t.isSuccess()) {
                return Optional.of(t.get());
              } else {
                t.getFailure().printStackTrace();
                return Optional.empty();
              }
            })
            .subscribe(this::applyHighlighting);
  }

  private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
    request.setStyleSpans(0, highlighting);
  }

  private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
    String text = request.getText();
    Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
      @Override
      protected StyleSpans<Collection<String>> call() throws Exception {
        return computeHighlighting(text);
      }
    };
    executor.execute(task);
    return task;
  }

  @FXML
  private void execute() {
    MainController.getInstance().saveState();

    dataTableController.clear();
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() {
        ResultSet rs = null;
        try {
          rs = database.executeQuery(request.getText());

          int columnCount = rs.getMetaData().getColumnCount();
          String columnName[] = new String[columnCount];

          for (int i = 0; i < columnCount; ++i) {
            columnName[i] = rs.getMetaData().getColumnName(i + 1);
          }

          ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

          while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();

            for (int i = 1; i <= columnCount; ++i) {
              row.add(rs.getString(i));
            }

            data.add(row);
          }


          Platform.runLater(() -> dataTableController.display(data, columnName));


        } catch (SQLException e) {
          Platform.runLater(() -> MainController.getInstance().addToUserCommunication(e.getMessage()));
        }
        return null;
      }
    };

    progress.visibleProperty().bind(task.runningProperty());


    Thread th = new Thread(task);
    th.start();

  }

  public void setDatabase(Database database) {
    this.database = database;
    execute.disableProperty().bind(database.enabledProperty().not());
    request.getStylesheets().add(EditorController.class.getResource("highlighting.css").toExternalForm());
  }

  public String getContent() {
    return request.getText();
  }

  public void setContent(String content) {
    request.replaceText(content);
  }

    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        List<String> groupNames = database.getHighliter().getMatcherGroupNames();
        Matcher matcher = database.getHighliter().getPattern().matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = null;
            for (String gn : groupNames) {
                if (matcher.group(gn) != null) {
                    styleClass = gn.toLowerCase();
                    break;
                }
            }
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
