package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

import java.util.LinkedList;

/**
 * Created by leroy on 15.07.2016.
 */
public class Server extends DbObject {
    private String name;
    private Driver driver;
    private String host;
    private LinkedList<Database> openedDatabase = new LinkedList<>();

    public Server(Driver driver, String host){
        this.host = host;
        this.driver = driver;
        name = host;
    }

    public Database[] getAllDatabases(){
        return driver.getDatabases();
    }

    public Database[] getDatabases(){
        return openedDatabase.toArray(new Database[openedDatabase.size()]);
    }

    public void addDatabase(Database database){
        openedDatabase.add(database);
    }

    public void removeDatabase(Database database){ openedDatabase.remove(database); }

    public String toString(){
        return name + " - " + driver;
    }

    public String getHost(){
        return host;
    }

    public void disconnect(){
        driver.close();
    }

    public void connect(String username, String password){
        String[] connectionInfo = new String[3];
        connectionInfo[0] = host;
        connectionInfo[1] = username;
        connectionInfo[2] = password;
        driver.connect(connectionInfo);
    }

    public void setName(String name){
        this.name = name;
    }






}
