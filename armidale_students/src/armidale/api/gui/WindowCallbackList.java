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
 * ./armidale/api/gui/WindowCallbackList.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:58:25
 *
 */

package armidale.api.gui;

  import armidale.api.context.Context;
  import armidale.api.io.Debug;
  import java.util.LinkedList;
  import java.util.ListIterator;

  /**
   * An implementation of the {@link armidale.api.gui.WindowCallback WindowCallback} interface which operates on lists of callbacks.
   */

public class WindowCallbackList extends LinkedList implements WindowCallback {

  /**
   * @see armidale.api.gui.WindowCallback#windowOpen
   */
  public void windowOpen(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowOpen(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowActivated
   */
  public void windowActivated(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowActivated(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowClosed
   */
  public void windowClosed(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowClosed(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowClosing
   */
  public void windowClosing(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowClosing(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowDeactivated
   */
  public void windowDeactivated(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowDeactivated(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowDeiconified
   */
  public void windowDeiconified(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowDeiconified(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowIconified
   */
  public void windowIconified(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowIconified(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowOpened
   */
  public void windowOpened(Window window) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowOpened(window);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowMoved
   */
  public void windowMoved(Window window, Position position) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowMoved(window, position);
      }
    }
  }

  /**
   * @see armidale.api.gui.WindowCallback#windowResized
   */
  public void windowResized(Window window, Size size) {
    synchronized(this) {
      WindowCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (WindowCallback)iterator.next();
        theCallback.windowResized(window, size);
      }
    }
  }

  public boolean add(Object o) {
    synchronized(this) {
      return super.add(o);
    }
  }

  public boolean remove(Object o) {
    synchronized(this) {
      return super.remove(o);
    }
  }

  public int size() {
    synchronized(this) {
      return super.size();
    }
  }

  public void clear() {
    synchronized(this) {
      super.clear();
    }
  }


}
