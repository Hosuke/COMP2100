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
 * ./armidale/api/gui/impl/clientserver/LabelClientImpl.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:01
 *
 */

package armidale.api.gui.impl.clientserver;

  import armidale.api.ImplementationException;
  import armidale.api.context.clientserver.*;
  import armidale.api.gui.Image;
  import armidale.api.gui.Label;
  import armidale.api.gui.LabelFactory;
  import armidale.api.gui.constants.Compass;

public class LabelClientImpl extends ActionClientImpl implements LabelClientServerIds {

  // constructor
  //
  public LabelClientImpl(ClientContext context, Label peer) {
    super(context, peer);
  }

  // Class ID
  //
  public int getClassId() {
    return LabelFactory.CLASS_ID;
  }

  // attribute set/get/is methods
  //

  public String getText() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setText(String text) {
    ((Label)peer).setText(text);
  }

  public int getTextPosition() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setTextPosition(int textPosition) {
    ((Label)peer).setTextPosition(textPosition);
  }

  public Image getImage() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setImage(Image image) {
    if (image==null) {
      ((Label)peer).setImage(null);
    } else {
      ((Label)peer).setImage((Image)((ImageClientImpl)image).peer);
    }
  }

  public int getAlignment() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setAlignment(int alignment) {
    ((Label)peer).setAlignment(alignment);
  }

  public int getImageTextGap() {
    throw new ImplementationException("attribute get/is methods are not supported in client context");
  }

  public void setImageTextGap(int imageTextGap) {
    ((Label)peer).setImageTextGap(imageTextGap);
  }

  public void handleSetAttribute(int attributeId, Message message) {
    int index;
    int first;
    int count;

    switch (attributeId) {
      case textId:
        setText(message.readString());
        break;
      case textPositionId:
        setTextPosition(message.readInt());
        break;
      case imageId:
        setImage((Image)objectRegistry.findObject(message.readObjectId()));
        break;
      case alignmentId:
        setAlignment(message.readInt());
        break;
      case imageTextGapId:
        setImageTextGap(message.readInt());
        break;
      default:
        super.handleSetAttribute(attributeId, message);
    }
  }
}