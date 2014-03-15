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
 
package armidale.api.io;

import armidale.api.context.Context;
import java.io.IOException;

public class Debug {

  public final static  int                 COURSEST    = 6;
  public final static  int                 COURSER     = 5;
  public final static  int                 COURSE      = 4;
  public final static  int                 FINE        = 3;
  public final static  int                 FINER       = 2;
  public final static  int                 FINEST      = 1;
  public final static  int                 ALL         = 0;

  public final static  int                 ALLWAYS     = -1;

  private static       boolean             enabled     = false;
  private static       boolean             pause       = false;
  private static       int                 debugLevel  = 0;

  protected            AbstractTextOutput  textOutput;


  public Debug() {
    textOutput = new StreamTextOutput(System.out);
  }


  public Debug(AbstractTextOutput textOutput) {
    this.textOutput = textOutput;
  }


  public Debug(Context context) {
    this.textOutput = context.getTextOutput();
  }


  public void setEnabled(boolean state) {
    enabled = state;
  }


  public void setPause(boolean state) {
    pause = state;
  }


  public void setLevel(int level) {
    debugLevel = level;
  }


  public void message(String title, String[] message, int level) {
    int  temp;
    if ((enabled && (level >= debugLevel)) || level == ALLWAYS) {
      textOutput.println("DEBUG: [" + title + "] " + Thread.currentThread().toString());
      for (int i = 0; i < message.length; i++) {
        textOutput.println("     : " + message[i]);
      }
      textOutput.println();
      if (pause) {
        textOutput.println("     : press any key to continue");
        try {
          temp = System.in.read();
        } catch (IOException e) {
        }
      }
    }
  }


  public void message(String title, String message, int level) {
    String[]  msg  = new String[1];
    msg[0] = message;
    message(title, msg, level);
  }


  public void message(String[] message, int level) {
    message("debug", message, level);
  }


  public void message(String[] message) {
    message(message, FINEST);
  }


  public void message(String title, String[] message) {
    message(title, message, FINEST);
  }


  public void message(String message, int level) {
    message("debug", message, level);
  }


  public void message(String message) {
    message(message, FINEST);
  }


  public void message(String title, String message) {
    message(title, message, FINEST);
  }


  private class StackTraceException extends Exception {
  }
  
  public void showStackTrace() {
    try {
      throw new StackTraceException();
    } catch (StackTraceException e) {
      e.printStackTrace();
    }
  }

}

