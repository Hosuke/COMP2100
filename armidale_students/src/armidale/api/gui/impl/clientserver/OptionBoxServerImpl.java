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
 * ./armidale/api/gui/impl/clientserver/OptionBoxServerImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:35
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.Desktop;
  import armidale.api.gui.Image;
  import armidale.api.gui.OptionBoxCallback;
  import armidale.api.gui.OptionBoxCallbackList;
  import armidale.api.gui.OptionBoxFactory;
  import armidale.api.gui.constants.OptionBoxButton;
  import armidale.api.gui.constants.OptionBoxButtonSet;
  import armidale.api.gui.constants.OptionBoxMessageType;
  import armidale.api.impl.clientserver.ArmidaleObjectServerImpl;
  import java.io.IOException;
  import java.util.LinkedList;

public class OptionBoxServerImpl extends ArmidaleObjectServerImpl implements OptionBoxClientServerIds {

  // readable attributes
  //
  protected String title = DEFAULT_TITLE;
  protected String message = DEFAULT_MESSAGE;
  protected int messageType = DEFAULT_MESSAGETYPE;
  protected int buttonSet = DEFAULT_BUTTONSET;
  protected Image icon;
  protected boolean input = DEFAULT_INPUT;

  // Callback object
  //

  protected OptionBoxCallbackList optionBoxCallbackList = new OptionBoxCallbackList();

  // constructor
  //
  public OptionBoxServerImpl(ServerContext context) {
    super(context);
  }

  // Class ID
  //
  public int getClassId() {
    return OptionBoxFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    Message msg = new SetAttributeMessage(this.getId(), titleId);
    msg.writeString(title);
    transport.writeMessage(msg);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
    Message msg = new SetAttributeMessage(this.getId(), messageId);
    msg.writeString(message);
    transport.writeMessage(msg);
  }

  public int getMessageType() {
    return messageType;
  }

  public void setMessageType(int messageType) {
    this.messageType = messageType;
    Message msg = new SetAttributeMessage(this.getId(), messageTypeId);
    msg.writeInt(messageType);
    transport.writeMessage(msg);
  }

  public int getButtonSet() {
    return buttonSet;
  }

  public void setButtonSet(int buttonSet) {
    this.buttonSet = buttonSet;
    Message msg = new SetAttributeMessage(this.getId(), buttonSetId);
    msg.writeInt(buttonSet);
    transport.writeMessage(msg);
  }

  public Image getIcon() {
    return icon;
  }

  public void setIcon(Image icon) {
    this.icon = icon;
    Message msg = new SetAttributeMessage(this.getId(), iconId);
    msg.writeObjectId((MessagingObject)icon);
    transport.writeMessage(msg);
  }

  public boolean hasInput() {
    return input;
  }

  public void setInput(boolean input) {
    this.input = input;
    Message msg = new SetAttributeMessage(this.getId(), inputId);
    msg.writeBoolean(input);
    transport.writeMessage(msg);
  }

  // Callback
  //

  public void addOptionBoxCallback(OptionBoxCallback callback) {
    Message msg;
    optionBoxCallbackList.add(callback);
    if (optionBoxCallbackList.size() == 1) {
      msg = new EnableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeOptionBoxCallback(OptionBoxCallback callback) {
    Message msg;
    optionBoxCallbackList.remove(callback);
    if (optionBoxCallbackList.size() == 0) {
      msg = new DisableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeAllOptionBoxCallbacks() {
    Message msg;
    optionBoxCallbackList.clear();
    msg = new DisableCallbacksMessage(this.getId());
    transport.writeMessage(msg);
  }

  public OptionBoxCallbackList getOptionBoxCallbackList() {
    return optionBoxCallbackList;
  }

  // other methods
  //

  public void open() {
    Message msg = new MethodCallMessage(this.getId(), openId);
    transport.writeMessage(msg);
  }

  public void open(Desktop desktop) {
    Message msg = new MethodCallMessage(this.getId(), openDesktopId);
    msg.writeObjectId((MessagingObject)desktop);
    transport.writeMessage(msg);
  }

  // transport
  //

  public void handleCallback(int callbackMethodId, Message message) {
    int optionSelectedOption;
    String optionSelectedText;

    switch (callbackMethodId) {
      case optionBoxOptionSelectedIntStringId:
        optionSelectedOption = message.readInt();
        optionSelectedText = message.readString();
        optionBoxCallbackList.optionSelected(this, optionSelectedOption, optionSelectedText);
        break;
    }
  }

}
