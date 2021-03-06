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

import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.Server;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;

/**
 * This class is used to connect database in files, like Sqlite
 *
 * @author j.leroy
 */
public class FileServerController implements ServerController {
  private final String[] connectionData = new String[1];
  private final BooleanProperty filedOk = new SimpleBooleanProperty(false);
  @FXML
  private Pane mainPane;
  @FXML
  private TextField path;
  private File file;
  private Driver driver;
  private Server server;
  private Database mainDatabase;

  @FXML
  private void initialize() {
    path.textProperty().addListener((observable, oldValue, newValue) ->
    {
      if (newValue == null || newValue.isEmpty())
        filedOk.set(false);
      else
        filedOk.set(true);
    });
  }

  /**
   * Called by the browse button to choose a file for the file database
   */
  @FXML
  private void browse() {
    FileChooser fileChooser = new FileChooser();
    file = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
    if (file != null) {
      path.setText(file.getPath());
      connectionData[0] = file.getPath();
    }
  }

  @Override
  public Server getServer() {
    server = new Server(driver, path.getText(), 0, file.getName());
    mainDatabase = new Database(driver, "main", null, server, "main");

    return server;
  }

  @Override
  public BooleanProperty getFieldValidation() {
    return filedOk;
  }

  @Override
  public void setDriver(Driver driver) {
    this.driver = driver;
  }

  @Override
  public void connect() throws SQLException, ClassNotFoundException {
    mainDatabase.connect(null);
    server.addDatabase(mainDatabase);
  }

  @Override
  public boolean newServer() {
    return true;
  }

}
