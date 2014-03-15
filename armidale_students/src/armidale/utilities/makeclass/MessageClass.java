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
import armidale.api.UncheckedException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.LinkedList;
import java.util.ListIterator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class MessageClass {

  private final static  String      distributedPackage  = "armidale.context.distributed";

  private               String      name;
  private               String      parentName;
  private               boolean     isAbstract;
  private               LinkedList  fieldList;


  public MessageClass(Element root) {
    fieldList = new LinkedList();
    Node  tempNode  = root.getAttributes().getNamedItem("name");
    if (tempNode == null) {
      throw new XmlException("'name' attribute expected", root);
    } else {
      name = tempNode.getNodeValue();
    }
    tempNode = root.getAttributes().getNamedItem("abstract");
    if (tempNode == null) {
      isAbstract = false;
    } else {
      isAbstract = (tempNode.getNodeValue().equals("yes"));
    }
    tempNode = root.getAttributes().getNamedItem("parent");
    if (tempNode == null) {
      parentName = "Message";
    } else {
      parentName = tempNode.getNodeValue();
    }
    tempNode = root.getFirstChild();
    loadNodes(tempNode);
  }


  public void generateCode(String sourceRoot) {
    PrintWriter   output;
    ListIterator  fieldIterator;
    Field         theField;
    String        filename;

    try {
      filename = sourceRoot + File.separatorChar
         + distributedPackage.replace('.', File.separatorChar)
         + File.separatorChar + Strings.capitalize(name) + "Message.java";

      System.out.println("  - writing message class to " + filename);

      output = new PrintWriter(new FileWriter(filename));

      writeHeader(output, filename);

      output.println();
      output.println("package " + distributedPackage + ";");
      output.println();
      output.println("  import armidale.structures.*;");
      output.println();
      output.print("public ");
      if (isAbstract) {
        output.print("abstract ");
      }
      output.println("class " + Strings.capitalize(name) + "Message extends " + parentName + " {");

      // message fields
      fieldIterator = fieldList.listIterator(0);
      if (fieldIterator.hasNext()) {
        output.println();
        output.println("  // fields");
        output.println("  //");
      }
      while (fieldIterator.hasNext()) {
        theField = (Field) fieldIterator.next();
        output.println("  private " + theField.typeName.name + " " + theField.name + ";");
      }

      // constructors
      output.println();
      output.println("  // constructors");
      output.println("  //");
      output.print("  public " + Strings.capitalize(name) + "Message(");
      fieldIterator = fieldList.listIterator(0);
      while (fieldIterator.hasNext()) {
        theField = (Field) fieldIterator.next();
        output.print(theField.declaration());
        if (fieldIterator.hasNext()) {
          output.print(", ");
        }
      }
      output.println(") {");
      output.println("    super(MessageTypes." + name.toUpperCase() + ");");
      fieldIterator = fieldList.listIterator(0);
      while (fieldIterator.hasNext()) {
        theField = (Field) fieldIterator.next();
        output.println("    this." + theField.name + " = " + theField.name + ";");
      }
      output.println("  }");

      output.println();
      output.println("  public " + Strings.capitalize(name) + "Message(ByteArray data) {");
      output.println("    super(MessageTypes." + name.toUpperCase() + ");");
      output.println("    data.resetReadPtr();");
      fieldIterator = fieldList.listIterator(0);
      while (fieldIterator.hasNext()) {
        theField = (Field) fieldIterator.next();
        output.print("    " + theField.name + " = data.read");
        if (theField.isArmidale) {
          output.println("ObjectId();");
        } else {
          output.println(Strings.capitalize(theField.typeName.name) + "();");
        }
      }
      output.println("  }");

      // toBytes
      output.println();
      output.println("  // message byte data");
      output.println("  //");
      output.println("  public byte[] toBytes() {");
      output.println("    resetWritePtr();");
      fieldIterator = fieldList.listIterator(0);
      while (fieldIterator.hasNext()) {
        theField = (Field) fieldIterator.next();
        if (theField.isArmidale) {
          output.println("    writeObjectId((DistributedArmidaleObject)" + theField.name + ");");
        } else {
          output.println("    write" + Strings.capitalize(theField.typeName.name) + "(" + theField.name + ");");
        }
      }
      output.println("    return byteStream.toByteArray();");
      output.println("  }");

      // get and set methods for fields
      fieldIterator = fieldList.listIterator(0);
      if (fieldIterator.hasNext()) {
        output.println();
        output.println("  // field set/get/is methods");
        output.println("  //");
      }
      while (fieldIterator.hasNext()) {
        theField = (Field) fieldIterator.next();

        output.println();
        output.println(theField.getSpec() + " {");
        output.println("    return " + theField.name + ";");
        output.println("  }");

        output.println();
        output.println(theField.setSpec() + " {");
        output.println("    this." + theField.name + " = " + theField.name + ";");
        output.println("  }");
      }

      // done
      output.println("}");
      output.flush();
      output.close();
    } catch (IOException e) {
      throw new UncheckedException("generateCode", e);
    }
  }


  private void loadNodes(Node node) {
    Node  tempNode  = node;
    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          if (tempNode.getNodeName().equals("field")) {
            fieldList.add(new Field(tempNode, null));
          } else {
            throw new XmlException("'" + tempNode.getNodeName() + "' is an unknown node name", node);
          }
          break;
        case Node.TEXT_NODE:
          break;
      }
      tempNode = tempNode.getNextSibling();
    }
  }


  private void writeHeader(PrintWriter output, String filename) {
    Date              now   = new Date();
    SimpleDateFormat  date  = new SimpleDateFormat("ddMMMyyyy");
    SimpleDateFormat  time  = new SimpleDateFormat("HH:mm:ss");

    output.println("//");
    output.println("// generated by " + Main.programName);
    output.println("// on " + date.format(now) + " at " + time.format(now) + " from " + filename);
    output.println("//");
  }

}

