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
 * ./armidale/api/gui/TabbedPanel.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:20
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:20
   * @version armidale v0.9.5
   */

  import armidale.api.gui.constants.OrthogonalCompass;
  import armidale.api.gui.constants.TabLayout;

public interface TabbedPanel extends Widget, OrthogonalCompass, TabLayout {

  // Attributes Defaults
  //
  public static final int DEFAULT_SELECTEDINDEX = 0;
  public static final int DEFAULT_TABPLACEMENT = NORTH;
  public static final int DEFAULT_TABLAYOUT = WRAP;

  // attribute set/get/is methods
  //

  /**
   * Sets the <tt>selectedIndex</tt> attribute.
   * <p>
   *
   * @param  selectedIndex value of the <tt>selectedIndex</tt> attribute. 
   */
  public void setSelectedIndex(int selectedIndex);

  /**
   * Returns the value of the <tt>tabPlacement</tt> attribute.
   * <p>
   *
   * @return value of the <tt>tabPlacement</tt> attribute
   */
  public int getTabPlacement();

  /**
   * Sets the <tt>tabPlacement</tt> attribute.
   * <p>
   *
   * @param  tabPlacement value of the <tt>tabPlacement</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.OrthogonalCompass armidale.api.gui.constants.OrthogonalCompass} interface
   */
  public void setTabPlacement(int tabPlacement);

  /**
   * Returns the value of the <tt>tabLayout</tt> attribute.
   * <p>
   *
   * @return value of the <tt>tabLayout</tt> attribute
   */
  public int getTabLayout();

  /**
   * Sets the <tt>tabLayout</tt> attribute.
   * <p>
   *
   * @param  tabLayout value of the <tt>tabLayout</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.TabLayout armidale.api.gui.constants.TabLayout} interface
   */
  public void setTabLayout(int tabLayout);

  // callback
  //

  /**
   * Registers a callback with this TabbedPanel
   *
   * @param  callback callback to be added
   *
   */
  public void addTabbedPanelCallback(TabbedPanelCallback callback);

  /**
   * Removes a callback registered with this TabbedPanel
   *
   * @param  callback callback to be removed
   */
  public void removeTabbedPanelCallback(TabbedPanelCallback callback);

  /**
   * Removes all callbacks registered with this TabbedPanel
   *
   */
  public void removeAllTabbedPanelCallbacks();

  /**
   * Returns the list of callbacks registered with this TabbedPanel
   *
   * @return list of callbacks registered with this object
   */
  public TabbedPanelCallbackList getTabbedPanelCallbackList();

  // other methods
  //

  /**
   * Adds a specified TabbedPanelWidget to the list of Tabs associated with 
   * this TabbedPanel.
   * <p>
   * @see #addTab
   * @see #removeTab
   * @see #removeAllTabs
   *
   * @param tabbedPanelWidget TabbedPanelWidget to be added
   */
  public void addTab(TabbedPanelWidget tabbedPanelWidget);

  /**
   * Removes a specified TabbedPanelWidget from the list of Tabs associated 
   * with this TabbedPanel.
   * <p>
   * @see #addTab
   * @see #removeTab
   * @see #removeAllTabs
   *
   * @param tabbedPanelWidget TabbedPanelWidget to be removed
   */
  public void removeTab(TabbedPanelWidget tabbedPanelWidget);

  /**
   * Clears the list of Tabs associated with this TabbedPanel.
   * <p>
   * @see #addTab
   * @see #removeTab
   * @see #removeAllTabs
   *
   */
  public void removeAllTabs();
}
