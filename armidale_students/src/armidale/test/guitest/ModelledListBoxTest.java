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
import javax.swing.AbstractListModel;
import javax.swing.ListModel;

class ModelledListBoxTest {

  private  ListBox      listBox;
  private  TestModel    listModel;
  private  BorderPanel  listPanel;
  private  Context      context;


  public ModelledListBoxTest(Context context, TestImages testImages) {

    this.context = context;
    listModel = new TestModel();

    listBox = ModelledListBoxFactory.create(context, listModel);
    listBox.addListBoxCallback(new ListBoxCallbackDebug(context));

    listPanel = BorderPanelFactory.create(context);
    listPanel.addWidget(BorderPanelWidgetFactory.create(context, listBox, BorderPanelWidget.CENTER));
    PushButton  listContentsButton  = PushButtonFactory.create(context);
    listContentsButton.setText("Modify List Contents");
    listContentsButton.addActionCallback(new ListContentsButtonCallback());

    listPanel.addWidget(BorderPanelWidgetFactory.create(context, listContentsButton, BorderPanelWidget.SOUTH));
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new ModelledListBoxTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return listPanel;
  }


  private class TestModel extends AbstractListModel {

    private  String  text  = "";


    public void setText(String text) {
      this.text = text;
      fireContentsChanged(this, 0, getSize() - 1);
    }


    public Object getElementAt(int index) {
      Label  theLabel  = LabelFactory.create(context);
      theLabel.setText("modelled item " + index + " " + text);
      theLabel.setAlignment(Label.WEST);
      return theLabel;
    }


    public int getSize() {
      return 20000;
    }
  }


  private class ListContentsButtonCallback implements ActionCallback {

    private  int  count  = 0;


    public void actionPerformed(Action action) {
      listModel.setText("[change " + count + "]");
      count++;
    }
  }

}

