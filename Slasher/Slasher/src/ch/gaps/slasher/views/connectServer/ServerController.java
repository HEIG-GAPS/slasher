package ch.gaps.slasher.views.connectServer;

import javafx.beans.property.BooleanProperty;

/**
 * Created by leroy on 12.07.2016.
 */
public interface ServerController {

    public String[] getConnectionData();

    public BooleanProperty getFieldValidation();
}
