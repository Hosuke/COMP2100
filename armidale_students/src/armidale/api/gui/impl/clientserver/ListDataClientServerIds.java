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
 * ./armidale/api/gui/impl/clientserver/ListDataClientServerIds.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:32
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.ListData;
  import armidale.api.gui.ListDataFactory;

  /**
   * Defines various constants used in communications between {@link armidale.api.gui.ListData ListData} clients and servers
   */

public interface ListDataClientServerIds extends ListData {

  public final static int BASE_ID = ListDataFactory.CLASS_ID + 1;

  // Callback method IDs
  //

  public static final int listDataRequestItemsIntIntId = BASE_ID + 0;


  // Other Method IDs
  //
  public static final int setItemCountIntId = BASE_ID + 1;
  public static final int setItemIntArmidaleObjectId = BASE_ID + 2;
  public static final int setItemIntIntArmidaleObjectId = BASE_ID + 3;
  public static final int setItemsIntArmidaleObjectId = BASE_ID + 4;
  public static final int insertItemIntArmidaleObjectId = BASE_ID + 5;
  public static final int insertItemIntIntArmidaleObjectId = BASE_ID + 6;
  public static final int insertItemsIntArmidaleObjectId = BASE_ID + 7;
  public static final int removeItemIntId = BASE_ID + 8;
  public static final int removeItemsIntIntId = BASE_ID + 9;
  public static final int addItemArmidaleObjectId = BASE_ID + 10;
  public static final int removeItemArmidaleObjectId = BASE_ID + 11;
  public static final int removeAllItemsId = BASE_ID + 12;
}