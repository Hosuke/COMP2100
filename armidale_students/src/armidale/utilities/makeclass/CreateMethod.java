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
import armidale.api.ArgumentException;

import java.io.PrintWriter;

import java.util.LinkedList;
import java.util.ListIterator;
import org.w3c.dom.Node;

class CreateMethod extends DocumentedItem {
  public   LinkedList     attributeList;

  private  ArmidaleClass  theClass;


  public CreateMethod(Node node, ArmidaleClass theClass) {
    super(node);
    this.theClass = theClass;
    Node  childNode;
    Node  tempNode;

    attributeList = new LinkedList();
    tempNode = node.getFirstChild();
    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          if (tempNode.getNodeName().equals("attribute")) {
            attributeList.add(findAttribute((new Name(tempNode, theClass)).name));
          }
          break;
        case Node.TEXT_NODE:
          break;
      }
      tempNode = tempNode.getNextSibling();
    }
  }


  public String spec() {
    ListIterator  attributeIterator  = attributeList.listIterator(0);
    Attribute     theAttribute;
    String        result             = "  public static " + theClass.className.name + " create(Context context, ";
    while (attributeIterator.hasNext()) {
      theAttribute = (Attribute) attributeIterator.next();
      result = result + theAttribute.declaration();
      if (attributeIterator.hasNext()) {
        result = result + ", ";
      }
    }
    return result + ")";
  }


  public void writeDocumentation(PrintWriter output) {
    openDocumentation(output);
    super.writeDocumentation(output);
    ListIterator  attributeIterator  = attributeList.listIterator(0);
    Attribute     theAttribute;
    output.println("   * @param  context the context in which the object will be created");
    while (attributeIterator.hasNext()) {
      theAttribute = (Attribute) attributeIterator.next();
      theAttribute.writeDocumentation(output, "@param  " + theAttribute.name);
    }
    closeDocumentation(output);
  }


  private Attribute findAttribute(String name) {
    Attribute     theAttribute;
    ListIterator  attributeIterator  = theClass.attributes.listIterator(0);
    while (attributeIterator.hasNext()) {
      theAttribute = (Attribute) attributeIterator.next();
      if (theAttribute.name.equals(name)) {
        return theAttribute;
      }
    }
    throw new ArgumentException("'" + name + "' is not an attribute of class '" + theClass.className.name + "'");
  }
}

