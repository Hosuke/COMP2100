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

import armidale.api.*;
import armidale.api.context.*;
import armidale.api.context.platform.*;

import armidale.api.gui.*;
import armidale.api.io.*;
import armidale.api.gui.constants.*;

public class Home extends Application {

  public void init() {
    Frame frame = FrameFactory.create(context);
    frame.addWindowCallback
      ( new WindowCloseCallback
              (context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));
    frame.setTitle("Welcome to Armidale");
    frame.setSize(new Size(600, 340));
    frame.setAlignment(Frame.CENTER);
    frame.setResizable(false);

    Label welcomeText = LabelFactory.create(context);
    welcomeText.setText(
        "<html><body><center><h1>Welcome to the <font color=\"purple\">armidale</font> server</h1>"
      + "<font color=\"black\">"
      + "If you have any comments, suggestions or problems, please "
      + "email the author at <font color=\"blue\">shayne@cs.anu.edu.au</font> or register "
      + "a comment at <font color=\"blue\">http://sourceforge.net/projects/armidale.</font><br><br>"
      + "Please click the following links to run the <font color=\"purple\">armidale</font> demo programs.</font></center></body></html>"
      );
    
    
    Image        image        = ImageFactory.create(context);
    image.setFile(new File("images/armidaledemo.png"));

    Label guiTestLink = LabelFactory.create(context);
    guiTestLink.setImage(image);
    guiTestLink.addActionCallback(new LinkActionCallback(guiTestLink, "arm://armidale.anu.edu.au/armidale.test.guitest.Main", null, context));

    Label armidaleImageLink = LabelFactory.create(context);
    armidaleImageLink.setText("<html><body><center><h1><font color=\"purple\">armidale</font><font color=\"red\">IMAGE</font></h1></center></body></html>");
    armidaleImageLink.addActionCallback(new LinkActionCallback(armidaleImageLink, "arm://armidale.anu.edu.au/armidale.test.ArmidaleImage", null, context));

    BoxPanel linkPanel = BoxPanelFactory.create(context);
    linkPanel.setOrientation(BoxPanel.VERTICAL);
    linkPanel.addVerticalSpring();
    linkPanel.addWidget(welcomeText);
    linkPanel.addVerticalStrut(20);
    linkPanel.addWidget(guiTestLink);
    linkPanel.addVerticalStrut(20);
    linkPanel.addWidget(armidaleImageLink);
    linkPanel.addVerticalSpring();
    linkPanel.setBackgroundColor(Color.WHITE);
    
    frame.setContent(linkPanel);
    frame.show();
  }


  public final static void main(String[] args) {
    Home          homeApp  = new Home();
    
    homeApp.setContext(new SwingContext());
    homeApp.setArgs(args);
    homeApp.start();
  }
}

