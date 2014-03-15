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

class BorderPanelTest {

  private  BorderPanel  borderPanel;


  public BorderPanelTest(Context context, TestImages testImages) {
    borderPanel = BorderPanelFactory.create(context);
    //borderPanel.setHorizontalGap(10);
    //borderPanel.setVerticalGap(10);
    borderPanel.setTitle("BorderPanel");
    borderPanel.setEdgeStyle(BorderPanel.ETCHED);

    PushButton  button[]         = new PushButton[5];
    for (int i = 0; i < 4; i++) {
      button[i] = PushButtonFactory.create(context);
      button[i].setText("Btn " + i);
      button[i].addActionCallback(new ActionCallbackDebug(context));
      borderPanel.addWidget(BorderPanelWidgetFactory.create(context, button[i], i + 1));
    }

    button[0].setIcon(testImages.homeIcon);

    Label       armidaleLabel  = LabelFactory.create(context);
    armidaleLabel.setImage(testImages.armidaleImage);
    armidaleLabel.setAlignment(Label.CENTER);

    borderPanel.addWidget(BorderPanelWidgetFactory.create(context, armidaleLabel, BorderPanelWidget.CENTER));
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new BorderPanelTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return borderPanel;
  }

}

