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
 * ./armidale/api/gui/impl/clientserver/WindowClientServerIds.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:25
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.Window;

  /**
   * Defines various constants used in communications between {@link armidale.api.gui.Window Window} clients and servers
   */

public interface WindowClientServerIds extends Window {

  public final static int BASE_ID = 0x00002500 + 1;

  // Callback method IDs
  //

  public static final int windowWindowOpenId = BASE_ID + 0;
  public static final int windowWindowActivatedId = BASE_ID + 1;
  public static final int windowWindowClosedId = BASE_ID + 2;
  public static final int windowWindowClosingId = BASE_ID + 3;
  public static final int windowWindowDeactivatedId = BASE_ID + 4;
  public static final int windowWindowDeiconifiedId = BASE_ID + 5;
  public static final int windowWindowIconifiedId = BASE_ID + 6;
  public static final int windowWindowOpenedId = BASE_ID + 7;
  public static final int windowWindowMovedPositionId = BASE_ID + 8;
  public static final int windowWindowResizedSizeId = BASE_ID + 9;


  // Other Method IDs
  //
  public static final int disposeId = BASE_ID + 10;
}
