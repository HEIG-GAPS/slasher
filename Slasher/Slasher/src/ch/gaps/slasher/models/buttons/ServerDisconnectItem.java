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
package ch.gaps.slasher.models.buttons;

import ch.gaps.slasher.models.treeItem.ServerTreeItem;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

/**
 * Class to model the close server button in the top menu
 * @author j.leroy
 */
public class ServerDisconnectItem extends MenuItem {
    private ServerTreeItem serverTreeItem;

    public ServerDisconnectItem(ServerTreeItem serverTreeItem){
        super();
        this.serverTreeItem = serverTreeItem;
        setText(serverTreeItem.getValue().toString());
        addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
        });
    }

    /**
     * @return the serverTreeItem linked to the button
     */
    public ServerTreeItem getServerTreeItem(){
        return serverTreeItem;
    }

}
