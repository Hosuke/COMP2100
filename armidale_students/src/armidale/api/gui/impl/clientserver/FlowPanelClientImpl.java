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
 * ./armidale/api/gui/impl/clientserver/FlowPanelClientImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:09
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.ImplementationException;
  import armidale.api.context.clientserver.*;
  import armidale.api.gui.FlowPanel;
  import armidale.api.gui.FlowPanelFactory;
  import armidale.api.gui.Widget;
  import armidale.api.gui.constants.FlowAlignment;

public class FlowPanelClientImpl extends PanelClientImpl implements FlowPanelClientServerIds {

  // constructor
  //
  public FlowPanelClientImpl(ClientContext context, FlowPanel peer) {
    super(context, peer);
  }

  // Class ID
  //
  public int getClassId() {
    return FlowPanelFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public int getHorizontalGap() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setHorizontalGap(int horizontalGap) {
    ((FlowPanel)peer).setHorizontalGap(horizontalGap);
  }

  public int getVerticalGap() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setVerticalGap(int verticalGap) {
    ((FlowPanel)peer).setVerticalGap(verticalGap);
  }

  public int getAlignment() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setAlignment(int alignment) {
    ((FlowPanel)peer).setAlignment(alignment);
  }

  // other methods
  //

  public void addWidget(Widget widget) {
    Widget widgetPeer;
    if (widget==null) {
     widgetPeer = null;
    } else {
     widgetPeer = (Widget)((WidgetClientImpl)widget).peer;
    }
    ((FlowPanel)peer).addWidget(widgetPeer);
  }

  public void removeWidget(Widget widget) {
    Widget widgetPeer;
    if (widget==null) {
     widgetPeer = null;
    } else {
     widgetPeer = (Widget)((WidgetClientImpl)widget).peer;
    }
    ((FlowPanel)peer).removeWidget(widgetPeer);
  }

  public void removeAllWidgets() {
    ((FlowPanel)peer).removeAllWidgets();
  }

  public void handleSetAttribute(int attributeId, Message message) {
    int index;
    int first;
    int count;

    switch (attributeId) {
      case horizontalGapId:
        setHorizontalGap(message.readInt());
        break;
      case verticalGapId:
        setVerticalGap(message.readInt());
        break;
      case alignmentId:
        setAlignment(message.readInt());
        break;
      default:
        super.handleSetAttribute(attributeId, message);
    }
  }

  public void handleMethodCall(int methodId, Message message) {

    // method parameter values
    //
    Widget addWidgetWidget;
    Widget removeWidgetWidget;

    switch (methodId) {
      case addWidgetWidgetId:
        addWidgetWidget = (Widget)objectRegistry.findObject(message.readObjectId());
        addWidget(addWidgetWidget);
        break;
      case removeWidgetWidgetId:
        removeWidgetWidget = (Widget)objectRegistry.findObject(message.readObjectId());
        removeWidget(removeWidgetWidget);
        break;
      case removeAllWidgetsId:
        removeAllWidgets();
        break;
      default:
        super.handleMethodCall(methodId, message);
    }
  }
}