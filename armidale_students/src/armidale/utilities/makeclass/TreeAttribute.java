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
import armidale.api.XmlException;

import java.io.PrintWriter;

import java.util.LinkedList;
import java.util.ListIterator;

import org.w3c.dom.Node;

class TreeAttribute extends TypedName {

  public  boolean  hasCallback  = false;


  public TreeAttribute(Node node, ArmidaleClass theClass) {
    super(node, theClass);
    CallbackMethod  theCallbackMethod;
    Method          theMethod;
    Callback        theCallback;
    if (!isArmidale) {
      throw new XmlException("trees of non-armidale types are not supported (" + typeName.name + "[])", node);
    }
    if (isArmidale && isArray) {
      throw new XmlException("trees of armidale object arrays are not supported (" + typeName.name + "[])", node);
    }

    theMethod = new Method("setChild" + Strings.capitalize(name), theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("setChild" + Strings.capitalize(name) + "Count", theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "count", "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("setChild" + Strings.capitalize(name), theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "count", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("setChild" + Strings.capitalize(name) + "s", theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName + "[]", Strings.lowercase(typeName.name) + "s", "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("insertChild" + Strings.capitalize(name), theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("insertChild" + Strings.capitalize(name), theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "count", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("insertChild" + Strings.capitalize(name) + "s", theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName + "[]", Strings.lowercase(typeName.name) + "s", "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("removeChild" + Strings.capitalize(name), theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("removeChild" + Strings.capitalize(name) + "s", theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "index", "", false, theClass));
    theMethod.parameterList.add(new Parameter("int", "count", "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("addChild" + Strings.capitalize(name), theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("removeChild" + Strings.capitalize(name), theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theMethod.parameterList.add(new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass));
    theClass.methods.add(theMethod);

    theMethod = new Method("removeAllChild" + Strings.capitalize(name) + "s", theClass);
    theMethod.parameterList.add(new Parameter("@armidale.api.gui.Widget", "parent", "", false, theClass));
    theClass.methods.add(theMethod);

    Node            tempNode           = node.getAttributes().getNamedItem("callback");
    if (tempNode != null) {
      if (tempNode.getNodeValue().equalsIgnoreCase("yes")) {
        hasCallback = true;
        theClass.processIncludeFile("TreeDataCallback.xml");
      }
    }
  }

}

