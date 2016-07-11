/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.gaps.slasher;

import ch.gaps.slasher.database.driver.Driver;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvarani
 */
public final class DriverService {
  private static DriverService service;
  private final ServiceLoader<Driver> loader;
  
  private DriverService() {
    loader = ServiceLoader.load(Driver.class);
  }
  
  public static synchronized DriverService instance() {
    if (service == null)
      service = new DriverService();
    return service;
  }
  
  public LinkedList<Driver> getAll() {
    LinkedList<Driver> l = new LinkedList<>();
    try {
      Iterator<Driver> drivers = loader.iterator();
      while (drivers.hasNext())
        l.add(drivers.next());
    } catch(ServiceConfigurationError serviceError) {
      Logger.getLogger(DriverService.class.getName()).log(Level.SEVERE, null, serviceError);
    }
    return l;
  }
}
