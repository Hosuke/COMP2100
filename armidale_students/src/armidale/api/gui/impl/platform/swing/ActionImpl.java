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
 * ./armidale/api/gui/impl/platform/swing/ActionImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:49
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.Action;
  import armidale.api.gui.ActionCallback;
  import armidale.api.gui.ActionCallbackList;
  import armidale.api.gui.impl.platform.swing.WidgetImpl;
  import java.util.LinkedList;
  //--ARM-- imports
import java.awt.Cursor;
import java.util.LinkedList;
import javax.swing.JComponent;
  //--ARM-- imports

public abstract class ActionImpl extends WidgetImpl implements Action {

  //--ARM-- declarations
  protected void setCursor() {
    if (actionCallbackList.size() == 0) {
      setCursorType(DEFAULT_CURSOR);
    } else {
      setCursorType(HAND_CURSOR);
    }
  }
  //--ARM-- declarations

  // Callback object
  //

  protected ActionCallbackList actionCallbackList = new ActionCallbackList();

  public ActionImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    //--ARM-- constructor() - end
    ((java.awt.Component) peer).addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(java.awt.event.MouseEvent event) {
          actionCallbackList.actionPerformed(ActionImpl.this);
        }
      });
    setCursorType(DEFAULT_CURSOR);
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    //--ARM-- setPeer()
  }

  // Callbacks
  //

  public void addActionCallback(ActionCallback callback) {
    actionCallbackList.add(callback);
    //--ARM-- public void addActionCallback(ActionCallback callback)
    setCursor();
    //--ARM-- public void addActionCallback(ActionCallback callback)
  }

  public void removeActionCallback(ActionCallback callback) {
    actionCallbackList.remove(callback);
    //--ARM-- public void removeActionCallback(ActionCallback callback)
    setCursor();
    //--ARM-- public void removeActionCallback(ActionCallback callback)
  }

  public void removeAllActionCallbacks() {
    actionCallbackList.clear();
    //--ARM-- public void removeAllActionCallbacks()
    setCursor();
    //--ARM-- public void removeAllActionCallbacks()
  }

  public ActionCallbackList getActionCallbackList() {
    //--ARM-- public ActionCallbackList getActionCallbackList()
    //--ARM-- public ActionCallbackList getActionCallbackList()
    return actionCallbackList;
  }
}
