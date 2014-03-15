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
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.LinkedList;
import java.util.ListIterator;
import org.w3c.dom.Node;

class DocumentedItem {

  protected  ArmidaleClass  theClass;

  private    LinkedList     documentation;


  public DocumentedItem(Node node) {
    this();
    Node  tempNode  = node.getFirstChild();
    while (tempNode != null) {
//System.out.println("documentatedItem loading: " + tempNode);
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          if (tempNode.getNodeName().equals("documentation")) {
            addDocumentation(tempNode);
          }
          break;
        case Node.TEXT_NODE:
          break;
      }
      tempNode = tempNode.getNextSibling();
    }
  }


  public DocumentedItem() {
    documentation = new LinkedList();
  }


  public void addDocumentation(String text) {
    documentation.add(text);
  }


  public void addParagraph(String text) {
    documentation.add(text);
    documentation.add("<p>");
  }


  public void addDocumentation(DocumentedItem documentedItem) {
    ListIterator  iterator  = documentedItem.documentation.listIterator(0);
    while (iterator.hasNext()) {
      addDocumentation((String) iterator.next());
    }
  }


  public void addTaggedDocumentation(String tag, String text) {
    addDocumentation("@" + tag + " " + text);
  }


  public void addDocumentation(Node documentationNode) {
    String  text      = "";
    String  contents  = "";
    String  nodeName  = "";

    Node    tempNode  = documentationNode.getFirstChild();
    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          contents = nodeContents(tempNode).trim();
          nodeName = tempNode.getNodeName();
          if (nodeName.equals("para")) {
            text = fixSpaces(text);
            addParagraph(text);
            text = "";
            if (!contents.equals("")) {
              addParagraph(contents);
            }
          } else {
            text = text + nodeText(nodeName, contents);
          }
          break;
        case Node.TEXT_NODE:
          text = text + tempNode.getNodeValue();
      }
      tempNode = tempNode.getNextSibling();
    }
    text = fixSpaces(text);
    addParagraph(text);
  }


  public void openDocumentation(PrintWriter output) {
    output.println("  /**");
  }


  public void closeDocumentation(PrintWriter output) {
    output.println("   */");
  }


  public void writeDocumentation(PrintWriter output, String tag) {
    String        text;

    if (documentation.size() == 0) {
      if (tag != null) {
        writeText(output, tag);
      }
      return;
    }

    if (tag.length() > 0) {
      text = tag + " ";
    } else {
      text = "";
    }

    ListIterator  iterator  = documentation.listIterator(0);
    while (iterator.hasNext()) {
      text = text + (String) iterator.next();
      writeText(output, text);
      text = "";
    }
  }


  public void writeDocumentation(PrintWriter output) {
    writeDocumentation(output, "");
  }


  public void writeAuthorVersion(PrintWriter output) {
    Date              now   = new Date();
    SimpleDateFormat  date  = new SimpleDateFormat("ddMMMyyyy");
    SimpleDateFormat  time  = new SimpleDateFormat("HH:mm:ss");

    output.println("   * @author  generated by <tt>" + Main.programName + "</tt> on " + date.format(now) + " at " + time.format(now));
    output.println("   * @version armidale " + ProductInfo.versionAsString());
  }


  public void writeLicense(PrintWriter output) {

    SimpleDateFormat  year      = new SimpleDateFormat("yyyy");
    String            thisYear  = year.format(new Date());

    output.println("/*");
    output.println(" * Copyright (c) 2000-" + thisYear + ", Shayne R Flint");
    output.println(" * All rights reserved.");
    output.println(" * ");
    output.println(" * Redistribution and use in source and binary forms, with or without");
    output.println(" * modification, are permitted provided that the following conditions");
    output.println(" * are met:");
    output.println(" *");
    output.println(" *    * Redistributions of source code must retain the above copyright");
    output.println(" *      notice, this list of conditions and the following disclaimer.");
    output.println(" *");
    output.println(" *    * Redistributions in binary form must reproduce the above copyright");
    output.println(" *      notice, this list of conditions and the following disclaimer in the");
    output.println(" *      documentation and/or other materials provided with the distribution.");
    output.println(" *");
    output.println(" *    * The name of the author may not be used to endorse or promote products");
    output.println(" *      derived from this software without specific prior written permission.");
    output.println(" *");
    output.println(" * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR");
    output.println(" * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES");
    output.println(" * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.");
    output.println(" * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,");
    output.println(" * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT");
    output.println(" * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,");
    output.println(" * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY");
    output.println(" * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT");
    output.println(" * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF");
    output.println(" * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
    output.println(" */");
  }


  private String nodeText(String nodeName, String contents) {
    if (contents.equals("")) {
      return "<" + nodeName + "/>";
    } else {
      return "<" + nodeName + ">" + contents + "</" + nodeName + ">";
    }
  }


  private String nodeContents(Node node) {
    String  text      = "";
    Node    tempNode  = node.getFirstChild();

    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          text = text + nodeText(tempNode.getNodeName(), nodeContents(tempNode));
          break;
        case Node.TEXT_NODE:
          text = text + tempNode.getNodeValue();
      }
      tempNode = tempNode.getNextSibling();
    }
    return text;
  }


  private void writeText(PrintWriter output, String text) {
    if (text.length() == 0) {
      return;
    }
    int  first  = 0;
    int  last   = 0;
    while (first < text.length()) {
      last = first + 75;
      if (last > text.length()) {
        last = text.length();
        output.println("   * " + text.substring(first, last));
        return;
      } else {
        while (!Character.isWhitespace(text.charAt(last - 1))) {
          last--;
          if (last == first) {
            return;
          }
        }
        output.println("   * " + text.substring(first, last));
        first = last;
      }
    }
  }


  private String fixSpaces(String t) {
    String  text  = t.trim();
    int     j     = 0;
    for (int i = 0; i < text.length(); i++) {
      if (Character.isWhitespace(text.charAt(i))) {
        j = i;
        while (Character.isWhitespace(text.charAt(j))) {
          j++;
        }
        text = text.substring(0, i) + " " + text.substring(j);
      }
    }
    return text;
  }

}

