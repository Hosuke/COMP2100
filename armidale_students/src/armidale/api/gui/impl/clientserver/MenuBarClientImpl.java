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
 * ./armidale/api/gui/impl/clientserver/MenuBarClientImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:46
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.ImplementationException;
  import armidale.api.context.clientserver.*;
  import armidale.api.gui.Menu;
  import armidale.api.gui.MenuBar;
  import armidale.api.gui.MenuBarFactory;

public class MenuBarClientImpl extends ComponentClientImpl implements MenuBarClientServerIds {

  // constructor
  //
  public MenuBarClientImpl(ClientContext context, MenuBar peer) {
    super(context, peer);
  }

  // Class ID
  //
  public int getClassId() {
    return MenuBarFactory.CLASS_ID;
  }

  // other methods
  //

  public void addMenu(Menu menu) {
    Menu menuPeer;
    if (menu==null) {
     menuPeer = null;
    } else {
     menuPeer = (Menu)((MenuClientImpl)menu).peer;
    }
    ((MenuBar)peer).addMenu(menuPeer);
  }

  public void removeMenu(Menu menu) {
    Menu menuPeer;
    if (menu==null) {
     menuPeer = null;
    } else {
     menuPeer = (Menu)((MenuClientImpl)menu).peer;
    }
    ((MenuBar)peer).removeMenu(menuPeer);
  }

  public void removeAllMenus() {
    ((MenuBar)peer).removeAllMenus();
  }

  public void handleSetAttribute(int attributeId, Message message) {
    int index;
    int first;
    int count;

    switch (attributeId) {
      default:
        super.handleSetAttribute(attributeId, message);
    }
  }

  public void handleMethodCall(int methodId, Message message) {

    // method parameter values
    //
    Menu addMenuMenu;
    Menu removeMenuMenu;

    switch (methodId) {
      case addMenuMenuId:
        addMenuMenu = (Menu)objectRegistry.findObject(message.readObjectId());
        addMenu(addMenuMenu);
        break;
      case removeMenuMenuId:
        removeMenuMenu = (Menu)objectRegistry.findObject(message.readObjectId());
        removeMenu(removeMenuMenu);
        break;
      case removeAllMenusId:
        removeAllMenus();
        break;
      default:
        super.handleMethodCall(methodId, message);
    }
  }
}
