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

class LabeledWidgetTest {

  private final static  String      THE_TEXT            = "This is some label text";

  private               BoxPanel    labeledWidgetPanel;
  private               TestImages  theImages;


  public LabeledWidgetTest(Context context, TestImages testImages) {
    theImages = testImages;
    labeledWidgetPanel = BoxPanelFactory.create(context);
    labeledWidgetPanel.setOrientation(BoxPanel.VERTICAL);
    final  LabeledWidget     labeledWidget;
           CheckBox          imageCheckBox;
    labeledWidget = LabeledWidgetFactory.create(context);
    labeledWidget.setImage(testImages.homeIcon);
    labeledWidget.setText(THE_TEXT);
    imageCheckBox = CheckBoxFactory.create(context);
    imageCheckBox.addToggleCallback(new ToggleCallbackDebug(context));
    imageCheckBox.setText("A Widget");
    labeledWidget.setWidget(imageCheckBox);
    labeledWidget.setLabelWidgetAlignment(Compass.NORTH);
    labeledWidget.setTextImageAlignment(Compass.NORTH);

    labeledWidgetPanel.addRigidArea(new Size(300, 20));
    labeledWidgetPanel.addWidget(labeledWidget);

    CheckBox          textOnCheckBox             = CheckBoxFactory.create(context);
    textOnCheckBox.addToggleCallback
      (
      new ToggleCallbackAdapter() {
        public void stateChange(Toggle toggle, boolean selected) {
          if (selected) {
            labeledWidget.setText(THE_TEXT);
          } else {
            labeledWidget.setText("");
          }
        }
      });
    textOnCheckBox.setText("Text On");
    textOnCheckBox.setSelected(true);

    CheckBox          imageOnCheckBox            = CheckBoxFactory.create(context);
    imageOnCheckBox.addToggleCallback
      (
      new ToggleCallbackAdapter() {
        public void stateChange(Toggle toggle, boolean selected) {
          if (selected) {
            labeledWidget.setImage(theImages.homeIcon);
          } else {
            labeledWidget.setImage(null);
          }
        }
      });
    imageOnCheckBox.setText("Image On");
    imageOnCheckBox.setSelected(true);

    String[]          radioItems                 = {"N", "E", "S", "W", "NE", "SE", "SW", "NW"};

    RadioButtonPanel  labelWidgetAlignmentPanel  = SimpleRadioButtonPanelFactory.create(context, radioItems, "Select Label-Widget Alignment", EdgeStyle.ETCHED);
    labelWidgetAlignmentPanel.addRadioButtonPanelCallback
      (
      new RadioButtonPanelCallbackAdapter() {
        public void indexSelected(RadioButtonPanel radioButtonPanel, int index) {
          labeledWidget.setLabelWidgetAlignment(index + 1);
        }
      });

    RadioButtonPanel  textImageAlignmentPanel    = SimpleRadioButtonPanelFactory.create(context, radioItems, "Select Text-Image Alignment", EdgeStyle.ETCHED);
    textImageAlignmentPanel.addRadioButtonPanelCallback
      (
      new RadioButtonPanelCallbackAdapter() {
        public void indexSelected(RadioButtonPanel radioButtonPanel, int index) {
          labeledWidget.setTextImageAlignment(index + 1);
        }
      });

    labelWidgetAlignmentPanel.setOrientation(Orientation.HORIZONTAL);
    labelWidgetAlignmentPanel.setMaximumSize(new Size(600, 200));
    textImageAlignmentPanel.setOrientation(Orientation.HORIZONTAL);
    textImageAlignmentPanel.setMaximumSize(new Size(600, 200));

    labeledWidgetPanel.addVerticalSpring();
    labeledWidgetPanel.addWidget(textOnCheckBox);
    labeledWidgetPanel.addWidget(imageOnCheckBox);
    labeledWidgetPanel.addRigidArea(new Size(300, 10));
    labeledWidgetPanel.addWidget(labelWidgetAlignmentPanel);
    labeledWidgetPanel.addRigidArea(new Size(300, 10));
    labeledWidgetPanel.addWidget(textImageAlignmentPanel);
    labeledWidgetPanel.addRigidArea(new Size(300, 20));
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new LabeledWidgetTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return labeledWidgetPanel;
  }

}

