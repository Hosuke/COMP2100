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
 * ./armidale/api/gui/TabbedPanelWidget.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:31
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:31
   * @version armidale v0.9.5
   */

  import armidale.api.ArmidaleObject;

public interface TabbedPanelWidget extends ArmidaleObject {

  // Attributes Defaults
  //
  public static final String DEFAULT_TITLE = "tab";

  // attribute set/get/is methods
  //

  /**
   * Returns the value of the <tt>widget</tt> attribute.
   * <p>
   * the widget shown in the tab
   * <p>
   *
   * @return value of the <tt>widget</tt> attribute
   */
  public Widget getWidget();

  /**
   * Sets the <tt>widget</tt> attribute.
   * <p>
   * the widget shown in the tab
   * <p>
   *
   * @param  widget value of the <tt>widget</tt> attribute. 
   */
  public void setWidget(Widget widget);

  /**
   * Returns the value of the <tt>title</tt> attribute.
   * <p>
   * the title of the tab
   * <p>
   *
   * @return value of the <tt>title</tt> attribute
   */
  public String getTitle();

  /**
   * Sets the <tt>title</tt> attribute.
   * <p>
   * the title of the tab
   * <p>
   *
   * @param  title value of the <tt>title</tt> attribute. 
   */
  public void setTitle(String title);

  /**
   * Returns the value of the <tt>icon</tt> attribute.
   * <p>
   * the icon displayed to the left of the tab title
   * <p>
   *
   * @return value of the <tt>icon</tt> attribute
   */
  public Image getIcon();

  /**
   * Sets the <tt>icon</tt> attribute.
   * <p>
   * the icon displayed to the left of the tab title
   * <p>
   *
   * @param  icon value of the <tt>icon</tt> attribute. 
   */
  public void setIcon(Image icon);
}