package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

/**
 * Created by leroy on 15.07.2016.
 */
public class Server extends DbObject {
    private Driver driver;
    private String host;

    public Server(Driver driver, String host){
        this.host = host;
        this.driver = driver;
    }

    public Database getDatabase(){
        return null;
    }

    public String toString(){
        return host;
    }

    public String getHost(){
        return host;
    }

    public void disconnect(){
        driver.close();
    }

    public void connect(){
        String[] connectionInfo = new String[3];
        connectionInfo[0] = host;
        connectionInfo[1] = "root";
        connectionInfo[2] = "";
        driver.connect(host);
    }




}
