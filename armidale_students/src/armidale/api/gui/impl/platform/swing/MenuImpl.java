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
 * ./armidale/api/gui/impl/platform/swing/MenuImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:54
 *
 */

package armidale.api.gui.impl.platform.swing;

  import armidale.api.context.*;
  import armidale.api.gui.Menu;
  import armidale.api.gui.MenuItem;
  import armidale.api.gui.impl.platform.swing.MenuItemImpl;
  //--ARM-- imports
  import javax.swing.JMenu;
  import javax.swing.JMenuItem;
  import javax.swing.JComponent;
  //--ARM-- imports

public class MenuImpl extends MenuItemImpl implements Menu {

  //--ARM-- declarations
  //--ARM-- declarations

  public MenuImpl() {
    //--ARM-- constructor()
    //--ARM-- constructor()
    //--ARM-- constructor() - end
    //--ARM-- constructor() - end
  }

  protected void setPeer() {
    //--ARM-- setPeer()
    peer = new JMenu();
    //--ARM-- setPeer()
  }

  // other methods
  //

  public void addSeparator() {
    //--ARM-- public void addSeparator()
    ((JMenu)peer).addSeparator();
    //--ARM-- public void addSeparator()
  }


  public void addMenuItem(MenuItem menuItem) {
    //--ARM-- public void addMenuItem(MenuItem menuItem)
    JMenuItem  theMenuItem = null;

    if (menuItem != null) {
      theMenuItem = (JMenuItem)((MenuItemImpl)menuItem).peer;
    }       
    ((JMenu)peer).add(theMenuItem);
    //--ARM-- public void addMenuItem(MenuItem menuItem)
  }


  public void removeMenuItem(MenuItem menuItem) {
    //--ARM-- public void removeMenuItem(MenuItem menuItem)
    JComponent theComponent = (JComponent) ((WidgetImpl)menuItem).peer;
    ((JMenu)peer).remove(theComponent);
    //--ARM-- public void removeMenuItem(MenuItem menuItem)
  }


  public void removeAllMenuItems() {
    //--ARM-- public void removeAllMenuItems()
   ((JMenu)peer).removeAll();    
    //--ARM-- public void removeAllMenuItems()
  }

}
