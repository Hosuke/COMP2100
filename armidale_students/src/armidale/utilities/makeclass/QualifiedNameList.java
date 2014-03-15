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
 
package armidale.utilities.makeclass;
import armidale.api.util.Strings;

import java.util.LinkedList;
import java.util.ListIterator;

class QualifiedNameList extends LinkedList {

  public void add(QualifiedName qualifiedName) {
//    System.out.println("QualifiednameList: adding " + qualifiedName.toString());
    ListIterator  iterator  = listIterator(0);
    if (qualifiedName == null || qualifiedName.qualifiedName.length() == 0) {
      return;
    }
    while (iterator.hasNext()) {
//      System.out.println("QualifiednameList: " + iterator.next().toString());
      if (qualifiedName.qualifiedName.equals(((QualifiedName) iterator.next()).qualifiedName)) {
        return;
      }
    }
    super.add(qualifiedName);
  }


  public void add(TypedName typedName) {
    if (!typedName.isPrimitive) {
      add(typedName.typeName);
    }
    if (typedName.constants != null) {
      add(typedName.constants);
    }
  }


  public void add(Interface theInterface) {
    add(new QualifiedName(theInterface.qualifiedName));
  }


  public void add(String qualifiedName) {
    add(new QualifiedName(qualifiedName));
  }


  public void removeLocals(String packageName) {
    ListIterator  iterator  = listIterator(0);
    while (iterator.hasNext()) {
      if (((QualifiedName) iterator.next()).packageName.equals(packageName)) {
        iterator.remove();
      }
    }
  }


  public void addAttributes(LinkedList attributes) {
    ListIterator  attributeIterator  = attributes.listIterator(0);
    Attribute     theAttribute;
    while (attributeIterator.hasNext()) {
      theAttribute = (Attribute) attributeIterator.next();
      add(theAttribute);
    }
  }


  public void addListAttributes(LinkedList listAttributes) {
    ListIterator   listIterator      = listAttributes.listIterator(0);
    ListAttribute  theListAttribute;
    while (listIterator.hasNext()) {
      theListAttribute = (ListAttribute) listIterator.next();
      add(theListAttribute);
    }
  }


  public void addMethods(LinkedList methods) {
    ListIterator  methodIterator     = methods.listIterator(0);
    Method        theMethod;
    ListIterator  parameterIterator;
    Parameter     theParameter;
    while (methodIterator.hasNext()) {
      theMethod = (Method) methodIterator.next();
      parameterIterator = theMethod.parameterList.listIterator(0);
      while (parameterIterator.hasNext()) {
        theParameter = (Parameter) parameterIterator.next();
        add(theParameter);
      }
    }
  }


  public void addInterfaces(LinkedList interfaces) {
    ListIterator  interfaceIterator  = interfaces.listIterator(0);
    Interface     theInterface;
    while (interfaceIterator.hasNext()) {
      theInterface = (Interface) interfaceIterator.next();
      add(theInterface);
    }
  }


  public void addCallback(Callback callback) {
    ListIterator    methodIterator;
    CallbackMethod  theCallbackMethod;
    ListIterator    parameterIterator;
    Parameter       theParameter;
    LinkedList      methods;
    if (callback != null) {
      methods = callback.methodList;
      methodIterator = methods.listIterator(0);
      while (methodIterator.hasNext()) {
        theCallbackMethod = (CallbackMethod) methodIterator.next();
        parameterIterator = theCallbackMethod.parameterList.listIterator(0);
        while (parameterIterator.hasNext()) {
          theParameter = (Parameter) parameterIterator.next();
          add(theParameter);
        }
      }
    }
  }


  public void addCreateMethods(LinkedList createMethods) {
    ListIterator  methodIterator     = createMethods.listIterator(0);
    CreateMethod  theCreateMethod;
    ListIterator  attributeIterator;
    Attribute     theAttribute;
    while (methodIterator.hasNext()) {
      theCreateMethod = (CreateMethod) methodIterator.next();
      attributeIterator = theCreateMethod.attributeList.listIterator(0);
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();
        add(theAttribute);
      }
    }
  }

}

