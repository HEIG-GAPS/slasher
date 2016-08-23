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
package ch.gaps.slasher.views.connectServer;

import ch.gaps.slasher.DriverService;
import ch.gaps.slasher.Slasher;
import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.views.main.MainController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author leroy
 */
public class ConnectServerController {
    @FXML private ChoiceBox<Driver> driversListCB;
    @FXML private TextField serverDescription;
    @FXML private Button validateButton;
    @FXML private AnchorPane mainPane;
    @FXML private Label displayLabel;

    private ServerController serverController;
    private MainController mainController;
    private AnchorPane connectionPane;


    private BooleanProperty nameOk = new SimpleBooleanProperty(false);
    private BooleanProperty driverOk = new SimpleBooleanProperty(false);
    private BooleanProperty otherDataOk = new SimpleBooleanProperty(false);
    private Server server;
    private boolean oldServer = false;

    @FXML
    private void initialize(){

       serverDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                nameOk.set(false);
            else
                nameOk.set(true);
        });

        validateButton.disableProperty().bind(driverOk.not().or(otherDataOk.not()));

        driversListCB.getItems().addAll(DriverService.instance().getAll());
        driversListCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mainPane.getChildren().remove(connectionPane);
            driverOk.set(true);
            if (newValue.type() == Driver.ServerType.Server){
                try {
                    displayLabel.setText(Slasher.getBundle().getString("leave.blank.host"));
                    FXMLLoader loader = new FXMLLoader(ConnectServerController.class.getResource("ServerServer.fxml"), Slasher.getBundle());
                    connectionPane = loader.load();
                    AnchorPane.setTopAnchor(connectionPane, 75.0);
                    AnchorPane.setLeftAnchor(connectionPane, 10.0);
                    AnchorPane.setRightAnchor(connectionPane, 10.0);
                    AnchorPane.setBottomAnchor(connectionPane, 10.0);
                    mainPane.getChildren().add(connectionPane);
                    serverController = loader.getController();
                    serverController.setDriver(newValue);
                    otherDataOk.bind(serverController.getFieldValidation());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (newValue.type() == Driver.ServerType.File){
                try {
                    displayLabel.setText(Slasher.getBundle().getString("leave.blank.file"));
                    FXMLLoader loader = new FXMLLoader(ConnectServerController.class.getResource("FileServer.fxml"), Slasher.getBundle());
                    connectionPane = loader.load();
                    AnchorPane.setTopAnchor(connectionPane, 75.0);
                    AnchorPane.setLeftAnchor(connectionPane, 0.0);
                    AnchorPane.setRightAnchor(connectionPane, 0.0);
                    AnchorPane.setBottomAnchor(connectionPane, 0.0);
                    mainPane.getChildren().add(connectionPane);
                    serverController = loader.getController();
                    serverController.setDriver(newValue);
                    otherDataOk.bind(serverController.getFieldValidation());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @FXML
    private void cancel(){
        ((Stage)mainPane.getScene().getWindow()).close();
        oldServer = false;
    }

    @FXML
    private void validate(){
        server = serverController.getServer();
        if (!serverDescription.getText().isEmpty())
            server.setDescription(serverDescription.getText());
        try
        {
            serverController.connect();
        } catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        if (serverController.newServer()){
            mainController.addServer(server);
            oldServer = false;
        }
        else {
            oldServer = true;
        }
        ((Stage)mainPane.getScene().getWindow()).close();
    }

    public void setController(MainController mainController){
        this.mainController = mainController;
    }

}
