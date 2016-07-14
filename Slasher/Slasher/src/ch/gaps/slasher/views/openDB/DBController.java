package ch.gaps.slasher.views.openDB;

import javafx.beans.property.BooleanProperty;

/**
 * Created by leroy on 12.07.2016.
 */
public interface DBController {

    public String[] getConnectionData();

    public BooleanProperty getFieldValidation();
}
