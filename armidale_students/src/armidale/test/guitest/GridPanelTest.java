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

class GridPanelTest {

  private  GridPanel  gridPanel;


  public GridPanelTest(Context context, TestImages testImages) {

    gridPanel = GridPanelFactory.create(context);
    gridPanel.setBackgroundColor(new Color(153, 153, 51));
    PushButton       gridButton[]  = new PushButton[6];
    GridPanelWidget  gridWidget[]  = new GridPanelWidget[6];

    Insets           insets      = new Insets(4, 4, 4, 4);

    for (int i = 0; i < 4; i++) {
      gridButton[i] = PushButtonFactory.create(context);
      gridButton[i].setText("Grid " + (i + 1) + ", " + i);
      gridButton[i].addActionCallback(new ActionCallbackDebug(context));
      gridWidget[i] = GridPanelWidgetFactory.create(context, gridButton[i], i + 1, i);
      gridWidget[i].setWeightX(1.0f);
      gridWidget[i].setWeightY(1.0f);
      gridWidget[i].setInsets(insets);
      gridPanel.addWidget(gridWidget[i]);
    }

    gridButton[4] = PushButtonFactory.create(context);
    gridButton[4].setText("Left");
    gridButton[4].addActionCallback(new ActionCallbackDebug(context));
    gridWidget[4] = GridPanelWidgetFactory.create(context, gridButton[4], 0, 0, 1, 5);
    gridWidget[4].setWeightX(1.0f);
    gridWidget[4].setWeightY(1.0f);
    gridWidget[4].setInsets(insets);
    gridPanel.addWidget(gridWidget[4]);

    gridButton[5] = PushButtonFactory.create(context);
    gridButton[5].setText("Bottom");
    gridButton[5].addActionCallback(new ActionCallbackDebug(context));
    gridWidget[5] = GridPanelWidgetFactory.create(context, gridButton[5], 1, 4, 4, 1);
    gridWidget[5].setWeightX(1.0f);
    gridWidget[5].setWeightY(1.0f);
    gridWidget[5].setInsets(insets);
    gridPanel.addWidget(gridWidget[5]);

  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new GridPanelTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return gridPanel;
  }

}


