/*
 * The MIT License
 *
 * Copyright 2015 jvarani.
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

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.Schema;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.database.driver.database.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author jvarani
 */
public interface Driver {

  /**
   * @return the id of this driver. Can be the lower case name of the main class.
   */
  public String id();
  
  /**
   * @return the human readable name of this driver.
   */
  public String toString();

  public ServerType type();

  public void connect(String host, String username, String password, String database) throws SQLException, ClassNotFoundException;

  public void test();

  public boolean hasSchema();

  public Table[] getTables(String name);

  public Schema[] getSchemas(Database database);

  public void close();

  public LinkedList<Database> getDatabases(Server server, String username, String password);

  public enum ServerType{Server, File};

  public ResultSet executeQuarry (String quarry) throws SQLException;

  public Boolean isConnected();
  
}
