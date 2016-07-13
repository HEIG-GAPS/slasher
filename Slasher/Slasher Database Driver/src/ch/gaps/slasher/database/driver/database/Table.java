package ch.gaps.slasher.database.driver.database;

/**
 * Created by leroy on 13.07.2016.
 */
public class Table extends DbObject {
    private String name;

    public Table(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

}
