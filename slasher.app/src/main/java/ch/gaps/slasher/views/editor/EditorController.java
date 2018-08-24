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
import ch.gaps.slasher.corrector.SyntaxError;
import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.utils.Utils;
import ch.gaps.slasher.views.dataTableView.DataTableController;
import ch.gaps.slasher.views.main.MainController;
import ch.gaps.slasher.views.popupMenu.PopupMenu;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  @FXML
  private AnchorPane textPane;

  private Database database;

  private DataTableController dataTableController;

  // used for the asynchronous code highlighting
  private Executor highlightingExecutor;

  // text completion popup menu
  private PopupMenu popupMenu;

  private int fontSize = 14;

  @FXML
  private void initialize() {
    request.setStyle("-fx-font-size: " + fontSize);
    progress.setVisible(false);
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
      Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, e.getMessage());
    }

    // text highlighting
    highlightingExecutor = Executors.newSingleThreadExecutor();
    request.setParagraphGraphicFactory(LineNumberFactory.get(request));

    request.multiPlainChanges()
            .successionEnds(Duration.ofMillis(500))
            .supplyTask(this::computeStylesAsync)
            .awaitLatest(request.multiPlainChanges())
            .filterMap(t -> {
              if (t.isSuccess()) {
                return Optional.of(t.get());
              } else {
                t.getFailure().printStackTrace();
                return Optional.empty();
              }
            })
            .subscribe(this::applyStyles);

    // code completion popup menu
    popupMenu = new PopupMenu();
    textPane.getChildren().add(popupMenu);
    request.textProperty().addListener((observable, oldValue, newValue) -> {
      String lastWord = getLastWord();
      if (lastWord.length() == 0) {
        popupMenu.setVisible(false);
      } else {
        popupMenu.wordCompletion(lastWord);
        if (!popupMenu.isEmpty()) {
          if (!popupMenu.isVisible() && request.getCaretBounds().isPresent()) {
            Bounds localBounds = request.screenToLocal(request.getCaretBounds().get());
            double popupX = localBounds.getMaxX();
            double popupY = localBounds.getMaxY();
            popupMenu.setLayoutX(popupX);
            popupMenu.setLayoutY(popupY + fontSize);
            popupMenu.setVisible(true);
          }
        } else {
          popupMenu.setVisible(false);
        }
      }
    });

    // focus management
    // if the user clicks the CodeArea, popup menu disappears
    request.setOnMouseClicked(event -> popupMenu.setVisible(false));

    // replace the word by the item selected from the popup menu if the user validates it
    popupMenu.addEventHandler(PopupMenu.ValidatingEvent.ITEM_CHOSEN, event ->
            replaceLastWord(event.getResult()));

    // if the user presses the key that concerns the code area while the popup menu is in focus,
    // the focus is set to the code area and the event associated with the key is transmitted to the code area
    popupMenu.addEventHandler(PopupMenu.EventTransmittingEvent.TRANSMIT_EVENT, event ->
            request.fireEvent(event.getEvent()));

    // when the popup menu disappears, the focus is set to the code area
    popupMenu.visibleProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        request.requestFocus();
      }
    });
  }

  /**
   * Replaces the last word in the {@link CodeArea} with a word passed as a parameter
   * @param word the word to replace
   */
  private void replaceLastWord(String word) {
    String lastWord = getLastWord();
    if (!lastWord.isEmpty()) {
      request.replaceText(request.getText().substring(0, request.getText().length() - lastWord.length()) + word);
    }
  }

   /**
    * Retrieves the last word written in the {@link CodeArea}
    * @return the last word written in the text area
    */
  private String getLastWord() {
    if (request.getText().isEmpty()) {
      return "";
    }
    String[] words = request.getText().split("[^\\w]+");
    if (words.length == 0) {
      return "";
    }
    char lastChar = request.getText().charAt(request.getText().length()-1);
    if (Character.isLetterOrDigit(lastChar) || lastChar == '_')
      return words[words.length-1];
    return "";
  }

  /**
   * Applies the highlighting to the {@link CodeArea}
   * @param highlighting the {@link StyleSpans} object containing the highlighting to apply
   */
  private void applyStyles(StyleSpans<Collection<String>> highlighting) {
    request.setStyleSpans(0, highlighting);
  }

  /**
   * Executes the highlighting in the separate {@link Executor} to make it asynchronous
   * @return the {@link Task} executed in the {@link Executor}
   */
  private Task<StyleSpans<Collection<String>>> computeStylesAsync() {
    String text = request.getText();
    Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
      @Override
      protected StyleSpans<Collection<String>> call() throws Exception {
        return computeStyles(text);
      }
    };
    highlightingExecutor.execute(task);
    return task;
  }

  @FXML
  private void execute() {
    MainController.getInstance().saveState();

    dataTableController.clear();
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() {

        try(ResultSet rs = database.executeQuery(request.getText())) {

          int columnCount = rs.getMetaData().getColumnCount();
          String[] columnName = new String[columnCount];

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

        } catch (Exception e) {
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
    // data structures for text completion popup
      try {
          popupMenu.addEntries(database.getHighliter().getKeywords(), "keyword");
      } catch (URISyntaxException|IOException e) {
          Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
      }
  }

  public String getContent() {
    return request.getText();
  }

  public void setContent(String content) {
    request.replaceText(content);
  }

  private StyleSpans<Collection<String>> computeStyles(String text) {
    return computeSpellCheck(text).overlay(computeHighlighting(text), (bottomSpan, list) -> {
      List<String> l = new ArrayList<>(bottomSpan.size() + list.size());
      l.addAll(bottomSpan);
      l.addAll(list);
      return l;
    });
  }

  /**
   * Computes the {@link StyleSpans} for highlighting of the text
   * accordong to {@link ch.gaps.slasher.highliter.Highlighter}'s matcher group names
   * @param text the text to which we want to apply the highlighting
   * @return {@link StyleSpans} for the highlighting of the text
   */
  private StyleSpans<Collection<String>> computeHighlighting(String text) {
    // highlighting of the Highlighter (keywords, string literals, etc.)
    List<String> groupNames = database.getHighliter().getMatcherGroupNames();
    Matcher matcher = database.getHighliter().getPattern().matcher(text);
    int lastKwEnd = 0;
    StyleSpansBuilder<Collection<String>> spansBuilder
            = new StyleSpansBuilder<>();
    while (matcher.find()) {
      /**
       * Convention : CSS style class is the matcher group name in lower case
       */
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

  /**
   * Computes the {@link StyleSpans} for the syntax errors highlighting
   * @param text the text to check for syntax errors
   * @return {@link StyleSpans} for the syntax errors highlighting
   */
  private StyleSpans<Collection<String>> computeSpellCheck(String text) {
    // highlighting for the Corrector (syntax errors underlined)
    StyleSpansBuilder<Collection<String>> spansBuilder
            = new StyleSpansBuilder<>();
    List<SyntaxError> syntaxErrors = database.getCorrector().check(text);
    int lastErrEnds = 0;
    for (SyntaxError e : syntaxErrors) {
      int[] lineIndexes = Utils.getLineIndexes(text, e.getLine());
      if (lineIndexes[0] != lineIndexes[1]-1) {
        spansBuilder.add(Collections.emptyList(), lineIndexes[0]-lastErrEnds);
        spansBuilder.add(Collections.singleton("underlined"), lineIndexes[1]-1 - lineIndexes[0]);
        lastErrEnds = lineIndexes[1]-1;
      }
    }
    spansBuilder.add(Collections.emptyList(), text.length() - lastErrEnds);
    return spansBuilder.create();
  }
}
