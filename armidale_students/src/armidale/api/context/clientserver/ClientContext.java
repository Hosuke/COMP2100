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

import armidale.api.io.AbstractTextOutput;
import armidale.api.io.Debug;
import armidale.api.io.File;

import java.util.LinkedList;
import java.util.ListIterator;

public abstract class ClientContext extends ClientServerContext {

  private  LinkedList  fileMonitorList;


  public ClientContext(AbstractTextOutput textOutput, Transport transport) {
    super(textOutput, transport);
    fileMonitorList = new LinkedList();
  }


  public void startApplication(String appUrl, String[] appArgs) {
    try {
      RemoteApplication  application  = new RemoteApplication(appUrl, appArgs);
      application.start();
    } catch (Throwable e) {
      handleError("Cannot open '" + appUrl + "'", e);
    }
  }



  protected void handleConstruct(int classId, int objectId) {
    debug.message("ClientContext", "constructing object " + objectId + " of class " + classId);
    MessagingObject  theObject  = ClassList.createObject(classId, objectId, this);
    objectRegistry.registerObject(theObject);
  }


  protected void handleSetAttribute(int objectId, int attributeId, Message message) {
    debug.message("ClientContext", "setting attribuite " + attributeId + " of object " + objectId);
    MessagingObject  theObject  = objectRegistry.findObject(objectId);
    if (theObject != null) {
      theObject.handleSetAttribute(attributeId, message);
    }
  }


  protected void handleSetFileAttribute(int objectId, int attributeId, Message message) {
    int     dummyInt;
    debug.message("ClientContext", "updating file attribute " + attributeId + " of object " + objectId);
    String  filename      = message.readString();
    int     lastModified  = message.readInt();
    boolean fileExists    = (lastModified != SetFileAttributeMessage.NO_SUCH_FILE);
    if (fileExists) {
      // reset data read ptr
      message.resetReadPtr();
      dummyInt = message.readObjectId();
      dummyInt = message.readInt();
      // handle the file
      File    theFile       = new File(filename);
      if (theFile.isCurrent(lastModified)) {
        debug.message("ClientContext", "   - file '" + filename + "' exists");
        handleSetAttribute(objectId, attributeId, message);
      } else {
        debug.message("ClientContext", "   - file '" + filename + "' does NOT exist");
        fileMonitorList.add(new FileMonitor(filename, message));
        transport.writeMessage(new GetFileMessage(filename));
      }
    } else {
      message.resetReadPtr();
      dummyInt = message.readObjectId();
      dummyInt = message.readInt();
      handleSetAttribute(objectId, attributeId, message);
    }
  }


  protected void handleFileAvailable(File file) {
    ListIterator  iterator        = fileMonitorList.listIterator(0);
    FileMonitor   theFileMonitor;
    while (iterator.hasNext()) {
      theFileMonitor = (FileMonitor) iterator.next();
      if (theFileMonitor.filename.equals(file.getName())) {
        handleSetAttribute(theFileMonitor.objectId, theFileMonitor.attributeId, theFileMonitor.message);
        iterator.remove();
      }
    }
  }


  protected void handleCallback(int objectId, int callbackMethodId, Message message) {
    handleError("ClientMessenger: callback textOutput not supported by this agen");
  }


  protected abstract void handleClassNotFound(int classId);

}

