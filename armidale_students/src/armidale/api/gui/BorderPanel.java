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
 * ./armidale/api/gui/BorderPanel.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:07
 *
 */

package armidale.api.gui;
  /**
   * A BorderPanel is a panel on which widgets are layed out using a border 
   * layout manager. See the JDK description of java.awt.BorderLayout for more 
   * details.
   * <p>
   * This is a second paragraph
   * <p>
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:07
   * @version armidale v0.9.5
   */

public interface BorderPanel extends Panel {

  // Attributes Defaults
  //
  public static final int DEFAULT_HORIZONTALGAP = 0;
  public static final int DEFAULT_VERTICALGAP = 0;

  // attribute set/get/is methods
  //

  /**
   * Returns the value of the <tt>horizontalGap</tt> attribute.
   * <p>
   * The horizontalGap attribute specifies the horizontal gap between widgets 
   * placed on the BorderPanel
   * <p>
   *
   * @return value of the <tt>horizontalGap</tt> attribute
   */
  public int getHorizontalGap();

  /**
   * Sets the <tt>horizontalGap</tt> attribute.
   * <p>
   * The horizontalGap attribute specifies the horizontal gap between widgets 
   * placed on the BorderPanel
   * <p>
   *
   * @param  horizontalGap value of the <tt>horizontalGap</tt> attribute. 
   */
  public void setHorizontalGap(int horizontalGap);

  /**
   * Returns the value of the <tt>verticalGap</tt> attribute.
   * <p>
   * The verticalGap attribute specifies the <tt>vertical</tt> gap between 
   * widgets placed on the BorderPanel
   * <p>
   *
   * @return value of the <tt>verticalGap</tt> attribute
   */
  public int getVerticalGap();

  /**
   * Sets the <tt>verticalGap</tt> attribute.
   * <p>
   * The verticalGap attribute specifies the <tt>vertical</tt> gap between 
   * widgets placed on the BorderPanel
   * <p>
   *
   * @param  verticalGap value of the <tt>verticalGap</tt> attribute. 
   */
  public void setVerticalGap(int verticalGap);

  // other methods
  //

  /**
   * Adds a specified BorderPanelWidget to the list of Widgets associated with 
   * this BorderPanel.
   * <p>
   * A widget is added to a BorderPanel by first encapsulating it in a 
   * BorderPanelWidget and then adding the BorderPanelWidget to the BorderPanel 
   * using the <tt>addWidget</tt> method. Widgets are removed using the 
   * <tt>removeWidget</tt> or <tt>removeAllWidgets</tt> methods.
   * <p>
   * @see #addWidget
   * @see #removeWidget
   * @see #removeAllWidgets
   *
   * @param borderPanelWidget BorderPanelWidget to be added
   */
  public void addWidget(BorderPanelWidget borderPanelWidget);

  /**
   * Removes a specified BorderPanelWidget from the list of Widgets associated 
   * with this BorderPanel.
   * <p>
   * @see #addWidget
   * @see #removeWidget
   * @see #removeAllWidgets
   *
   * @param borderPanelWidget BorderPanelWidget to be removed
   */
  public void removeWidget(BorderPanelWidget borderPanelWidget);

  /**
   * Clears the list of Widgets associated with this BorderPanel.
   * <p>
   * @see #addWidget
   * @see #removeWidget
   * @see #removeAllWidgets
   *
   */
  public void removeAllWidgets();
}