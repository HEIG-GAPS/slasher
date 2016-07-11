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
public class Sqlite implements Driver {
  
  // <editor-fold desc="Driver Overrides" defaultstate="collapsed">
  @Override
  public String id() { return Sqlite.class.getName().toLowerCase(); }
  
  @Override
  public String name() { return "Sqlite"; }
  // </editor-fold>
}
