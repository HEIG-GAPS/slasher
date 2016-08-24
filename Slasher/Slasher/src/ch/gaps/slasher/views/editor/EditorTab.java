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
import javafx.scene.Node;
import javafx.scene.control.Tab;

/**
 * @author j.leroy
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
