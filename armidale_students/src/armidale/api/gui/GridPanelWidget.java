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
 * ./armidale/api/gui/GridPanelWidget.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:30
 *
 */

package armidale.api.gui;
  /**
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:30
   * @version armidale v0.9.5
   */

  import armidale.api.ArmidaleObject;
  import armidale.api.gui.constants.Compass;
  import armidale.api.gui.constants.FillMode;
  import armidale.api.gui.constants.GridCell;

public interface GridPanelWidget extends ArmidaleObject, GridCell, Compass, FillMode {

  // Attributes Defaults
  //
  public static final int DEFAULT_GRIDWIDTH = 1;
  public static final int DEFAULT_GRIDHEIGHT = 1;
  public static final float DEFAULT_WEIGHTX = 0.5f;
  public static final float DEFAULT_WEIGHTY = 0.5f;
  public static final int DEFAULT_ANCHOR = CENTER;
  public static final int DEFAULT_FILL = BOTH;
  public static final Insets DEFAULT_INSETS = new Insets(0,0,0,0);
  public static final int DEFAULT_INTERNALPADX = 0;
  public static final int DEFAULT_INTERNALPADY = 0;

  // attribute set/get/is methods
  //

  /**
   * Returns the value of the <tt>widget</tt> attribute.
   * <p>
   *
   * @return value of the <tt>widget</tt> attribute
   */
  public Widget getWidget();

  /**
   * Sets the <tt>widget</tt> attribute.
   * <p>
   *
   * @param  widget value of the <tt>widget</tt> attribute. 
   */
  public void setWidget(Widget widget);

  /**
   * Returns the value of the <tt>gridX</tt> attribute.
   * <p>
   *
   * @return value of the <tt>gridX</tt> attribute
   */
  public int getGridX();

  /**
   * Sets the <tt>gridX</tt> attribute.
   * <p>
   *
   * @param  gridX value of the <tt>gridX</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.GridCell armidale.api.gui.constants.GridCell} interface
   */
  public void setGridX(int gridX);

  /**
   * Returns the value of the <tt>gridY</tt> attribute.
   * <p>
   *
   * @return value of the <tt>gridY</tt> attribute
   */
  public int getGridY();

  /**
   * Sets the <tt>gridY</tt> attribute.
   * <p>
   *
   * @param  gridY value of the <tt>gridY</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.GridCell armidale.api.gui.constants.GridCell} interface
   */
  public void setGridY(int gridY);

  /**
   * Returns the value of the <tt>gridWidth</tt> attribute.
   * <p>
   *
   * @return value of the <tt>gridWidth</tt> attribute
   */
  public int getGridWidth();

  /**
   * Sets the <tt>gridWidth</tt> attribute.
   * <p>
   *
   * @param  gridWidth value of the <tt>gridWidth</tt> attribute. 
   */
  public void setGridWidth(int gridWidth);

  /**
   * Returns the value of the <tt>gridHeight</tt> attribute.
   * <p>
   *
   * @return value of the <tt>gridHeight</tt> attribute
   */
  public int getGridHeight();

  /**
   * Sets the <tt>gridHeight</tt> attribute.
   * <p>
   *
   * @param  gridHeight value of the <tt>gridHeight</tt> attribute. 
   */
  public void setGridHeight(int gridHeight);

  /**
   * Returns the value of the <tt>weightX</tt> attribute.
   * <p>
   *
   * @return value of the <tt>weightX</tt> attribute
   */
  public float getWeightX();

  /**
   * Sets the <tt>weightX</tt> attribute.
   * <p>
   *
   * @param  weightX value of the <tt>weightX</tt> attribute. 
   */
  public void setWeightX(float weightX);

  /**
   * Returns the value of the <tt>weightY</tt> attribute.
   * <p>
   *
   * @return value of the <tt>weightY</tt> attribute
   */
  public float getWeightY();

  /**
   * Sets the <tt>weightY</tt> attribute.
   * <p>
   *
   * @param  weightY value of the <tt>weightY</tt> attribute. 
   */
  public void setWeightY(float weightY);

  /**
   * Returns the value of the <tt>anchor</tt> attribute.
   * <p>
   *
   * @return value of the <tt>anchor</tt> attribute
   */
  public int getAnchor();

  /**
   * Sets the <tt>anchor</tt> attribute.
   * <p>
   *
   * @param  anchor value of the <tt>anchor</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.Compass armidale.api.gui.constants.Compass} interface
   */
  public void setAnchor(int anchor);

  /**
   * Returns the value of the <tt>fill</tt> attribute.
   * <p>
   *
   * @return value of the <tt>fill</tt> attribute
   */
  public int getFill();

  /**
   * Sets the <tt>fill</tt> attribute.
   * <p>
   *
   * @param  fill value of the <tt>fill</tt> attribute. 
   * Values for this parameter are defined in the {@link armidale.api.gui.constants.FillMode armidale.api.gui.constants.FillMode} interface
   */
  public void setFill(int fill);

  /**
   * Returns the value of the <tt>insets</tt> attribute.
   * <p>
   *
   * @return value of the <tt>insets</tt> attribute
   */
  public Insets getInsets();

  /**
   * Sets the <tt>insets</tt> attribute.
   * <p>
   *
   * @param  insets value of the <tt>insets</tt> attribute. 
   */
  public void setInsets(Insets insets);

  /**
   * Returns the value of the <tt>internalPadX</tt> attribute.
   * <p>
   *
   * @return value of the <tt>internalPadX</tt> attribute
   */
  public int getInternalPadX();

  /**
   * Sets the <tt>internalPadX</tt> attribute.
   * <p>
   *
   * @param  internalPadX value of the <tt>internalPadX</tt> attribute. 
   */
  public void setInternalPadX(int internalPadX);

  /**
   * Returns the value of the <tt>internalPadY</tt> attribute.
   * <p>
   *
   * @return value of the <tt>internalPadY</tt> attribute
   */
  public int getInternalPadY();

  /**
   * Sets the <tt>internalPadY</tt> attribute.
   * <p>
   *
   * @param  internalPadY value of the <tt>internalPadY</tt> attribute. 
   */
  public void setInternalPadY(int internalPadY);
}
