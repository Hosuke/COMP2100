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
 * ./armidale/api/gui/impl/platform/swing/SplitPanelImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:19
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.SplitPanel;
  import armidale.api.gui.Widget;
  import armidale.api.gui.constants.Orientation;
  import armidale.api.gui.impl.platform.swing.WidgetImpl;
  //--ARM-- imports
  import javax.swing.JSplitPane;
  import javax.swing.JComponent;
  //--ARM-- imports

public class SplitPanelImpl extends WidgetImpl implements SplitPanel {

  //--ARM-- declarations
  private int    orientation;
  private float  dividerLocation;
  private Widget leftWidget;
  private Widget rightWidget;
  //--ARM-- declarations

  public SplitPanelImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    setOrientation(DEFAULT_ORIENTATION);
    setDividerLocation(DEFAULT_DIVIDERLOCATION);
    setDividerSize(DEFAULT_DIVIDERSIZE);
    setResizeWeight(DEFAULT_RESIZEWEIGHT);
    setExpandable(DEFAULT_EXPANDABLE);
    //--ARM-- constructor() - end
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new JSplitPane();
    //--ARM-- setPeer()
  }

  // set/get/is methods
  //

  public int getOrientation() {
    //--ARM-- public int getOrientation()
    return orientation;
    //--ARM-- public int getOrientation()
  }

  public void setOrientation(int orientation) {
    //--ARM-- public void setOrientation(int orientation)
    this.orientation = orientation;
    switch (orientation) {
      case VERTICAL:
        ((JSplitPane)peer).setOrientation(JSplitPane.VERTICAL_SPLIT);
        break;
      case HORIZONTAL:
        ((JSplitPane)peer).setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        break;
    }
    //--ARM-- public void setOrientation(int orientation)
  }

  public float getDividerLocation() {
    //--ARM-- public float getDividerLocation()
    return dividerLocation;
    //--ARM-- public float getDividerLocation()
  }

  public void setDividerLocation(float dividerLocation) {
    //--ARM-- public void setDividerLocation(float dividerLocation)
    this.dividerLocation = dividerLocation;
    ((JSplitPane)peer).setDividerLocation(dividerLocation);
    //--ARM-- public void setDividerLocation(float dividerLocation)
  }

  public int getDividerSize() {
    //--ARM-- public int getDividerSize()
    return ((JSplitPane)peer).getDividerSize();
    //--ARM-- public int getDividerSize()
  }

  public void setDividerSize(int dividerSize) {
    //--ARM-- public void setDividerSize(int dividerSize)
    ((JSplitPane)peer).setDividerSize(dividerSize);
    //--ARM-- public void setDividerSize(int dividerSize)
  }

  public float getResizeWeight() {
    //--ARM-- public float getResizeWeight()
    return (float)((JSplitPane)peer).getResizeWeight();
    //--ARM-- public float getResizeWeight()
  }

  public void setResizeWeight(float resizeWeight) {
    //--ARM-- public void setResizeWeight(float resizeWeight)
    ((JSplitPane)peer).setResizeWeight(resizeWeight);
    //--ARM-- public void setResizeWeight(float resizeWeight)
  }

  public boolean isExpandable() {
    //--ARM-- public boolean isExpandable()
    return ((JSplitPane)peer).isOneTouchExpandable();
    //--ARM-- public boolean isExpandable()
  }

  public void setExpandable(boolean expandable) {
    //--ARM-- public void setExpandable(boolean expandable)
    ((JSplitPane)peer).setOneTouchExpandable(expandable);
    //--ARM-- public void setExpandable(boolean expandable)
  }

  public Widget getLeftWidget() {
    //--ARM-- public Widget getLeftWidget()
    return leftWidget;
    //--ARM-- public Widget getLeftWidget()
  }

  public void setLeftWidget(Widget leftWidget) {
    //--ARM-- public void setLeftWidget(Widget leftWidget)
    this.leftWidget = leftWidget;
    if (leftWidget != null)
      ((JSplitPane)peer).setLeftComponent((JComponent)((WidgetImpl)leftWidget).peer);
    //--ARM-- public void setLeftWidget(Widget leftWidget)
  }

  public Widget getTopWidget() {
    return getLeftWidget();
  }

  public void setTopWidget(Widget leftWidget) {
    setLeftWidget(leftWidget);
  }

  public Widget getRightWidget() {
    //--ARM-- public Widget getRightWidget()
    return rightWidget;
    //--ARM-- public Widget getRightWidget()
  }

  public void setRightWidget(Widget rightWidget) {
    //--ARM-- public void setRightWidget(Widget rightWidget)
    this.rightWidget = rightWidget;
    if (rightWidget != null)
      ((JSplitPane)peer).setRightComponent((JComponent)((WidgetImpl)rightWidget).peer);
    //--ARM-- public void setRightWidget(Widget rightWidget)
  }

  public Widget getBottomWidget() {
    return getRightWidget();
  }

  public void setBottomWidget(Widget rightWidget) {
    setRightWidget(rightWidget);
  }
}