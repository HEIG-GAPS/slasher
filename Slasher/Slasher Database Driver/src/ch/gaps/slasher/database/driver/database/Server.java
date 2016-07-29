package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

import java.util.LinkedList;

/**
 * Created by leroy on 15.07.2016.
 */
public class Server extends DbObject {
    private String description;
    private Driver driver;
    private String host;
    private LinkedList<Database> openedDatabase = new LinkedList<>();

    public Server(Driver driver, String host){
        this.host = host;
        this.driver = driver;
    }

    public Server(Driver driver, String host, String descritpion){
        this.host = host;
        this.driver = driver;
        if (descritpion == null){
            this.description = host;
        }
        this.description = descritpion;
    }

    public Database[] getDatabases(String username, String password){
        return driver.getDatabases(this, username, password);
    }

    public Database[] getDatabases(){
        return openedDatabase.toArray(new Database[openedDatabase.size()]);
    }

    public void addDatabase(Database database){
        openedDatabase.add(database);
    }

    public void removeDatabase(Database database){ openedDatabase.remove(database); }

    public String toString(){
        return description;
    }

    public String getHost(){
        return host;
    }

    public void setDescription(String newDescription){
        description = newDescription;
    }

    public String getDescription(){
        return description;
    }

    public void disconnect(){
        driver.close();
    }

    public void connectSelectedDatabases(String password){
        for(Database database : openedDatabase){
            database.connect(password);
        }
    }

}
