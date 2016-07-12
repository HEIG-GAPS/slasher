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

import ch.gaps.slasher.views.main.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author leroy
 */
public class OpenDBController {
    @FXML private ChoiceBox<Driver> driversListCB;
    @FXML private Pane dbSelectionPane;
    private DBController dbController;
    private MainController mainController;

    private Driver driver;

    @FXML
    private void initialize(){
        driversListCB.getItems().addAll(DriverService.instance().getAll());
        driversListCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            dbSelectionPane.getChildren().clear();
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

        driver.connect(tmp);
        mainController.setDriver(driver);
        ((Stage)dbSelectionPane.getScene().getWindow()).close();
    }

    public void setController(MainController mainController){
        this.mainController = mainController;
    }
    
    
}
