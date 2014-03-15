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
import org.w3c.dom.Node;

class Attribute extends TypedName {

  public   boolean  hasAlternateName  = false;
  public   boolean  isReadable        = true;
  public   boolean  isWriteable       = true;
  public   boolean  isCreateOnly      = false;
  public   boolean  isInToString      = false;

  public   String   defaultValue;

  private  String   setName;
  private  String   getName;
  private  String   alternateSetName;
  private  String   alternateGetName;


  public Attribute(Node node, ArmidaleClass theClass) {
    super(node, theClass);
    Node    childNode;
    Node    tempNode    = node.getAttributes().getNamedItem("default");
    Node    tempNode2;
    String  access;
    String  inToString;

    this.theClass = theClass;

    if (isArmidale && isArray) {
      throw new XmlException("armidale object array attributes are not supported (" + typeName.name + "[])", node);
    }

    if (tempNode == null) {
      if (typeName.name.equals("String") && !isArray) {
        defaultValue = "\"\"";
      } else {
        defaultValue = null;
        if (!isArmidale) {
          System.out.println("      - WARNING: attribute '" + name + "' does not have a default value");
        }
      }
    } else {
      defaultValue = tempNode.getNodeValue();
    }
    tempNode = node.getAttributes().getNamedItem("inToString");
    if (tempNode == null) {
      isInToString = false;
    } else {
      inToString = tempNode.getNodeValue();
      if (inToString.equalsIgnoreCase("true")) {
        isInToString = true;
      } else if (inToString.equalsIgnoreCase("false")) {
        isInToString = false;
      } else {
        throw new XmlException("inToString '" + inToString + "' must be 'true' or 'false'", node);
      }
    }
    tempNode = node.getAttributes().getNamedItem("access");
    if (tempNode == null) {
      isReadable = true;
      isWriteable = true;
    } else {
      access = tempNode.getNodeValue();
      if (access.equalsIgnoreCase("Private")) {
        isReadable = false;
        isWriteable = false;
      } else if (access.equalsIgnoreCase("ReadOnly")) {
        isReadable = true;
        isWriteable = false;
      } else if (access.equalsIgnoreCase("WriteOnly")) {
        isReadable = false;
        isWriteable = true;
      } else if (access.equalsIgnoreCase("ReadWrite")) {
        isReadable = true;
        isWriteable = true;
      } else {
        throw new XmlException("mode '" + access + "' must be 'ReadOnly', 'WriteOnly', 'ReadWrite' or 'Private'", node);
      }
    }

    tempNode = node.getAttributes().getNamedItem("getName");
    if (tempNode == null) {
      getName = "get" + Strings.capitalize(name);
    } else {
      getName = tempNode.getNodeValue();
    }
    tempNode = node.getAttributes().getNamedItem("setName");
    if (tempNode == null) {
      setName = "set" + Strings.capitalize(name);
    } else {
      setName = tempNode.getNodeValue();
    }

    hasAlternateName = false;
    tempNode = node.getAttributes().getNamedItem("alternateGetName");
    tempNode2 = node.getAttributes().getNamedItem("alternateSetName");

    if (tempNode != null && tempNode2 != null) {
      alternateGetName = tempNode.getNodeValue();
      alternateSetName = tempNode2.getNodeValue();
      hasAlternateName = true;
    } else {
      if (tempNode != null || tempNode2 != null) {
        throw new XmlException("'alternateSetName' attribute expected with 'alternateGetName' attribute", node);
      }
    }
  }


  public String setSpec() {
    return "  public void " + setName + "(" + typeName.name + dimensionsString() + " " + name + ")";
  }


  public String getSpec() {
    return "  public " + typeName.name + dimensionsString() + " " + getName + "()";
  }


  public String defaultValueDeclaration() {
    return "  public static final " + typeName.name + " " + defaultName() + " = " + defaultValue + ";";
  }


  public String defaultDeclaration() {
    String  result  = super.declaration();
    if (defaultValue != null) {
      return result + " = " + defaultName() + ";";
    } else {
      return result + ";";
    }
  }


  public String privateDeclaration() {
    return "  private " + defaultDeclaration();
  }


  public String protectedDeclaration() {
    return "  protected " + defaultDeclaration();
  }


  public String defaultName() {
    return "DEFAULT_" + name.toUpperCase();
  }


  public String alternateSetSpec() {
    return "  public void " + alternateSetName
       + "(" + typeName.name + " " + name + ")";
  }


  public String alternateSetStatement() {
    return "    " + setName + "(" + name + ");";
  }


  public String alternateGetSpec() {
    return "  public " + typeName.name + " " + alternateGetName + "()";
  }


  public String alternateGetStatement() {
    return "    return " + getName + "();";
  }



  public void writeGetDocumentation(PrintWriter output) {
    openDocumentation(output);
    output.println("   * Returns the value of the <tt>" + name + "</tt> attribute.");
    output.println("   * <p>");
    writeDocumentation(output);
    output.println("   *");
    output.println("   * @return value of the <tt>" + name + "</tt> attribute");
    closeDocumentation(output);
  }


  public void writeSetDocumentation(PrintWriter output) {
    openDocumentation(output);
    output.println("   * Sets the <tt>" + name + "</tt> attribute.");
    output.println("   * <p>");
    writeDocumentation(output);
    output.println("   *");
    output.println("   * @param  " + name + " value of the <tt>" + name + "</tt> attribute. ");
    if (constants != null) {
      output.println("   * Values for this parameter are defined in the {@link " + constants.qualifiedName + " " + constants.qualifiedName + "} interface");
    }
    closeDocumentation(output);
  }


  public void writeAlternateGetDocumentation(PrintWriter output) {
    openDocumentation(output);
    String  linkName;
    if (typeName.name.equals("boolean")) {
      linkName = "is" + Strings.capitalize(name) + "()";
    } else {
      linkName = "get" + Strings.capitalize(name) + "()";
    }
    output.println("   * see the {@link #" + linkName + " " + linkName + "} method");
    closeDocumentation(output);
  }


  public void writeAlternateSetDocumentation(PrintWriter output) {
    openDocumentation(output);
    String  linkName;
    linkName = "set" + Strings.capitalize(name) + "(" + typeName.name + ")";
    output.println("   * see the {@link #" + linkName + " " + linkName + "} method");
    closeDocumentation(output);
  }

}

