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
package ch.gaps.slasher.views.openDB;

import ch.gaps.slasher.DriverService;
import ch.gaps.slasher.database.driver.Driver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.views.main.MainController;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.StyledEditorKit;

/**
 *
 * @author leroy
 */
public class OpenDBController {
    @FXML private ChoiceBox<Driver> driversListCB;
    @FXML private Pane dbSelectionPane;
    @FXML private TextField dbName;
    @FXML private Button validateButton;
    private DBController dbController;
    private MainController mainController;

    private Driver driver;
    private ObservableValue<Valide> test;
    private Valide valide;

    @FXML
    private void initialize(){
        test = new SimpleObjectProperty<>(new Valide());
        valide  = new Valide();
        dbName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                test.getValue().name.set(false);
            else
                test.getValue().name.set(true);
        });

        test.addListener((observable, oldValue, newValue) -> {
            newValue.driver.get();
        });

        driversListCB.getItems().addAll(DriverService.instance().getAll());
        driversListCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            dbSelectionPane.getChildren().clear();
            valide.driver.set(true);
            if (newValue.type().equals("server")){
                try {
                    FXMLLoader loader = new FXMLLoader(OpenDBController.class.getResource("ServerDB.fxml"));
                    dbSelectionPane.getChildren().add(loader.load());
                    dbController = loader.getController();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (newValue.type().equals("file")){
                try {
                    FXMLLoader loader = new FXMLLoader(OpenDBController.class.getResource("FileDB.fxml"));
                    dbSelectionPane.getChildren().add(loader.load());
                    dbController = loader.getController();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            driver = newValue;

        });

    }

    @FXML
    private void cancel(){
        ((Stage)dbSelectionPane.getScene().getWindow()).close();
    }

    @FXML
    private void validate() throws SQLException, ClassNotFoundException {
       String [] tmp = dbController.getConnectionData();
        Optional<Database> db;

            driver.connect(tmp);
            db = Optional.of(new Database(driver, dbName.getText()));


        if (db.isPresent()){
            mainController.addDatabase(db.get());
        }
        ((Stage)dbSelectionPane.getScene().getWindow()).close();
    }

    public void setController(MainController mainController){
        this.mainController = mainController;
    }

    class Valide{
        BooleanProperty driver = new SimpleBooleanProperty(false);
        BooleanProperty name = new SimpleBooleanProperty(false);
        BooleanProperty otherdata = new SimpleBooleanProperty(true);

        BooleanProperty allOk = new SimpleBooleanProperty(false);

        Valide(){

            validateButton.disableProperty().bind(allOk.not());

            driver.addListener((observable, oldValue, newValue) -> {
                if (newValue && name.get() && otherdata.get())
                    allOk.set(true);
                else
                    allOk.set(false);
            });

            name.addListener((observable, oldValue, newValue) -> {
                if (newValue && driver.get() && otherdata.get())
                    allOk.set(true);
                else
                    allOk.set(false);

            });

            otherdata.addListener((observable, oldValue, newValue) -> {
                if (newValue && driver.get() && name.get())
                    allOk.set(false);
                else
                    allOk.set(false);
            });
        }



    }
    
    
}
