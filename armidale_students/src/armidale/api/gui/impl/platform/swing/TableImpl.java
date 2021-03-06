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
 * ./armidale/api/gui/impl/platform/swing/TableImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:16
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.Table;
  import armidale.api.gui.TableCallback;
  import armidale.api.gui.TableCallbackList;
  import armidale.api.gui.TableData;
  import armidale.api.gui.impl.platform.swing.WidgetImpl;
  import java.util.LinkedList;
  //--ARM-- imports
import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableCellRenderer;

import armidale.api.gui.Label;
import armidale.api.impl.platform.swing.ArmidaleObjectImpl;
  //--ARM-- imports

public class TableImpl extends WidgetImpl implements Table {

  //--ARM-- declarations
  private               JTable              theJTable;

  private               TableData           tableData;
  private               TableModel          model;

  private UpdateThread  updateThread;
  
  private final static  int                 PAGE_LENGTH         = 20;

  private  Label  pleaseWaitLabel;

  private int firstVisibleRow() {
    JScrollPane pane = (JScrollPane)peer;
    return theJTable.rowAtPoint(pane.getViewport().getViewPosition());
  }
  
  private int lastVisibleRow() {
    JScrollPane pane = (JScrollPane)peer;
    int y = pane.getViewport().getViewPosition().y;
    y = y + pane.getViewport().getExtentSize().height;
    int row = theJTable.rowAtPoint(new Point(0, y));
    if (row > -1) {
      return row;
    } else {
      return theJTable.getRowCount() - 1;
    }
  }
  
  private class Renderer implements TableCellRenderer {

    public Renderer() {
      super();
    }


    public java.awt.Component getTableCellRendererComponent(
                                                            JTable table,
                                                            Object value,
                                                            boolean isSelected,
                                                            boolean cellHasFocus,
                                                            int row, int column) {

      JComponent  theWidget;
      Label       label;
      
      if (value == null) {
        theWidget = (JComponent) ((ArmidaleObjectImpl) pleaseWaitLabel).peer;
      } else {
        if (((ArmidaleObjectImpl) value).peer instanceof JComponent) {
          theWidget = (JComponent) ((ArmidaleObjectImpl) value).peer;
        } else {
          label = new LabelImpl();
          label.setText(value.toString());
          theWidget = (JComponent) ((ArmidaleObjectImpl) label).peer;
        }
      }
      theWidget.setOpaque(true);
      if (theWidget instanceof JLabel) {
        if (isSelected) {
          theWidget.setBackground(table.getSelectionBackground());
          theWidget.setForeground(table.getSelectionForeground());
        } else {
          theWidget.setBackground(table.getBackground());
          theWidget.setForeground(table.getForeground());
        }
      }
      return theWidget;
    }
  }

  private boolean hasNull(int row) {
    for (int column=0; column<model.getColumnCount(); column++) {
      if (model.getValueAt(row, column) == null) {
        return true;
      }
    }
    return false;
  }
  
  private void requestNullItems(int first, int last) {

    int  getFirst    = 0;
    int  getLast     = -1;
    int  lowerBound;
    int  upperBound;

    int  pageLength;

//    System.out.println("TableImpl: requestNullItems " + first + " to " + last);

    if ((first < 0) || (last < first)) {
      return;
    }
//System.out.println("rows=" + model.getRowCount() + ", cols=" + model.getColumnCount());    
    if (model==null || model.getRowCount()<=first || model.getColumnCount()==0) {
//      System.out.println("TableImpl: model is null");
      return;
    }
    int lastLimit = min(last, model.getRowCount()-1);
    for (int i = first; i <= lastLimit; i++) {
      if (model.getValueAt(i, 0) == null) {
        getFirst = i;
        while ((i <= last) && hasNull(i)) {
          getLast = i;
          i++;
        }
        if (getLast >= getFirst) {
          // now see if we can request more items upto a full page (to minimise traffic and give smoother interface to user)
          pageLength = last - first + 1;
          lowerBound = max(0, getFirst - pageLength);
          upperBound = min(model.getRowCount() - 1, getLast + pageLength);
          //System.out.println("TableImpl: pageLength=" + pageLength + " modelSize=" + model.getRowCount() + "first=" + getFirst + " last=" + getLast + " lower=" + lowerBound + " upper=" + upperBound);
          while ((getFirst > lowerBound) && hasNull(getFirst)) {
            getFirst--;
          }
          while ((getLast < upperBound) && hasNull(getLast)) {
            getLast++;
          }
          //System.out.println("TableImpl: callback.requestItems " + getFirst + " to " + getLast + ",count=" + (getLast - getFirst + 1));
          tableData.setItem(getFirst, getLast - getFirst + 1, 0, pleaseWaitLabel);
          tableData.getTableDataCallbackList().requestRows(tableData, getFirst, getLast - getFirst + 1);
        }
      }
    }
  }


