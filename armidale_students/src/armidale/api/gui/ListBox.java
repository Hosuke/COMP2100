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
 * ./armidale/api/gui/ListBox.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:13
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:13
   * @version armidale v0.9.5
   */

public interface ListBox extends Widget {

  // attribute set/get/is methods
  //

  /**
   * Returns the value of the <tt>listData</tt> attribute.
   * <p>
   *
   * @return value of the <tt>listData</tt> attribute
   */
  public ListData getListData();

  /**
   * Sets the <tt>listData</tt> attribute.
   * <p>
   *
   * @param  listData value of the <tt>listData</tt> attribute. 
   */
  public void setListData(ListData listData);

  // callback
  //

  /**
   * Registers a callback with this ListBox
   *
   * @param  callback callback to be added
   *
   */
  public void addListBoxCallback(ListBoxCallback callback);

  /**
   * Removes a callback registered with this ListBox
   *
   * @param  callback callback to be removed
   */
  public void removeListBoxCallback(ListBoxCallback callback);

  /**
   * Removes all callbacks registered with this ListBox
   *
   */
  public void removeAllListBoxCallbacks();

  /**
   * Returns the list of callbacks registered with this ListBox
   *
   * @return list of callbacks registered with this object
   */
  public ListBoxCallbackList getListBoxCallbackList();
}
