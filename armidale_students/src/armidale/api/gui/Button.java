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
 * ./armidale/api/gui/Button.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:51
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:57:51
   * @version armidale v0.9.5
   */

  import armidale.api.gui.constants.OrthogonalCompass;

public interface Button extends Action, OrthogonalCompass {

  // Attributes Defaults
  //
  public static final String DEFAULT_TEXT = "Button";
  public static final Image DEFAULT_ICON = null;
  public static final int DEFAULT_TEXTALIGNMENT = EAST;

  // attribute set/get/is methods
  //

  /**
   * Returns the value of the <tt>text</tt> attribute.
   * <p>
   *
   * @return value of the <tt>text</tt> attribute
   */
  public String getText();

  /**
   * Sets the <tt>text</tt> attribute.
   * <p>
   *
   * @param  text value of the <tt>text</tt> attribute. 
   */
  public void setText(String text);

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
   * Returns the value of the <tt>textAlignment</tt> attribute.
   * <p>
   *
   * @return value of the <tt>textAlignment</tt> attribute
   */
  public int getTextAlignment();

  /**
   * Sets the <tt>textAlignment</tt> attribute.
   * <p>
   *
   * @param  textAlignment value of the <tt>textAlignment</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.OrthogonalCompass armidale.api.gui.constants.OrthogonalCompass} interface
   */
  public void setTextAlignment(int textAlignment);
}
