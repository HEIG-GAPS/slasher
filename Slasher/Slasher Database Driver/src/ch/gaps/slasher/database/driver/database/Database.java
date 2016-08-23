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

public class Database extends DbObject implements DbParent {

        private Driver driver;
        private Server server;
        private String name;
        private String username;
        private String description;
        private boolean connected = false;

        private BooleanProperty connectedProperty = new SimpleBooleanProperty();


    public Database(Driver driver, String name, Server server, String username){
            this.driver = driver;
            this.name = name;
            this.server = server;
            this.username = username;
            description = username + "@" + name;
            connectedProperty.setValue(false);
        }

    public Database(Driver driver, String name, String description, Server server, String username){
            this.driver = driver;
            this.name = name;
            this.server = server;
            this.username = username;
            if (description == null){
                description = username + "@" + name;
            }
            this.description = description;
            connectedProperty.setValue(false);
        }

    public String toString(){
        return description;
    }

    public LinkedList<Table> getTables() throws SQLException
    {
        return driver.getTables(this, null);
    }

    public LinkedList<Table> getTables(Schema schema) throws SQLException
    { return driver.getTables(this, schema); }

    public View[] getView() { return driver.getViews();}

    public LinkedList<Schema> getSchemas() throws SQLException
    {
        return driver.getSchemas(this);
    }

    public View[] getViews(){
        return driver.getViews();
    }

    public boolean hasSchemas(){
        return driver.hasSchema();
    }

    public void close() throws SQLException
    {
        if (connected) {
            driver.close();
            connected = false;
            connectedProperty.setValue(false);
        }
    }

    public String getName(){
        return name;
    }

    public String getUsername(){ return username; }

    public void connect(String password) throws SQLException, ClassNotFoundException
    {
        if (!connected) {
            driver.connect(server.getHost(), username, password, name);
            connected = true;
            connectedProperty.setValue(true);
        }
    }

    public void setDescription(String newDescritpion){
        description = newDescritpion;
    }

    public String getDescritpion (){
        return description;
    }

    public Driver.ServerType type(){
        return driver.type();
    }

    public boolean disabled(){
        return !connected;
    }

    public boolean isConnected() { return connected; }

    public BooleanProperty disabledProperty() { return connectedProperty; }

    public ResultSet executeQuarry(String query) throws SQLException{
        return driver.executeQuery(query);
    }

    public ResultSet getAllData(Schema schema, Table table) throws SQLException
    {
        return driver.getAllData(this, schema, table);
    }

    @Override
    public ResultSet getAllData(Table table) throws SQLException
    {
        return driver.getAllData(this, null, table);
    }
}
