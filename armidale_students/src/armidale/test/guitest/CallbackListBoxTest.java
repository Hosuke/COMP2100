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

class CallbackListBoxTest {

  private final static  int          LIST_SIZE                 = 20000;

  private               String       callbackListChangeString  = "";

  private               ListBox      listBox;
  private               ListData     listBoxItems;
  private               BorderPanel  listPanel;
  private               Context      context;
  private               TestImages   testImages;


  public CallbackListBoxTest(Context theContext, TestImages theTestImages) {

    this.context = theContext;
    this.testImages = theTestImages;

    listBox = ListBoxFactory.create(context);
    listBox.addListBoxCallback(new ListBoxCallbackDebug(context));
    listBoxItems = ListDataFactory.create(context);
    listBox.setListData(listBoxItems);

    listBoxItems.addListDataCallback
      (
      new ListDataCallback() {
        public void requestItems(ListData listData, int index, int count) {
          //System.out.println("CallbackListBoxTest.requestItems(" + index + ", " + count + ")" + Thread.currentThread().toString());
          Label[]  tempLabels  = new Label[count];
          for (int i = 0; i < tempLabels.length; i++) {
            tempLabels[i] = LabelFactory.create(context);
            tempLabels[i].setText("Callback List Item " + (i + index) + " " + callbackListChangeString);
            tempLabels[i].setImage(testImages.checkIcon);
            tempLabels[i].setAlignment(Label.WEST);
            tempLabels[i].setTextPosition(Label.EAST);
            tempLabels[i].setBackgroundColor(Color.RED);
          }
          listBoxItems.setItems(index, tempLabels);
        }
      });

    listBoxItems.setItemCount(LIST_SIZE);

    listPanel = BorderPanelFactory.create(context);
    listPanel.addWidget(BorderPanelWidgetFactory.create(context, listBox, BorderPanelWidget.CENTER));
    PushButton  callbackListContentsButton  = PushButtonFactory.create(context);

    callbackListContentsButton.setText("Modify Callback List Contents");
    callbackListContentsButton.addActionCallback
      (
      new ActionCallback() {
        private  int  count  = 0;


        public void actionPerformed(Action action) {
          callbackListChangeString = "[Change " + count + "]";
          count++;
          listBoxItems.removeAllItems();
          listBoxItems.setItemCount(LIST_SIZE);
        }
      });

    listPanel.addWidget(BorderPanelWidgetFactory.create(context, callbackListContentsButton, BorderPanelWidget.SOUTH));
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new CallbackListBoxTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return listPanel;
  }

}


