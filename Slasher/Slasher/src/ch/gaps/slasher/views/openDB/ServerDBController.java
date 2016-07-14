/*
 * The MIT License
 *
 * Copyright 2016 leroy.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.gaps.slasher.views.openDB;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ServerDBController implements DBController {
    @FXML private TextField host;
    @FXML private TextField username;
    @FXML private PasswordField password;

    private BooleanProperty hostOk = new SimpleBooleanProperty(false);
    private BooleanProperty usernameOk = new SimpleBooleanProperty(false);
    private BooleanProperty passwordOk = new SimpleBooleanProperty(false);

    private String [] connectionData = new String[1];
    private BooleanProperty filedOk = new SimpleBooleanProperty(false);


    @FXML
    public void initialize(){

        filedOk.bind(hostOk.and(usernameOk).and(passwordOk));

        host.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                hostOk.set(false);
            else
                hostOk.set(true);
        });

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                usernameOk.set(false);
            else
                usernameOk.set(true);
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty())
                passwordOk.set(false);
            else
                passwordOk.set(true);
        });
    }

    @Override
    public String[] getConnectionData() {
        return connectionData;
    }

    @Override
    public BooleanProperty getFieldValidation() {
        return filedOk;
    }
}
