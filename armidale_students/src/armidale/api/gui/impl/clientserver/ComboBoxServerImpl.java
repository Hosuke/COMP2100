/*
 * Copyright (c) 2000-2002, Shayne R Flint
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 *    * The name of the author may not be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * ./armidale/api/gui/impl/clientserver/ComboBoxServerImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:14
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.ComboBoxCallback;
  import armidale.api.gui.ComboBoxCallbackList;
  import armidale.api.gui.ComboBoxFactory;
  import java.io.IOException;
  import java.util.LinkedList;

public class ComboBoxServerImpl extends WidgetServerImpl implements ComboBoxClientServerIds {

  // readable attributes
  //
  protected int selectedItem = DEFAULT_SELECTEDITEM;

  // Callback object
  //

  protected ComboBoxCallbackList comboBoxCallbackList = new ComboBoxCallbackList();

  // constructor
  //
  public ComboBoxServerImpl(ServerContext context) {
    super(context);
  }

  // Class ID
  //
  public int getClassId() {
    return ComboBoxFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public void setItems(String[] items) {
    Message msg = new SetAttributeMessage(this.getId(), itemsId);
    msg.writeInt(items.length);
    for (int i=0; i<items.length; i++) {
      msg.writeString(items[i]);
    }
    transport.writeMessage(msg);
  }

  public int getSelectedItem() {
    return selectedItem;
  }

  public void setSelectedItem(int selectedItem) {
    this.selectedItem = selectedItem;
    Message msg = new SetAttributeMessage(this.getId(), selectedItemId);
    msg.writeInt(selectedItem);
    transport.writeMessage(msg);
  }

  // Callback
  //

  public void addComboBoxCallback(ComboBoxCallback callback) {
    Message msg;
    comboBoxCallbackList.add(callback);
    if (comboBoxCallbackList.size() == 1) {
      msg = new EnableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeComboBoxCallback(ComboBoxCallback callback) {
    Message msg;
    comboBoxCallbackList.remove(callback);
    if (comboBoxCallbackList.size() == 0) {
      msg = new DisableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeAllComboBoxCallbacks() {
    Message msg;
    comboBoxCallbackList.clear();
    msg = new DisableCallbacksMessage(this.getId());
    transport.writeMessage(msg);
  }

  public ComboBoxCallbackList getComboBoxCallbackList() {
    return comboBoxCallbackList;
  }

  // transport
  //

  public void handleCallback(int callbackMethodId, Message message) {
    int indexSelectedIndex;

    switch (callbackMethodId) {
      case comboBoxIndexSelectedIntId:
        indexSelectedIndex = message.readInt();
        comboBoxCallbackList.indexSelected(this, indexSelectedIndex);
        break;
    }
  }

}
