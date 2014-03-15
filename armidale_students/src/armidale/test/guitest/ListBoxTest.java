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

class ListBoxTest {

  private  ListBox      listBox;
  private  ListData     listBoxItems;
  private  BorderPanel  listPanel;


  public ListBoxTest(Context context, TestImages testImages) {

    listBox = ListBoxFactory.create(context);
    listBox.addListBoxCallback(new ListBoxCallbackDebug(context));

    listBoxItems = ListDataFactory.create(context);
    listBox.setListData(listBoxItems);

    listBoxItems.setItemCount(40);

    Label[]     tempLabels          = new Label[40];
    for (int i = 0; i < tempLabels.length; i++) {
      tempLabels[i] = LabelFactory.create(context);
      tempLabels[i].setText("List Item " + i);
      tempLabels[i].setImage(testImages.checkIcon);
      tempLabels[i].setAlignment(Label.WEST);
      tempLabels[i].setTextPosition(Label.EAST);
      tempLabels[i].setBackgroundColor(Color.RED);
    }
    listBoxItems.setItems(0, tempLabels);

    listPanel = BorderPanelFactory.create(context);
    listPanel.addWidget(BorderPanelWidgetFactory.create(context, listBox, BorderPanelWidget.CENTER));
    PushButton  listContentsButton  = PushButtonFactory.create(context);
    listContentsButton.setText("Modify List Contents");
    listContentsButton.addActionCallback(new ListContentsButtonCallback(context, testImages));

    listPanel.addWidget(BorderPanelWidgetFactory.create(context, listContentsButton, BorderPanelWidget.SOUTH));
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new ListBoxTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return listPanel;
  }


  private class ListContentsButtonCallback implements ActionCallback {

             Context     context;
             TestImages  testImages;

    private  int         count       = 0;


    public ListContentsButtonCallback(Context context, TestImages testImages) {
      this.context = context;
      this.testImages = testImages;
    }


    public void actionPerformed(Action action) {
      Label[]  newLabels  = new Label[10];
      for (int i = 0; i < 10; i++) {
        newLabels[i] = LabelFactory.create(context);
        newLabels[i].setText("MODIFIED - List Item " + i + " [count=" + count + "]");
        newLabels[i].setImage(testImages.checkIcon);
        newLabels[i].setAlignment(Label.WEST);
        newLabels[i].setTextPosition(Label.EAST);
        count++;
      }
      listBoxItems.setItems(0, newLabels);
    }
  }

}

