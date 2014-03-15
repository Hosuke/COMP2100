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

import armidale.api.context.*;

import armidale.api.context.platform.*;
import armidale.api.io.Debug;
import armidale.api.io.AbstractTextOutput;
import armidale.api.io.StreamTextOutput;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.io.IOException;

import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;

public class LocalApplication {

  protected AbstractTextOutput textOutput;
  protected String   appName;
  protected String[] appArgs;
  
  public LocalApplication(String appName, String[] appArgs, AbstractTextOutput textOutput) {
    this.textOutput = textOutput;
    this.appName = appName;
    this.appArgs = appArgs;
  }


  public static void main(String[] args) {
    String[]           tempArgs  = new String[args.length - 1];
    for (int i = 0; i < tempArgs.length; i++) {
      tempArgs[i] = args[i + 1];
    }
    LocalApplication  app = new LocalApplication(args[0], tempArgs, new StreamTextOutput(System.out));
    app.start();
  }


  public void start() {
    Class       theClass;
    Application userApp;
    
    try {
      theClass = Class.forName(appName);
      userApp = (Application) theClass.newInstance();
      userApp.setArgs(appArgs);
      userApp.setContext(new SwingContext());
      userApp.start();
    } catch (Exception e) {
      throw new UncheckedException("Unable to start local application", e);
    }
  }
  
}

