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

import java.util.Arrays;

import armidale.api.context.Context;
import armidale.api.io.*;
import armidale.api.structures.*;

public abstract class ClientServerContext extends Context {

  protected  Transport       transport;
  protected  ObjectRegistry  objectRegistry;

  private    boolean         stopped         = false;


  public ClientServerContext(AbstractTextOutput textOutput, Transport transport) {
    super(textOutput);
    this.transport = transport;
    objectRegistry = new ObjectRegistry(1000, 1000);
  }


  public Transport getTransport() {
    return transport;
  }


  public ObjectRegistry getObjectRegistry() {
    return objectRegistry;
  }


  public void dispatchMessages() {
    Message  message;

    while (!Thread.currentThread().interrupted()) {
      try {
        message = transport.readMessage();
        switch (message.getMessageType()) {
          case MessageTypes.STOP_APPLICATION:
            handleStopApplication(message);
            return;
          case MessageTypes.APPLICATION_STOPPED:
            handleApplicationStopped(message);
            return;
          default:
            handleMessage(message);
        }
      } catch (Exception e1) {
        e1.printStackTrace();
        handleError("ClientServerContext", e1);
        return;
      }
    }
  }


  public void handleStopApplication(Message message) {
    handleError("ClientServerContext: unexpected message [" + message.getMessageType() + "]");
  }


  public void handleApplicationStopped(Message message) {
    handleError("ClientServerContext: unexpected message [" + message.getMessageType() + "]");
  }


  protected void handleMessage(Message message) {
    int        messageType;
    int        classId;
    int        objectId;
    int        attributeId;
    int        methodId;
    int        callbackMethodId;
    String     filename;
    boolean    fileAvailable;
    int        lastModified;
    ByteArray  fileData;
    File       theFile;
    String     appUrl;
    String[]   appArgs;

    messageType = message.getMessageType();
    switch (messageType) {
      case MessageTypes.ERROR:
        handleError(message.readString());
        break;
      case MessageTypes.START_APPLICATION:
        appUrl = message.readString();
        appArgs = message.readStringArray();
        debug.message("ClientServerContext", "Got START_APPLICATION " + appUrl + ", args=" + Arrays.toString(appArgs), debug.COURSE);
        startApplication(appUrl, appArgs);
        break;
      case MessageTypes.CONSTRUCT:
        classId = message.readInt();
        objectId = message.readObjectId();
        debug.message("ClientServerContext", "Got CONSTRUCT classId=" + classId + ", objectId=" + objectId, debug.COURSE);
        handleConstruct(classId, objectId);
        break;
      case MessageTypes.SET_ATTRIBUTE:
        objectId = message.readObjectId();
        attributeId = message.readInt();
        debug.message("ClientServerContext", "Got SET_ATTRIBUTE attributeId=" + attributeId + ", objectId=" + objectId + ", object=" + objectRegistry.findObject(objectId).toString(), debug.COURSE);
        handleSetAttribute(objectId, attributeId, message);
        break;
      case MessageTypes.SET_FILE_ATTRIBUTE:
        objectId = message.readObjectId();
        attributeId = message.readInt();
        debug.message("ClientServerContext", "Got SET_FILE_ATTRIBUTE attributeId=" + attributeId + ", objectId=" + objectId + ", object=" + objectRegistry.findObject(objectId).toString(), debug.COURSE);
        handleSetFileAttribute(objectId, attributeId, message);
        break;
      case MessageTypes.GET_FILE:
        filename = message.readString();
        debug.message("ClientServerContext", "Got GET_FILE filename=" + filename);
        theFile = new File(filename);
        transport.writeMessage(new FileMessage(theFile));
        break;
      case MessageTypes.FILE:
        filename = message.readString();
        theFile = new File(filename);
        lastModified = message.readInt();
        fileData = message.readByteArray();
        debug.message("ClientServerContext", "Got FILE filename=" + filename + ", lastModified=" + lastModified, debug.COURSE);
        theFile.writeByteArray(fileData);
        theFile.setLastModified(lastModified);
        handleFileAvailable(theFile);
        break;
      case MessageTypes.METHOD_CALL:
        objectId = message.readObjectId();
        methodId = message.readInt();
        debug.message("ClientServerContext", "Got METHOD_CALL methodId=" + methodId + ", objectId=" + objectId + ", object=" + objectRegistry.findObject(objectId).toString(), debug.COURSE);
        handleMethodCall(objectId, methodId, message);
        break;
      case MessageTypes.CALLBACK:
        objectId = message.readObjectId();
        callbackMethodId = message.readInt();
        debug.message("ClientServerContext", "Got CALLBACK callbackMethodId=" + callbackMethodId + ", objectId=" + objectId + ", object=" + objectRegistry.findObject(objectId).toString(), debug.COURSE);
        handleCallback(objectId, callbackMethodId, message);
        break;
      case MessageTypes.ENABLE_CALLBACKS:
        objectId = message.readObjectId();
        debug.message("ClientServerContext", "Got ENABLE_CALLBACKS objectId=" + objectId + ", object=" + objectRegistry.findObject(objectId).toString(), debug.COURSE);
        handleEnableCallbacks(objectId);
        break;
      case MessageTypes.DISABLE_CALLBACKS:
        objectId = message.readObjectId();
        debug.message("ClientServerContext", "Got DISABLE_CALLBACKS objectId=" + objectId + ", object=" + objectRegistry.findObject(objectId).toString(), debug.COURSE);
        handleDisableCallbacks(objectId);
        break;
      default:
        handleUnexpectedMessage(message);
        break;
    }
  }


  protected void handleError(String message) {
    textOutput.showError(message);
  }


  protected void handleError(String message, Throwable e) {
    //e.printStackTrace();
    textOutput.showError(message, e);
  }


  protected abstract void handleConstruct(int classId, int objectId);


  protected abstract void handleSetAttribute(int objectId, int attributeId, Message message);


  protected void handleSetFileAttribute(int objectId, int attributeId, Message message) {
    handleSetAttribute(objectId, attributeId, message);
  }


  protected void handleFileAvailable(File file) { }


  protected abstract void handleMethodCall(int objectId, int methodId, Message message);


  protected abstract void handleCallback(int objectId, int callbackMethodId, Message message);


  protected abstract void handleEnableCallbacks(int objectId);


  protected abstract void handleDisableCallbacks(int objectId);


  protected void handleUnexpectedMessage(Message message) {
    handleError("ClientServerContext: unexpected message [" + message.getMessageType() + "]");
  }


  protected void closeContext() {
    transport.close();
    objectRegistry.clear();
  }

}

