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
 * ./armidale/api/gui/impl/clientserver/WindowClientImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:25
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.ImplementationException;
  import armidale.api.context.clientserver.*;
  import armidale.api.gui.Position;
  import armidale.api.gui.Size;
  import armidale.api.gui.Window;
  import armidale.api.gui.WindowCallback;
  import armidale.api.gui.WindowCallbackList;

public abstract class WindowClientImpl extends VisibleObjectClientImpl implements WindowClientServerIds {

  // constructor
  //
  public WindowClientImpl(ClientContext context, Window peer) {
    super(context, peer);
  }

  // Callback methods
  //

  public void addWindowCallback(WindowCallback callback) {
    ((Window)peer).addWindowCallback(callback);
  }

  public void removeWindowCallback(WindowCallback callback) {
    ((Window)peer).removeWindowCallback(callback);
  }

  public void removeAllWindowCallbacks() {
    ((Window)peer).removeAllWindowCallbacks();
  }

  public WindowCallbackList getWindowCallbackList() {
    throw new ImplementationException("callback get methods are not supported in client context");
  }

  // other methods
  //

  public void dispose() {
    ((Window)peer).dispose();
  }

  // local callback handlers
  //

  private class LocalCallback implements WindowCallback {

    public void windowOpen(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowOpenId);
      transport.writeMessage(message);
    }

    public void windowActivated(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowActivatedId);
      transport.writeMessage(message);
    }

    public void windowClosed(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowClosedId);
      transport.writeMessage(message);
    }

    public void windowClosing(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowClosingId);
      transport.writeMessage(message);
    }

    public void windowDeactivated(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowDeactivatedId);
      transport.writeMessage(message);
    }

    public void windowDeiconified(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowDeiconifiedId);
      transport.writeMessage(message);
    }

    public void windowIconified(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowIconifiedId);
      transport.writeMessage(message);
    }

    public void windowOpened(Window window) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowOpenedId);
      transport.writeMessage(message);
    }

    public void windowMoved(Window window, Position position) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowMovedPositionId);
      message.writePosition(position);
      transport.writeMessage(message);
    }

    public void windowResized(Window window, Size size) {
      Message message = new CallbackMessage(WindowClientImpl.this.getId(), windowWindowResizedSizeId);
      message.writeSize(size);
      transport.writeMessage(message);
    }
  }

  public void handleMethodCall(int methodId, Message message) {

    // method parameter values
    //

    switch (methodId) {
      case disposeId:
        dispose();
        break;
      default:
        super.handleMethodCall(methodId, message);
    }
  }

  public void handleEnableCallbacks() {
    addWindowCallback(new LocalCallback());
  }

  public void handleDisableCallbacks() {
    removeAllWindowCallbacks();
  }
}