  private int min(int a, int b) {
    if (a < b) {
      return a;
    } else {
      return b;
    }
  }


  private int max(int a, int b) {
    if (a > b) {
      return a;
    } else {
      return b;
    }
  }


  private class UpdateThread extends Thread {
    public void run() {
      try {
        while (firstVisibleRow()<0 && model.getRowCount()<1) {
          sleep(50);
        }
        while (true) {
          requestNullItems(firstVisibleRow(), lastVisibleRow());
          sleep(1000);
        }
      } catch (Exception e) {
      }
    }
  }


  //--ARM-- declarations

  // Callback object
  //

  protected TableCallbackList tableCallbackList = new TableCallbackList();

  public TableImpl() {
    //--ARM-- constructor()
    pleaseWaitLabel = new LabelImpl();
    pleaseWaitLabel.setText("Please wait ...");
    //--ARM-- constructor()
    //--ARM-- constructor() - end

    theJTable.setDefaultRenderer(Object.class, new Renderer());

    JScrollBar  vScrollBar  = ((JScrollPane) peer).getVerticalScrollBar();

    vScrollBar.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(java.awt.event.MouseEvent event) {
          int  pageLength;
//System.out.println("TableImpl: adjustmentValueChanged");
//          System.out.println("TableImpl: - calling list scrolled callback");
          requestNullItems(firstVisibleRow(), lastVisibleRow());
//          System.out.println("TableImpl: - done");
        }
      });

//     theJTable.addListSelectionListener(
//       new ListSelectionListener() {
//         public void valueChanged(javax.swing.event.ListSelectionEvent event) {
// //System.out.println("TableImpl: valueChanged");
//           if (!event.getValueIsAdjusting()) {
// //System.out.println("TableImpl: - calling list event callback");
//             listBoxEventCallback.select(ListBoxImpl.this, ((JList) event.getSource()).getSelectedIndices());
// //System.out.println("TableImpl: - done");
//           }
//         }
//       });
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    theJTable = new JTable();
    peer = new JScrollPane(theJTable);
    //--ARM-- setPeer()
  }

  // Callbacks
  //

  public void addTableCallback(TableCallback callback) {
    tableCallbackList.add(callback);
    //--ARM-- public void addTableCallback(TableCallback callback)
    //--ARM-- public void addTableCallback(TableCallback callback)
  }

  public void removeTableCallback(TableCallback callback) {
    tableCallbackList.remove(callback);
    //--ARM-- public void removeTableCallback(TableCallback callback)
    //--ARM-- public void removeTableCallback(TableCallback callback)
  }

  public void removeAllTableCallbacks() {
    tableCallbackList.clear();
    //--ARM-- public void removeAllTableCallbacks()
    //--ARM-- public void removeAllTableCallbacks()
  }

  public TableCallbackList getTableCallbackList() {
    //--ARM-- public TableCallbackList getTableCallbackList()
    //--ARM-- public TableCallbackList getTableCallbackList()
    return tableCallbackList;
  }

  // set/get/is methods
  //

  public TableData getTableData() {
    //--ARM-- public TableData getTableData()
    return tableData;
    //--ARM-- public TableData getTableData()
  }

  public void setTableData(TableData tableData) {
    //--ARM-- public void setTableData(TableData tableData)
    this.tableData = tableData;
    model = (TableModel) ((TableDataImpl) tableData).peer;
    theJTable.setModel(model);
//    System.out.println("setTableData: about the requestNullItems");
    updateThread = new UpdateThread();
    updateThread.start();
    //--ARM-- public void setTableData(TableData tableData)
  }
}
