package ch.gaps.slasher.database.driver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by julien on 19.08.16.
 */
public class View extends DbComponent
{
    public View(String name, Database database)
    {
        super(name, database);
    }

    public ResultSet getAllData() throws SQLException
    {
        return database.executeQuarry("SELECT * FROM " + name);
    }
}
