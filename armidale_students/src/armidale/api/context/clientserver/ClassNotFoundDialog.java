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

import javax.swing.JOptionPane;

class ClassNotFoundDialog {

  public final static  int  MORE_INFORMATION  = 0;
  public final static  int  CANCEL            = 1;


  public ClassNotFoundDialog() { }


  public int show() {
    String[]  options  = {"More Information", "Cancel"};
    int       temp     = JOptionPane.showOptionDialog
      (null,
      "Your computer is unable process the following request"
//         + "\n\n      " + context.getURL().toString()
       + "\n\nClick the 'More Information' button below for details and help"
       + "\nor the 'Cancel' button to move on.\n  ",
      "Armidale",
      JOptionPane.OK_CANCEL_OPTION,
      JOptionPane.ERROR_MESSAGE,
      null,
      options,
      options[0]);
    if (temp == JOptionPane.OK_OPTION) {
      return MORE_INFORMATION;
    } else {
      return CANCEL;
    }
  }
}

