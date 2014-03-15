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
 * ./armidale/api/gui/MenuBarFactory.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:46
 *
 */

package armidale.api.gui;

  import armidale.api.ArmidaleObjectFactory;
  import armidale.api.context.*;
  import armidale.api.context.clientserver.*;
  import armidale.api.context.platform.*;

  /**
   * This factory class is used to create {@link armidale.api.gui.MenuBar MenuBar} instances.
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:57:46
   * @version armidale v0.9.5
   */

public class MenuBarFactory extends ArmidaleObjectFactory {

  public static final int CLASS_ID = 0x00000400;


  /**
   * Creates an object that implements the {@link armidale.api.gui.MenuBar MenuBar} interface for the specified context.
   *
   * @param  context context for which the object will be created
   * @return created object
   *
   * @see armidale.api.context.Context
   * @see armidale.api.context.clientserver.ServerContext
   * @see armidale.api.context.platform.SwingContext
   * @see armidale.api.context.clientserver.SwingClientContext
   */
  public static MenuBar create(Context context) {
    if (context instanceof ServerContext) {
        return new armidale.api.gui.impl.clientserver.MenuBarServerImpl((ServerContext)context);
    } else if (context instanceof SwingContext) {
        return new armidale.api.gui.impl.platform.swing.MenuBarImpl();
    } else if (context instanceof SwingClientContext) {
        MenuBar swingMenuBar = new armidale.api.gui.impl.platform.swing.MenuBarImpl();
        return new armidale.api.gui.impl.clientserver.MenuBarClientImpl((ClientContext)context, swingMenuBar);
    } else {
        return null;
    }
  }

}
