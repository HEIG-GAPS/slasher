/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.gaps.slasher.database.driver;

import ch.gaps.slasher.database.driver.database.Table;

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
  public String type() {
    return "file";
  }



  @Override
  public void connect(String ... connectionInfo) {
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }


    try {
      connection = DriverManager.getConnection("jdbc:sqlite:" + connectionInfo[0]);
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("select * from person");

      while(rs.next())
      {
        // read the result set
        System.out.println("name = " + rs.getString("name"));
        System.out.println("id = " + rs.getInt("id"));
      }

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

  public Table[] tables(){
    LinkedList<Table> tables = new LinkedList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");

      while(rs.next())
      {
        tables.add(new Table(rs.getString("name")));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return tables.toArray(new Table[tables.size()]);
  }


}
