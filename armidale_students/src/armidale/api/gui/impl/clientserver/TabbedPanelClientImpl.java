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
 * ./armidale/api/gui/impl/clientserver/TabbedPanelClientImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:20
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.ImplementationException;
  import armidale.api.context.clientserver.*;
  import armidale.api.gui.TabbedPanel;
  import armidale.api.gui.TabbedPanelCallback;
  import armidale.api.gui.TabbedPanelCallbackList;
  import armidale.api.gui.TabbedPanelFactory;
  import armidale.api.gui.TabbedPanelWidget;
  import armidale.api.gui.constants.OrthogonalCompass;
  import armidale.api.gui.constants.TabLayout;

public class TabbedPanelClientImpl extends WidgetClientImpl implements TabbedPanelClientServerIds {

  // constructor
  //
  public TabbedPanelClientImpl(ClientContext context, TabbedPanel peer) {
    super(context, peer);
  }

  // Class ID
  //
  public int getClassId() {
    return TabbedPanelFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public void setSelectedIndex(int selectedIndex) {
    ((TabbedPanel)peer).setSelectedIndex(selectedIndex);
  }

  public int getTabPlacement() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setTabPlacement(int tabPlacement) {
    ((TabbedPanel)peer).setTabPlacement(tabPlacement);
  }

  public int getTabLayout() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setTabLayout(int tabLayout) {
    ((TabbedPanel)peer).setTabLayout(tabLayout);
  }

  // Callback methods
  //

  public void addTabbedPanelCallback(TabbedPanelCallback callback) {
    ((TabbedPanel)peer).addTabbedPanelCallback(callback);
  }

  public void removeTabbedPanelCallback(TabbedPanelCallback callback) {
    ((TabbedPanel)peer).removeTabbedPanelCallback(callback);
  }

  public void removeAllTabbedPanelCallbacks() {
    ((TabbedPanel)peer).removeAllTabbedPanelCallbacks();
  }

  public TabbedPanelCallbackList getTabbedPanelCallbackList() {
    throw new ImplementationException("callback get methods are not supported in client context");
  }

  // other methods
  //

  public void addTab(TabbedPanelWidget tabbedPanelWidget) {
    TabbedPanelWidget tabbedPanelWidgetPeer;
    if (tabbedPanelWidget==null) {
     tabbedPanelWidgetPeer = null;
    } else {
     tabbedPanelWidgetPeer = (TabbedPanelWidget)((TabbedPanelWidgetClientImpl)tabbedPanelWidget).peer;
    }
    ((TabbedPanel)peer).addTab(tabbedPanelWidgetPeer);
  }

  public void removeTab(TabbedPanelWidget tabbedPanelWidget) {
    TabbedPanelWidget tabbedPanelWidgetPeer;
    if (tabbedPanelWidget==null) {
     tabbedPanelWidgetPeer = null;
    } else {
     tabbedPanelWidgetPeer = (TabbedPanelWidget)((TabbedPanelWidgetClientImpl)tabbedPanelWidget).peer;
    }
    ((TabbedPanel)peer).removeTab(tabbedPanelWidgetPeer);
  }

  public void removeAllTabs() {
    ((TabbedPanel)peer).removeAllTabs();
  }

  // local callback handlers
  //

  private class LocalCallback implements TabbedPanelCallback {

    public void indexSelected(TabbedPanel tabbedPanel, int index) {
      Message message = new CallbackMessage(TabbedPanelClientImpl.this.getId(), tabbedPanelIndexSelectedIntId);
      message.writeInt(index);
      transport.writeMessage(message);
    }
  }

  public void handleSetAttribute(int attributeId, Message message) {
    int index;
    int first;
    int count;

    switch (attributeId) {
      case selectedIndexId:
        setSelectedIndex(message.readInt());
        break;
      case tabPlacementId:
        setTabPlacement(message.readInt());
        break;
      case tabLayoutId:
        setTabLayout(message.readInt());
        break;
      default:
        super.handleSetAttribute(attributeId, message);
    }
  }

  public void handleMethodCall(int methodId, Message message) {

    // method parameter values
    //
    TabbedPanelWidget addTabTabbedPanelWidget;
    TabbedPanelWidget removeTabTabbedPanelWidget;

    switch (methodId) {
      case addTabTabbedPanelWidgetId:
        addTabTabbedPanelWidget = (TabbedPanelWidget)objectRegistry.findObject(message.readObjectId());
        addTab(addTabTabbedPanelWidget);
        break;
      case removeTabTabbedPanelWidgetId:
        removeTabTabbedPanelWidget = (TabbedPanelWidget)objectRegistry.findObject(message.readObjectId());
        removeTab(removeTabTabbedPanelWidget);
        break;
      case removeAllTabsId:
        removeAllTabs();
        break;
      default:
        super.handleMethodCall(methodId, message);
    }
  }

  public void handleEnableCallbacks() {
    addTabbedPanelCallback(new LocalCallback());
  }

  public void handleDisableCallbacks() {
    removeAllTabbedPanelCallbacks();
  }
}
