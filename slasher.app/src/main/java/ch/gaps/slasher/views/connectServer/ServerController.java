package ch.gaps.slasher.views.connectServer;

import ch.gaps.slasher.database.driver.Driver;
import ch.gaps.slasher.database.driver.database.Server;
import javafx.beans.property.BooleanProperty;

import java.sql.SQLException;

/**
 * Created by leroy on 12.07.2016.
 */
interface ServerController {

  /**
   * @return the server to add to the main controller server list
   */
  Server getServer();

  /**
   * @return the validation property
   */
  BooleanProperty getFieldValidation();

  /**
   * @param driver driver to set
   */
  void setDriver(Driver driver);

  /**
   * Connect to the server or databases
   *
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  void connect() throws SQLException, ClassNotFoundException;

  /**
   * @return true if it's a new server
   * (new in the way no other database from this server is opened in the application)
   */
  boolean newServer();
}
