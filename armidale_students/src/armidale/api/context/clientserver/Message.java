/*
 *  Copyright (c) 2000-2002, Shayne R Flint
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *  3. The name of the author may not be used to endorse or promote products
 *  derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
package armidale.api.context.clientserver;

import armidale.api.UncheckedException;
import armidale.api.gui.Color;
import armidale.api.gui.Font;
import armidale.api.gui.Insets;
import armidale.api.gui.Position;
import armidale.api.gui.Size;

import armidale.api.io.Debug;
import armidale.api.structures.ByteArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Message {

  private  Debug                  debug          = new Debug();

  private  int                    messageType;
  private  ByteArrayOutputStream  byteStream;
  private  DataInputStream        readStream;
  private  DataOutputStream       messageStream;


  public Message(int messageType) {
    this.messageType = messageType;
    byteStream = new ByteArrayOutputStream();
    messageStream = new DataOutputStream(byteStream);
    resetReadPtr();
  }


  public Message(int messageType, byte[] messageData) {
    this.messageType = messageType;
    if (messageData.length > 0) {
      byteStream = new ByteArrayOutputStream();
      messageStream = new DataOutputStream(byteStream);
      try {
        messageStream.write(messageData);
      } catch (IOException e) {
        messagingError(e);
      }
      resetReadPtr();
    }
  }


  public int getMessageType() {
    return messageType;
  }


  public byte[] getMessageData() {
    return byteStream.toByteArray();
  }


  public void resetReadPtr() {
    readStream = new DataInputStream(new ByteArrayInputStream(byteStream.toByteArray()));
  }


  public void resetWritePtr() {
    byteStream.reset();
  }


  //
  // Field IO methods
  //

  public void writeString(String item) {
    try {
      if (item != null) {
        messageStream.writeInt(item.length());
        messageStream.writeBytes(item);
        debug.message("message W", "String: " + item);
      } else {
        messageStream.writeInt(0);
        debug.message("message W", "String: <null>");
      }
    } catch (IOException e) {
      messagingError(e);
    }
  }


  public void writeStringArray(String[] strings) {
    if (strings == null) {
      writeInt(0);
    } else {
      writeInt(strings.length);
      for (int i = 0; i < strings.length; i++) {
        writeString(strings[i]);
      }
    }
  }


  public String readString() {
    String  result;
    byte[]  data    = readBinaryData();
    if (data == null) {
      result = new String();
    } else {
      result = new String(data);
    }
    debug.message("message R", "String: " + result);
    return result;
  }


  public String[] readStringArray() {
    int       len     = readInt();
    String[]  result  = new String[len];
    for (int i = 0; i < len; i++) {
      result[i] = readString();
    }
    return result;
  }


  public void writeByte(byte item) {
    try {
      messageStream.write(item);
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message W", "Byte: " + item);
  }


  public byte readByte() {
    byte  result;
    try {
      result = readStream.readByte();
      debug.message("message R", "Byte: " + result);
      return result;
    } catch (IOException e) {
      messagingError(e);
    }
    return 0;
  }


  public void writeInt(int item) {
    try {
      messageStream.writeInt(item);
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message W", "Integer: " + item);
  }


  public int readInt() {
    int  result  = 0;
    try {
      result = readStream.readInt();
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message R", "Integer: " + result);
    return result;
  }


  public void writeFloat(float item) {
    try {
      messageStream.writeFloat(item);
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message W", "Float: " + item);
  }


  public float readFloat() {
    float  result  = 0.0f;
    try {
      result = readStream.readFloat();
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message R", "Float: " + result);
    return result;
  }


  public void writeBoolean(boolean item) {
    try {
      messageStream.writeBoolean(item);
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message W", "Boolean: " + item);
  }


  public boolean readBoolean() {
    boolean  result  = false;
    try {
      result = readStream.readBoolean();
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message R", "Boolean: " + result);
    return result;
  }


  public void writeBinary(byte[] item) {
    try {
      if (item != null) {
        messageStream.writeInt(item.length);
        messageStream.write(item, 0, item.length);
        debug.message("message W", "Binary: <" + item.length + " bytes>");
      } else {
        messageStream.writeInt(0);
        debug.message("message W", "Binary: <0 bytes>");
      }
    } catch (IOException e) {
      messagingError(e);
    }
  }


  public byte[] readBinary() {
    debug.message("message R", "Binary");
    return readBinaryData();
  }


  public void writeObjectId(MessagingObject item) {
    try {
      if (item != null) {
        messageStream.writeInt(item.getId());
        debug.message("message W", "Object: " + item.getId());
      } else {
        messageStream.writeInt(MessagingObject.NULL_ID);
        debug.message("message W", "Object: <null>");
      }
    } catch (IOException e) {
      messagingError(e);
    }
  }


  public void writeObjectId(int item) {
    try {
      messageStream.writeInt(item);
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message W", "Object: " + item);
  }


  public int readObjectId() {

    int  result  = 0;
    try {
      result = readStream.readInt();
    } catch (IOException e) {
      messagingError(e);
    }
    debug.message("message R", "object: " + result);
    return result;
  }


  //
  // Helper IO methods
  //

  public Color readColor() {
    int  red    = 255;
    int  green  = 0;
    int  blue   = 0;

    red = readInt();
    green = readInt();
    blue = readInt();
    return new Color(red, green, blue);
  }


  public void writeColor(Color color) {
    writeInt(color.getRed());
    writeInt(color.getGreen());
    writeInt(color.getBlue());
  }


  public Font readFont() {
    String   name;
    int      fontSize;
    int      fontStyle   = 0;
    boolean  fontBold;
    boolean  fontItalic;

    Font     result;

    name = readString();
    fontSize = readInt();
    fontBold = readBoolean();
    fontItalic = readBoolean();
    result = new Font(name, fontSize, fontBold, fontItalic);
    return result;
  }


  public void writeFont(Font font) {
    writeString(font.getName());
    writeInt(font.getSize());
    writeBoolean(font.isBold());
    writeBoolean(font.isItalic());
  }


  public Size readSize() {
    int  width   = readInt();
    int  height  = readInt();
    return new Size(width, height);
  }


  public void writeSize(Size size) {
    writeInt(size.getWidth());
    writeInt(size.getHeight());
  }


  public Insets readInsets() {
    int  top     = readInt();
    int  left    = readInt();
    int  bottom  = readInt();
    int  right   = readInt();
    return new Insets(top, left, bottom, right);
  }


  public void writeInsets(Insets insets) {
    writeInt(insets.getTop());
    writeInt(insets.getLeft());
    writeInt(insets.getBottom());
    writeInt(insets.getRight());
  }


  public ByteArray readByteArray() {
    return new ByteArray(readBinary());
  }


  public void writeByteArray(ByteArray byteArray) {
    writeBinary(byteArray.getBytes());
  }


  public Position readPosition() {
    int  x  = readInt();
    int  y  = readInt();
    return new Position(x, y);
  }


  public void writePosition(Position position) {
    writeInt(position.getX());
    writeInt(position.getY());
  }



  public String toString() {
    return "Message " + messageType + "[" + byteStream.size() + " byte(s)]";
  }


  //
  // Primitive read methods
  //

  protected byte[] readBinaryData() {
    int     len;
    byte[]  buffer;

    try {
      len = readStream.readInt();
      if (len == 0) {
        return null;
      } else {
        buffer = new byte[len];
        readStream.readFully(buffer, 0, len);
        return buffer;
      }
    } catch (IOException e) {
      messagingError(e);
    }
    return null;
  }


  private void messagingError(Exception t) {
    int  messageType  = getMessageType();
    int  objectId;
    int  attributeId;
    int  methodId;
//    System.out.print("Transport Error: ");
    switch (messageType) {
      case MessageTypes.SET_ATTRIBUTE:
        resetReadPtr();
        objectId = readObjectId();
        attributeId = readInt();
//        System.out.println("SET_ATTRIBUTE object=" + objectId + " attribute=" + attributeId);
        break;
      case MessageTypes.METHOD_CALL:
        resetReadPtr();
        objectId = readObjectId();
        methodId = readInt();
//        System.out.println("METHOD_CALL object=" + objectId + " method=" + methodId);
        break;
      case MessageTypes.CALLBACK:
        resetReadPtr();
        objectId = readObjectId();
        methodId = readInt();
//        System.out.println("CALLBACK object=" + objectId + " method=" + methodId);
        break;
    }
    throw new UncheckedException(t);
  }

}

