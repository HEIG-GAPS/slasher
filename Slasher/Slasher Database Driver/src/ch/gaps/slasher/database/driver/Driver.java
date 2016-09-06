/*
 * The MIT License
 *
 * Copyright 2015 Leroy.
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

import ch.gaps.slasher.database.driver.database.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author j.leroy
 */
public interface Driver
{

    /**
     * Server type enum
     */
    public enum ServerType
    {
        Server, File
    }

    /**
     * @return the id of this driver. Can be the lower case name of the main class.
     */
    public String id();

    /**
     * @return the human readable name of this driver.
     */
    public String toString();

    /**
     * To get the server type
     * @return the server type
     */
    public ServerType type();

    /**
     * Methode to connect the driver to the server
     * @param server the server
     * @param username the username
     * @param password the password of the username
     * @param database the database to connect to
     * @throws SQLException problem with the sql connection
     * @throws ClassNotFoundException jdbc driver not found
     */
    public void connect(Server server, String username, String password, String database) throws SQLException, ClassNotFoundException;

    /**
     * Close the database
     * @throws SQLException
     */
    public void close() throws SQLException;

    /**
     * @return if the driver is connected
     */
    public Boolean isConnected();

    /**
     * @return true if has a schema false else
     */
    public boolean hasSchema();

    /**
     * Use by the server to list the databases
     * @param server server to get the host
     * @param username
     * @param password
     * @return database(s) list
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public LinkedList<Database> getDatabases(Server server, String username, String password) throws SQLException, ClassNotFoundException;

    /**
     * Get the database schema(s) list
     * @param database
     * @return schema(s) list
     * @throws SQLException
     */
    public LinkedList<Schema> getSchemas(Database database) throws SQLException;

    /**
     * Get the database or schema table(s) list
     * @param database
     * @param schema
     * @return table(s) list
     * @throws SQLException
     */
    public LinkedList<Table> getTables(Database database, Schema schema) throws SQLException;

    /**
     * To get the vise of view(s)
     * @return the list of view(s)
     */
    public LinkedList<View> getViews();

    /**
     * To get the trigger(s)
     * @return the list of the trigger(s)
     */
    public LinkedList<Trigger> getTriggers();

    /**
     * Methode to execute a query on the database
     * @param query query to execute
     * @return the result set corresponding to the query
     * @throws SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException;

    /**
     * Method to get all the data of a table
     * @param database the database of the table
     * @param schema the schema if it has one
     * @param table
     * @return The result set with all the data
     * @throws SQLException
     */
    public ResultSet getAllData(Database database, Schema schema, Table table) throws SQLException;

    /**
     * @return the driver default port
     */
    public int getDefaultPort();

}
