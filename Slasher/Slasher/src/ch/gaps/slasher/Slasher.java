package ch.gaps.slasher;

import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.tool.Tool;
import ch.gaps.slasher.views.main.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author jvarani
 */
public class Slasher extends Application{
    private static ResourceBundle bundle = ResourceBundle.getBundle("ch.gaps.slasher.bundle.Bundle", new Locale("en", "EN"));
    private Slasher instance;
    private LinkedList<Driver> drivers;
    private LinkedList<Tool> tools;
 
  /**
   * Creates new form Slasher
   */
  public Slasher() {
    
    loadDrivers();
    loadTools();
  }
  
  public Slasher instance(){
      if (instance == null) instance = new Slasher();
      return instance;
  }

  private void loadDrivers() {
      drivers = DriverService.instance().getAll();
    for (Driver d : DriverService.instance().getAll())
      System.out.println(d);
  }
  
  private void loadTools() {
      tools = ToolService.instance().getAll();
   for (Tool t : ToolService.instance().getAll())
      System.out.println(t.name());
  }  

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    
      launch(args);
    
  }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("ch.gaps.slasher.bundle.Bundle", new Locale("en", "EN"));
        primaryStage.setTitle("Slasher");
        FXMLLoader loader = new FXMLLoader(Slasher.class.getResource("views/main/MainView.fxml"), bundle);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
    
    @Override
    public void stop(){
        MainController.getInstance().saveState();
    }

    public static ResourceBundle getBundle() { return bundle;}

}
