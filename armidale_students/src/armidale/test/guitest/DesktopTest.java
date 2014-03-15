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
import armidale.api.io.*;

class DesktopTest {

  private  Desktop        desktop;

  private  InternalFrame  internalFrame1;
  private  InternalFrame  internalFrame2;
  private  InternalFrame  internalFrame3;
  private  InternalFrame  internalFrame4;

  private  CheckBox       internalInputCheckBox;


  public DesktopTest(Context context, TestImages testImages) {
    desktop = DesktopFactory.create(context);
           Image       wallpaper              = ImageFactory.create(context);
    wallpaper.setImageData(File.createByteArray("images/blue_angle_swirl.jpg"));
    desktop.setWallpaper(wallpaper);
    desktop.setWallpaperMode(Desktop.TILED);

    internalFrame1 = InternalFrameFactory.create(context);
    internalFrame1.addWindowCallback(new WindowCloseCallback(context, WindowCloseCallback.HIDE_ON_CLOSE));
    internalFrame1.setTitle("Hide on Close");
    internalFrame1.setSize(new Size(400, 400));
    internalFrame1.setVisible(true);
    desktop.addInternalFrame(internalFrame1);

    internalFrame2 = InternalFrameFactory.create(context);
    internalFrame2.addWindowCallback(new WindowCallbackDebug(context));
    internalFrame2.setTitle("Not Closable");
    internalFrame2.setSize(new Size(200, 200));
    internalFrame2.setVisible(true);
    desktop.addInternalFrame(internalFrame2);

    internalFrame3 = InternalFrameFactory.create(context);
    internalFrame3.addWindowCallback(new WindowCloseCallback(context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));
    internalFrame3.setTitle("Stop App on Close");
    internalFrame3.setSize(new Size(200, 200));
    internalFrame3.setVisible(true);
    desktop.addInternalFrame(internalFrame3);

    internalFrame4 = InternalFrameFactory.create(context);
    internalFrame4.addWindowCallback(new WindowCloseCallback(context, WindowCloseCallback.DISPOSE_ON_CLOSE));
    internalFrame4.setTitle("Dispose on Close");
    internalFrame4.setSize(new Size(200, 200));
    internalFrame4.setVisible(true);
    desktop.addInternalFrame(internalFrame4);

    internalFrame2.setClosable(false);
    internalFrame3.setIconifiable(false);
    internalFrame4.setMaximizable(false);

    // internal optionBoxes
    //
    final  OptionBox   internalOptionBox      = OptionBoxFactory.create(context);

    BoxPanel    internalOptionPanel    = BoxPanelFactory.create(context);
    internalOptionPanel.setOrientation(BoxPanel.VERTICAL);
    internalOptionPanel.addVerticalSpring();

    internalInputCheckBox = CheckBoxFactory.create(context);
    internalInputCheckBox.setText("enable input box");
    internalInputCheckBox.setSelected(false);
    internalInputCheckBox.addToggleCallback(new ToggleUpdateCallback());

    internalOptionPanel.addWidget(internalInputCheckBox);
    internalOptionPanel.addRigidArea(new Size(300, 4));

    PushButton  internalOptionButtons[]  = new PushButton[8];
    for (int i = 0; i < internalOptionButtons.length; i++) {
      internalOptionButtons[i] = PushButtonFactory.create(context);
      internalOptionButtons[i].setMinimumSize(new Size(300, 30));
      internalOptionButtons[i].setPreferredSize(new Size(300, 30));
      internalOptionButtons[i].setMaximumSize(new Size(300, 30));
      internalOptionPanel.addWidget(internalOptionButtons[i]);
      internalOptionPanel.addRigidArea(new Size(300, 4));
    }
    internalOptionPanel.addVerticalSpring();

    internalOptionBox.addOptionBoxCallback(new OptionBoxCallbackDebug(context));

    internalOptionButtons[0].setText("Error Option Box");
    internalOptionButtons[0].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Error");
          internalOptionBox.setMessage("Error Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.ERROR);
          internalOptionBox.setInput(internalInputCheckBox.isSelected());
          internalOptionBox.open(desktop);
        }
      });

    internalOptionButtons[1].setText("Information Option Box");
    internalOptionButtons[1].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Information");
          internalOptionBox.setMessage("Information Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.INFORMATION);
          internalOptionBox.setInput(internalInputCheckBox.isSelected());
          internalOptionBox.open(desktop);
        }
      });

    internalOptionButtons[2].setText("Warning Option Box");
    internalOptionButtons[2].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Warning");
          internalOptionBox.setMessage("Warning Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.WARNING);
          internalOptionBox.setInput(internalInputCheckBox.isSelected());
          internalOptionBox.open(desktop);
        }
      });

    internalOptionButtons[3].setText("Question Option Box");
    internalOptionButtons[3].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Question");
          internalOptionBox.setMessage("Question Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.QUESTION);
          internalOptionBox.setInput(internalInputCheckBox.isSelected());
          internalOptionBox.open(desktop);
        }
      });

    internalOptionButtons[4].setText("Plain Option Box");
    internalOptionButtons[4].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Plain");
          internalOptionBox.setMessage("Plain Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.PLAIN);
          internalOptionBox.setInput(internalInputCheckBox.isSelected());
          internalOptionBox.open(desktop);
        }
      });

    internalOptionButtons[5].setText("Plain Yes-No Option Box");
    internalOptionButtons[5].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Plain");
          internalOptionBox.setMessage("Plain Yes-No Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.PLAIN);
          internalOptionBox.setButtonSet(OptionBoxButtonSet.YES_NO);
          internalOptionBox.setInput(false);
          internalOptionBox.open(desktop);
        }
      });

    internalOptionButtons[6].setText("Plain Yes-No-Cancel Option Box");
    internalOptionButtons[6].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Plain");
          internalOptionBox.setMessage("Plain Yes-No-Cancel Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.PLAIN);
          internalOptionBox.setButtonSet(OptionBoxButtonSet.YES_NO_CANCEL);
          internalOptionBox.setInput(false);
          internalOptionBox.open(desktop);
        }
      });

    internalOptionButtons[7].setText("Plain Ok-Cancel Option Box");
    internalOptionButtons[7].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          internalOptionBox.setTitle("Plain");
          internalOptionBox.setMessage("Plain Ok-Cancel Option Box Message");
          internalOptionBox.setMessageType(OptionBoxMessageType.PLAIN);
          internalOptionBox.setButtonSet(OptionBoxButtonSet.OK_CANCEL);
          internalOptionBox.setInput(false);
          internalOptionBox.open(desktop);
        }
      });

    internalFrame1.setContent(internalOptionPanel);

  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    DesktopTest      desktop       = new DesktopTest(swingContext, testImages);
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, desktop.widget());
    desktop.alignFrames();
  }


  public void alignFrames() {
    internalFrame1.setAlignment(InternalFrame.NORTH);
    internalFrame2.setAlignment(InternalFrame.SOUTH);
    internalFrame3.setAlignment(InternalFrame.SOUTH_EAST);
    internalFrame4.setAlignment(InternalFrame.SOUTH_WEST);
  }


  public Widget widget() {
    return desktop;
  }

}

