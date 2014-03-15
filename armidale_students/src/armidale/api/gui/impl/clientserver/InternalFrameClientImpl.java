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
 * ./armidale/api/gui/impl/clientserver/InternalFrameClientImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:28
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.ImplementationException;
  import armidale.api.context.clientserver.*;
  import armidale.api.gui.Color;
  import armidale.api.gui.Image;
  import armidale.api.gui.InternalFrame;
  import armidale.api.gui.InternalFrameFactory;
  import armidale.api.gui.MenuBar;
  import armidale.api.gui.Position;
  import armidale.api.gui.Size;
  import armidale.api.gui.Widget;
  import armidale.api.gui.constants.Compass;

public class InternalFrameClientImpl extends WindowClientImpl implements InternalFrameClientServerIds {

  // constructor
  //
  public InternalFrameClientImpl(ClientContext context, InternalFrame peer) {
    super(context, peer);
  }

  // Class ID
  //
  public int getClassId() {
    return InternalFrameFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public Size getSize() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setSize(Size size) {
    ((InternalFrame)peer).setSize(size);
  }

  public Position getPosition() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setPosition(Position position) {
    ((InternalFrame)peer).setPosition(position);
  }

  public boolean isResizable() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setResizable(boolean resizable) {
    ((InternalFrame)peer).setResizable(resizable);
  }

  public boolean isClosable() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setClosable(boolean closable) {
    ((InternalFrame)peer).setClosable(closable);
  }

  public boolean isMaximizable() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setMaximizable(boolean maximizable) {
    ((InternalFrame)peer).setMaximizable(maximizable);
  }

  public boolean isIconifiable() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setIconifiable(boolean iconifiable) {
    ((InternalFrame)peer).setIconifiable(iconifiable);
  }

  public String getTitle() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setTitle(String title) {
    ((InternalFrame)peer).setTitle(title);
  }

  public Widget getContent() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setContent(Widget content) {
    if (content==null) {
      ((InternalFrame)peer).setContent(null);
    } else {
      ((InternalFrame)peer).setContent((Widget)((WidgetClientImpl)content).peer);
    }
  }

  public Image getIcon() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setIcon(Image icon) {
    if (icon==null) {
      ((InternalFrame)peer).setIcon(null);
    } else {
      ((InternalFrame)peer).setIcon((Image)((ImageClientImpl)icon).peer);
    }
  }

  public MenuBar getMenubar() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setMenubar(MenuBar menubar) {
    if (menubar==null) {
      ((InternalFrame)peer).setMenubar(null);
    } else {
      ((InternalFrame)peer).setMenubar((MenuBar)((MenuBarClientImpl)menubar).peer);
    }
  }

  public Widget getToolbar() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setToolbar(Widget toolbar) {
    if (toolbar==null) {
      ((InternalFrame)peer).setToolbar(null);
    } else {
      ((InternalFrame)peer).setToolbar((Widget)((WidgetClientImpl)toolbar).peer);
    }
  }

  public Widget getStatusbar() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setStatusbar(Widget statusbar) {
    if (statusbar==null) {
      ((InternalFrame)peer).setStatusbar(null);
    } else {
      ((InternalFrame)peer).setStatusbar((Widget)((WidgetClientImpl)statusbar).peer);
    }
  }

  public Color getBackgroundColor() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setBackgroundColor(Color backgroundColor) {
    ((InternalFrame)peer).setBackgroundColor(backgroundColor);
  }

  // other methods
  //

  public void setAlignment(float xAlign, float yAlign) {
    ((InternalFrame)peer).setAlignment(xAlign, yAlign);
  }

  public void setAlignment(int alignment) {
    ((InternalFrame)peer).setAlignment(alignment);
  }

  public void handleSetAttribute(int attributeId, Message message) {
    int index;
    int first;
    int count;

    switch (attributeId) {
      case sizeId:
        setSize(message.readSize());
        break;
      case positionId:
        setPosition(message.readPosition());
        break;
      case resizableId:
        setResizable(message.readBoolean());
        break;
      case closableId:
        setClosable(message.readBoolean());
        break;
      case maximizableId:
        setMaximizable(message.readBoolean());
        break;
      case iconifiableId:
        setIconifiable(message.readBoolean());
        break;
      case titleId:
        setTitle(message.readString());
        break;
      case contentId:
        setContent((Widget)objectRegistry.findObject(message.readObjectId()));
        break;
      case iconId:
        setIcon((Image)objectRegistry.findObject(message.readObjectId()));
        break;
      case menubarId:
        setMenubar((MenuBar)objectRegistry.findObject(message.readObjectId()));
        break;
      case toolbarId:
        setToolbar((Widget)objectRegistry.findObject(message.readObjectId()));
        break;
      case statusbarId:
        setStatusbar((Widget)objectRegistry.findObject(message.readObjectId()));
        break;
      case backgroundColorId:
        setBackgroundColor(message.readColor());
        break;
      default:
        super.handleSetAttribute(attributeId, message);
    }
  }

  public void handleMethodCall(int methodId, Message message) {

    // method parameter values
    //
    float setAlignmentXAlign;
    float setAlignmentYAlign;
    int setAlignmentAlignment;

    switch (methodId) {
      case setAlignmentFloatFloatId:
        setAlignmentXAlign = message.readFloat();
        setAlignmentYAlign = message.readFloat();
        setAlignment(setAlignmentXAlign, setAlignmentYAlign);
        break;
      case setAlignmentIntId:
        setAlignmentAlignment = message.readInt();
        setAlignment(setAlignmentAlignment);
        break;
      default:
        super.handleMethodCall(methodId, message);
    }
  }
}
