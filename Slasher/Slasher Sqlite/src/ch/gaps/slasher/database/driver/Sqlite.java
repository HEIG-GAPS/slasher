/*
 * The MIT License
 *
 * Copyright 2015 Leroy.
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
package ch.gaps.slasher.database.driver;

import ch.gaps.slasher.database.driver.database.*;

import java.sql.*;
import java.util.LinkedList;

/**
 *
 * @author j.leroy
 */
public class Sqlite implements Driver {

  Connection connection;

  @Override
  public String id() { return Sqlite.class.getName().toLowerCase(); }
  
  @Override
  public String toString() { return "Sqlite"; }

  @Override
  public ServerType type() {
    return ServerType.File;
  }



  @Override
  public void connect(Server server, String username, String password, String database) {
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }


    try {
      connection = DriverManager.getConnection("jdbc:sqlite:" + server.getHost());

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public LinkedList<Table> getTables(Database database, Schema schema){
    LinkedList<Table> tables = new LinkedList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");

      while(rs.next())
      {
        tables.add(new Table(rs.getString("name"), database));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return tables;
  }

  @Override
  public LinkedList<Schema> getSchemas(Database database) {
    return null;
  }

  @Override
  public void close() {
  }

  @Override
  public LinkedList<Database> getDatabases(Server server, String username, String password) {
    return null;
  }

  @Override
  public ResultSet executeQuery(String query) throws SQLException
  {
    return connection.createStatement().executeQuery(query);
  }

  @Override
  public Boolean isConnected()
  {
    return true;
  }

  @Override
  public LinkedList<View> getViews()
  {
    return null;
  }

  @Override
  public LinkedList<Trigger> getTriggers() {
    return null;
  }

  @Override
  public ResultSet getAllData(Database database, Schema schema, Table table)
  {
    try
    {
      return connection.createStatement().executeQuery("SELECT * FROM " + table);
    } catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public int getDefaultPort()
  {
    return 0;
  }

  @Override
  public boolean hasSchema() {
    return false;
  }


}
