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
 * ./armidale/api/gui/ListData.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:32
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:32
   * @version armidale v0.9.5
   */

  import armidale.api.ArmidaleObject;

public interface ListData extends ArmidaleObject {

  // callback
  //

  /**
   * Registers a callback with this ListData
   *
   * @param  callback callback to be added Callback used to request items from 
   * a list.
   * <p>
   *
   */
  public void addListDataCallback(ListDataCallback callback);

  /**
   * Removes a callback registered with this ListData
   *
   * @param  callback callback to be removed Callback used to request items 
   * from a list.
   * <p>
   */
  public void removeListDataCallback(ListDataCallback callback);

  /**
   * Removes all callbacks registered with this ListData
   *
   */
  public void removeAllListDataCallbacks();

  /**
   * Returns the list of callbacks registered with this ListData
   *
   * @return list of callbacks registered with this object Callback used to 
   * request items from a list.
   * <p>
   */
  public ListDataCallbackList getListDataCallbackList();

  // other methods
  //

  /**
   * Sets the size of the list of Items associated with this ListData.
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param count number of ArmidaleObjects in the list of ArmidaleObjects
   */
  public void setItemCount(int count);

  /**
   * Replaces the ArmidaleObject at a specified index in the list of Items 
   * associated with this ListData.
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index of the ArmidaleObject to replace
   * @param armidaleObject ArmidaleObject to be put into list
   */
  public void setItem(int index, ArmidaleObject armidaleObject);

  /**
   * Replaces a specified number of ArmidaleObjects in the list of Items 
   * associated with this ListData with copies of a specified ArmidaleObject.
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index of the first ArmidaleObject to replace
   * @param count number of ArmidaleObjects to replace
   * @param armidaleObject ArmidaleObject to copy into list
   */
  public void setItem(int index, int count, ArmidaleObject armidaleObject);

  /**
   * Copies an array of ArmidaleObjects to the list of Items associated with 
   * this ListData starting at a specified index.
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index of the first ArmidaleObject to replace
   * @param armidaleObjects array of ArmidaleObjects to be put into list
   */
  public void setItems(int index, ArmidaleObject[] armidaleObjects);

  /**
   * Inserts a specified ArmidaleObject at a specified index in the list of 
   * Items associated with this ListData. Moves all subsequent ArmidaleObjects 
   * right (adds one to their indices).
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index at which the ArmidaleObject is to be inserted
   * @param armidaleObject ArmidaleObject to insert
   */
  public void insertItem(int index, ArmidaleObject armidaleObject);

  /**
   * Inserts a specified number of copies of a specified ArmidaleObject at a 
   * specified index in the list of Items associated with this ListData. Moves 
   * all subsequent ArmidaleObjects right (adds 'count' to their indices).
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index at which the first ArmidaleObject is to be inserted
   * @param count number of copies to insert
   * @param armidaleObject ArmidaleObject to be copied
   */
  public void insertItem(int index, int count, ArmidaleObject armidaleObject);

  /**
   * Inserts an array of ArmidaleObjects at a specified index in the list of 
   * Items associated with this ListData. Moves all subsequent ArmidaleObjects 
   * right (adds 'armidaleObjects.length' to their indices).
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index at which the first ArmidaleObject is to be inserted
   * @param armidaleObjects array of ArmidaleObjects to insert
   */
  public void insertItems(int index, ArmidaleObject[] armidaleObjects);

  /**
   * Removes the ArmidaleObject at a specified index in the list of Items 
   * associated with this ListData. Moves all subsequent ArmidaleObjects left 
   * (subtracts one from their indices).
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index of the ArmidaleObject to be removed
   */
  public void removeItem(int index);

  /**
   * Removes a specified number of ArmidaleObjects from the list of Items 
   * associated with this ListData starting at a specified index. Moves all 
   * subsequent ArmidaleObjects left (subtracts 'count' from their indices).
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param index index of the first ArmidaleObject to be removed
   * @param count number of ArmidaleObjects to be removed
   */
  public void removeItems(int index, int count);

  /**
   * Adds a specified ArmidaleObject to the list of Items associated with this 
   * ListData.
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param armidaleObject ArmidaleObject to be added
   */
  public void addItem(ArmidaleObject armidaleObject);

  /**
   * Removes a specified ArmidaleObject from the list of Items associated with 
   * this ListData.
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   * @param armidaleObject ArmidaleObject to be removed
   */
  public void removeItem(ArmidaleObject armidaleObject);

  /**
   * Clears the list of Items associated with this ListData.
   * <p>
   * @see #setItemCount
   * @see #setItem
   * @see #setItem
   * @see #setItems
   * @see #insertItem
   * @see #insertItem
   * @see #insertItems
   * @see #removeItem
   * @see #removeItems
   * @see #addItem
   * @see #removeItem
   * @see #removeAllItems
   *
   */
  public void removeAllItems();
}
