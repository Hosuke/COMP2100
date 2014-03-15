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

class ProgressBarTest {
  private  ProgressBar[]  progressBars   = new ProgressBar[2];

  private  BoxPanel       progressPanel;


  public ProgressBarTest(Context context, TestImages testImages) {

    progressPanel = BoxPanelFactory.create(context);
    progressPanel.setOrientation(BoxPanel.VERTICAL);

    for (int i = 0; i < progressBars.length; i++) {
      progressBars[i] = ProgressBarFactory.create(context);
      progressBars[i].setMinimum(0);
      progressBars[i].setMaximum(100);
      progressBars[i].setPreferredSize(new Size(400, 25));
      progressBars[i].setMaximumSize(new Size(400, 25));

      progressPanel.addRigidArea(new Size(300, 20));
      progressPanel.addWidget(progressBars[i]);
    }

    progressBars[0].setDrawProgressString(true);
    progressBars[1].setDrawProgressString(false);

    PushButton  progressButton;
    progressButton = PushButtonFactory.create(context);
    progressButton.setMinimumSize(new Size(100, 30));
    progressButton.setPreferredSize(new Size(100, 30));
    progressButton.setMaximumSize(new Size(100, 30));
    progressButton.setText("Start");
    progressButton.addActionCallback
      (
      new ActionCallbackAdapter() {
        public void actionPerformed(Action action) {
          Thread  progressThread  =
            new Thread() {
              public void run() {
                for (int i = 0; i <= progressBars[0].getMaximum(); i++) {
                  progressBars[0].setValue(i);
                  progressBars[1].setValue(i);
                  try {
                    sleep(10);
                  } catch (Exception e) {
                  }
                }
                try {
                  sleep(2000);
                } catch (Exception e) {
                }
                progressBars[0].setValue(0);
                progressBars[1].setValue(0);
              }
            };
          progressThread.start();
        }
      });

    progressPanel.addRigidArea(new Size(300, 20));
    progressPanel.addWidget(progressButton);
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new ProgressBarTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return progressPanel;
  }

}


