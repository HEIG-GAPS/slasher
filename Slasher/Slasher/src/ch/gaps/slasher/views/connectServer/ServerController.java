package ch.gaps.slasher.views.connectServer;

import ch.gaps.slasher.database.driver.Driver;
import javafx.beans.property.BooleanProperty;

/**
 * Created by leroy on 12.07.2016.
 */
public interface ServerController {

    public String[] getConnectionData();

    public BooleanProperty getFieldValidation();

    public void setDriver(Driver driver);
}
