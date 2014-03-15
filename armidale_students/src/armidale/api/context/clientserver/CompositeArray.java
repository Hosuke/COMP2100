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

import armidale.api.io.Debug;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CompositeArray {

  protected  Debug     debug               = new Debug();

  protected  Object[]  denseComponent;
  protected  HashMap   sparseComponent;
  protected  int       denseComponentSize;


  public CompositeArray(int denseComponentSize) {
    super();
    denseComponent = new Object[denseComponentSize];
    sparseComponent = new HashMap();
    this.denseComponentSize = denseComponentSize;
  }


  public Object set(int index, Object element) {
    Object  oldElement;
    if (index >= denseComponentSize) {
      oldElement = sparseComponent.get(new Integer(index));
      sparseComponent.put(new Integer(index), element);
    } else {
      oldElement = denseComponent[index];
      denseComponent[index] = element;
    }
    return oldElement;
  }


  public boolean isEmpty() {
    return size() == 0;
  }


  public Object get(int index) {
    if (index >= denseComponentSize) {
      return sparseComponent.get(new Integer(index));
    } else {
      return denseComponent[index];
    }
  }


  public int size() {
    return denseComponent.length + sparseComponent.size();
  }


  public void clear() {
    for (int i = 0; i < denseComponent.length; i++) {
      denseComponent[i] = null;
    }
    sparseComponent.clear();
  }


  public void show(String title) {
    Map.Entry  theEntry;
    debug.message("Composite Array", title);
    for (int i = 0; i < denseComponent.length; i++) {
      if (denseComponent[i] != null) {
        debug.message("Composite Array", "    [" + i + "] " + denseComponent[i].toString());
      }
    }
    Iterator   iterator  = sparseComponent.entrySet().iterator();
    while (iterator.hasNext()) {
      theEntry = (Map.Entry) iterator.next();
      debug.message("Composite Array", "    [" + theEntry.getKey().toString() + "] " + theEntry.getValue().toString());
    }
  }

}

