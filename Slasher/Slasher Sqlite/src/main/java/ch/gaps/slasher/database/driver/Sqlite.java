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
package ch.gaps.slasher.database.driver;

import ch.gaps.slasher.database.driver.database.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the driver to connect to a SQLite database.
 * 
 * @author Julien Leroy
 */
public class Sqlite implements Driver {

  // **************************************************************************
  // Attributes
  // **************************************************************************
  /** the {@link Connection} on the database. */
  private Connection connection;

  // **************************************************************************
  // Object Overrides
  // **************************************************************************
  @Override
  public String toString() {
    return "Sqlite";
  }

  // **************************************************************************
  // Driver Overrides - Driver Parameters
  // **************************************************************************
  @Override
  public String id() {
    return Sqlite.class.getName().toLowerCase();
  }

  @Override
  public DataHandlingType type() {
    return DataHandlingType.File;
  }

  @Override
  public int getDefaultPort() {
    return 0;
  }

  // **************************************************************************
  // Driver Overrides - Connection Matters
  // **************************************************************************
  @Override
  public void connect(Server server, String username, String password,
      String database) throws ClassNotFoundException, SQLException {

    Class.forName("org.sqlite.JDBC");
    connection = DriverManager.getConnection("jdbc:sqlite:" + server.getHost());
  }

  @Override
  public void close() {
  }

  @Override
  public Boolean isConnected() {
    return true;
  }

  // **************************************************************************
  // Driver Overrides - Schema Matters
  // **************************************************************************

  @Override
  public LinkedList<Database> getDatabases(Server server, String username,
      String password) {
    return null;
  }

  @Override
  public boolean hasSchema() {
    return false;
  }

  @Override
  public List<Schema> getSchemas(Database database) {
    return null;
  }

  @Override
  public List<Table> getTables(Schema schema) {
    LinkedList<Table> tables = new LinkedList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement
          .executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");

      while (rs.next()) {
        tables.add(new Table(rs.getString("name"), schema.getDatabase()));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return tables;
  }

  @Override
  public List<View> getViews(Schema schema) {
    return null;
  }

  @Override
  public List<Trigger> getTriggers(Schema schema) {
    return null;
  }

  // **************************************************************************
  // Driver Overrides - Queries Matters
  // **************************************************************************

  @Override
  public ResultSet executeQuery(String query) throws SQLException {
    return connection.createStatement().executeQuery(query);
  }

  @Override
  public ResultSet getAllData(Database database, Schema schema, Table table) {
    try {
      return connection.createStatement()
          .executeQuery("SELECT * FROM " + table);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
