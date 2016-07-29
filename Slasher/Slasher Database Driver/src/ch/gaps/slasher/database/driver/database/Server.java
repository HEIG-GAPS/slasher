package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

    public LinkedList<Database> getAllDatabases(String username, String password) {
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

    public void disconnect() {
        driver.close();
    }

    public void connectSelectedDatabases(String password) {
        for (Database database : openedDatabase) {
            database.connect(password);
        }
    }

    public void closeSelectedDatabases(){
       openedDatabase.stream().peek(database -> {
           database.close();
       });
        openedDatabase.clear();
    }

    public Driver.ServerType type(){
        return driver.type();
    }

}
