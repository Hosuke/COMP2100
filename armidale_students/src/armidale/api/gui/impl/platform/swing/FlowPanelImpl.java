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
 * ./armidale/api/gui/impl/platform/swing/FlowPanelImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:09
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.FlowPanel;
  import armidale.api.gui.Widget;
  import armidale.api.gui.constants.FlowAlignment;
  import armidale.api.gui.impl.platform.swing.PanelImpl;
  //--ARM-- imports
  import java.awt.FlowLayout;
  import javax.swing.JPanel;
  import javax.swing.JComponent;  
  import armidale.api.impl.platform.swing.ArmidaleObjectImpl;
  //--ARM-- imports

public class FlowPanelImpl extends PanelImpl implements FlowPanel {

  //--ARM-- declarations
  private int alignment;
  //--ARM-- declarations

  public FlowPanelImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    setHorizontalGap(DEFAULT_HORIZONTALGAP);
    setVerticalGap(DEFAULT_VERTICALGAP);
    setAlignment(DEFAULT_ALIGNMENT);
    //--ARM-- constructor() - end
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new JPanel();
    //--ARM-- setPeer()
  }

  // set/get/is methods
  //

  public int getHorizontalGap() {
    //--ARM-- public int getHorizontalGap()
    return ((FlowLayout)((JPanel)peer).getLayout()).getVgap();
    //--ARM-- public int getHorizontalGap()
  }

  public void setHorizontalGap(int horizontalGap) {
    //--ARM-- public void setHorizontalGap(int horizontalGap)
    ((FlowLayout)((JPanel)peer).getLayout()).setHgap(horizontalGap);
    //--ARM-- public void setHorizontalGap(int horizontalGap)
  }

  public int getVerticalGap() {
    //--ARM-- public int getVerticalGap()
    return ((FlowLayout)((JPanel)peer).getLayout()).getVgap();
    //--ARM-- public int getVerticalGap()
  }

  public void setVerticalGap(int verticalGap) {
    //--ARM-- public void setVerticalGap(int verticalGap)
    ((FlowLayout)((JPanel)peer).getLayout()).setVgap(verticalGap);
    //--ARM-- public void setVerticalGap(int verticalGap)
  }

  public int getAlignment() {
    //--ARM-- public int getAlignment()
    return alignment;
    //--ARM-- public int getAlignment()
  }

  public void setAlignment(int alignment) {
    //--ARM-- public void setAlignment(int alignment)
    this.alignment = alignment;
    switch (alignment) {
      case LEFT:
        ((FlowLayout)((JPanel)peer).getLayout()).setAlignment(FlowLayout.LEFT);
        break;
      case CENTER:
        ((FlowLayout)((JPanel)peer).getLayout()).setAlignment(FlowLayout.CENTER);
        break;
      case RIGHT:
        ((FlowLayout)((JPanel)peer).getLayout()).setAlignment(FlowLayout.RIGHT);
        break;
      case LEADING:
        ((FlowLayout)((JPanel)peer).getLayout()).setAlignment(FlowLayout.LEADING);
        break;
      case TRAILING:
        ((FlowLayout)((JPanel)peer).getLayout()).setAlignment(FlowLayout.TRAILING);
        break;
    }
    //--ARM-- public void setAlignment(int alignment)
  }

  // other methods
  //

  public void addWidget(Widget widget) {
    //--ARM-- public void addWidget(Widget widget)
    if (widget != null) 
      ((JPanel)peer).add((JComponent)((WidgetImpl)widget).peer);
    //--ARM-- public void addWidget(Widget widget)
  }


  public void removeWidget(Widget widget) {
    //--ARM-- public void removeWidget(Widget widget)
    JComponent theComponent = (JComponent) ((WidgetImpl)widget).peer;
    ((JPanel)peer).remove(theComponent);
    //--ARM-- public void removeWidget(Widget widget)
  }


  public void removeAllWidgets() {
    //--ARM-- public void removeAllWidgets()
    ((JPanel)peer).removeAll();
    //--ARM-- public void removeAllWidgets()
  }

}
