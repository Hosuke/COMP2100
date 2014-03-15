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
 * ./armidale/api/gui/impl/platform/swing/LabeledWidgetImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:03
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.Image;
  import armidale.api.gui.LabeledWidget;
  import armidale.api.gui.Widget;
  import armidale.api.gui.constants.Compass;
  import armidale.api.gui.impl.platform.swing.PanelImpl;
  //--ARM-- imports
  import javax.swing.JPanel;
  import java.awt.BorderLayout;
  import javax.swing.JComponent;
  import javax.swing.JLabel;
  import javax.swing.ImageIcon;
  import armidale.api.util.Strings;
  import armidale.api.ArgumentException;
  import javax.swing.BoxLayout;
  import javax.swing.Box;
  import java.awt.Insets;
  import java.awt.Color;
  import java.awt.Dimension;
  import javax.swing.BorderFactory;
  //--ARM-- imports

public class LabeledWidgetImpl extends PanelImpl implements LabeledWidget {

  //--ARM-- declarations
  
  private BoxLayout theLayout;
  
  private Image  theImage;
  private Widget theWidget;
  private String theText;
  private int    textImageAlignment;
  private int    labelWidgetAlignment;
  private int    textImageGap;
  private int    labelWidgetGap;
  
  private void layoutLabeledWidget() {
    JComponent theJComponent = null;
    JLabel     theJLabel     = null;
    ((JPanel)peer).removeAll();
    
    // get the components
    if (theImage != null || !Strings.isEmpty(theText)) {
      theJLabel = new JLabel();
      if (theImage != null) {
        theJLabel.setIcon((ImageIcon) (((ImageImpl) theImage).peer));
      }
      if (!Strings.isEmpty(theText)) {
        theJLabel.setText(theText);
      }
    }
    
    if (theWidget != null) {
      theJComponent = (JComponent) ((WidgetImpl)theWidget).peer;
    }
    
    // align the label text and image
    if (theJLabel != null) {
      theJLabel.setIconTextGap(textImageGap);
      theJLabel.setVerticalTextPosition(Conversions.verticalAlignment(textImageAlignment));
      theJLabel.setHorizontalTextPosition(Conversions.horizontalAlignment(textImageAlignment));
    }
    
    // align the label with the widget
    if (theJLabel==null && theJComponent==null) {
      return;

    } else if (theJLabel!=null && theJComponent==null) {
      theLayout = new BoxLayout((JPanel)peer, BoxLayout.X_AXIS);
      ((JPanel)peer).add(Box.createHorizontalGlue());
      ((JPanel)peer).add(theJLabel);
      ((JPanel)peer).add(Box.createHorizontalGlue());

    } else if (theJLabel==null && theJComponent!=null) {
      theLayout = new BoxLayout((JPanel)peer, BoxLayout.X_AXIS);
      ((JPanel)peer).add(Box.createHorizontalGlue());
      ((JPanel)peer).add(theJComponent);
      ((JPanel)peer).add(Box.createHorizontalGlue());

    } else {
      switch (labelWidgetAlignment) {
        case Compass.NORTH:
        case Compass.SOUTH:
          theJLabel.setAlignmentX(0.5f);
          theJComponent.setAlignmentX(0.5f);
          break;
        case Compass.NORTH_EAST:
        case Compass.SOUTH_EAST:
          theJLabel.setAlignmentX(1.0f);
          theJComponent.setAlignmentX(1.0f);
          break;
        case Compass.EAST:
        case Compass.WEST:
          theJLabel.setAlignmentY(0.5f);
          theJComponent.setAlignmentY(0.5f);
          break;
        case Compass.SOUTH_WEST:
        case Compass.NORTH_WEST:
          theJLabel.setAlignmentX(0.0f);
          theJComponent.setAlignmentX(0.0f);
          break;
        default:
          throw new ArgumentException("invalid imageAlignment [" + labelWidgetAlignment + "]");          
      }
      switch (labelWidgetAlignment) {
        case Compass.NORTH:
        case Compass.NORTH_EAST:
        case Compass.NORTH_WEST:
          ((JPanel)peer).setLayout(new BoxLayout((JPanel)peer, BoxLayout.Y_AXIS));
          ((JPanel)peer).add(theJLabel);
          ((JPanel)peer).add(Box.createVerticalStrut(labelWidgetGap));
          ((JPanel)peer).add(theJComponent);
          break;
        case Compass.EAST:
          ((JPanel)peer).setLayout(new BoxLayout((JPanel)peer, BoxLayout.X_AXIS));
          ((JPanel)peer).add(theJComponent);
          ((JPanel)peer).add(Box.createHorizontalStrut(labelWidgetGap));
          ((JPanel)peer).add(theJLabel);
          break;
        case Compass.SOUTH:
        case Compass.SOUTH_EAST:
        case Compass.SOUTH_WEST:
          ((JPanel)peer).setLayout(new BoxLayout((JPanel)peer, BoxLayout.Y_AXIS));
          ((JPanel)peer).add(theJComponent);
          ((JPanel)peer).add(Box.createVerticalStrut(labelWidgetGap));
          ((JPanel)peer).add(theJLabel);
          break;
        case Compass.WEST:
          ((JPanel)peer).setLayout(new BoxLayout((JPanel)peer, BoxLayout.X_AXIS));
          ((JPanel)peer).add(theJLabel);
          ((JPanel)peer).add(Box.createHorizontalStrut(labelWidgetGap));
          ((JPanel)peer).add(theJComponent);
          break;
        default:
          throw new ArgumentException("invalid imageAlignment [" + labelWidgetAlignment + "]");          
      }
    }
    // size the component
    //
    int preferredWidth  = 0;
    int preferredHeight = 0;
    if (theJLabel != null) {
//System.out.println("label: " + theJLabel.getPreferredSize());    
      preferredWidth = theJLabel.getPreferredSize().width;
      preferredHeight = theJLabel.getPreferredSize().height;
    }
    if (theJComponent != null) {
      switch (labelWidgetAlignment) {
        case Compass.NORTH:
        case Compass.NORTH_EAST:
        case Compass.NORTH_WEST:
        case Compass.SOUTH:
        case Compass.SOUTH_EAST:
        case Compass.SOUTH_WEST:
          preferredHeight = preferredHeight + theJComponent.getPreferredSize().height + labelWidgetGap;
          preferredWidth  = max(preferredWidth, theJComponent.getPreferredSize().width);
          break;
        case Compass.EAST:
        case Compass.WEST:
          preferredHeight = max(preferredHeight, theJComponent.getPreferredSize().height);
          preferredWidth  = preferredWidth + theJComponent.getPreferredSize().width  + labelWidgetGap;
          break;
        default:
          throw new ArgumentException("invalid imageAlignment [" + labelWidgetAlignment + "]");          
      }
    }
    Dimension preferredSize = new Dimension(preferredWidth, preferredHeight);
//System.out.println("Size=" + preferredSize);    
    ((JPanel)peer).setPreferredSize(preferredSize);
    //((JPanel)peer).setMinimumSize(preferredSize);
    //((JPanel)peer).setMaximumSize(preferredSize);

    ((JPanel)peer).validate();
    ((JPanel)peer).repaint();    
  }
  
