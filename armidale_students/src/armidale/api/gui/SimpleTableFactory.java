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
 
package armidale.api.gui;

import armidale.api.ArmidaleObject;
import armidale.api.context.Context;

public class SimpleTableFactory {

  public static Table create(Context context, String[][] items, String[] columnNames) {
    Label[][]  labelItems  = new Label[items.length][items[0].length];
    for (int i = 0; i < labelItems.length; i++) {
      for (int j = 0; j < labelItems[0].length; j++) {
        labelItems[i][j] = LabelFactory.create(context);
        labelItems[i][j].setText(items[i][j]);
      }
    }
    return create(context, labelItems, columnNames);
  }


  public static Table create(Context context, ArmidaleObject[][] items, String[] columnNames) {
    Table      table      = TableFactory.create(context);
    TableData  tableData  = TableDataFactory.create(context);
    tableData.setItemRowCount(items.length);
    tableData.setItemColumnCount(items[0].length);
    for (int row = 0; row < items.length; row++) {
      tableData.setItemRow(row, items[row]);
    }
    if (columnNames != null) {
      for (int col = 0; col < columnNames.length; col++) {
        tableData.setItemColumnTitle(col, columnNames[col]);
      }
    }
    table.setTableData(tableData);
    return table;
  }


  public static Table create(Context context, String[][] items) {
    return create(context, items, null);
  }


  public static Table create(Context context, ArmidaleObject[][] items) {
    return create(context, items, null);
  }

}

