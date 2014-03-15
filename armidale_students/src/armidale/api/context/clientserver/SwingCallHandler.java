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

import armidale.api.context.*;
import armidale.api.io.Debug;

class SwingCallHandler implements Runnable {

  private  SwingClientContext  context;
  private  int                 objectId;
  private  int                 methodId;
  private  Message             message;
  private  MessagingObject     theObject;


  public SwingCallHandler(SwingClientContext context, int objectId, int methodId, Message message) {
    this.context = context;
    this.objectId = objectId;
    this.methodId = methodId;
    this.message = message;
    theObject = context.getObjectRegistry().findObject(objectId);
  }


  public void run() {
    if (theObject != null) {
      context.getDebug().message("SwingCallHandler", "invoking call message handler: object=" + theObject.toString() + ", method=" + methodId, Debug.COURSE);
      try {
        theObject.handleMethodCall(methodId, message);
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }
  }

}

