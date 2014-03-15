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
import armidale.api.ProductInfo;
import armidale.api.util.Strings;
import java.io.PrintWriter;

import java.util.LinkedList;
import java.util.ListIterator;
import org.w3c.dom.Node;

class Method extends Name {

  public  LinkedList  parameterList;


  public Method(Node node, ArmidaleClass theClass) {
    super(node, theClass);
    Node  childNode;
    Node  tempNode;

    parameterList = new LinkedList();
    tempNode = node.getFirstChild();
    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          if (tempNode.getNodeName().equals("parameter")) {
            parameterList.add(new Parameter(tempNode, theClass));
          }
          break;
        case Node.TEXT_NODE:
          break;
      }
      tempNode = tempNode.getNextSibling();
    }
  }


  public Method(String name, ArmidaleClass theClass) {
    super(name, theClass);
    parameterList = new LinkedList();
  }


  public void addParameter(Parameter parameter) {
    parameterList.add(parameter);
  }


  public String idName() {
    String        result             = name;
    ListIterator  parameterIterator  = parameterList.listIterator(0);
    Parameter     theParameter;
    while (parameterIterator.hasNext()) {
      theParameter = (Parameter) parameterIterator.next();
      result = result + Strings.capitalize(theParameter.typeName.name);
    }
    return result + "Id";
  }


  public String spec() {
    ListIterator  parameterIterator  = parameterList.listIterator(0);
    Parameter     theParameter;
    String        result             = "  public void " + name + "(";
    while (parameterIterator.hasNext()) {
      theParameter = (Parameter) parameterIterator.next();
      result = result + theParameter.formalParameter();
      if (parameterIterator.hasNext()) {
        result = result + ", ";
      }
    }
    return result + ")";
  }


  public void writeDocumentation(PrintWriter output) {
    openDocumentation(output);
    super.writeDocumentation(output);
    output.println("   *");
    ListIterator  parameterIterator  = parameterList.listIterator(0);
    Parameter     theParameter;
    while (parameterIterator.hasNext()) {
      theParameter = (Parameter) parameterIterator.next();
      theParameter.writeDocumentation(output);
    }
    closeDocumentation(output);
  }

}

