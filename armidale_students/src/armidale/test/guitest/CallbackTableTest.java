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

class CallbackTableTest {

  private final static  int         TABLE_ROWS     = 20000;
  private final static  int         TABLE_COLUMNS  = 4;
  private final static  String[]    columnTitles   = {"Shayne", "Sanae", "Andy", "Sara"};

  private               Table       table;
  private               TableData   tableItems;
  private               Context     context;
  private               TestImages  testImages;


  public CallbackTableTest(Context theContext, TestImages theTestImages) {

    this.context = theContext;
    this.testImages = theTestImages;

    table = TableFactory.create(context);

    table.addTableCallback(new TableCallbackDebug(context));

    tableItems = TableDataFactory.create(context);
    tableItems.setItemRowCount(TABLE_ROWS);
    tableItems.setItemColumnCount(TABLE_COLUMNS);
    for (int i = 0; i < columnTitles.length; i++) {
      tableItems.setItemColumnTitle(i, columnTitles[i]);
    }

    table.setTableData(tableItems);

    tableItems.addTableDataCallback
      (
      new TableDataCallback() {
        public void requestRows(TableData tableData, int row, int count) {
          //System.out.println("CallbackTableTest.requestItems(" + row + ", " + count + ")" + Thread.currentThread().toString());
          Label[][]  tempLabels  = new Label[count][4];
          for (int i = 0; i < count; i++) {
            for (int j = 0; j < TABLE_COLUMNS; j++) {
              tempLabels[i][j] = LabelFactory.create(context);
              tempLabels[i][j].setText("Hi " + (row + i) + ", " + j);
              tempLabels[i][j].setImage(testImages.checkIcon);
              tempLabels[i][j].setAlignment(Label.WEST);
              tempLabels[i][j].setTextPosition(Label.EAST);
              tempLabels[i][j].setBackgroundColor(Color.RED);
            }
            tableItems.setItemRow(row + i, tempLabels[i]);
          }
        }
      });
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new CallbackTableTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return table;
  }

}

