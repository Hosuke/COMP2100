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
import armidale.api.context.*;
import armidale.api.context.platform.*;

import armidale.api.gui.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

class ModelledTableTest {

  private  Table        table;
  private  TestModel    tableModel;
  private  BorderPanel  tablePanel;
  private  Context      context;


  public ModelledTableTest(Context context, TestImages testImages) {

    this.context = context;
    tableModel = new TestModel();

    table = ModelledTableFactory.create(context, tableModel);
    table.addTableCallback(new TableCallbackDebug(context));

    tablePanel = BorderPanelFactory.create(context);
    tablePanel.addWidget(BorderPanelWidgetFactory.create(context, table, BorderPanelWidget.CENTER));

    PushButton  tableContentsButton  = PushButtonFactory.create(context);
    tableContentsButton.setText("Modify Table Contents");
    tableContentsButton.addActionCallback(new TableContentsButtonCallback());
    tableContentsButton.setSize(new Size(200, 25));

    Label       tableColumnsLabel    = LabelFactory.create(context);
    tableColumnsLabel.setText("No. of Columns");
    TextField   tableColumnsText     = TextFieldFactory.create(context);
    tableColumnsText.addTextCallback(new TableColumnCountCallback(context));
    tableColumnsText.setSize(new Size(50, 25));

    BoxPanel    buttonPanel          = BoxPanelFactory.create(context, BoxPanel.HORIZONTAL);

    buttonPanel.addHorizontalSpring();
    buttonPanel.addWidget(tableContentsButton);
    buttonPanel.addHorizontalStrut(40);
    buttonPanel.addWidget(tableColumnsLabel);
    buttonPanel.addWidget(tableColumnsText);
    buttonPanel.addHorizontalSpring();
    buttonPanel.addVerticalStrut(30);

    tablePanel.addWidget(BorderPanelWidgetFactory.create(context, buttonPanel, BorderPanelWidget.SOUTH));
  }


  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new ModelledTableTest(swingContext, testImages).widget());
  }


  public Widget widget() {
    return tablePanel;
  }


  private class TestModel extends AbstractTableModel {

    private  int     columnCount  = 2;
    private  String  text         = "";


    public void setColumnCount(int count) {
      columnCount = count;
      fireTableStructureChanged();
    }


    public void setText(String text) {
      this.text = text;
      fireTableDataChanged();
    }


    public Object getValueAt(int row, int column) {
      Label  theLabel  = LabelFactory.create(context);
      theLabel.setText("modelled item (" + row + ", " + column + ") " + text);
      theLabel.setAlignment(Label.WEST);
      return theLabel;
    }


    public String getColumnName(int columnIndex) {
      return "Column " + columnIndex;
    }


    public int getRowCount() {
      return 20000;
    }


    public int getColumnCount() {
      return columnCount;
    }

  }


  private class TableContentsButtonCallback implements ActionCallback {

    private  int  count  = 0;


    public void actionPerformed(Action action) {
      tableModel.setText("[change " + count + "]");
      count++;
    }
  }


  private class TableColumnCountCallback implements TextCallback {

    private  Context  context;


    public TableColumnCountCallback(Context context) {
      this.context = context;
    }


    public void textChanged(Text textWidget, String text) {
      try {
        tableModel.setColumnCount(Integer.parseInt(text));
      } catch (Throwable t) {
        SimpleOptionBox.showErrorBox(context, t.toString());
      }
    }
  }

}

