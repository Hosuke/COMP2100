/*
 * Copyright (c) 2000-2002, Shayne R Flint
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    * Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 *    * The name of the author may not be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * ./armidale/api/gui/impl/platform/swing/TableDataImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:34
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.ArmidaleObject;
  import armidale.api.context.*;
  import armidale.api.gui.TableData;
  import armidale.api.gui.TableDataCallback;
  import armidale.api.gui.TableDataCallbackList;
  import armidale.api.impl.platform.swing.ArmidaleObjectImpl;
  import java.util.LinkedList;
  //--ARM-- imports
  import javax.swing.table.DefaultTableModel;
  import armidale.api.ArgumentException;
  //--ARM-- imports

public class TableDataImpl extends ArmidaleObjectImpl implements TableData {

  //--ARM-- declarations
  private String[] columnNames;
  
  private class LocalTableModel extends DefaultTableModel {
    
    public String getColumnName(int column) {
      if (columnNames != null && column < columnNames.length) {
        return columnNames[column];
      } else {
        return super.getColumnName(column);
      }
    }
  }
  //--ARM-- declarations

  // Callback object
  //

  protected TableDataCallbackList tableDataCallbackList = new TableDataCallbackList();

  public TableDataImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    //--ARM-- constructor() - end
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new LocalTableModel();
    //--ARM-- setPeer()
  }

  // Callbacks
  //

  public void addTableDataCallback(TableDataCallback callback) {
    tableDataCallbackList.add(callback);
    //--ARM-- public void addTableDataCallback(TableDataCallback callback)
    //--ARM-- public void addTableDataCallback(TableDataCallback callback)
  }

  public void removeTableDataCallback(TableDataCallback callback) {
    tableDataCallbackList.remove(callback);
    //--ARM-- public void removeTableDataCallback(TableDataCallback callback)
    //--ARM-- public void removeTableDataCallback(TableDataCallback callback)
  }

  public void removeAllTableDataCallbacks() {
    tableDataCallbackList.clear();
    //--ARM-- public void removeAllTableDataCallbacks()
    //--ARM-- public void removeAllTableDataCallbacks()
  }

  public TableDataCallbackList getTableDataCallbackList() {
    //--ARM-- public TableDataCallbackList getTableDataCallbackList()
    //--ARM-- public TableDataCallbackList getTableDataCallbackList()
    return tableDataCallbackList;
  }

  // other methods
  //

  public void setItemColumnCount(int count) {
    //--ARM-- public void setItemColumnCount(int count)
    if (count <= 0) {
      throw new ArgumentException("column count is less that 1 [" + count + "]");
    }
    ((DefaultTableModel)peer).setColumnCount(count);
    String[] oldColumnNames = columnNames;
    columnNames = new String[count];
    if (oldColumnNames != null) {
      for (int c=0; c < oldColumnNames.length && c < columnNames.length; c++) {
        columnNames[c] = oldColumnNames[c];
      }
    }
    //--ARM-- public void setItemColumnCount(int count)
  }


  public void setItemRowCount(int count) {
    //--ARM-- public void setItemRowCount(int count)
    ((DefaultTableModel)peer).setRowCount(count);
    //--ARM-- public void setItemRowCount(int count)
  }


  public void setItem(int row, int column, ArmidaleObject armidaleObject) {
    //--ARM-- public void setItem(int row, int column, ArmidaleObject armidaleObject)
    ((DefaultTableModel)peer).setValueAt(armidaleObject, row, column);
    //--ARM-- public void setItem(int row, int column, ArmidaleObject armidaleObject)
  }


  public void setItem(int firstRow, int rowCount, int column, ArmidaleObject armidaleObject) {
    //--ARM-- public void setItem(int firstRow, int rowCount, int column, ArmidaleObject armidaleObject)
    for (int row=firstRow; row < firstRow + rowCount; row++) {
      setItem(row, column, armidaleObject);
    }
    //--ARM-- public void setItem(int firstRow, int rowCount, int column, ArmidaleObject armidaleObject)
  }


  public void setItemRow(int row, ArmidaleObject[] armidaleObjects) {
    //--ARM-- public void setItemRow(int row, ArmidaleObject[] armidaleObjects)
    for (int column=0; column<armidaleObjects.length; column++) {
      setItem(row, column, armidaleObjects[column]);
    }
    //--ARM-- public void setItemRow(int row, ArmidaleObject[] armidaleObjects)
  }


  public void setItemRow(int row, int count, ArmidaleObject[] armidaleObjects) {
    //--ARM-- public void setItemRow(int row, int count, ArmidaleObject[] armidaleObjects)
    for (int i=row; i<row+count; i++) {
     setItemRow(i, armidaleObjects);
    }
    //--ARM-- public void setItemRow(int row, int count, ArmidaleObject[] armidaleObjects)
  }


  public void insertItemRow(int row, ArmidaleObject[] armidaleObjects) {
    //--ARM-- public void insertItemRow(int row, ArmidaleObject[] armidaleObjects)
    ((DefaultTableModel)peer).insertRow(row, armidaleObjects);
    //--ARM-- public void insertItemRow(int row, ArmidaleObject[] armidaleObjects)
  }


  public void removeItemRow(int row) {
    //--ARM-- public void removeItemRow(int row)
    ((DefaultTableModel)peer).removeRow(row);
    //--ARM-- public void removeItemRow(int row)
  }


  public void removeItemRows(int row, int count) {
    //--ARM-- public void removeItemRows(int row, int count)
    for (int r=row; r<row+count; r++) {
      removeItemRow(r);
    }
    //--ARM-- public void removeItemRows(int row, int count)
  }


  public void removeAllItemRows() {
    //--ARM-- public void removeAllItemRows()
    setItemRowCount(0);
    //--ARM-- public void removeAllItemRows()
  }


  public void setItemColumnTitle(int column, String title) {
    //--ARM-- public void setItemColumnTitle(int column, String title)
    columnNames[column] = title;
    //--ARM-- public void setItemColumnTitle(int column, String title)
  }

}