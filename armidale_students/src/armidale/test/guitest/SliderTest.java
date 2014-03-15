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

class SliderTest {

  private  BoxPanel  sliderPanel;


  public SliderTest(Context context, TestImages testImages) {
    sliderPanel = BoxPanelFactory.create(context);
    sliderPanel.setOrientation(BoxPanel.VERTICAL);

    Slider  sliders[]  = new Slider[4];
    for (int i = 0; i < sliders.length; i++) {
      sliders[i] = SliderFactory.create(context);
      sliders[i].setMinimum(100);
      sliders[i].setMaximum(200);
      sliders[i].setValue(100);
      sliders[i].addSliderCallback(new SliderCallbackDebug(context));
      sliderPanel.addRigidArea(new Size(300, 20));
      sliderPanel.addWidget(sliders[i]);
    }
    sliderPanel.addVerticalSpring();

    sliders[0].setTicksVisible(false);
    sliders[0].setLabelsVisible(false);
    sliders[0].setOrientation(Orientation.HORIZONTAL);

    sliders[1].setTicksVisible(false);
    sliders[1].setLabelsVisible(true);
    sliders[1].useIntegerLabels(20);
    sliders[1].setOrientation(Orientation.HORIZONTAL);

    sliders[2].setTicksVisible(true);
    sliders[2].setLabelsVisible(true);
    sliders[2].useIntegerLabels(10);
    sliders[2].setOrientation(Orientation.HORIZONTAL);

    sliders[3].setMajorTickSpacing(25);
    sliders[3].setMinorTickSpacing(25);
    sliders[3].addLabel(100, "Minimum");
    sliders[3].addLabel(200, "Maximum");
    sliders[3].setSnapToTicks(true);
    sliders[3].setOrientation(Orientation.VERTICAL);
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new SliderTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return sliderPanel;
  }

}