  private int max(int a, int b) {
    if (a > b) {
      return a;
    } else {
      return b;
    }
  }
  
  //--ARM-- declarations

  public LabeledWidgetImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    setText(DEFAULT_TEXT);
    setTextImageAlignment(DEFAULT_TEXTIMAGEALIGNMENT);
    setTextImageGap(DEFAULT_TEXTIMAGEGAP);
    setLabelWidgetAlignment(DEFAULT_LABELWIDGETALIGNMENT);
    setLabelWidgetGap(DEFAULT_LABELWIDGETGAP);
    //--ARM-- constructor() - end
//    setEdgeStyle(LINE);
//    setEdgeColor(armidale.api.gui.Color.RED);
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new JPanel();
    ((JPanel)peer).setLayout(theLayout);
    //--ARM-- setPeer()
  }

  // set/get/is methods
  //

  public Image getImage() {
    //--ARM-- public Image getImage()
    return theImage;
    //--ARM-- public Image getImage()
  }

  public void setImage(Image image) {
    //--ARM-- public void setImage(Image image)
    theImage = image;
    layoutLabeledWidget();
    //--ARM-- public void setImage(Image image)
  }

  public String getText() {
    //--ARM-- public String getText()
    return theText;
    //--ARM-- public String getText()
  }

  public void setText(String text) {
    //--ARM-- public void setText(String text)
    theText = text;
    layoutLabeledWidget();
    //--ARM-- public void setText(String text)
  }

  public Widget getWidget() {
    //--ARM-- public Widget getWidget()
    return theWidget;
    //--ARM-- public Widget getWidget()
  }

  public void setWidget(Widget widget) {
    //--ARM-- public void setWidget(Widget widget)
    theWidget = widget;
    layoutLabeledWidget();
    //--ARM-- public void setWidget(Widget widget)
  }

  public int getTextImageAlignment() {
    //--ARM-- public int getTextImageAlignment()
    return textImageAlignment;
    //--ARM-- public int getTextImageAlignment()
  }

  public void setTextImageAlignment(int textImageAlignment) {
    //--ARM-- public void setTextImageAlignment(int textImageAlignment)
    this.textImageAlignment = textImageAlignment;
    layoutLabeledWidget();
    //--ARM-- public void setTextImageAlignment(int textImageAlignment)
  }

  public int getTextImageGap() {
    //--ARM-- public int getTextImageGap()
    return textImageGap;
    //--ARM-- public int getTextImageGap()
  }

  public void setTextImageGap(int textImageGap) {
    //--ARM-- public void setTextImageGap(int textImageGap)
    this.textImageGap = textImageGap;
    //--ARM-- public void setTextImageGap(int textImageGap)
  }

  public int getLabelWidgetAlignment() {
    //--ARM-- public int getLabelWidgetAlignment()
    return labelWidgetAlignment;
    //--ARM-- public int getLabelWidgetAlignment()
  }

  public void setLabelWidgetAlignment(int labelWidgetAlignment) {
    //--ARM-- public void setLabelWidgetAlignment(int labelWidgetAlignment)
    this.labelWidgetAlignment = labelWidgetAlignment;
    layoutLabeledWidget();
    //--ARM-- public void setLabelWidgetAlignment(int labelWidgetAlignment)
  }

  public int getLabelWidgetGap() {
    //--ARM-- public int getLabelWidgetGap()
    return labelWidgetGap;
    //--ARM-- public int getLabelWidgetGap()
  }

  public void setLabelWidgetGap(int labelWidgetGap) {
    //--ARM-- public void setLabelWidgetGap(int labelWidgetGap)
    this.labelWidgetGap = labelWidgetGap;
    //--ARM-- public void setLabelWidgetGap(int labelWidgetGap)
  }
}