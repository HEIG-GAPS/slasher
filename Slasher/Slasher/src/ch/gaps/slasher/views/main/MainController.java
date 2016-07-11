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
package ch.gaps.slasher.views.main;

import ch.gaps.slasher.Slasher;
import ch.gaps.slasher.views.editor.EditorController;
import ch.gaps.slasher.views.openDB.OpenDBController;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author leroy
 */
public class MainController {
    
    
    @FXML private MenuBar menu;
    @FXML private BorderPane borderPane;
    @FXML private TabPane tabPane;
    
    @FXML
    private void initialize(){
        menu.setUseSystemMenuBar(true);
        
        
    }
    
    @FXML
    private void close(){
        Platform.exit();
    }
    
    @FXML
    private void newEditorTab() throws IOException{
        FXMLLoader loader = new FXMLLoader(EditorController.class.getResource("EditorView.fxml"));
        Pane newPane = loader.load();
        Tab newTab = new Tab("Editor", newPane);
        tabPane.getTabs().add(newTab);
      
    }
    
    @FXML
    private void connectDB() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(OpenDBController.class.getResource("OpenDBView.fxml"));
        stage.setTitle("Open a database");
        Pane pane = loader.load();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(pane));
        stage.showAndWait();
        
        
    }
}
