/*
 * The MIT License
 *
 * Copyright 2016 leroy.
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
package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author j.leroy
 */
public class Database extends DbObject implements DbParent
{

    private Driver driver;
    private Server server;
    private String name;
    private String username;
    private String description;
    private boolean connected = false;
    private BooleanProperty connectedProperty;


    public Database(Driver driver, String name, Server server, String username)
    {
        this.driver = driver;
        this.name = name;
        this.server = server;
        this.username = username;
        description = username + "@" + name;
        connectedProperty = new SimpleBooleanProperty(false);
    }

    public Database(Driver driver, String name, String description, Server server, String username)
    {
        this.driver = driver;
        this.name = name;
        this.server = server;
        this.username = username;
        if (description == null)
        {
            description = username + "@" + name;
        }
        this.description = description;
        connectedProperty = new SimpleBooleanProperty(false);
    }

    /**
      * @return description
     */
    public String toString()
    {
        return description;
    }

    /**
     * @return  talbe(s) list of the database
     * @throws SQLException
     */
    public LinkedList<Table> getTables() throws SQLException
    {
        return driver.getTables(this, null);
    }

    /**
     * If the database has a schema
     * @param schema
     * @return talbe(s) list of the database
     * @throws SQLException
     */
    public LinkedList<Table> getTables(Schema schema) throws SQLException
    {
        return driver.getTables(this, schema);
    }

    /**
     * @return view(s) list
     */
    public LinkedList<View> getViews()
    {
        return driver.getViews();
    }

    /**
     * @return schema(s) list
     * @throws SQLException
     */
    public LinkedList<Schema> getSchemas() throws SQLException
    {
        return driver.getSchemas(this);
    }


    /**
     * @return true if the database has schema(s)
     */
    public boolean hasSchemas()
    {
        return driver.hasSchema();
    }

    /**
     * To close nicly the database
     * @throws SQLException
     */
    public void close() throws SQLException
    {
        if (connectedProperty.getValue()){
            driver.close();
            connectedProperty.setValue(false);
        }
    }

    /**
     * @return database name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the username linked to this database
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * TO connect the database
     * @param password
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void connect(String password) throws SQLException, ClassNotFoundException
    {
        if (!connectedProperty.getValue())
        {
            driver.connect(server, username, password, name);
            connectedProperty.setValue(true);
        }
    }

    /**
     * @param newDescritpion new description to set
     */
    public void setDescription(String newDescritpion)   {
        description = newDescritpion;
    }

    /**
     * @return database description
     */
    public String getDescritpion() {
        return description;
    }

    /**
     * @return driver server type
     */
    public Driver.ServerType type() {
        return driver.type();
    }

    /**
     * @return !connected
     */
    public boolean disabled(){
        return !connectedProperty.getValue();
    }

    /**
     * @return connected
     */
    public boolean isConnected(){
        return connectedProperty.getValue();
    }

    /**
     * @return enabled property
     */
    public BooleanProperty enabledProperty()
    {
        return connectedProperty;
    }

    /**
     * To execute a query on the driver
     * @param query
     * @return query result set
     * @throws SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException
    {
        return driver.executeQuery(query);
    }

    /**
     * To get all the data of a table with a schema
     * @param schema
     * @param table
     * @return the result set with all the data
     * @throws SQLException
     */
    public ResultSet getAllData(Schema schema, Table table) throws SQLException
    {
        return driver.getAllData(this, schema, table);
    }

    /**
     * Implementation of the DbParent method
     * @param table
     * @return result set with all the talbe data
     * @throws SQLException
     */
    @Override
    public ResultSet getAllData(Table table) throws SQLException
    {
        return driver.getAllData(this, null, table);
    }
}
