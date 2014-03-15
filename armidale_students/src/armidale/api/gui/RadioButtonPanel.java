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
 * ./armidale/api/gui/RadioButtonPanel.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:11
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:11
   * @version armidale v0.9.5
   */

  import armidale.api.gui.constants.Orientation;

public interface RadioButtonPanel extends Panel, Orientation {

  // Attributes Defaults
  //
  public static final int DEFAULT_ORIENTATION = VERTICAL;

  // attribute set/get/is methods
  //

  /**
   * Returns the value of the <tt>orientation</tt> attribute.
   * <p>
   *
   * @return value of the <tt>orientation</tt> attribute
   */
  public int getOrientation();

  /**
   * Sets the <tt>orientation</tt> attribute.
   * <p>
   *
   * @param  orientation value of the <tt>orientation</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.Orientation armidale.api.gui.constants.Orientation} interface
   */
  public void setOrientation(int orientation);

  // callback
  //

  /**
   * Registers a callback with this RadioButtonPanel
   *
   * @param  callback callback to be added
   *
   */
  public void addRadioButtonPanelCallback(RadioButtonPanelCallback callback);

  /**
   * Removes a callback registered with this RadioButtonPanel
   *
   * @param  callback callback to be removed
   */
  public void removeRadioButtonPanelCallback(RadioButtonPanelCallback callback);

  /**
   * Removes all callbacks registered with this RadioButtonPanel
   *
   */
  public void removeAllRadioButtonPanelCallbacks();

  /**
   * Returns the list of callbacks registered with this RadioButtonPanel
   *
   * @return list of callbacks registered with this object
   */
  public RadioButtonPanelCallbackList getRadioButtonPanelCallbackList();

  // other methods
  //

  /**
   * Adds a specified RadioButton to the list of RadioButtons associated with 
   * this RadioButtonPanel.
   * <p>
   * @see #addRadioButton
   * @see #removeRadioButton
   * @see #removeAllRadioButtons
   *
   * @param radioButton RadioButton to be added
   */
  public void addRadioButton(RadioButton radioButton);

  /**
   * Removes a specified RadioButton from the list of RadioButtons associated 
   * with this RadioButtonPanel.
   * <p>
   * @see #addRadioButton
   * @see #removeRadioButton
   * @see #removeAllRadioButtons
   *
   * @param radioButton RadioButton to be removed
   */
  public void removeRadioButton(RadioButton radioButton);

  /**
   * Clears the list of RadioButtons associated with this RadioButtonPanel.
   * <p>
   * @see #addRadioButton
   * @see #removeRadioButton
   * @see #removeAllRadioButtons
   *
   */
  public void removeAllRadioButtons();
}
