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
 * ./armidale/api/gui/impl/clientserver/TabbedPanelServerImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:20
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.TabbedPanelCallback;
  import armidale.api.gui.TabbedPanelCallbackList;
  import armidale.api.gui.TabbedPanelFactory;
  import armidale.api.gui.TabbedPanelWidget;
  import armidale.api.gui.constants.OrthogonalCompass;
  import armidale.api.gui.constants.TabLayout;
  import java.io.IOException;
  import java.util.LinkedList;

public class TabbedPanelServerImpl extends WidgetServerImpl implements TabbedPanelClientServerIds {

  // readable attributes
  //
  protected int tabPlacement = DEFAULT_TABPLACEMENT;
  protected int tabLayout = DEFAULT_TABLAYOUT;

  // Callback object
  //

  protected TabbedPanelCallbackList tabbedPanelCallbackList = new TabbedPanelCallbackList();

  // constructor
  //
  public TabbedPanelServerImpl(ServerContext context) {
    super(context);
  }

  // Class ID
  //
  public int getClassId() {
    return TabbedPanelFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public void setSelectedIndex(int selectedIndex) {
    Message msg = new SetAttributeMessage(this.getId(), selectedIndexId);
    msg.writeInt(selectedIndex);
    transport.writeMessage(msg);
  }

  public int getTabPlacement() {
    return tabPlacement;
  }

  public void setTabPlacement(int tabPlacement) {
    this.tabPlacement = tabPlacement;
    Message msg = new SetAttributeMessage(this.getId(), tabPlacementId);
    msg.writeInt(tabPlacement);
    transport.writeMessage(msg);
  }

  public int getTabLayout() {
    return tabLayout;
  }

  public void setTabLayout(int tabLayout) {
    this.tabLayout = tabLayout;
    Message msg = new SetAttributeMessage(this.getId(), tabLayoutId);
    msg.writeInt(tabLayout);
    transport.writeMessage(msg);
  }

  // Callback
  //

  public void addTabbedPanelCallback(TabbedPanelCallback callback) {
    Message msg;
    tabbedPanelCallbackList.add(callback);
    if (tabbedPanelCallbackList.size() == 1) {
      msg = new EnableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeTabbedPanelCallback(TabbedPanelCallback callback) {
    Message msg;
    tabbedPanelCallbackList.remove(callback);
    if (tabbedPanelCallbackList.size() == 0) {
      msg = new DisableCallbacksMessage(this.getId());
      transport.writeMessage(msg);
    }
  }

  public void removeAllTabbedPanelCallbacks() {
    Message msg;
    tabbedPanelCallbackList.clear();
    msg = new DisableCallbacksMessage(this.getId());
    transport.writeMessage(msg);
  }

  public TabbedPanelCallbackList getTabbedPanelCallbackList() {
    return tabbedPanelCallbackList;
  }

  // other methods
  //

  public void addTab(TabbedPanelWidget tabbedPanelWidget) {
    Message msg = new MethodCallMessage(this.getId(), addTabTabbedPanelWidgetId);
    msg.writeObjectId((MessagingObject)tabbedPanelWidget);
    transport.writeMessage(msg);
  }

  public void removeTab(TabbedPanelWidget tabbedPanelWidget) {
    Message msg = new MethodCallMessage(this.getId(), removeTabTabbedPanelWidgetId);
    msg.writeObjectId((MessagingObject)tabbedPanelWidget);
    transport.writeMessage(msg);
  }

  public void removeAllTabs() {
    Message msg = new MethodCallMessage(this.getId(), removeAllTabsId);
    transport.writeMessage(msg);
  }

  // transport
  //

  public void handleCallback(int callbackMethodId, Message message) {
    int indexSelectedIndex;

    switch (callbackMethodId) {
      case tabbedPanelIndexSelectedIntId:
        indexSelectedIndex = message.readInt();
        tabbedPanelCallbackList.indexSelected(this, indexSelectedIndex);
        break;
    }
  }

}