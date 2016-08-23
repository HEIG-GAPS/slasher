package ch.gaps.slasher.database.driver.database;

import java.sql.ResultSet;

/**
 * Created by julien on 23.08.16.
 */
public interface DbParent
{
    public ResultSet getAllData(Table table);
}
