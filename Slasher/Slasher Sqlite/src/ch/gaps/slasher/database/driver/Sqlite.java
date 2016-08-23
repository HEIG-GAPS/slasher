/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.gaps.slasher.database.driver;

import ch.gaps.slasher.database.driver.database.*;

import java.sql.*;
import java.util.LinkedList;

/**
 *
 * @author leroy
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
  public void connect(String host, String username, String password, String database) {
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }


    try {
      connection = DriverManager.getConnection("jdbc:sqlite:" + host);

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void test() {
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");

      while(rs.next())
      {
        // read the result set
        System.out.println("name = " + rs.getString("name"));
      }

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
  public View[] getViews()
  {
    return new View[0];
  }

  @Override
  public Trigger[] getTriggers()
  {
    return new Trigger[0];
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
  public boolean hasSchema() {
    return false;
  }


}
