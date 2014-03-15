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

import armidale.api.io.AbstractTextOutput;
import armidale.api.io.Debug;

import armidale.api.io.StreamTextOutput;

public class ServerContext extends ClientServerContext {

  public ServerContext(Transport transport, AbstractTextOutput textOutput) {
    super(textOutput, transport);
  }


  public void startApplication(String appUrl, String[] appArgs) {
    Message  msg  = new StartApplicationMessage(appUrl, appArgs);
    transport.writeMessage(msg);
  }


  public void close() {
    transport.writeMessage(new StopApplicationMessage());
  }


  public void handleApplicationStopped(Message message) {
    debug.message("ServerContext", "Application stopped", Debug.COURSEST);
    textOutput.showInformation("ServerContext", "application stopped, context=" + toString());
    closeContext();
  }


  protected void handleConstruct(int classId, int objectId) {
    handleError("ServerContext: constructor messages not supported by this context");
  }


  protected void handleCallback(int objectId, int callbackMethodId, Message message) {
    MessagingObject  theObject  = objectRegistry.findObject(objectId);
    if (theObject != null) {
      theObject.handleCallback(callbackMethodId, message);
    }
  }


  protected void handleMethodCall(int objectId, int methodId, Message message) {
    handleError("ServerContext: Method Call messages not supported by this context");
  }


  protected void handleSetAttribute(int objectId, int attributeId, Message message) {
    handleError("ServerContext: Set Attribute messages not supported by this context");
  }


  protected void handleEnableCallbacks(int objectId) {
    handleError("ServerContext: Enable callbacks messages not supported by this context");
  }


  protected void handleDisableCallbacks(int objectId) {
    handleError("ServerContext: Disable callbacks messages not supported by this context");
  }

}

