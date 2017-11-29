/*
 * The MIT License Copyright 2017 HEIG-VD // SIPA. Permission is hereby granted,
 * free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions: The above copyright notice and this
 * permission notice shall be included in all copies or substantial portions of
 * the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
package ch.gaps.slasher;

import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.tool.Tool;
import ch.gaps.slasher.views.main.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Jérôme Varani, Julien Leroy
 */
public class Slasher extends Application {

  private static final ResourceBundle bundle = ResourceBundle
                                                   .getBundle("Bundle", new Locale("en", "EN"));
  private Slasher instance;

  /** Creates new form Slasher */
  public Slasher() {
    loadDrivers();
    loadTools();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    launch(args);
  }

  public static ResourceBundle getBundle() {
    return bundle;
  }

  public Slasher instance() {
    if (instance == null)
      instance = new Slasher();
    return instance;
  }

  private void loadDrivers() {
    LinkedList<Driver> drivers = DriverService.instance().getAll();
    for (Driver d : DriverService.instance().getAll())
      System.out.println(d);
  }

  private void loadTools() {
    for (Tool t : ToolService.instance().getAll())
      System.out.println(t.name());
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Slasher");
    Parent root = FXMLLoader.load(MainController.class.getResource("MainView.fxml"), bundle);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  @Override
  public void stop() {
    MainController.getInstance().saveState();
  }

}
