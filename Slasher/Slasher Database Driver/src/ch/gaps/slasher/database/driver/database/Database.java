package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

/**
 * Created by leroy on 13.07.2016.
 */
public class Database extends DbObject {

    private Driver driver;
    private String name;

    public Database(Driver driver, String name){
        this.driver = driver;
        this.name = name;
    }
    public String toString(){
        return driver.toString();
    }

    public Table[] getTables(){
        return driver.tables();
    }
}
