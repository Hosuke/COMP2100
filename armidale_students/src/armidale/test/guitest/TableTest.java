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

class TableTest {

  private  Table      table;
  private  TableData  tableItems;


  public TableTest(Context context, TestImages testImages) {
    table = TableFactory.create(context);

    tableItems = TableDataFactory.create(context);
    tableItems.setItemRowCount(20);
    tableItems.setItemColumnCount(4);

    tableItems.setItemColumnTitle(0, "Fred");
    tableItems.setItemColumnTitle(1, "Wilma");
    tableItems.setItemColumnTitle(2, "Barny");
    tableItems.setItemColumnTitle(3, "Dino");

    table.setTableData(tableItems);

    Label[][]  tableLabels  = new Label[20][4];
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 4; j++) {
        tableLabels[i][j] = LabelFactory.create(context);
        tableLabels[i][j].setText("Hi " + i + ", " + j);
        tableLabels[i][j].setImage(testImages.checkIcon);
        tableLabels[i][j].setAlignment(Label.WEST);
        tableLabels[i][j].setTextPosition(Label.EAST);
        tableLabels[i][j].setBackgroundColor(Color.RED);
      }
      tableItems.setItemRow(i, tableLabels[i]);
    }

    table.addTableCallback(new TableCallbackDebug(context));
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new TableTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return table;
  }

}

