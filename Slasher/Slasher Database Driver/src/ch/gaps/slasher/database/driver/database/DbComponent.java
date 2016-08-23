package ch.gaps.slasher.database.driver.database;

/**
 * Created by leroy on 15.07.2016.
 */
public class DbComponent extends DbObject {

    protected Database database;
    protected String name;

    protected DbParent dbParent;

    public DbComponent(String name, Database database){
        this.database = database;
        this.name = name;
    }

    public DbComponent(String name, DbParent dbParent){
        this.name = name;
        this.dbParent = dbParent;
    }

    public String getName() { return name; }
}
