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
 * ./armidale/api/gui/impl/platform/swing/MenuBarImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:46
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.Menu;
  import armidale.api.gui.MenuBar;
  import armidale.api.gui.impl.platform.swing.ComponentImpl;
  //--ARM-- imports
  import javax.swing.JMenu;
  import javax.swing.JMenuBar;
  import javax.swing.JComponent;
  import armidale.api.impl.platform.swing.ArmidaleObjectImpl;
  //--ARM-- imports

public class MenuBarImpl extends ComponentImpl implements MenuBar {

  //--ARM-- declarations
  //--ARM-- declarations

  public MenuBarImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    //--ARM-- constructor() - end
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new JMenuBar();
    //--ARM-- setPeer()
  }

  // other methods
  //

  public void addMenu(Menu menu) {
    //--ARM-- public void addMenu(Menu menu)
    JMenu  theMenu = null;
      
    if (menu != null) {
      theMenu = (JMenu)((MenuImpl)menu).peer;
    }
    ((JMenuBar)peer).add(theMenu);
    //--ARM-- public void addMenu(Menu menu)
  }


  public void removeMenu(Menu menu) {
    //--ARM-- public void removeMenu(Menu menu)
    JComponent theComponent = (JComponent) ((MenuImpl)menu).peer;
    ((JMenuBar)peer).remove(theComponent);
    //--ARM-- public void removeMenu(Menu menu)
  }


  public void removeAllMenus() {
    //--ARM-- public void removeAllMenus()
    ((JMenuBar)peer).removeAll();    
    //--ARM-- public void removeAllMenus()
  }

}
