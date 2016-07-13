package ch.gaps.slasher.database.driver.database;

import ch.gaps.slasher.database.driver.Driver;

/**
 * Created by leroy on 13.07.2016.
 */
public class Database extends DbObject {

    private Driver driver;

    public Database(Driver driver){
        this.driver = driver;
    }
    public String toString(){
        return driver.toString();
    }

    public Table[] getTables(){
        return driver.tables();
    }
}
