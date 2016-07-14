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
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
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

    private BooleanProperty nameOk = new SimpleBooleanProperty(false);
    private BooleanProperty driverOk = new SimpleBooleanProperty(false);
    private BooleanProperty otherDataOk = new SimpleBooleanProperty(false);

    private Driver driver;

    @FXML
    private void initialize(){


       dbName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                nameOk.set(false);
            else
                nameOk.set(true);
        });

        validateButton.disableProperty().bind(nameOk.not().or(driverOk.not()).or(otherDataOk.not()));


        driversListCB.getItems().addAll(DriverService.instance().getAll());
        driversListCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            dbSelectionPane.getChildren().clear();
            driverOk.set(true);
            if (newValue.type().equals("server")){
                try {
                    FXMLLoader loader = new FXMLLoader(OpenDBController.class.getResource("ServerDB.fxml"));
                    dbSelectionPane.getChildren().add(loader.load());
                    dbController = loader.getController();
                    otherDataOk.bind(dbController.getFieldValidation());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (newValue.type().equals("file")){
                try {
                    FXMLLoader loader = new FXMLLoader(OpenDBController.class.getResource("FileDB.fxml"));
                    dbSelectionPane.getChildren().add(loader.load());
                    dbController = loader.getController();
                    otherDataOk.bind(dbController.getFieldValidation());


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
}
