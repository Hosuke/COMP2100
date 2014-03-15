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

import armidale.api.io.Debug;
import armidale.api.Application;
import armidale.api.context.clientserver.*;
import armidale.api.io.AbstractTextOutput;
import armidale.api.io.StreamTextOutput;
import armidale.api.ProductInfo;
import armidale.api.Environment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//import javax.net.ssl.*;

public class Server extends Thread {

  private         int                 port;
  private         AbstractTextOutput  textOutput;
  private         Debug               debug;

  public Server(int port, AbstractTextOutput textOutput) {
    this.port       = port;
    this.textOutput = textOutput;
    debug = new Debug(textOutput);
  }

  public void run() {
    ServerSocket         serverSocket;
    Socket               clientSocket;
    //SSLServerSocketFactory  serverSocketFactory;
    //SSLServerSocket         serverSocket;
    //SSLSocket               clientSocket;
    StreamTransport      transport;
    ServerContext        context;
    String               className;
    Class                theClass;
    Application          userApp;
    int                  clientVersion;
    Message              message;
    int                  messageType;
    MessageInputStream   inputStream;
    MessageOutputStream  outputStream;
    String[]             args;

    textOutput.showInformation("Server", "Server thread started");

    try {
      try {
        //serverSocketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        //serverSocket = (SSLServerSocket)serverSocketFactory.createServerSocket(port);
        serverSocket = new ServerSocket(port);
      } catch (IOException e1) {
        throw new UncheckedException("Unable to establish server socket on port " + port, e1);
      }

      while (!interrupted()) {
        debug.message("server", "waiting for a client to connect");
        clientSocket = serverSocket.accept();
//        textOutput.showInformation("Server", "client connected, " + clientSocket.toString());
        textOutput.showInformation("Server", "client connected, " + clientSocket.getInetAddress().getHostName());
        inputStream = new MessageInputStream(clientSocket.getInputStream());
        outputStream = new MessageOutputStream(clientSocket.getOutputStream());
        message = inputStream.readMessage();
        debug.message("server", "message read");
        messageType = message.getMessageType();
        debug.message("server", "  - type = " + messageType);
        switch (messageType) {
          case MessageTypes.START_APPLICATION:
            className = message.readString();
            try {
              theClass = Class.forName(className);
              userApp = (Application) theClass.newInstance();
              args = new String[message.readInt()];
              for (int i = 0; i < args.length; i++) {
                args[i] = message.readString();
              }
              userApp.setArgs(args);
              outputStream.writeMessage(new ClassInformationMessage(userApp.getClassInformation()));
              message = inputStream.readMessage();
              messageType = message.getMessageType();
              switch (messageType) {
                case MessageTypes.CLIENT_OK:
                  transport = new StreamTransport(inputStream, outputStream);
                  context = new ServerContext(transport, textOutput);
                  userApp.setContext(context);
                  textOutput.showInformation("Server", "starting " + userApp.getClassInformation() + ", context=" + context.toString());
                  userApp.start();
                  break;
                case MessageTypes.ERROR:
                  textOutput.showError("armidale Server", message.readString());
                  break;
                default:
                  textOutput.showError("armidale server", "Expected 'CLIENT_OK' or 'ERROR' message");
                  break;
              }
            } catch (Exception e) {
              outputStream.writeMessage(new ErrorMessage("Unable to open application (" + e.getMessage() + ")"));
              textOutput.showError
                ("armidale Server",
                "Unable to run user thread (" + e.toString() + ")"
                );
            }
            break;
          default:
            textOutput.showError
              ("armidale Server",
              "Expected START_APPLICATION message (got " + message.toString() + ")"
              );
            return;
        }
      }
    } catch (Throwable t) {
      textOutput.showError("Server", t);
    } finally {
      textOutput.close();
    }
  }


  public void close() {
    this.interrupt();
  }


  private static String titleString = "armidale - Server " + ProductInfo.versionAsString();


  private static void usageError(String message) {
    System.out.println("Usage: armidale.api.distributed.server.Server [-port port_number] [-output filename]");
    System.out.println("     : " + message);
    System.exit(0);
  }


  public static void main(String[] args) {

    Class             theClass;
    Object            theApp;
    Server            appServer;

    int               port          = Environment.defaultPort();
    String            filename      = "";
    PrintStream       outputStream  = System.out;
    StreamTextOutput  textOutput    = new StreamTextOutput(outputStream);
    PrintStream       ipLog;
    boolean           append;
    
    try {
      for (int i = 0; i < args.length; i++) {
        if (args[i].equals("-port")) {
          i++;
          if (i < args.length) {
            port = Integer.parseInt(args[i]);
          } else {
            usageError("port number missing after -port");
          }
        } else if (args[i].equals("-newlog") || args[i].equals("-log")) {
          append = args[i].equals("-log");
          i++;
          if (i < args.length) {
            filename = args[i];
            try {
              outputStream = new PrintStream(new FileOutputStream(filename, append));
              textOutput   = new StreamTextOutput(outputStream);
            } catch (Throwable t) {
              usageError("can't open output file - " + t.getMessage());
            }
          } else {
            usageError("filename missing after -output");
          }
        } else {
          usageError("'" + args[i] + "' is not a valid argument");
        }
      }
      textOutput.show(titleString, "");

      appServer = new Server(port, textOutput);
      appServer.start();

    } catch (Throwable t) {
      textOutput.showError("Server.main", t);
    }
  }

}

