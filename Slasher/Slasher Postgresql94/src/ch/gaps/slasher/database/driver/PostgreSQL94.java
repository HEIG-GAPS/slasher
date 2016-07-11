/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.gaps.slasher.database.driver;

/**
 *
 * @author jvarani
 */
public class PostgreSQL94 implements Driver {
  
  // <editor-fold desc="Driver Overrides" defaultstate="collapsed">
  @Override
  public String id() { return PostgreSQL94.class.getName().toLowerCase(); }
  
  @Override
  public String name() { return "PostgresQL 9.4.*"; }
  // </editor-fold>
}
