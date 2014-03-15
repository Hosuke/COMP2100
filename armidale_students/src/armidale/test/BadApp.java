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
 
package armidale.test;

import armidale.api.Application;
import armidale.api.ClassInformation;
import armidale.api.context.*;

import armidale.api.context.clientserver.*;
import armidale.api.context.platform.*;

import java.io.IOException;

public class BadApp extends Application {

  public static void main(String[] args) {
    BadApp  app  = new BadApp();
    app.setContext(new SwingContext());
    app.start();
  }


  public ClassInformation getClassInformation() {
    ClassInformation  classInformation  = new ClassInformation("armidale.test.BadApp", "1.0", "Fred Nurk", "");
    classInformation.addRequiredClass(new ClassInformation("fred.class", "1.3", "unknown", ""));
    classInformation.addRequiredClass(new ClassInformation("armidale.extra.3DGraph", "0.1.0", "Fred Nurk", "http://fred-nurk-inc.com"));
    classInformation.addRequiredClass(new ClassInformation("bill.ben.firepot.men"));
    return classInformation;
  }


  public void init() { }

}

