/*
 *  Copyright (c) 2000-2002, Shayne R Flint
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *  3. The name of the author may not be used to endorse or promote products
 *  derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package armidale.api.context.clientserver;

import armidale.api.RemoteApplication;
import armidale.api.gui.Frame;
import armidale.api.io.Debug;

import armidale.api.io.FrameTextOutput;
import java.util.Enumeration;
import javax.swing.SwingUtilities;

public class SwingClientContext extends ClientContext {

  public SwingClientContext(Transport transport) {
    super(new FrameTextOutput(), transport);
  }


  public void close() { }


  public void handleStopApplication(Message message) {
    debug.message("SwingClientContext", "Application stopped", Debug.COURSEST);
    transport.writeMessage(new ApplicationStoppedMessage());
    closeContext();
  }


  protected void handleSetAttribute(int objectId, int attributeId, Message message) {
    debug.message("SwingClientContext", "setAttribute " + attributeId + " of object " + objectId);
    SwingUtilities.invokeLater(new SwingSetAttributeHandler(this, objectId, attributeId, message));
  }


  protected void handleMethodCall(int objectId, int methodId, Message message) {
    debug.message("SwingClientContext", "Call method " + methodId + " of object " + objectId);
    SwingUtilities.invokeLater(new SwingCallHandler(this, objectId, methodId, message));
  }


  protected void handleEnableCallbacks(int objectId) {
    MessagingObject  theObject  = objectRegistry.findObject(objectId);
    theObject.handleEnableCallbacks();
  }


  protected void handleDisableCallbacks(int objectId) {
    MessagingObject  theObject  = objectRegistry.findObject(objectId);
    theObject.handleDisableCallbacks();
  }


  protected void handleClassNotFound(int classId) {
    RemoteApplication    moreInformationApp;
    ClassNotFoundDialog  classNotFoundDialog  = new ClassNotFoundDialog();
    int                  result               = classNotFoundDialog.show();
    if (result == ClassNotFoundDialog.MORE_INFORMATION) {
      try {
//        moreInformationApp  = new RemoteApplication(getURL().getProtocolHostPort() + "/armidale.server.ClientRequirements " + getURL().getAppName());
//        moreInformationApp.start();
      } catch (Throwable e1) {
        textOutput.showError("SwingClientMessenger", e1.getMessage());
        return;
      }
    }
    close();
  }

}

