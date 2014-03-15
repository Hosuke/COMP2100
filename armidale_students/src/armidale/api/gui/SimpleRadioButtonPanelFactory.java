/*
 *  Copyright (c) 2000-2002, Shayne R Flint
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *  3. The name of the author may not be used to endorse or promote products
 *  derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package armidale.api.gui;

import armidale.api.ArmidaleObject;
import armidale.api.context.Context;

public class SimpleRadioButtonPanelFactory {

  public static RadioButtonPanel create(Context context, String[] items, String title, int edgeStyle) {
    RadioButton[]     buttons  = new RadioButton[items.length];
    RadioButtonPanel  group    = RadioButtonPanelFactory.create(context);
    for (int i = 0; i < buttons.length; i++) {
      buttons[i] = RadioButtonFactory.create(context);
      buttons[i].setText(items[i]);
      group.addRadioButton(buttons[i]);
    }
    group.setEdgeStyle(edgeStyle);
    group.setTitle(title);
    if (items.length > 0) {
      buttons[0].setSelected(true);
    }
    return group;
  }


  public static RadioButtonPanel create(Context context, String[] items, String title) {
    return create(context, items, title, 0);
  }


  public static RadioButtonPanel create(Context context, String[] items) {
    return create(context, items, "", 0);
  }
}

