package ch.gaps.slasher.views.popupMenu;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PopupMenu class is Both the Root and Controller of the PopupMenu.fxml document
 */
public class PopupMenu extends HBox {
    @FXML
    private ScrollPane entriesPane;
    @FXML
    private VBox entriesVbox;
    @FXML
    private ScrollPane descriptionPane;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ListView<Label> entriesListView;

    /**
     * entryDescription is a {@link Map} which stores the description of every entry of the {@link PopupMenu}
     */
    private Map<String, String> entryDescription;

    /**
     * entries {@link SortedSet} names of all the items of the {@link PopupMenu}
     */
    private SortedSet<String> entries;

    /**
     * Constructor
     */
    public PopupMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupMenu.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
        }

        entryDescription = new HashMap<>();
        entries = new TreeSet<>(String::compareToIgnoreCase);
        setVisible(false);

        visibleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.booleanValue() && !isEmpty()) {
                entriesListView.getSelectionModel().select(0);
                entriesListView.getFocusModel().focus(0);
                entriesListView.requestFocus();
            }
        });

        entriesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!isEmpty()) {
                descriptionTextArea.setText(entryDescription.get(newValue.getText()));
            }
        });

        entriesListView.setOnKeyPressed(event -> {
            if (!isEmpty()) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.ENTER)) {
                    final Label selectedItem = entriesListView.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        itemChosen();
                    }
                } else if (keyCode.equals(KeyCode.ESCAPE)) {
                    if (isVisible()) {
                        setVisible(false);
                    }
                } else {
                    if (!keyCode.equals(KeyCode.UP) && !keyCode.equals(KeyCode.DOWN)) {
                        fireEvent(new EventTransmittingEvent(this, entriesListView, event));
                        setVisible(false);
                    }
                }
            }
        });
    }

    /**
     * Fills up the {@link PopupMenu} with the items
     * @param word the word to complete
     */
    public void wordCompletion(String word) {
        if (entriesListView.getItems() != null) {
            entriesListView.getItems().clear();
        }
        LinkedList<String> searchResult = new LinkedList<>();
        searchResult.addAll(entries.subSet(word, word + Character.MAX_VALUE));

        for (int i = 0; i < searchResult.size(); i++) {
            final String result = searchResult.get(i);
            Label label = new Label(result);
            entriesListView.getItems().add(label);
        }
    }

    /**
     * Fires the {@link ValidatingEvent} and closes the {@link PopupMenu}
     */
    private void itemChosen() {
        fireEvent(new ValidatingEvent(this, entriesListView, entriesListView.getSelectionModel().getSelectedItem().getText()));
        setVisible(false);
    }

    /**
     * Adds the entries to the {@link PopupMenu} dictionary
     * @param entries the items' names to add
     * @param description the description for all the entries
     */
    public void addEntries(List<String> entries, String description) {
        this.entries.addAll(entries);
        entries.forEach(s -> entryDescription.put(s, s + " : " + description));
    }

    /**
     * Indicates if the {@link PopupMenu} is empty
     * @return true if there is no entries in the {@link PopupMenu} dictionary
     */
    public boolean isEmpty() {
        return entriesListView.getItems().isEmpty();
    }

    /**
     * {@link Event} subclass that is fired once the user has selected an item from the {@link PopupMenu}
     */
    public static class ValidatingEvent extends Event {

        /**
         * Name of the selected item
         */
        private String result;

        public static final EventType<ValidatingEvent> ITEM_CHOSEN =
                new EventType<>(Event.ANY, "ITEM_CHOSEN");

        ValidatingEvent(Object source, EventTarget target, String result) {
            super(source, target, ITEM_CHOSEN);
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }

    /**
     * {@link Event} subclass which is fired once an {@link Event} received by the {@link PopupMenu}
     * should be transmitted and handled outside
     */
    public static class EventTransmittingEvent extends Event {

        /**
         * {@link Event} to transmit
         */
        private Event event;

        public  static final EventType<EventTransmittingEvent> TRANSMIT_EVENT =
                new EventType<>(Event.ANY, "TRANSMIT_EVENT");

        EventTransmittingEvent(Object source, EventTarget target, Event event) {
            super(source, target, TRANSMIT_EVENT);
            this.event = event;
        }

        public Event getEvent() {
            return event;
        }

    }
}
