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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.text.BreakIterator;
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
  private Executor executor;

  // text completion popup menu
  private HBox popupHbox;

  Map<String, String> entryDescription;

  private SortedSet<String> entries;

  private ScrollPane entriesPane;
  private ScrollPane descriptionPane;
  private TextArea descriptionArea;
  private ListView<Label> codeCompletionListView;

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
    executor = Executors.newSingleThreadExecutor();
    request.setParagraphGraphicFactory(LineNumberFactory.get(request));

//    request.multiPlainChanges()
//            .successionEnds(Duration.ofMillis(500))
//            .supplyTask(this::computeHighlightingAsync)
//            .awaitLatest(request.multiPlainChanges())
//            .filterMap(t -> {
//              if (t.isSuccess()) {
//                return Optional.of(t.get());
//              } else {
//                t.getFailure().printStackTrace();
//                return Optional.empty();
//              }
//            })
//            .subscribe(this::applyHighlighting);

    // text completion popup
    entries = new TreeSet<>(String::compareToIgnoreCase);
    entryDescription = new HashMap<>();

    loader = new FXMLLoader(getClass().getResource("PopupPane.fxml"));
    try {
      popupHbox = loader.load();
    } catch (IOException e) {
      Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, e.getMessage());
    }
    textPane.getChildren().add(popupHbox);
    popupHbox.setVisible(false);

    entriesPane = (ScrollPane) findChildById(popupHbox, "entriesPane");
    descriptionPane = (ScrollPane) findChildById(popupHbox, "descriptionPane");
    descriptionArea = (TextArea) descriptionPane.getContent();

    request.textProperty().addListener((observable, oldValue, newValue) -> {
      String lastWord = getLastWord();
      if (lastWord.length() == 0) {
        popupHbox.setVisible(false);
      } else {
        LinkedList<String> searchResult = new LinkedList<>();
        searchResult.addAll(entries.subSet(lastWord, lastWord + Character.MAX_VALUE));
        if (!searchResult.isEmpty()) {
          populatePopup(searchResult);
          if (!popupHbox.isVisible() && request.getCaretBounds().isPresent()) {
            Bounds localBounds = request.screenToLocal(request.getCaretBounds().get());
            double popupX = localBounds.getMaxX();
            double popupY = localBounds.getMaxY();
            popupHbox.setLayoutX(popupX);
            popupHbox.setLayoutY(popupY + fontSize);
            popupHbox.setVisible(true);
            descriptionPane.setVisible(false);
          }
        } else {
          popupHbox.setVisible(false);
        }
      }
    });

    // focus management
    // if the user clicks the CodeArea, popup menu disapears
    request.setOnMouseClicked(event -> popupHbox.setVisible(false));

    // to select an item from the popup menu, user has to press the down arrow button
    request.setOnKeyPressed(event -> {
      KeyCode keyCode = event.getCode();
      if (popupHbox.isVisible() && keyCode.equals(KeyCode.DOWN)) {
        codeCompletionListView.requestFocus();
      } else {
        request.requestFocus();
      }
    });
  }

  // TODO: does not work as expected
