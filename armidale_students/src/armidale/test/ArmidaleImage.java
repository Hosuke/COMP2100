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

import armidale.api.*;
import armidale.api.context.*;
import armidale.api.context.clientserver.*;
import armidale.api.context.platform.*;

import armidale.api.gui.*;
import armidale.api.gui.constants.*;
import armidale.api.io.*;
import armidale.api.util.*;

import java.io.IOException;

public class ArmidaleImage extends Application {

  private String armidaleRootDir = Strings.concat("demos", "armidaleIMAGE", java.io.File.separator);

  public void init() {

    Frame frame = FrameFactory.create(context);

    frame.addWindowCallback
        (new WindowCloseCallback
        (context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));

    frame.setTitle("armidaleIMAGE " + ProductInfo.versionAsString());
    frame.setSize(new Size(440, 470));
    frame.setAlignment(Frame.CENTER);
    frame.setResizable(true);

    String rootDir = File.getRoot() + armidaleRootDir;
    String[] fileNames = (new java.io.File(rootDir)).list(new ThumbFilter());

    if (fileNames != null && fileNames.length > 0) {
      FlowPanel thumbsPanel = FlowPanelFactory.create(context);
      thumbsPanel.setBackgroundColor(Color.WHITE);
      thumbsPanel.setAlignment(FlowPanel.LEFT);
      thumbsPanel.setVerticalGap(10);
      thumbsPanel.setHorizontalGap(10);
      frame.setContent(thumbsPanel);
      frame.show();

      Image[] image = new Image[fileNames.length];
      Label[] label = new Label[fileNames.length];
      int displayIndex = -1;

      for (int i = 0; i < fileNames.length; i++) {
        displayIndex++;
        image[i] = ImageFactory.create(context);
        image[i].setFile(new File(Strings.concat(armidaleRootDir, fileNames[i], java.io.File.separator)));
        label[i] = LabelFactory.create(context);
        label[i].setImage(image[i]);
        label[i].setAlignment(Label.CENTER);
        label[i].setText(fileNames[i]);
        label[i].setTextPosition(Label.SOUTH);
        label[i].addActionCallback(new ShowImageCallback(fileNames[i]));
        thumbsPanel.addWidget(label[i]);
        frame.refresh();
      }
    } else {
      Label noImages = LabelFactory.create(context);
      noImages.setText("No images found");
      frame.setContent(noImages);
      frame.show();
    }
  }


  private class ThumbFilter implements java.io.FilenameFilter {
    public boolean accept(java.io.File dir, String name) {
      return name.startsWith("THM_");
    }
  }

  public class ShowImageCallback implements ActionCallback {

    String thumbName;

    public ShowImageCallback(String thumbName) {
      this.thumbName = thumbName;
    }

    public void actionPerformed(Action action) {
      Frame imageFrame = FrameFactory.create(context);
      String fileName = Strings.concat(armidaleRootDir, "AUT_" + thumbName.substring(4), java.io.File.separator);

      imageFrame.addWindowCallback
          (new WindowCloseCallback
          (context, WindowCloseCallback.DISPOSE_ON_CLOSE));
      imageFrame.setTitle(fileName);
      imageFrame.setSize(new Size(640, 480));
      imageFrame.setResizable(true);
      imageFrame.setAlignment(Frame.CENTER);
      Image image = ImageFactory.create(context);
      image.setFile(new File(fileName));
      Label label = LabelFactory.create(context);
      label.setImage(image);
      label.setAlignment(Label.CENTER);
      imageFrame.setContent(label);
      imageFrame.show();
    }
  }

  public static void main(String[] args) {

    ArmidaleImage app = new ArmidaleImage();
    app.setContext(new SwingContext());
    app.start();
  }

}

