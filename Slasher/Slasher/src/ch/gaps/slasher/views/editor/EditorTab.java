package ch.gaps.slasher.views.editor;

import ch.gaps.slasher.database.driver.database.Database;
import javafx.scene.Node;
import javafx.scene.control.Tab;

/**
 * Created by julien on 17.08.16.
 */
public class EditorTab extends Tab
{
    private Database database;
    private EditorController editorController;

    public EditorTab(String name,  Node node, Database database, EditorController editorController){
        super(name, node);
        this.database = database;
        this.editorController = editorController;
    }

    public Database getDatabase(){
        return database;
    }

    public String getModuleName(){
        return "Editor";
    }

    public void setController(EditorController editorController){
        this.editorController = editorController;
    }

    public EditorController getEditorController(){
        return editorController;
    }
}
