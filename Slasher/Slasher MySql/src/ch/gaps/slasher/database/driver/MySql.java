/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.gaps.slasher.database.driver;

import ch.gaps.slasher.database.driver.database.Table;

import java.sql.SQLException;

/**
 *
 * @author leory
 */
public class MySql implements Driver {
  
  @Override
  public String id() { return MySql.class.getName().toLowerCase(); }
  
  @Override
  public String toString() { return "MySql"; }

  @Override
  public String type() {
    return "server";
  }

  @Override
  public void connect(String... connectionInfo){

  }

  @Override
  public void test() {

  }

  @Override
  public Table[] tables() {
    return null;
  }

  @Override
  public boolean hasShema() {
    return true;
  }

}
