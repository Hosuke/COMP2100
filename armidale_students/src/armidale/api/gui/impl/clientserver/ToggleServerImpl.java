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
 * ./armidale/api/gui/impl/clientserver/ToggleServerImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:57
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.ToggleCallback;
  import armidale.api.gui.ToggleCallbackList;
  import java.io.IOException;
  import java.util.LinkedList;

public abstract class ToggleServerImpl extends ButtonServerImpl implements ToggleClientServerIds {

  // readable attributes
  //
  protected boolean selected = DEFAULT_SELECTED;

  // Callback object
  //

  protected ToggleCallbackList toggleCallbackList = new ToggleCallbackList();

  // constructor
  //
  public ToggleServerImpl(ServerContext context) {
    super(context);
  }

  // attribute set/get/is methods
  //

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    Message msg = new SetAttributeMessage(this.getId(), selectedId);
    msg.writeBoolean(selected);
    transport.writeMessage(msg);
  }

  // Callback
  //

  public void addToggleCallback(ToggleCallback callback) {
    Message msg;
    toggleCallbackList.add(callback);
    if (toggleCallbackList.size() == 1) {
      msg = new EnableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeToggleCallback(ToggleCallback callback) {
    Message msg;
    toggleCallbackList.remove(callback);
    if (toggleCallbackList.size() == 0) {
      msg = new DisableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeAllToggleCallbacks() {
    Message msg;
    toggleCallbackList.clear();
    msg = new DisableCallbacksMessage(this.getId());
    transport.writeMessage(msg);
  }

  public ToggleCallbackList getToggleCallbackList() {
    return toggleCallbackList;
  }

  // transport
  //

  public void handleCallback(int callbackMethodId, Message message) {
    boolean stateChangeSelected;

    switch (callbackMethodId) {
      case toggleStateChangeBooleanId:
        stateChangeSelected = message.readBoolean();
        toggleCallbackList.stateChange(this, stateChangeSelected);
        break;
    }
  }

}
