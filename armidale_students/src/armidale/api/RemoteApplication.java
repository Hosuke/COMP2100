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

package armidale.api;

import armidale.api.Application;
import armidale.api.Environment;
import armidale.api.UncheckedException;


import armidale.api.context.*;

import armidale.api.context.clientserver.*;
import armidale.api.io.Debug;
import armidale.api.io.FrameTextOutput;
import armidale.api.util.Strings;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.IOException;

import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;

public class RemoteApplication extends Application {

  public RemoteApplication(String urlStr, String[] args) {
    super();
    connect(urlStr, args);
  }


  public RemoteApplication(String urlStr) {
    String[]  tempArgs  = Strings.split(urlStr.trim(), " \t");
    String[]  args      = new String[tempArgs.length - 1];
    for (int i = 0; i < tempArgs.length; i++) {
      args[i] = tempArgs[i + 1];
    }
    connect(tempArgs[0], args);
  }


  public static void main(String[] args) {
    String[]           tempArgs  = new String[args.length - 1];
    for (int i = 0; i < tempArgs.length; i++) {
      tempArgs[i] = args[i + 1];
    }
    RemoteApplication  app       = new RemoteApplication(args[0], tempArgs);
    app.start();
  }


  private void connect(String urlStr, String[] args) {
    URL                  url;
    String               protocol;
    int                  port;
    String               address;
    String               appName;
    MessageInputStream   inputStream;
    MessageOutputStream  outputStream;
    Transport            transport;

//System.out.println("REMOTE_APPLICATION: " + urlStr);

    if (urlStr != null && urlStr.length() > 0) {
      url = new URL(urlStr);
    } else {
      throw new ArgumentException("Url is null string");
    }

    protocol = url.getProtocol();
    address = url.getHost();
    appName = url.getAppName();
    port = url.getPort();
    if (port == -1) {
      port = Environment.defaultPort();
    }

    Socket               appSocket;
    boolean              connected     = false;

    try {
      appSocket = new Socket(address, port);
      inputStream = new MessageInputStream(appSocket.getInputStream());
      outputStream = new MessageOutputStream(appSocket.getOutputStream());
      transport = new StreamTransport(inputStream, outputStream);
      context = new SwingClientContext(transport);
      setContext(context);
    } catch (Exception t) {
      throw new UncheckedException("Unable to connect to server", t);
    }

    context.getDebug().message("RemoteApplication", "protocol='" + protocol + "', address='" + address + "', port='" + port + "', application='" + appName + "'");

    transport = ((SwingClientContext) context).getTransport();
    transport.writeMessage(new StartApplicationMessage(appName, args));

    Message              message       = transport.readMessage();
    int                  messageType   = message.getMessageType();

    switch (messageType) {
      case MessageTypes.CLASS_INFORMATION:
        ClassInformation[] missingClasses  = getMissingClasses(message);
        if (missingClasses == null) {
          transport.writeMessage(new ClientOKMessage());
        } else {
          transport.writeMessage
             (new ErrorMessage(   "The following classes were not available on the client:\n" 
                                + ClassInformation.toString(missingClasses)
             ));
          throw new MissingClassesException("classes not available on client", missingClasses);
        }
        break;
      case MessageTypes.ERROR:
        String               errorMessage  = message.readString();
        throw new CommunicationException("Server Error: " + errorMessage);
      default:
        context.getDebug().message("RemoteApplication", "got '" + message.toString() + "' from server");
        throw new CommunicationException("Server Error: Server did not respond correctly");
    }
  }

  
  private ClassInformation[] getMissingClasses(Message message) {

    ClassInformation[]  classInformation;
    ClassInformation[]  result;
    int missingClassCount = 0;
    
    String              name             = message.readString();
    String              author           = message.readString();
    String              version          = message.readString();
    String              homePage         = message.readString();

    classInformation = new ClassInformation[message.readInt()];

    for (int i = 0; i < classInformation.length; i++) {
      classInformation[i] = new ClassInformation(message.readString());
      classInformation[i].setAuthor(message.readString());
      classInformation[i].setVersion(message.readString());
      classInformation[i].setHomePageUrl(message.readString());
      if (ClassList.classAvailable(classInformation[i].getName())) {
        classInformation[i] = null;
      } else {
        missingClassCount++;
      }
    }
    if (missingClassCount == 0) {
      return null;
    } else {
      result = new ClassInformation[missingClassCount];
      int j=0;
      for (int i=0; i<classInformation.length; i++) {
        if (classInformation[i] != null) {
          result[j] = classInformation[i];
          j++;
        }
      }
      return result;
    }
  }

}

