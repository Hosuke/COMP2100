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

class ListAttribute extends TypedName {

  public  boolean  isIndexed    = false;
  public  boolean  hasCallback  = false;


  public ListAttribute(Node node, ArmidaleClass theClass) {
    super(node, theClass);
    CallbackMethod  theCallbackMethod;
    Method          theMethod;
    Parameter       theParameter;
    Callback        theCallback;

    String          itemName           = Strings.capitalize(typeName.name);
    String          listName           = Strings.capitalize(name);
    String          className          = theClass.className.name;

    if (!isArmidale) {
      throw new XmlException("lists of non-armidale types are not supported (" + typeName.name + "[])", node);
    }
    if (isArmidale && isArray) {
      throw new XmlException("lists of armidale object arrays are not supported (" + typeName.name + "[])", node);
    }

    Node            tempNode           = node.getAttributes().getNamedItem("indexed");
    if (tempNode != null) {
      isIndexed = tempNode.getNodeValue().equalsIgnoreCase("yes");
    }

    if (isIndexed) {
      theMethod = new Method("set" + Strings.capitalize(name) + "Count", theClass);
      theMethod.addParagraph("Sets the size of the list of " + listName + "s associated with this " + className + ".");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "count", "", false, theClass);
      theParameter.addDocumentation("number of " + itemName + "s in the list of " + itemName + "s");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("set" + Strings.capitalize(name), theClass);
      theMethod.addParagraph("Replaces the " + itemName + " at a specified index in the list of " + listName
         + "s associated with this " + className + ".");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index of the " + itemName + " to replace");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
      theParameter.addDocumentation(itemName + " to be put into list");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("set" + Strings.capitalize(name), theClass);
      theMethod.addParagraph("Replaces a specified number of " + itemName + "s in the list of "
         + listName + "s associated with this " + className + " with copies of a specified " + itemName + ".");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index of the first " + itemName + " to replace");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("int", "count", "", false, theClass);
      theParameter.addDocumentation("number of " + itemName + "s to replace");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
      theParameter.addDocumentation(itemName + " to copy into list");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("set" + Strings.capitalize(name) + "s", theClass);
      theMethod.addParagraph("Copies an array of " + Strings.capitalize(typeName.name) + "s to the list of "
         + listName + "s associated with this " + className + " starting at a specified index.");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index of the first " + itemName + " to replace");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("@" + typeName.qualifiedName + "[]", Strings.lowercase(typeName.name) + "s", "", false, theClass);
      theParameter.addDocumentation("array of " + itemName + "s to be put into list");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("insert" + Strings.capitalize(name), theClass);
      theMethod.addParagraph("Inserts a specified " + itemName + " at a specified index in the list of "
         + listName + "s associated with this " + theClass.className.name
         + ". Moves all subsequent " + Strings.capitalize(typeName.name) + "s right (adds one to their indices).");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index at which the " + itemName + " is to be inserted");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
      theParameter.addDocumentation(itemName + " to insert");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("insert" + Strings.capitalize(name), theClass);
      theMethod.addParagraph("Inserts a specified number of copies of a specified " + itemName + " at a specified index in the list of "
         + listName + "s associated with this " + theClass.className.name
         + ". Moves all subsequent " + Strings.capitalize(typeName.name) + "s right (adds 'count' to their indices).");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index at which the first " + itemName + " is to be inserted");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("int", "count", "", false, theClass);
      theParameter.addDocumentation("number of copies to insert");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
      theParameter.addDocumentation(itemName + " to be copied");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("insert" + Strings.capitalize(name) + "s", theClass);
      theMethod.addParagraph("Inserts an array of " + Strings.capitalize(typeName.name) + "s at a specified index in the list of "
         + listName + "s associated with this " + theClass.className.name
         + ". Moves all subsequent " + Strings.capitalize(typeName.name) + "s right (adds '" + Strings.lowercase(typeName.name) + "s.length' to their indices).");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index at which the first " + itemName + " is to be inserted");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("@" + typeName.qualifiedName + "[]", Strings.lowercase(typeName.name) + "s", "", false, theClass);
      theParameter.addDocumentation("array of " + itemName + "s to insert");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("remove" + Strings.capitalize(name), theClass);
      theMethod.addParagraph("Removes the " + Strings.capitalize(typeName.name) + " at a specified index in the list of "
         + listName + "s associated with this " + className
         + ". Moves all subsequent " + Strings.capitalize(typeName.name) + "s left (subtracts one from their indices).");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index of the " + itemName + " to be removed");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

      theMethod = new Method("remove" + Strings.capitalize(name) + "s", theClass);
      theMethod.addParagraph("Removes a specified number of " + itemName + "s from the list of "
         + listName + "s associated with this " + className
         + " starting at a specified index"
         + ". Moves all subsequent " + Strings.capitalize(typeName.name) + "s left (subtracts 'count' from their indices).");
      addCrossReferences(theMethod);
      theParameter = new Parameter("int", "index", "", false, theClass);
      theParameter.addDocumentation("index of the first " + itemName + " to be removed");
      theMethod.parameterList.add(theParameter);
      theParameter = new Parameter("int", "count", "", false, theClass);
      theParameter.addDocumentation("number of " + itemName + "s to be removed");
      theMethod.parameterList.add(theParameter);
      theClass.methods.add(theMethod);

    }

    theMethod = new Method("add" + Strings.capitalize(name), theClass);
    theMethod.addParagraph("Adds a specified " + itemName + " to the list of "
       + listName + "s associated with this " + className + ".");
    if (!isIndexed) {
      theMethod.addDocumentation(this);
    }
    addCrossReferences(theMethod);
    theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
    theParameter.addDocumentation(itemName + " to be added");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("remove" + Strings.capitalize(name), theClass);
    theMethod.addParagraph("Removes a specified " + itemName + " from the list of "
       + listName + "s associated with this " + className + ".");
    addCrossReferences(theMethod);
    theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
    theParameter.addDocumentation(itemName + " to be removed");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("removeAll" + Strings.capitalize(name) + "s", theClass);
    theMethod.addParagraph("Clears the list of " + listName + "s associated with this " + className + ".");
    addCrossReferences(theMethod);
    theClass.methods.add(theMethod);

    tempNode = node.getAttributes().getNamedItem("callback");
    if (tempNode != null) {
      if (tempNode.getNodeValue().equalsIgnoreCase("yes")) {
        if (!isIndexed) {
          throw new XmlException("callbacks are only allowed with indexed lists", node);
        }
        hasCallback = true;
        theClass.processIncludeFile("ListDataCallback.xml");
      }
    }
  }


  private void addCrossReferences(Method theMethod) {
    if (isIndexed) {
      theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name) + "Count");
      theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name));
      theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name));
      theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name) + "s");
      theMethod.addTaggedDocumentation("see", "#insert" + Strings.capitalize(name));
      theMethod.addTaggedDocumentation("see", "#insert" + Strings.capitalize(name));
      theMethod.addTaggedDocumentation("see", "#insert" + Strings.capitalize(name) + "s");
      theMethod.addTaggedDocumentation("see", "#remove" + Strings.capitalize(name));
      theMethod.addTaggedDocumentation("see", "#remove" + Strings.capitalize(name) + "s");
    }
    theMethod.addTaggedDocumentation("see", "#add" + Strings.capitalize(name));
    theMethod.addTaggedDocumentation("see", "#remove" + Strings.capitalize(name));
    theMethod.addTaggedDocumentation("see", "#removeAll" + Strings.capitalize(name) + "s");
  }

}

