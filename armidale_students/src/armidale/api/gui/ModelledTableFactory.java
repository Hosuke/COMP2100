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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ModelledTableFactory {

  public static Table create(Context context, TableModel tableModel) {
    Table      table      = TableFactory.create(context);
    TableData  tableData  = TableDataFactory.create(context);
    tableData.setItemRowCount(tableModel.getRowCount());
    tableData.setItemColumnCount(tableModel.getColumnCount());
    setColumnNames(tableModel, tableData);
    tableData.addTableDataCallback(new ModelledTableDataCallback(tableData, tableModel));
    table.setTableData(tableData);
    return table;
  }


  private static void setColumnNames(TableModel tableModel, TableData tableData) {
    for (int c = 0; c < tableModel.getColumnCount(); c++) {
      tableData.setItemColumnTitle(c, tableModel.getColumnName(c));
    }
  }


  private static class ModelledTableDataCallback implements TableDataCallback {

    private  TableData   tableData;
    private  TableModel  tableModel;


    public ModelledTableDataCallback(TableData tableData, TableModel tableModel) {
      this.tableModel = tableModel;
      this.tableData = tableData;
      this.tableModel.addTableModelListener(new ModelListener());
    }


    public void requestRows(TableData tableData, int first, int count) {
//System.out.println("ModelledTableBoxFactory: request items " + first + " " + count);
      ArmidaleObject[]  armidaleObjects  = new ArmidaleObject[tableModel.getColumnCount()];
      for (int i = first; i < first + count; i++) {
        for (int j = 0; j < armidaleObjects.length; j++) {
          armidaleObjects[j] = (ArmidaleObject) tableModel.getValueAt(i, j);
        }
        tableData.setItemRow(i, armidaleObjects);
      }
    }


    private class ModelListener implements TableModelListener {

      public void tableChanged(TableModelEvent e) {
        ArmidaleObject[]  nullRow  = new ArmidaleObject[tableModel.getColumnCount()];
        int               first    = e.getFirstRow();
        int               last     = e.getLastRow();
//System.out.println("tableChanged: " + e);
//System.out.println("            : " + first + ", " + last + ", " + e.getType());
        if (first == TableModelEvent.HEADER_ROW) {
          tableData.setItemRowCount(tableModel.getRowCount());
          tableData.setItemColumnCount(tableModel.getColumnCount());
          setColumnNames(tableModel, tableData);
          tableData.setItemRow(0, tableModel.getRowCount(), nullRow);
        } else {
          if (last > tableModel.getRowCount()) {
            last = tableModel.getRowCount() - 1;
          }
          tableData.setItemRow(first, last - first + 1, nullRow);
        }
      }
    }

  }

}