//  private enum Direction {
//    DOWN, UP;
//  }
//
//
//  /**
//   * This function makes the rotation over the items from the popup menu.
//   * For example, if the last item from the menu is selected and the user presses
//   * down arrow keyboard key, the first item is selected
//   * @param direction the direction of an arrow keyboard key
//   */
//  private void selectNextItem(Direction direction) {
//    if (codeCompletionListView.getItems().isEmpty()) {
//      return;
//    }
//    int selectionIndex = codeCompletionListView.getSelectionModel().getSelectedIndex();
//    switch (direction) {
//      case UP:
//        if (selectionIndex == 0) {
//          codeCompletionListView.getSelectionModel().select(codeCompletionListView.getItems().size()-1);
//          System.out.println("next selection : " + codeCompletionListView.getItems().get(codeCompletionListView.getItems().size()-1).getText());
//        }
//        break;
//      case DOWN:
//        if (selectionIndex == codeCompletionListView.getItems().size()-1) {
//          codeCompletionListView.getSelectionModel().select(0);
//          System.out.println("next selection : " + codeCompletionListView.getItems().get(0).getText());
//        }
//        break;
//      default:
//        Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, "Unknown arrow direction");
//    }
//  }

  private void populatePopup(LinkedList<String> searchResult) {
    codeCompletionListView = new ListView<>();
    for (int i = 0; i < searchResult.size(); i++) {
      final String result = searchResult.get(i);
      Label label = new Label(result);
      codeCompletionListView.getItems().add(label);
    }

    if (!codeCompletionListView.getItems().isEmpty() && codeCompletionListView.getSelectionModel().getSelectedItem() == null) {
      codeCompletionListView.getSelectionModel().select(0);
      codeCompletionListView.getFocusModel().focus(0);
      codeCompletionListView.requestFocus();
    }
    codeCompletionListView.setOnMouseClicked(event -> {
      if (codeCompletionListView.getSelectionModel().getSelectedItem() == null) {
        return;
      }
      descriptionArea.setText(entryDescription.get(codeCompletionListView.getSelectionModel().getSelectedItem().getText()));
      descriptionPane.setVisible(true);

      if(event.getButton().equals(MouseButton.PRIMARY)){
        if(event.getClickCount() == 2){
          itemFromCompletionPopupSelected(codeCompletionListView.getSelectionModel().getSelectedItem());
        }
      }
    });

    codeCompletionListView.setOnKeyPressed(event -> {
      KeyCode keyCode = event.getCode();
      if (keyCode.equals(KeyCode.ENTER)) {
        final Label selectedItem = codeCompletionListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
          itemFromCompletionPopupSelected(codeCompletionListView.getSelectionModel().getSelectedItem());
        }
      } else if (keyCode.equals(keyCode.ESCAPE)) {
        if (popupHbox.isVisible()) {
          popupHbox.setVisible(false);
        }
          request.requestFocus();
      } else {
          if (!keyCode.equals(KeyCode.UP) && !keyCode.equals(KeyCode.DOWN)) {
              popupHbox.setVisible(false);
              request.fireEvent(event);
              request.requestFocus();
          }
      }
    });
    entriesPane.setContent(codeCompletionListView);
  }

  /**
   * Replaces the last word of the {@link CodeArea} with the item
   * from the completion popup.
   * Used while double click/ENTER button pressed.
   * Note: if nothing is selected, nothing happens
   * @param selectedItem the item from the viewList selected by the user
   */
  private void itemFromCompletionPopupSelected(Label selectedItem) {
    if (selectedItem != null) {
      replaceLastWord(selectedItem.getText());
      popupHbox.setVisible(false);
      request.requestFocus();
    }
  }

  private void replaceLastWord(String word) {
    String lastWord = getLastWord();
    if (!lastWord.isEmpty()) {
      request.replaceText(request.getText().substring(0, request.getText().length() - lastWord.length()) + word);
    }
  }

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

  private Node findChildById(Parent parent, String id) {
    for (Node child : parent.getChildrenUnmodifiable()) {
      if (child.getId().equals(id)) {
        return child;
      }
    }
    return null;
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
      entries.addAll(database.getHighliter().getKeywords());
    } catch (URISyntaxException|IOException e) {
        Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, e.getMessage());
    }
    entries.forEach(s -> entryDescription.put(s, s + ": keyword"));
  }

  public String getContent() {
    return request.getText();
  }

  public void setContent(String content) {
    request.replaceText(content);
  }

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

    // highlighting for the Corrector (syntax errors underlined)
    List<SyntaxError> syntaxErrors = database.getCorrector().check(text);
    BreakIterator wb = BreakIterator.getWordInstance();
    wb.setText(text);
    lastKwEnd = 0;
    if (syntaxErrors != null) {
      for (SyntaxError e : syntaxErrors) {
        int[] lineIndexes = Utils.getLineIndexes(text, e.getLine());
        if (lineIndexes[0] != lineIndexes[1]-1) {
          System.out.println("line index : "+lineIndexes[0]);
          System.out.println("last index : "+lineIndexes[1]);
          spansBuilder.add(Collections.emptyList(), lineIndexes[0]-lastKwEnd);
          spansBuilder.add(Collections.singleton("underlined"), lineIndexes[1]-1 - lineIndexes[0]);
          lastKwEnd = lineIndexes[1]-1;
        }
      }
    }

    return spansBuilder.create();
  }
}
