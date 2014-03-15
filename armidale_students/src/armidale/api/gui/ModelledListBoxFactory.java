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
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ModelledListBoxFactory {

  public static ListBox create(Context context, ListModel listModel) {
    ListBox   listBox   = ListBoxFactory.create(context);
    ListData  listData  = ListDataFactory.create(context);
    listData.addListDataCallback(new ModelledListDataCallback(listData, listModel));
    listData.setItemCount(listModel.getSize());
    listBox.setListData(listData);
    return listBox;
  }


  private static class ModelledListDataCallback implements ListDataCallback {

    private  ListData   listData;
    private  ListModel  listModel;


    public ModelledListDataCallback(ListData listData, ListModel listModel) {
      this.listModel = listModel;
      this.listData = listData;
      this.listModel.addListDataListener(new ModelListener());
    }


    public void requestItems(ListData listData, int first, int count) {
//System.out.println("ModelledListBoxFactory: request items " + first + " " + count);
      ArmidaleObject[]  armidaleObjects  = new ArmidaleObject[count];
      for (int i = 0; i < count; i++) {
        armidaleObjects[i] = (ArmidaleObject) listModel.getElementAt(i + first);
      }
      listData.setItems(first, armidaleObjects);
    }


    private class ModelListener implements ListDataListener {

      public void contentsChanged(ListDataEvent e) {
        int  first  = e.getIndex0();
        int  count  = e.getIndex1() - first + 1;
//System.out.println("contentsChanged" + e);
        listData.setItem(first, count, null);
      }


      public void intervalAdded(ListDataEvent e) {
        int  first  = e.getIndex0();
        int  count  = e.getIndex1() - first + 1;
//System.out.println("intervalAdded" + e);
        listData.insertItem(first, count, null);
      }


      public void intervalRemoved(ListDataEvent e) {
        int  first  = e.getIndex0();
        int  count  = e.getIndex1() - first + 1;
//System.out.println("intervalRemoved" + e);
        listData.removeItems(first, count);
      }
    }

  }

}

