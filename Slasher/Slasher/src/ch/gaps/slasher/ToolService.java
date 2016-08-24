/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.gaps.slasher;

import ch.gaps.slasher.tool.Tool;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author j.leroy
 */
public final class ToolService {
  private static ToolService service;
  private final ServiceLoader<Tool> loader;
  
  private ToolService() {
    loader = ServiceLoader.load(Tool.class);
  }
  
  public static synchronized ToolService instance() {
    if (service == null)
      service = new ToolService();
    return service;
  }
  
  public LinkedList<Tool> getAll() {
    LinkedList<Tool> l = new LinkedList<>();
    try {
      Iterator<Tool> tools = loader.iterator();
      while (tools.hasNext())
        l.add(tools.next());
    } catch(ServiceConfigurationError serviceError) {
      Logger.getLogger(ToolService.class.getName()).log(Level.SEVERE, null, serviceError);
    }
    return l;
  }
}
