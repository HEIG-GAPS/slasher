/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.gaps.slasher.database.driver;

import ch.gaps.slasher.database.driver.database.Database;
import ch.gaps.slasher.database.driver.database.Schema;
import ch.gaps.slasher.database.driver.database.Server;
import ch.gaps.slasher.database.driver.database.Table;

import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @author leory
 */
public class MySql implements Driver
{

    private Connection connection;
    private Boolean connected = false;


    @Override
    public String id()
    {
        return MySql.class.getName().toLowerCase();
    }

    @Override
    public String toString()
    {
        return "MySql";
    }

    @Override
    public ServerType type()
    {
        return ServerType.Server;
    }

    @Override
    public void connect(String host, String username, String password, String database) throws SQLException, ClassNotFoundException
    {


        Class.forName("com.mysql.jdbc.Driver");


        Properties info = new Properties();
        info.setProperty("user", username);
        info.setProperty("password", password);
        info.setProperty("useSSL", "true");

        if (database == null){
            database = "";
        }


        String url = "jdbc:mysql://" + host + ":3306/" + database;
        connection = DriverManager.getConnection(url, info);

        connected = true;

    }

    @Override
    public void test()
    {

    }

    @Override
    public Table[] getTables(String name)
    {

        LinkedList<Table> tables = new LinkedList<>();

        try
        {
            DatabaseMetaData meta = connection.getMetaData();
            Statement statement = connection.createStatement();
            ResultSet rs2 = meta.getTables(null, name, "%", null);
            ResultSet rs = statement.executeQuery("SELECT DISTINCT TABLE_NAME\n" +
                    "      FROM INFORMATION_SCHEMA.COLUMNS\n" +
                    "      WHERE TABLE_SCHEMA='" + name + "'");

            while (rs.next())
            {
                System.out.println(rs.getString(1));
                tables.add(new Table(rs.getString(1)));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return tables.toArray(new Table[tables.size()]);
    }

    @Override
    public Schema[] getSchemas(Database database)
    {
        LinkedList<Schema> schemas = new LinkedList<>();

        Statement statement = null;

        try
        {
            ResultSet rs = connection.getMetaData().getCatalogs();
            while (rs.next())
            {
                schemas.add(new Schema(database, rs.getString("TABLE_CAT")));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return schemas.toArray(new Schema[schemas.size()]);
    }

    @Override
    public void close()
    {
        try
        {
            if (!connected)
            {
                connection.close();
                connected = false;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<Database> getDatabases(Server server, String username, String password)
    {
        LinkedList<Database> databases = new LinkedList<>();

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Properties info = new Properties();
        info.setProperty("user", username);
        info.setProperty("password", password);
        info.setProperty("useSSL", "true");

        try
        {
            String url = "jdbc:mysql://" + server.getHost() + ":3306";
            connection = DriverManager.getConnection(url, info);
            ResultSet rs = connection.getMetaData().getCatalogs();
            while (rs.next())
            {
                databases.add(new Database(new MySql(), rs.getString("TABLE_CAT"), null, server, username));

            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return databases;
    }

    @Override
    public ResultSet executeQuarry(String quarry) throws SQLException
    {
        if (connected)
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(quarry);
            return rs;
        } else
        {
            return null;
        }
    }

    @Override
    public Boolean isConnected()
    {
        return connected;
    }

    @Override
    public boolean hasSchema()
    {
        return false;
    }

}
