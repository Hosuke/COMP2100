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
package armidale.armidaleslides;

import armidale.api.*;
import armidale.api.context.*;
import armidale.api.context.clientserver.*;
import armidale.api.context.platform.*;
import armidale.api.gui.*;
import armidale.api.gui.constants.*;
import armidale.api.io.*;

public class Main extends Application {

  String[] slideFiles;
  
  public static void main(String[] args) {

    Main  app  = new Main();
    app.setContext(new SwingContext());
    app.start();
  }


  public void init() {

    Frame        frame        = FrameFactory.create(context);

    frame.addWindowCallback
      (new WindowCloseCallback
      (context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));

    frame.setTitle("armidaleSLIDES");
    frame.setSize(new Size(600, 400));
    frame.setAlignment(Frame.CENTER);
    frame.setResizable(true);

    BorderPanel  mainPanel    = BorderPanelFactory.create(context);
    mainPanel.setBackgroundColor(Color.WHITE);

    Image        slide        = ImageFactory.create(context);
//    image.setFile(new File("images/armidaledemo.png"));

    Label        label        = LabelFactory.create(context);
    label.setImage(slide);
    label.setAlignment(Label.CENTER);

    mainPanel.addWidget
      (BorderPanelWidgetFactory.create
      (context, label, BorderPanelWidget.CENTER));

    PushButton   firstButton  = PushButtonFactory.create(context);
    firstButton.setIcon(ImageFactory.create(context, new File("images/icons/media/Rewind24.gif")));
    firstButton.setText("");
    firstButton.setPreferredSize(new Size(30, 30));
    firstButton.setToolTip("first slide");

    PushButton   backButton   = PushButtonFactory.create(context);
    backButton.setIcon(ImageFactory.create(context, new File("images/icons/media/StepBack24.gif")));
    backButton.setText("");
    backButton.setPreferredSize(new Size(30, 30));
    backButton.setToolTip("previous slide");

    PushButton   fwdButton    = PushButtonFactory.create(context);
    fwdButton.setIcon(ImageFactory.create(context, new File("images/icons/media/StepForward24.gif")));
    fwdButton.setText("");
    fwdButton.setPreferredSize(new Size(30, 30));
    fwdButton.setToolTip("next slide");

    PushButton   lastButton   = PushButtonFactory.create(context);
    lastButton.setIcon(ImageFactory.create(context, new File("images/icons/media/FastForward24.gif")));
    lastButton.setText("");
    lastButton.setPreferredSize(new Size(30, 30));
    lastButton.setToolTip("last slide");

    PushButton   stopButton   = PushButtonFactory.create(context);
    stopButton.setIcon(ImageFactory.create(context, new File("images/icons/media/Stop24.gif")));
    stopButton.setText("");
    stopButton.setPreferredSize(new Size(30, 30));
    stopButton.setToolTip("end slide show");
    stopButton.addActionCallback(new StopCallback());

    FlowPanel    buttonPanel  = FlowPanelFactory.create(context);
    buttonPanel.setBackgroundColor(Color.WHITE);
    buttonPanel.setHorizontalGap(6);
    buttonPanel.setVerticalGap(6);

    buttonPanel.addWidget(firstButton);
    buttonPanel.addWidget(backButton);
    buttonPanel.addWidget(fwdButton);
    buttonPanel.addWidget(lastButton);
    buttonPanel.addWidget(stopButton);

    mainPanel.addWidget
      (BorderPanelWidgetFactory.create
      (context, buttonPanel, BorderPanelWidget.SOUTH));

    frame.setContent(mainPanel);

    frame.show();
  }

  private class StopCallback implements ActionCallback {
    
    public void actionPerformed(Action action) {
//      if (SimpleOptionBox.showQuestionBox(context, "Are you sure you want to exit?")==OptionBoxButton.OK) {
        context.close();
//      }
    }
  }
  
}

