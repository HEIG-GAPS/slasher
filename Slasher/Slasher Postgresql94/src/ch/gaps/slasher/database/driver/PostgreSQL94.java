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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author j.leroy
 */
public class PostgreSQL94 implements Driver
{

    private Connection connection;
    private Boolean connected = false;

    @Override
    public String id()
    {
        return PostgreSQL94.class.getName().toLowerCase();
    }

    @Override
    public String toString()
    {
        return "PostgresQL 9.4.*";
    }

    @Override
    public ServerType type()
    {
        return ServerType.Server;
    }

    @Override
    public void connect(String host, String username, String password, String database) throws SQLException, ClassNotFoundException
    {
        if (!connected)
        {
            Class.forName("org.postgresql.Driver");
            if (database == null)
                database = "";

            connection = DriverManager
                    .getConnection("jdbc:postgresql://" + host + ":5432/" + database,
                            username, password);

            connected = true;
        }
    }

    @Override
    public boolean hasSchema()
    {
        return true;
    }

    @Override
    public LinkedList<Table> getTables(Database database, Schema schema)
    {
        LinkedList<Table> tables = new LinkedList<>();
        try
        {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname = \'" + schema + "\'");

            while (resultSet.next())
            {
                tables.add(new Table(resultSet.getString(1), schema));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return tables;
    }

    @Override
    public LinkedList<Schema> getSchemas(Database database) throws SQLException
    {
        LinkedList<Schema> schemas = new LinkedList<>();

        ResultSet resultSet = connection.createStatement().executeQuery("select nspname " +
                "from pg_catalog.pg_namespace");


        while (resultSet.next())
        {
            schemas.add(new Schema(resultSet.getString(1), database));
        }

        return schemas;
    }

    @Override
    public void close() throws SQLException
    {
        if (connected)
        {
            connection.close();
        }

    }

    @Override
    public LinkedList<Database> getDatabases(Server server, String username, String password) throws SQLException, ClassNotFoundException
    {
        LinkedList<Database> databases = new LinkedList<>();


        Class.forName("org.postgresql.Driver");
        connection = DriverManager
                .getConnection("jdbc:postgresql://" + server + ":5432/",
                        username, password);

        ResultSet rs = connection.createStatement().executeQuery("SELECT datname FROM pg_database WHERE datistemplate = false");


        while (rs.next())
        {
            databases.add(new Database(new PostgreSQL94(), rs.getString("datname"), null, server, username));
        }


        return databases;

    }

    @Override
    public ResultSet executeQuery(String query) throws SQLException
    {
        return connection.createStatement().executeQuery(query);
    }

    @Override
    public Boolean isConnected()
    {
        return connected;
    }

    @Override
    public LinkedList<View> getViews()
    {
        return null;
    }

    @Override
    public LinkedList<Trigger> getTriggers()
    {
        return null;
    }

    @Override
    public ResultSet getAllData(Database database, Schema schema, Table table)
    {

        try
        {
            return connection.createStatement().executeQuery("SELECT * FROM " + schema + "." + table);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
