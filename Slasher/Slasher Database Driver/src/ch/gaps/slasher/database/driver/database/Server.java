package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by leroy on 15.07.2016.
 */
public class Server extends DbObject {
    private String description;
    private Driver driver;
    private String host;
    private LinkedList<Database> openedDatabase = new LinkedList<>();

    public Server(Driver driver, String host, String descritpion) {
        this.host = host;
        this.driver = driver;
        if (descritpion == null) {
            this.description = host;
        }
        else
            this.description = descritpion;
    }

    public LinkedList<Database> getAllDatabases(String username, String password) throws SQLException, ClassNotFoundException
    {
        return driver.getDatabases(this, username, password);
    }

    public LinkedList<Database> getDatabases() {
        return openedDatabase;
    }

    public boolean addDatabase(Database database) {

        if (openedDatabase.stream().filter(database1 -> database1.getDescritpion().equals(database.getDescritpion())).count() < 0) {
            return false;
        }
        else {
            openedDatabase.add(database);
            return true;
        }
    }

    public void removeDatabase(Database database) {
        openedDatabase.remove(database);
    }

    public String toString() {
        return description;
    }

    public String getHost() {
        return host;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public String getDescription() {
        return description;
    }

    public void disconnect() throws SQLException
    {
        driver.close();
    }

    public void connectSelectedDatabases(String password) throws SQLException, ClassNotFoundException
    {
        for (Database database : openedDatabase) {
            database.connect(password);
        }
    }

    public void closeSelectedDatabases(){
       openedDatabase.stream().peek(database -> {
           try
           {
               database.close();
           } catch (SQLException e)
           {
               e.printStackTrace();
           }
       });
        openedDatabase.clear();
    }

    public Driver.ServerType type(){
        return driver.type();
    }

    public String getDiverName(){
        return driver.toString();
    }

}
