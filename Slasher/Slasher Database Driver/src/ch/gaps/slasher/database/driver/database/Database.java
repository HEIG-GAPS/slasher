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

public class Database extends DbObject {

    private Driver driver;
    private Server server;
    private String name;
    private String username;
    private String description;

    public Database(Driver driver, String name){
        this.driver = driver;
        this.name = name;
    }

    public Database(Driver driver, String name, Server server){
        this.driver = driver;
        this.name = name;
        this.server = server;
    }

    public Database(Driver driver, String name, Server server, String username){
        this.driver = driver;
        this.name = name;
        this.server = server;
        this.username = username;
    }

    public Database(Driver driver, String name, String description, Server server, String username){
        this.driver = driver;
        this.name = name;
        this.server = server;
        this.username = username;
        if (description == null){
            description = name;
        }
        this.description = description;
    }

    public String toString(){
        return description;
    }

    public Table[] getTables(){
        return driver.getTables(name);
    }

    public Schema[] getSchemas(){
       return driver.getSchemas(this);
    }

    public boolean hasSchemas(){
        return driver.hasSchema();
    }

    public void close(){ driver.close(); }

    public String getName(){
        return name;
    }

    public void connect(String password){
        driver.connect(server.getHost(), username, password);
    }

    public void setDescription(String newDescritpion){
        description = newDescritpion;
    }

}
