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

class TableAttribute extends TypedName {

  public  boolean  isIndexed    = false;
  public  boolean  hasCallback  = false;


  public TableAttribute(Node node, ArmidaleClass theClass) {
    super(node, theClass);
    CallbackMethod  theCallbackMethod;
    Method          theMethod;
    Parameter       theParameter;
    Callback        theCallback;
    if (!isArmidale) {
      throw new XmlException("tables of non-armidale types are not supported (" + typeName.name + "[])", node);
    }
    if (isArmidale && isArray) {
      throw new XmlException("tables of armidale object arrays are not supported (" + typeName.name + "[])", node);
    }

    String          itemName           = Strings.capitalize(typeName.name);
    String          tableName          = Strings.capitalize(name);
    String          className          = theClass.className.name;

    theMethod = new Method("set" + Strings.capitalize(name) + "ColumnCount", theClass);
    theMethod.addParagraph("Sets the number of columns in the table of " + tableName + "s associated with this " + className);
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "count", "", false, theClass);
    theParameter.addDocumentation("number of columns in the table of " + tableName + "s");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("set" + Strings.capitalize(name) + "RowCount", theClass);
    theMethod.addParagraph("Sets the number of rows in the table of " + tableName + "s associated with this " + className);
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "count", "", false, theClass);
    theParameter.addDocumentation("number of rows in the table of " + tableName + "s");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("set" + Strings.capitalize(name), theClass);
    theMethod.addParagraph("Replaces the " + itemName + " at a specified row and column in the table of "
       + tableName + "s associated with this " + className);
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "row", "", false, theClass);
    theParameter.addDocumentation("row of the " + itemName + " to be replaced");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("int", "column", "", false, theClass);
    theParameter.addDocumentation("column of the " + itemName + " to be replaced");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
    theParameter.addDocumentation(itemName + " to be put into table");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("set" + Strings.capitalize(name), theClass);
    theMethod.addParagraph("Replaces the " + itemName + "s at a specified column in a specified set of rows "
       + "with copies of a specified " + itemName + ".");
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "firstRow", "", false, theClass);
    theParameter.addDocumentation("firstRow of the " + itemName + " to be replaced");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("int", "rowCount", "", false, theClass);
    theParameter.addDocumentation("number of rows to be modified");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("int", "column", "", false, theClass);
    theParameter.addDocumentation("column of the " + itemName + " to be replaced");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("@" + typeName.qualifiedName, Strings.lowercase(typeName.name), "", false, theClass);
    theParameter.addDocumentation(itemName + " to be put into table");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("set" + Strings.capitalize(name) + "Row", theClass);
    theMethod.addParagraph("Replaces a specified row in the table of " + tableName + "s associated with this " + className);
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "row", "", false, theClass);
    theParameter.addDocumentation("row to be replaced");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("@" + typeName.qualifiedName + "[]", Strings.lowercase(typeName.name) + "s", "", false, theClass);
    theParameter.addDocumentation("row of " + itemName + "s to be put into table");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("set" + Strings.capitalize(name) + "Row", theClass);
    theMethod.addParagraph("Replaces a specified number of rows in the table of " + tableName + "s associated with this " + className
       + " with copies of a specified row");
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "row", "", false, theClass);
    theParameter.addDocumentation("first row of " + itemName + "s to be replaced");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("int", "count", "", false, theClass);
    theParameter.addDocumentation("number of rows to be replaced");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("@" + typeName.qualifiedName + "[]", Strings.lowercase(typeName.name) + "s", "", false, theClass);
    theParameter.addDocumentation("row of " + itemName + "s to be copied into table");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("insert" + Strings.capitalize(name) + "Row", theClass);
    theMethod.addParagraph("Inserts a specified row into the table of " + tableName + "s associated with this " + className
       + ". Moves all subsequent rows down (adds one to their row indices)");
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "row", "", false, theClass);
    theParameter.addDocumentation("row index at which the row of " + itemName + "s to be inserted");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("@" + typeName.qualifiedName + "[]", Strings.lowercase(typeName.name) + "s", "", false, theClass);
    theParameter.addDocumentation("row of the " + itemName + "s to be inserted");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("remove" + Strings.capitalize(name) + "Row", theClass);
    theMethod.addParagraph("Removes a specified row from the table of " + tableName + "s associated with this " + className
       + ". Moves all subsequent rows up (subtracts one from their row indices)");
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "row", "", false, theClass);
    theParameter.addDocumentation("row to be removed");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("remove" + Strings.capitalize(name) + "Rows", theClass);
    theMethod.addParagraph("Removes a specified number of rows from the table of " + tableName + "s associated with this " + className
       + ". Moves all subsequent rows up (subtracts 'count' from their row indices)");
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "row", "", false, theClass);
    theParameter.addDocumentation("first row to be removed");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("int", "count", "", false, theClass);
    theParameter.addDocumentation("number of rows to be removed");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    theMethod = new Method("removeAll" + Strings.capitalize(name) + "Rows", theClass);
    theMethod.addParagraph("Removes all rows from the table of " + tableName + "s associated with this " + className);
    addCrossReferences(theMethod);
    theClass.methods.add(theMethod);

    theMethod = new Method("set" + Strings.capitalize(name) + "ColumnTitle", theClass);
    theMethod.addParagraph("Sets the title of a specified column in the table of " + tableName + "s associated with this " + className);
    addCrossReferences(theMethod);
    theParameter = new Parameter("int", "column", "", false, theClass);
    theParameter.addDocumentation("column to be re-titled");
    theMethod.parameterList.add(theParameter);
    theParameter = new Parameter("String", "title", "", false, theClass);
    theParameter.addDocumentation("new column title");
    theMethod.parameterList.add(theParameter);
    theClass.methods.add(theMethod);

    Node            tempNode           = node.getAttributes().getNamedItem("callback");
    if (tempNode != null) {
      if (tempNode.getNodeValue().equalsIgnoreCase("yes")) {
        hasCallback = true;
        theClass.processIncludeFile("TableDataCallback.xml");
      }
    }
  }


  private void addCrossReferences(Method theMethod) {
    theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name) + "ColumnCount");
    theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name) + "RowCount");
    theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name));
    theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name) + "Row");
    theMethod.addTaggedDocumentation("see", "#insert" + Strings.capitalize(name) + "Row");
    theMethod.addTaggedDocumentation("see", "#remove" + Strings.capitalize(name) + "Row");
    theMethod.addTaggedDocumentation("see", "#remove" + Strings.capitalize(name) + "Rows");
    theMethod.addTaggedDocumentation("see", "#removeAll" + Strings.capitalize(name) + "Rows");
    theMethod.addTaggedDocumentation("see", "#set" + Strings.capitalize(name) + "ColumnTitle");
  }

}

