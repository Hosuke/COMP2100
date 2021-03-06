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
 * ./armidale/api/gui/impl/platform/swing/SliderImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:04
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.Slider;
  import armidale.api.gui.SliderCallback;
  import armidale.api.gui.SliderCallbackList;
  import armidale.api.gui.constants.Orientation;
  import armidale.api.gui.impl.platform.swing.WidgetImpl;
  import java.util.LinkedList;
  //--ARM-- imports
  import javax.swing.JSlider;
  import javax.swing.JLabel;
  import javax.swing.SwingConstants;
  import java.util.Hashtable;
  //--ARM-- imports

public class SliderImpl extends WidgetImpl implements Slider {

  //--ARM-- declarations
  private Hashtable labels;
  //--ARM-- declarations

  // Callback object
  //

  protected SliderCallbackList sliderCallbackList = new SliderCallbackList();

  public SliderImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    setMinimum(DEFAULT_MINIMUM);
    setMaximum(DEFAULT_MAXIMUM);
    setValue(DEFAULT_VALUE);
    setOrientation(DEFAULT_ORIENTATION);
    setMajorTickSpacing(DEFAULT_MAJORTICKSPACING);
    setMinorTickSpacing(DEFAULT_MINORTICKSPACING);
    setTicksVisible(DEFAULT_TICKSVISIBLE);
    setLabelsVisible(DEFAULT_LABELSVISIBLE);
    setSnapToTicks(DEFAULT_SNAPTOTICKS);
    //--ARM-- constructor() - end
    ((JSlider)peer).addMouseListener(
       new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent event) {
              sliderCallbackList.valueAdjusted(SliderImpl.this, SliderImpl.this.getValue());
            }
         });
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new JSlider();
    //--ARM-- setPeer()
  }

  // Callbacks
  //

  public void addSliderCallback(SliderCallback callback) {
    sliderCallbackList.add(callback);
    //--ARM-- public void addSliderCallback(SliderCallback callback)
    //--ARM-- public void addSliderCallback(SliderCallback callback)
  }

  public void removeSliderCallback(SliderCallback callback) {
    sliderCallbackList.remove(callback);
    //--ARM-- public void removeSliderCallback(SliderCallback callback)
    //--ARM-- public void removeSliderCallback(SliderCallback callback)
  }

  public void removeAllSliderCallbacks() {
    sliderCallbackList.clear();
    //--ARM-- public void removeAllSliderCallbacks()
    //--ARM-- public void removeAllSliderCallbacks()
  }

  public SliderCallbackList getSliderCallbackList() {
    //--ARM-- public SliderCallbackList getSliderCallbackList()
    //--ARM-- public SliderCallbackList getSliderCallbackList()
    return sliderCallbackList;
  }

  // set/get/is methods
  //

  public int getMinimum() {
    //--ARM-- public int getMinimum()
    return ((JSlider)peer).getMinimum();
    //--ARM-- public int getMinimum()
  }

  public void setMinimum(int minimum) {
    //--ARM-- public void setMinimum(int minimum)
    ((JSlider)peer).setMinimum(minimum);
    //--ARM-- public void setMinimum(int minimum)
  }

  public int getMaximum() {
    //--ARM-- public int getMaximum()
    return ((JSlider)peer).getMaximum();
    //--ARM-- public int getMaximum()
  }

  public void setMaximum(int maximum) {
    //--ARM-- public void setMaximum(int maximum)
    ((JSlider)peer).setMaximum(maximum);
    //--ARM-- public void setMaximum(int maximum)
  }

  public int getValue() {
    //--ARM-- public int getValue()
    return ((JSlider)peer).getValue();
    //--ARM-- public int getValue()
  }

  public void setValue(int value) {
    //--ARM-- public void setValue(int value)
    ((JSlider)peer).setValue(value);
    //--ARM-- public void setValue(int value)
  }

  public int getOrientation() {
    //--ARM-- public int getOrientation()
    if (((JSlider)peer).getOrientation()==SwingConstants.HORIZONTAL) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
    //--ARM-- public int getOrientation()
  }

  public void setOrientation(int orientation) {
    //--ARM-- public void setOrientation(int orientation)
    if (orientation==Orientation.HORIZONTAL) {
      ((JSlider)peer).setOrientation(SwingConstants.HORIZONTAL);
    } else {
      ((JSlider)peer).setOrientation(SwingConstants.VERTICAL);
    }
    //--ARM-- public void setOrientation(int orientation)
  }

  public int getMajorTickSpacing() {
    //--ARM-- public int getMajorTickSpacing()
    return ((JSlider)peer).getMajorTickSpacing();
    //--ARM-- public int getMajorTickSpacing()
  }

  public void setMajorTickSpacing(int majorTickSpacing) {
    //--ARM-- public void setMajorTickSpacing(int majorTickSpacing)
    ((JSlider)peer).setMajorTickSpacing(majorTickSpacing);
    //--ARM-- public void setMajorTickSpacing(int majorTickSpacing)
  }

  public int getMinorTickSpacing() {
    //--ARM-- public int getMinorTickSpacing()
    return ((JSlider)peer).getMinorTickSpacing();
    //--ARM-- public int getMinorTickSpacing()
  }

  public void setMinorTickSpacing(int minorTickSpacing) {
    //--ARM-- public void setMinorTickSpacing(int minorTickSpacing)
    ((JSlider)peer).setMinorTickSpacing(minorTickSpacing);
    //--ARM-- public void setMinorTickSpacing(int minorTickSpacing)
  }

  public boolean ticksVisible() {
    //--ARM-- public boolean ticksVisible()
    return ((JSlider)peer).getPaintTicks();
    //--ARM-- public boolean ticksVisible()
  }

  public void setTicksVisible(boolean ticksVisible) {
    //--ARM-- public void setTicksVisible(boolean ticksVisible)
    ((JSlider)peer).setPaintTicks(ticksVisible);
    //--ARM-- public void setTicksVisible(boolean ticksVisible)
  }

  public boolean labelsVisible() {
    //--ARM-- public boolean labelsVisible()
    return ((JSlider)peer).getPaintLabels();
    //--ARM-- public boolean labelsVisible()
  }

  public void setLabelsVisible(boolean labelsVisible) {
    //--ARM-- public void setLabelsVisible(boolean labelsVisible)
    ((JSlider)peer).setPaintLabels(labelsVisible);
    //--ARM-- public void setLabelsVisible(boolean labelsVisible)
  }

  public boolean snapToTicks() {
    //--ARM-- public boolean snapToTicks()
    return ((JSlider)peer).getSnapToTicks();
    //--ARM-- public boolean snapToTicks()
  }

  public void setSnapToTicks(boolean snapToTicks) {
    //--ARM-- public void setSnapToTicks(boolean snapToTicks)
    ((JSlider)peer).setSnapToTicks(snapToTicks);
    //--ARM-- public void setSnapToTicks(boolean snapToTicks)
  }

  // other methods
  //

  public void addLabel(int value, String label) {
    //--ARM-- public void addLabel(int value, String label)
    if (labels == null) {
      labels = new Hashtable();
    }
    labels.put(new Integer(value), new JLabel(label));
    ((JSlider)peer).setLabelTable(labels);
    //--ARM-- public void addLabel(int value, String label)
  }


  public void useIntegerLabels(int spacing) {
    //--ARM-- public void useIntegerLabels(int spacing)
    labels = null;
    ((JSlider)peer).setLabelTable(((JSlider)peer).createStandardLabels(spacing));
    //--ARM-- public void useIntegerLabels(int spacing)
  }

}
