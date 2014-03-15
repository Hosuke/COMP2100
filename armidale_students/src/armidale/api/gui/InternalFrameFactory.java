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
 * ./armidale/api/gui/InternalFrameFactory.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:27
 *
 */

package armidale.api.gui;

  import armidale.api.ArmidaleObjectFactory;
  import armidale.api.context.*;
  import armidale.api.context.clientserver.*;
  import armidale.api.context.platform.*;

  /**
   * This factory class is used to create {@link armidale.api.gui.InternalFrame InternalFrame} instances.
   *
   * @author  generated by <tt>armidale.utilities.makeclass.Main</tt> on 10Apr2002 at 17:58:27
   * @version armidale v0.9.5
   */

public class InternalFrameFactory extends ArmidaleObjectFactory {

  public static final int CLASS_ID = 0x00002700;


  /**
   * Creates an object that implements the {@link armidale.api.gui.InternalFrame InternalFrame} interface for the specified context.
   *
   * @param  context context for which the object will be created
   * @return created object
   *
   * @see armidale.api.context.Context
   * @see armidale.api.context.clientserver.ServerContext
   * @see armidale.api.context.platform.SwingContext
   * @see armidale.api.context.clientserver.SwingClientContext
   */
  public static InternalFrame create(Context context) {
    if (context instanceof ServerContext) {
        return new armidale.api.gui.impl.clientserver.InternalFrameServerImpl((ServerContext)context);
    } else if (context instanceof SwingContext) {
        return new armidale.api.gui.impl.platform.swing.InternalFrameImpl();
    } else if (context instanceof SwingClientContext) {
        InternalFrame swingInternalFrame = new armidale.api.gui.impl.platform.swing.InternalFrameImpl();
        return new armidale.api.gui.impl.clientserver.InternalFrameClientImpl((ClientContext)context, swingInternalFrame);
    } else {
        return null;
    }
  }

  /**
   * @param  context the context in which the object will be created
   * @param  content
   */
  public static InternalFrame create(Context context, Widget content) {
    InternalFrame internalFrame = create(context);
    internalFrame.setContent(content);
    return internalFrame;
  }

  /**
   * @param  context the context in which the object will be created
   * @param  content
   * @param  title
   */
  public static InternalFrame create(Context context, Widget content, String title) {
    InternalFrame internalFrame = create(context);
    internalFrame.setContent(content);
    internalFrame.setTitle(title);
    return internalFrame;
  }

  /**
   * @param  context the context in which the object will be created
   * @param  content
   * @param  title
   * @param  icon
   */
  public static InternalFrame create(Context context, Widget content, String title, Image icon) {
    InternalFrame internalFrame = create(context);
    internalFrame.setContent(content);
    internalFrame.setTitle(title);
    internalFrame.setIcon(icon);
    return internalFrame;
  }

}
