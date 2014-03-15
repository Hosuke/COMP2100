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

class OptionBoxTest {

  private  BoxPanel  optionPanel;
  private  CheckBox  inputCheckBox;


  public OptionBoxTest(Context context, TestImages testImages) {

    final  OptionBox   optionBox      = OptionBoxFactory.create(context);

    optionPanel = BoxPanelFactory.create(context);
    optionPanel.setOrientation(BoxPanel.VERTICAL);
//    optionPanel.addHorizontalStrut(100);
    optionPanel.addVerticalSpring();

    inputCheckBox = CheckBoxFactory.create(context);
    inputCheckBox.setText("enable input box");
    inputCheckBox.setSelected(false);
    inputCheckBox.addToggleCallback(new ToggleUpdateCallback());

    optionPanel.addWidget(inputCheckBox);
    optionPanel.addRigidArea(new Size(300, 4));

    PushButton  optionButtons[]  = new PushButton[8];
    for (int i = 0; i < optionButtons.length; i++) {
      optionButtons[i] = PushButtonFactory.create(context);
      optionButtons[i].setMinimumSize(new Size(300, 30));
      optionButtons[i].setPreferredSize(new Size(300, 30));
      optionButtons[i].setMaximumSize(new Size(300, 30));
      optionPanel.addWidget(optionButtons[i]);
      optionPanel.addRigidArea(new Size(300, 4));
    }
    optionPanel.addVerticalSpring();

    optionBox.addOptionBoxCallback(new OptionBoxCallbackDebug(context));

    optionButtons[0].setText("Error Option Box");
    optionButtons[0].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Error");
          optionBox.setMessage("Error Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.ERROR);
          optionBox.setInput(inputCheckBox.isSelected());
          optionBox.open();
        }
      });

    optionButtons[1].setText("Information Option Box");
    optionButtons[1].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Information");
          optionBox.setMessage("Information Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.INFORMATION);
          optionBox.setInput(inputCheckBox.isSelected());
          optionBox.open();
        }
      });

    optionButtons[2].setText("Warning Option Box");
    optionButtons[2].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Warning");
          optionBox.setMessage("Warning Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.WARNING);
          optionBox.setInput(inputCheckBox.isSelected());
          optionBox.open();
        }
      });

    optionButtons[3].setText("Question Option Box");
    optionButtons[3].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Question");
          optionBox.setMessage("Question Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.QUESTION);
          optionBox.setInput(inputCheckBox.isSelected());
          optionBox.open();
        }
      });

    optionButtons[4].setText("Plain Option Box");
    optionButtons[4].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Plain");
          optionBox.setMessage("Plain Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.PLAIN);
          optionBox.setInput(inputCheckBox.isSelected());
          optionBox.open();
        }
      });

    optionButtons[5].setText("Plain Yes-No Option Box");
    optionButtons[5].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Plain");
          optionBox.setMessage("Plain Yes-No Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.PLAIN);
          optionBox.setButtonSet(OptionBoxButtonSet.YES_NO);
          optionBox.setInput(false);
          optionBox.open();
        }
      });

    optionButtons[6].setText("Plain Yes-No-Cancel Option Box");
    optionButtons[6].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Plain");
          optionBox.setMessage("Plain Yes-No-Cancel Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.PLAIN);
          optionBox.setButtonSet(OptionBoxButtonSet.YES_NO_CANCEL);
          optionBox.setInput(false);
          optionBox.open();
        }
      });

    optionButtons[7].setText("Plain Ok-Cancel Option Box");
    optionButtons[7].addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          optionBox.setTitle("Plain");
          optionBox.setMessage("Plain Ok-Cancel Option Box Message");
          optionBox.setMessageType(OptionBoxMessageType.PLAIN);
          optionBox.setButtonSet(OptionBoxButtonSet.OK_CANCEL);
          optionBox.setInput(false);
          optionBox.open();
        }
      });

  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new OptionBoxTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return optionPanel;
  }

}

