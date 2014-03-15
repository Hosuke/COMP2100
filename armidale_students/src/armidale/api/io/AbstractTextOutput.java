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

import armidale.api.UncheckedException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractTextOutput {

  public abstract void showError(String title, String message);


  public void showError(String message) {
    showError("error", message);
  }


  public void showError(String title, Throwable e) {
    if (e instanceof UncheckedException) {
      showError(title, e.getMessage()); // + " [" + ((UncheckedException)e).getException().getMessage() + "]");
    } else {
      showError(title, e.getMessage());
    }
  }


  public void showErrorStackTrace(String title, Throwable e) {
    showError(title, e);
    e.printStackTrace();
  }


  public void showError(Throwable e) {
    showError("error", e);
  }


  public abstract void showWarning(String title, String message);


  public void showWarning(String message) {
    showWarning("warning", message);
  }


  public abstract void showInformation(String title, String message);


  public void showInformation(String message) {
    showInformation("information", message);
  }


  public abstract void show(String title, String message);


  public void show(String message) {
    show("", message);
  }


  public abstract void println(String str);


  public abstract void println();


  public void close() { }


  protected String timeStamp() {
    Date              now   = new Date();
    SimpleDateFormat  date  = new SimpleDateFormat("ddMMMyyyy");
    SimpleDateFormat  time  = new SimpleDateFormat("HH:mm:ss");
    return "[" + date.format(now) + "-" + time.format(now) + "]";
  }

}

