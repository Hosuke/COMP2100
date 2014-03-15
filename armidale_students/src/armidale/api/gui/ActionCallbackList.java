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
 * ./armidale/api/gui/ActionCallbackList.java
 * Generated by armidale.utilities.makeclass.Main armidale v0.9.5 on 10Apr2002 at 17:57:49
 *
 */

package armidale.api.gui;

  import armidale.api.context.Context;
  import armidale.api.io.Debug;
  import java.util.LinkedList;
  import java.util.ListIterator;

  /**
   * An implementation of the {@link armidale.api.gui.ActionCallback ActionCallback} interface which operates on lists of callbacks.
   */

public class ActionCallbackList extends LinkedList implements ActionCallback {

  /**
   * @see armidale.api.gui.ActionCallback#actionPerformed
   */
  public void actionPerformed(Action action) {
    synchronized(this) {
      ActionCallback theCallback;
      ListIterator iterator = listIterator(0);
      while (iterator.hasNext()) {
        theCallback = (ActionCallback)iterator.next();
        theCallback.actionPerformed(action);
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
