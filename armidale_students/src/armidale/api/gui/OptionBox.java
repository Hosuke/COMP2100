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
 * ./armidale/api/gui/OptionBox.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:35
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:35
   * @version armidale v0.9.5
   */

  import armidale.api.ArmidaleObject;
  import armidale.api.gui.constants.OptionBoxButtonSet;
  import armidale.api.gui.constants.OptionBoxMessageType;

public interface OptionBox extends ArmidaleObject, OptionBoxMessageType, OptionBoxButtonSet {

  // Attributes Defaults
  //
  public static final String DEFAULT_TITLE = "";
  public static final String DEFAULT_MESSAGE = "";
  public static final int DEFAULT_MESSAGETYPE = PLAIN;
  public static final int DEFAULT_BUTTONSET = DEFAULT;
  public static final boolean DEFAULT_INPUT = false;

  // attribute set/get/is methods
  //

  /**
   * Returns the value of the <tt>title</tt> attribute.
   * <p>
   *
   * @return value of the <tt>title</tt> attribute
   */
  public String getTitle();

  /**
   * Sets the <tt>title</tt> attribute.
   * <p>
   *
   * @param  title value of the <tt>title</tt> attribute. 
   */
  public void setTitle(String title);

  /**
   * Returns the value of the <tt>message</tt> attribute.
   * <p>
   *
   * @return value of the <tt>message</tt> attribute
   */
  public String getMessage();

  /**
   * Sets the <tt>message</tt> attribute.
   * <p>
   *
   * @param  message value of the <tt>message</tt> attribute. 
   */
  public void setMessage(String message);

  /**
   * Returns the value of the <tt>messageType</tt> attribute.
   * <p>
   *
   * @return value of the <tt>messageType</tt> attribute
   */
  public int getMessageType();

  /**
   * Sets the <tt>messageType</tt> attribute.
   * <p>
   *
   * @param  messageType value of the <tt>messageType</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.OptionBoxMessageType armidale.api.gui.constants.OptionBoxMessageType} interface
   */
  public void setMessageType(int messageType);

  /**
   * Returns the value of the <tt>buttonSet</tt> attribute.
   * <p>
   *
   * @return value of the <tt>buttonSet</tt> attribute
   */
  public int getButtonSet();

  /**
   * Sets the <tt>buttonSet</tt> attribute.
   * <p>
   *
   * @param  buttonSet value of the <tt>buttonSet</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.OptionBoxButtonSet armidale.api.gui.constants.OptionBoxButtonSet} interface
   */
  public void setButtonSet(int buttonSet);

  /**
   * Returns the value of the <tt>icon</tt> attribute.
   * <p>
   *
   * @return value of the <tt>icon</tt> attribute
   */
  public Image getIcon();

  /**
   * Sets the <tt>icon</tt> attribute.
   * <p>
   *
   * @param  icon value of the <tt>icon</tt> attribute. 
   */
  public void setIcon(Image icon);

  /**
   * Returns the value of the <tt>input</tt> attribute.
   * <p>
   *
   * @return value of the <tt>input</tt> attribute
   */
  public boolean hasInput();

  /**
   * Sets the <tt>input</tt> attribute.
   * <p>
   *
   * @param  input value of the <tt>input</tt> attribute. 
   */
  public void setInput(boolean input);

  // callback
  //

  /**
   * Registers a callback with this OptionBox
   *
   * @param  callback callback to be added
   *
   */
  public void addOptionBoxCallback(OptionBoxCallback callback);

  /**
   * Removes a callback registered with this OptionBox
   *
   * @param  callback callback to be removed
   */
  public void removeOptionBoxCallback(OptionBoxCallback callback);

  /**
   * Removes all callbacks registered with this OptionBox
   *
   */
  public void removeAllOptionBoxCallbacks();

  /**
   * Returns the list of callbacks registered with this OptionBox
   *
   * @return list of callbacks registered with this object
   */
  public OptionBoxCallbackList getOptionBoxCallbackList();

  // other methods
  //

  /**
   *
   */
  public void open();

  /**
   *
   * @param desktop
   */
  public void open(Desktop desktop);
}
