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
 * ./armidale/api/gui/impl/clientserver/ImageClientServerIds.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:42
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.context.clientserver.*;
  import armidale.api.gui.Image;
  import armidale.api.gui.ImageFactory;

  /**
   * Defines various constants used in communications between {@link armidale.api.gui.Image Image} clients and servers
   */

public interface ImageClientServerIds extends Image {

  public final static int BASE_ID = ImageFactory.CLASS_ID + 1;

  // Attribute IDs
  //
  public static final int imageDataId = BASE_ID + 0;
  public static final int sizeId = BASE_ID + 1;
  public static final int fileId = BASE_ID + 2;
  public static final int descriptionId = BASE_ID + 3;

  // Callback method IDs
  //

  public static final int imageImageChangedId = BASE_ID + 4;
  public static final int imageErrorId = BASE_ID + 5;
  public static final int imageGetFileStringId = BASE_ID + 6;

}