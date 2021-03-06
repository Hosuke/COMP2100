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
 * ./armidale/api/gui/impl/clientserver/PopupMenuServerImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:47
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.MenuItem;
  import armidale.api.gui.PopupMenuFactory;
  import java.io.IOException;

public class PopupMenuServerImpl extends ComponentServerImpl implements PopupMenuClientServerIds {

  // constructor
  //
  public PopupMenuServerImpl(ServerContext context) {
    super(context);
  }

  // Class ID
  //
  public int getClassId() {
    return PopupMenuFactory.CLASS_ID;
  }

  // other methods
  //

  public void addMenuItem(MenuItem menuItem) {
    Message msg = new MethodCallMessage(this.getId(), addMenuItemMenuItemId);
    msg.writeObjectId((MessagingObject)menuItem);
    transport.writeMessage(msg);
  }

  public void removeMenuItem(MenuItem menuItem) {
    Message msg = new MethodCallMessage(this.getId(), removeMenuItemMenuItemId);
    msg.writeObjectId((MessagingObject)menuItem);
    transport.writeMessage(msg);
  }

  public void removeAllMenuItems() {
    Message msg = new MethodCallMessage(this.getId(), removeAllMenuItemsId);
    transport.writeMessage(msg);
  }

}
