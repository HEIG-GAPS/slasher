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
package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author j.leroy
 */
public class Server implements DBObject {

  private String description;
  private Driver driver;
  private String host;
  private int port;
  private LinkedList<Database> openedDatabase = new LinkedList<>();

  public Server(Driver driver, String host, int port, String descritpion) {
    this.host = host;
    this.port = port;
    this.driver = driver;
    if (descritpion == null) {
      this.description = host;
    } else this.description = descritpion;
  }

  /**
   * @param username
   * @param password
   *
   * @return All the databases accessible on the server for this user
   *
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  public List<Database> getAllDatabases(String username, String password) throws SQLException, ClassNotFoundException {
    return driver.getDatabases(this, username, password);
  }

  /**
   * @return the list of the registered (connected) database
   */
  public LinkedList<Database> getDatabases() {
    return openedDatabase;
  }

  /**
   * Add a database to the registered ones
   *
   * @param database
   *
   * @return
   */
  public boolean addDatabase(Database database) {

    if (openedDatabase.stream()
                      .filter(database1 -> database1.getDescritpion().equals(database.getDescritpion()))
                      .count() < 0) {
      return false;
    } else {
      openedDatabase.add(database);
      return true;
    }
  }

  /**
   * Remove from the registered ones
   *
   * @param database
   */
  public void removeDatabase(Database database) {
    openedDatabase.remove(database);
  }

  /**
   * @return server description
   */
  public String toString() {
    return description;
  }

  /**
   * @return server host name
   */
  public String getHost() {
    return host;
  }

  /**
   * @return the server description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set a new description for the server
   *
   * @param newDescription
   */
  public void setDescription(String newDescription) {
    description = newDescription;
  }

  /**
   * Disconnect the server "nicely"
   *
   * @throws SQLException
   */
  public void disconnect() throws SQLException {
    driver.close();
  }

  /**
   * Connect all the registered databases
   *
   * @param password password for the databases
   *
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  public void connectRegisteredDatabases(String password) throws SQLException, ClassNotFoundException {
    for (Database database : openedDatabase) {
      database.connect(password);
    }
  }

  /**
   * Close all the registered database
   *
   * @throws SQLException
   */
  public void closeRegisteredDatabases() throws SQLException {
    for (Database database : openedDatabase) {
      database.close();
    }
  }

  /**
   * @return driver server type
   */
  public Driver.DataHandlingType type() {
    return driver.type();
  }

  /**
   * @return the driver name
   */
  public String getDiverName() {
    return driver.toString();
  }

  /**
   * @return the port number
   */
  public int getPort() {
    return port;
  }

}
