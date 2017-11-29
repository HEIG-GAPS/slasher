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
package ch.gaps.slasher.database.driver.database;

/**
 * Representation of any elements composing a database.
 * 
 * @author Julien Leroy
 */
public abstract class DBComponent implements DBObject {

  // **************************************************************************
  // Attributes
  // **************************************************************************
  /** The {@link Database}. */
  protected Database database;
  /** The name of this component. */
  protected String name;
  /** The {@link DBParent}. */
  protected DBParent dbParent;

  // **************************************************************************
  // Constructors
  // **************************************************************************
  /**
   * Creates a new {@link DBComponent}.
   * 
   * @param name the name of the component.
   * @param database the {@link Database}.
   */
  public DBComponent(String name, Database database) {
    this.database = database;
    this.name = name;
  }

  /**
   * Creates a new {@link DBComponent} for any component held by any parent
   * object.
   * 
   * @param name the name of the component.
   * @param dbParent the {@link DBParent}.
   */
  public DBComponent(String name, DBParent dbParent) {
    this.name = name;
    this.dbParent = dbParent;
  }

  // **************************************************************************
  // DBObject Overrides
  // **************************************************************************
  /** @return the name of this component. */
  @Override
  public String toString() {
    return name;
  }

  // **************************************************************************
  // Getters / Setters
  // **************************************************************************
  /** @return the name of this component. */
  public String name() {
    return name;
  }

}
