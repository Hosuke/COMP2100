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

class TypedName extends Name {
  public   boolean        isArray      = false;
  public   boolean        isFile       = false;

  public   QualifiedName  typeName;
  public   boolean        isArmidale;
  public   boolean        isPrimitive;
  public   QualifiedName  constants;

  private  String         typeNameStr;


  public TypedName(Node node, ArmidaleClass theClass) {
    super(node, theClass);
    Node  tempNode  = node.getAttributes().getNamedItem("type");
    if (tempNode == null) {
      throw new XmlException("'type' attribute expected", node);
    } else {
      typeNameStr = tempNode.getNodeValue();
      analyzeTypeName();
    }
    tempNode = node.getAttributes().getNamedItem("constants");
    if (tempNode != null) {
      constants = new QualifiedName(tempNode.getNodeValue());
    }
  }


  public TypedName(String typeName, String name, String constants, ArmidaleClass theClass) {
    super(name, theClass);
    this.typeNameStr = typeName;
    analyzeTypeName();
    this.constants = new QualifiedName(constants);
  }


  public String typeDeclaration() {
    return typeName.name + dimensionsString();
  }


  public String declaration() {
    return typeName.name + dimensionsString() + " " + name;
  }


  protected String dimensionsString() {
    if (isArray) {
      return "[]";
    } else {
      return "";
    }
  }


  private void analyzeTypeName() {
    if (typeNameStr.endsWith("[]")) {
      typeNameStr = typeNameStr.substring(0, typeNameStr.length() - 2);
      isArray = true;
    }
    isArmidale = typeNameStr.startsWith("@");
    if (isArmidale) {
      typeNameStr = typeNameStr.substring(1);
    }
    if (typeNameStr.equals("armidale.api.io.File")) {
      isFile = true;
    }
    isPrimitive = (typeNameStr.equals("int")
       || typeNameStr.equals("float")
       || typeNameStr.equals("double")
       || typeNameStr.equals("byte")
       || typeNameStr.equals("boolean")
       || typeNameStr.equals("String"));
    typeName = new QualifiedName(typeNameStr);
  }

}

