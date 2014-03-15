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
 * ./armidale/api/gui/impl/platform/swing/RadioButtonPanelImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:12
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.RadioButton;
  import armidale.api.gui.RadioButtonPanel;
  import armidale.api.gui.RadioButtonPanelCallback;
  import armidale.api.gui.RadioButtonPanelCallbackList;
  import armidale.api.gui.constants.Orientation;
  import armidale.api.gui.impl.platform.swing.PanelImpl;
  import java.util.LinkedList;
  //--ARM-- imports
  import javax.swing.JRadioButton;
  import javax.swing.ButtonGroup;
  import javax.swing.JPanel;
  import javax.swing.JComponent;
  import javax.swing.AbstractButton;
  import java.awt.GridLayout;
  import java.awt.event.ActionListener;
  import java.awt.event.ActionEvent;
  import java.util.Enumeration;
  //--ARM-- imports

public class RadioButtonPanelImpl extends PanelImpl implements RadioButtonPanel {

  //--ARM-- declarations
  private ButtonGroup    theButtonGroup;
  private int            orientation;
  //--ARM-- declarations

  // Callback object
  //

  protected RadioButtonPanelCallbackList radioButtonPanelCallbackList = new RadioButtonPanelCallbackList();

  public RadioButtonPanelImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    setOrientation(DEFAULT_ORIENTATION);
    //--ARM-- constructor() - end
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new JPanel();
    //--ARM-- setPeer()
  }

  // Callbacks
  //

  public void addRadioButtonPanelCallback(RadioButtonPanelCallback callback) {
    radioButtonPanelCallbackList.add(callback);
    //--ARM-- public void addRadioButtonPanelCallback(RadioButtonPanelCallback callback)
    //--ARM-- public void addRadioButtonPanelCallback(RadioButtonPanelCallback callback)
  }

  public void removeRadioButtonPanelCallback(RadioButtonPanelCallback callback) {
    radioButtonPanelCallbackList.remove(callback);
    //--ARM-- public void removeRadioButtonPanelCallback(RadioButtonPanelCallback callback)
    //--ARM-- public void removeRadioButtonPanelCallback(RadioButtonPanelCallback callback)
  }

  public void removeAllRadioButtonPanelCallbacks() {
    radioButtonPanelCallbackList.clear();
    //--ARM-- public void removeAllRadioButtonPanelCallbacks()
    //--ARM-- public void removeAllRadioButtonPanelCallbacks()
  }

  public RadioButtonPanelCallbackList getRadioButtonPanelCallbackList() {
    //--ARM-- public RadioButtonPanelCallbackList getRadioButtonPanelCallbackList()
    //--ARM-- public RadioButtonPanelCallbackList getRadioButtonPanelCallbackList()
    return radioButtonPanelCallbackList;
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
    if (orientation==Orientation.VERTICAL) {
      ((JPanel)peer).setLayout(new GridLayout(0,1));
    } else {
      ((JPanel)peer).setLayout(new GridLayout(1,0));
    }
    //--ARM-- public void setOrientation(int orientation)
  }

  // other methods
  //

  public void addRadioButton(RadioButton radioButton) {
    //--ARM-- public void addRadioButton(RadioButton radioButton)
    AbstractButton theComponent;
    if (radioButton != null) {
      theComponent = (AbstractButton) ((WidgetImpl)radioButton).peer;
      ((JPanel)peer).add(theComponent);
      if (theButtonGroup == null) {
        theButtonGroup = new ButtonGroup();
      }
      theButtonGroup.add(theComponent);
      theComponent.addActionListener
      ( new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int index = 0;
          for (Enumeration element=theButtonGroup.getElements(); element.hasMoreElements();) {
            AbstractButton b = (AbstractButton)element.nextElement();
            if (b.isSelected()) {
              radioButtonPanelCallbackList.indexSelected(RadioButtonPanelImpl.this, index);
            }
            index++;
          }
        }
      });
    }
    //--ARM-- public void addRadioButton(RadioButton radioButton)
  }


  public void removeRadioButton(RadioButton radioButton) {
    //--ARM-- public void removeRadioButton(RadioButton radioButton)
    AbstractButton theComponent = (AbstractButton) ((WidgetImpl)radioButton).peer;
    ((JPanel)peer).remove(theComponent);
    theButtonGroup.remove(theComponent);
    //--ARM-- public void removeRadioButton(RadioButton radioButton)
  }


  public void removeAllRadioButtons() {
    //--ARM-- public void removeAllRadioButtons()
    ((JPanel)peer).removeAll();
    //--ARM-- public void removeAllRadioButtons()
  }

}