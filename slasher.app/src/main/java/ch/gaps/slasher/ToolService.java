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
package ch.gaps.slasher;

import ch.gaps.slasher.tool.Tool;

import java.util.LinkedList;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julien Leroy
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
      for (Tool t : loader) l.add(t);
    } catch (ServiceConfigurationError serviceError) {
      Logger.getLogger(ToolService.class.getName()).log(Level.SEVERE, null,
          serviceError);
    }
    return l;
  }
}
