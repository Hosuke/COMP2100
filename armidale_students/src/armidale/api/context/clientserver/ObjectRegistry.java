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

import armidale.api.gui.Frame;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class ObjectRegistry {

  private  MessagingObject  elementData[];
  private  int              increment;


  public ObjectRegistry(int initialCapacity, int increment) {
    super();
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("IndexedCollection: illegal capacity " + initialCapacity);
    }
    this.elementData = new MessagingObject[initialCapacity];
    this.increment = increment;
  }


  public ObjectRegistry() {
    this(10, 10);
  }


  public void registerObject(MessagingObject messagingObject) {
    int  id  = messagingObject.getId();
    if (id == messagingObject.NULL_ID) {
      id = replaceFirstNull(messagingObject);
      messagingObject.setId(id);
    } else {
      set(id, messagingObject);
    }
  }


  public void clear() {
    for (int i = 0; i < elementData.length; i++) {
      if (elementData[i] != null) {
        if (elementData[i] instanceof armidale.api.gui.impl.clientserver.FrameClientImpl) {
          ((Frame) elementData[i]).removeAllWindowCallbacks();
          ((Frame) elementData[i]).dispose();
        }
        elementData[i] = null;
      }
    }
  }


  public MessagingObject findObject(int id) {
    if (id == MessagingObject.NULL_ID) {
      return null;
    } else {
      checkSize(id);
      return elementData[id];
    }
  }


  public Enumeration objectEnumeration() {
    return new RegistryEnumeration();
  }


  private void set(int index, MessagingObject element) {
    checkSize(index);

    elementData[index] = element;
  }


  private void increaseCapacity() {
    MessagingObject  oldData[]  = elementData;
    elementData = new MessagingObject[elementData.length + increment];
    System.arraycopy(oldData, 0, elementData, 0, oldData.length);
  }


  private void checkSize(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException("IndexedCollection: index=" + index);
    }
    if (index >= elementData.length) {
      increaseCapacity();
      checkSize(index);
    }
  }


  private int replaceFirstNull(MessagingObject object) {
    for (int i = elementData.length - 1; i >= 0; i--) {
      if (elementData[i] == null) {
        elementData[i] = object;
        return i;
      }
    }
    increaseCapacity();
    return replaceFirstNull(object);
  }


  private class RegistryEnumeration implements Enumeration {

    private  int  cursor  = 0;


    public boolean hasMoreElements() {
      return cursor != elementData.length;
    }


    public Object nextElement() {
      checkCursor();
      while ((cursor < elementData.length) && (elementData[cursor] == null)) {
        cursor++;
      }
      Object  obj  = elementData[cursor];
      cursor++;
      return obj;
    }


    private void checkCursor() {
      if ((cursor < 0) || (cursor >= elementData.length)) {
        throw new NoSuchElementException("index=" + cursor);
      }
    }
  }

}

