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
public class PostgreSQL93 implements Driver {
  
  // <editor-fold desc="Driver Overrides" defaultstate="collapsed">
  @Override
  public String id() { return PostgreSQL93.class.getName().toLowerCase(); }
  
  @Override
  public String name() { return "PostgresQL 9.3.*"; }
  // </editor-fold>
}
