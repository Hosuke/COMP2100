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

class LinkLabelTest {

  private  TextField  hostBox;
  private  Label      hostLabel;
  private  Label      goodLink;
  private  Label      badLink;
  private  Label      badAppLink;
  private  BoxPanel   linkPanel;


  public LinkLabelTest(Context context, TestImages testImages) {
    hostLabel = LabelFactory.create(context);
    hostLabel.setText("Host name");
    hostBox = TextFieldFactory.create(context);
    hostBox.setSize(new Size(300, 25));
    hostBox.setText("armidale.anu.edu.au");
    hostBox.addTextCallback(new HostCallback(context));

    goodLink = LabelFactory.create(context);
    goodLink.setText("Click here to start SimpleApp");
    goodLink.addActionCallback(new LinkActionCallback(goodLink, "arm://" + hostBox.getText() + ":3000/armidale.test.SimpleApp", null, context));
    badLink = LabelFactory.create(context);
    badLink.setText("Click here to try and start a non-existent application");
    badLink.addActionCallback(new LinkActionCallback(badLink, "arm://" + hostBox.getText() + ":3000/nonExistentClass", null, context));
    badAppLink = LabelFactory.create(context);
    badAppLink.setText("Click here to try and start an application that cannot run on this computer");
    badAppLink.addActionCallback(new LinkActionCallback(badAppLink, "arm://" + hostBox.getText() + ":3000/armidale.test.BadApp", null, context));

    linkPanel = BoxPanelFactory.create(context);
    linkPanel.setOrientation(BoxPanel.VERTICAL);
    linkPanel.addVerticalSpring();
    linkPanel.addWidget(hostLabel);
    linkPanel.addWidget(hostBox);
    linkPanel.addVerticalStrut(40);
    linkPanel.addWidget(goodLink);
    linkPanel.addVerticalStrut(40);
    linkPanel.addWidget(badLink);
    linkPanel.addVerticalStrut(40);
    linkPanel.addWidget(badAppLink);
    linkPanel.addVerticalSpring();
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new LinkLabelTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return linkPanel;
  }


  private class HostCallback implements TextCallback {

    private  Context  context;


    public HostCallback(Context context) {
      this.context = context;
    }


    public void textChanged(Text text, String theText) {
      goodLink.removeAllActionCallbacks();
      goodLink.addActionCallback(new LinkActionCallback(goodLink, "arm://" + theText + ":3000/armidale.test.SimpleApp", null, context));
      badLink.removeAllActionCallbacks();
      badLink.addActionCallback(new LinkActionCallback(badLink, "arm://" + theText + ":3000/nonExistentClass", null, context));
      badAppLink.removeAllActionCallbacks();
      badAppLink.addActionCallback(new LinkActionCallback(badAppLink, "arm://" + theText + ":3000/armidale.test.BadApp", null, context));
    }
  }
}

