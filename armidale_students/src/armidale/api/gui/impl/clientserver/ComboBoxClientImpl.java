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
 * ./armidale/api/gui/impl/clientserver/ComboBoxClientImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:14
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.ImplementationException;
  import armidale.api.context.clientserver.*;
  import armidale.api.gui.ComboBox;
  import armidale.api.gui.ComboBoxCallback;
  import armidale.api.gui.ComboBoxCallbackList;
  import armidale.api.gui.ComboBoxFactory;

public class ComboBoxClientImpl extends WidgetClientImpl implements ComboBoxClientServerIds {

  // constructor
  //
  public ComboBoxClientImpl(ClientContext context, ComboBox peer) {
    super(context, peer);
  }

  // Class ID
  //
  public int getClassId() {
    return ComboBoxFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public void setItems(String[] items) {
    ((ComboBox)peer).setItems(items);
  }

  public int getSelectedItem() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setSelectedItem(int selectedItem) {
    ((ComboBox)peer).setSelectedItem(selectedItem);
  }

  // Callback methods
  //

  public void addComboBoxCallback(ComboBoxCallback callback) {
    ((ComboBox)peer).addComboBoxCallback(callback);
  }

  public void removeComboBoxCallback(ComboBoxCallback callback) {
    ((ComboBox)peer).removeComboBoxCallback(callback);
  }

  public void removeAllComboBoxCallbacks() {
    ((ComboBox)peer).removeAllComboBoxCallbacks();
  }

  public ComboBoxCallbackList getComboBoxCallbackList() {
    throw new ImplementationException("callback get methods are not supported in client context");
  }

  // local callback handlers
  //

  private class LocalCallback implements ComboBoxCallback {

    public void indexSelected(ComboBox comboBox, int index) {
      Message message = new CallbackMessage(ComboBoxClientImpl.this.getId(), comboBoxIndexSelectedIntId);
      message.writeInt(index);
      transport.writeMessage(message);
    }
  }

  public void handleSetAttribute(int attributeId, Message message) {
    int index;
    int first;
    int count;

    // temporary array attribute values
    //
    String[] tempItems;

    switch (attributeId) {
      case itemsId:
        tempItems = new String[message.readInt()];
        for (int i=0; i < tempItems.length; i++) {
          tempItems[i] = message.readString();
        }
        setItems(tempItems);
        break;
      case selectedItemId:
        setSelectedItem(message.readInt());
        break;
      default:
        super.handleSetAttribute(attributeId, message);
    }
  }

  public void handleEnableCallbacks() {
    addComboBoxCallback(new LocalCallback());
  }

  public void handleDisableCallbacks() {
    removeAllComboBoxCallbacks();
  }
}
