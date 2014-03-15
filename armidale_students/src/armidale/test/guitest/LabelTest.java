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
 
package armidale.test.guitest;

import armidale.api.context.*;
import armidale.api.context.platform.*;

import armidale.api.gui.*;
import armidale.api.gui.constants.*;

class LabelTest {

  private  Label  label;


  public LabelTest(Context context, TestImages testImages) {
    label = LabelFactory.create(context);
    label.setText("<html><body><h1><b><i>Right click here to show popup menu</i></b></h1></body></html>");
    label.setAlignment(Label.CENTER);
    label.setTextPosition(Label.SOUTH);
    label.setImage(testImages.checkIcon);

    PopupMenu  popupMenu       = PopupMenuFactory.create(context);
    MenuItem   popupMenuItems[]  = new MenuItem[6];
    for (int i = 0; i < popupMenuItems.length; i++) {
      popupMenuItems[i] = MenuItemFactory.create(context);
      popupMenuItems[i].setText("menu item " + i);
      popupMenu.addMenuItem(popupMenuItems[i]);
    }

    label.setPopupMenu(popupMenu);
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new LabelTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return label;
  }
}

