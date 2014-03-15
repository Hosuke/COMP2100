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
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.w3c.dom.Node;

class Callback extends DocumentedItem {

  public  LinkedList  methodList;


  public Callback(Node node, ArmidaleClass theClass) {
    super(node);

    Node  childNode;

    this.theClass = theClass;

    methodList = new LinkedList();
    Node  tempNode   = node.getFirstChild();
    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          if (tempNode.getNodeName().equals("method")) {
            methodList.add(new CallbackMethod(tempNode, theClass));
          }
          break;
        case Node.TEXT_NODE:
          break;
      }
      tempNode = tempNode.getNextSibling();
    }
//    theClass.imports.add(new Import("armidale.gui.callbacks.*", theClass));
  }


  public Callback(ArmidaleClass theClass) {
    super();
    methodList = new LinkedList();
  }


  public String getCallbackListSpec() {
    return "  public " + Strings.capitalize(callbackListName())
       + " " + getCallbackListName() + "()";
  }


  public String getCallbackListName() {
    return "get" + Strings.capitalize(callbackName()) + "List";
  }


//  public String idName() {
//    return callbackName() + "Id";
//  }

  public String addCallbackSpec() {
    return "  public void " + addCallbackName() + "("
       + Strings.capitalize(callbackName()) + " callback)";
  }


  public String removeCallbackSpec() {
    return "  public void " + removeCallbackName() + "("
       + Strings.capitalize(callbackName()) + " callback)";
  }


  public String removeAllCallbacksSpec() {
    return "  public void " + removeAllCallbacksName() + "()";
  }



  public String callbackObjectDeclaration() {
    return "  protected " + Strings.capitalize(callbackListName()) + " " + callbackListName()
       + " = new " + Strings.capitalize(callbackListName()) + "();";
  }


  public String callbackName() {
    return Strings.lowercase(theClass.className.name + "Callback");
  }


  public String callbackListName() {
    return callbackName() + "List";
  }


  public String addCallbackName() {
    return "add" + Strings.capitalize(callbackName());
  }


  public String removeCallbackName() {
    return "remove" + Strings.capitalize(callbackName());
  }


  public String removeAllCallbacksName() {
    return "removeAll" + Strings.capitalize(callbackName()) + "s";
  }


  public String qualifiedName() {
    return theClass.callbacksPackage + "." + Strings.capitalize(callbackName());
  }



  public void writeAddDocumentation(PrintWriter output) {
    openDocumentation(output);
    output.println("   * Registers a callback with this " + theClass.className.name);
    output.println("   *");
    writeDocumentation(output, "@param  callback callback to be added");
    output.println("   *");
//    output.println("   * @see " + theClass.callbacksPackage + "." + Strings.capitalize(callbackName()));
    closeDocumentation(output);
  }


  public void writeRemoveDocumentation(PrintWriter output) {
    openDocumentation(output);
    output.println("   * Removes a callback registered with this " + theClass.className.name);
    output.println("   *");
    writeDocumentation(output, "@param  callback callback to be removed");
    closeDocumentation(output);
  }


  public void writeRemoveAllDocumentation(PrintWriter output) {
    openDocumentation(output);
    output.println("   * Removes all callbacks registered with this " + theClass.className.name);
    output.println("   *");
    closeDocumentation(output);
  }


  public void writeGetDocumentation(PrintWriter output) {
    openDocumentation(output);
    output.println("   * Returns the list of callbacks registered with this " + theClass.className.name);
    output.println("   *");
    writeDocumentation(output, "@return list of callbacks registered with this object");
    closeDocumentation(output);
  }


  public void writeDocumentation(PrintWriter output) {
    openDocumentation(output);
    super.writeDocumentation(output);
    output.println("   *");
    writeAuthorVersion(output);
    closeDocumentation(output);
  }
}

