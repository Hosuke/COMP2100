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
import armidale.api.gui.*;
import armidale.api.gui.constants.*;

class ToolBarTest {

  private  FlowPanel  toolBar;


  public ToolBarTest(Context context, TestImages testImages) {

    toolBar = FlowPanelFactory.create(context);
    toolBar.setPreferredSize(new Size(100, 35));
    toolBar.setEdgeStyle(Panel.ETCHED);
    //toolBar.setOrientation(Orientation.HORIZONTAL);
    toolBar.setAlignment(FlowAlignment.LEFT);
    toolBar.setHorizontalGap(10);
    Label      toolBarHomeLabel  = LabelFactory.create(context, testImages.homeIcon);
    toolBarHomeLabel.addActionCallback(new ActionCallbackDebug(context));
    toolBar.addWidget(toolBarHomeLabel);
    TextField  toolBarTextField  = TextFieldFactory.create(context);
    toolBarTextField.setPreferredSize(new Size(150, 20));
    toolBarTextField.addTextCallback(new TextCallbackDebug(context));
    toolBar.addWidget(toolBarTextField);
    toolBar.addWidget(LabelFactory.create(context, testImages.stopIcon));
    toolBar.addWidget(LabelFactory.create(context, "toolbar"));
    toolBar.addWidget(LabelFactory.create(context, testImages.checkIcon));

    //toolBar.setBackgroundColor(Color.GREEN);
  }


  public Widget widget() {
    return toolBar;
  }

}

