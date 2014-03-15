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

  import java.io.*;
  
public class UncheckedException extends ArmidaleException {

  private Exception theException;
  private String    stackTraceback = "";
  
  public UncheckedException(String message) {
    super(message);
  }

  public UncheckedException(Exception theException) {
    super(theException.toString());
    saveException(theException);
  }

  public UncheckedException(String message, Exception theException) {
    super(message + " [" + theException.toString() + "]");
    saveException(theException);
  }

  public Exception getException() {
    return theException;
  }
  
  public void printStackTrace(PrintWriter s) {
    synchronized (s) {
      s.print(getClass().getName() + ": ");
      s.print(stackTraceback);
    }
  }
  
  public void rethrow() throws Exception {
    throw theException;
  }
  
  private void saveException(Exception theException) {
    this.theException = theException;
    if (theException != null) {
      StringWriter stringWriter = new StringWriter();
      theException.printStackTrace(new PrintWriter(stringWriter));
      stackTraceback = stringWriter.toString();
    }
  }
  
}

