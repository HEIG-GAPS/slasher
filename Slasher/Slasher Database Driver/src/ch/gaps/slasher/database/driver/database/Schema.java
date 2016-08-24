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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author j.leroy
 */
public class Schema extends DbObject implements DbParent {

    Database database;
    String name;

    public Schema(String name, Database database){
        this.name = name;
        this.database = database;
    }

    /**
     * @return talbe(s) list
     * @throws SQLException
     */
    public LinkedList<Table> getTables() throws SQLException
    {
        return database.getTables(this);
    }

    /**
     * @return view(s) list
     */
    public LinkedList<View> getViews() { return null;}

    /**
     * @return trigger(s) list
     */
    public LinkedList<Trigger> getTriggers() { return null;}

    /**
     * @return schema name
     */
    public String toString()
    {
        return name;
    }

    /**
     * @return schema's database
     */
    public Database getDatabase(){
        return database;
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
        return database.getAllData(this, table);
    }
}
