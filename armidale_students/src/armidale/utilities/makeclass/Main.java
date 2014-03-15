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

import armidale.api.io.StreamTextOutput;
import armidale.api.util.Strings;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class Main {

  public final static  String            programName        = "armidale.utilities.makeclass.Main";
  private static       StreamTextOutput  textio             = new StreamTextOutput(System.out);

  private static       boolean           noRestore          = false;
  private static       boolean           messageClasses     = false;

  private static       String            sourceRoot         = ".";
  private static       String            classId;
  private static       String[]          platforms;

  public static void main(String argv[]) {

    String   filename;

    if (argv.length < 1) {
      usageError("too few arguments");
    }

    boolean  foundClassId   = false;
    boolean  foundPlatforms = false;

    for (int i = 0; i < argv.length; i++) {
      if (argv[i].equals("-norestore")) {
        noRestore = true;
      } else if (argv[i].equals("-source")) {
        i++;
        if (i < argv.length) {
          sourceRoot = argv[i];
        } else {
          usageError("path missing after -source");
        }
      } else if (argv[i].equals("-classid")) {
        i++;
        if (i < argv.length) {
          classId = argv[i];
          foundClassId = true;
        } else {
          usageError("classid or filename missing after -classid");
        }
      } else if (argv[i].equals("-platforms")) {
        i++;
        if (i < argv.length) {
          platforms = Strings.split(argv[i], File.pathSeparator);
          foundPlatforms = true;
        } else {
          usageError("platform name(s) missing after -platforms");
        }
      } else if (!argv[i].startsWith("-")) {
        if (!foundClassId) {
          usageError("-classid class_id|id_filename must be specified before " + argv[i]);
        }
        if (!foundPlatforms) {
          usageError("-platforms platform_list must be specified before " + argv[i]);
        }
        processFile(argv[i]);
      } else {
        usageError(argv[i] + " is not a valid argument");
      }
    }
  }


  static void processFile(String name) {

    textio.showInformation("Loading XML from " + name);

    Document       doc;
    Element        root;

    ArmidaleClass  armidaleClass;

    try {

      DocumentBuilderFactory  docBuilderFactory  = DocumentBuilderFactory.newInstance();
      DocumentBuilder         docBuilder         = docBuilderFactory.newDocumentBuilder();

      File                    xmlFile            = new File(name);
      doc = docBuilder.parse(xmlFile);
      doc.getDocumentElement().normalize();
      root = doc.getDocumentElement();
      String                  xmlRoot            = xmlFile.getParent();
      if (root.getNodeName() == "ArmidaleClass") {
        armidaleClass = new ArmidaleClass(root, noRestore, sourceRoot, xmlRoot, classId, platforms);
        armidaleClass.generateCode(sourceRoot);
      } else {
        textio.showError("'ArmidaleClass' expected");
      }

    } catch (SAXParseException err) {
      textio.showError("** Parsing error"
         + ", line " + err.getLineNumber()
         + ", uri " + err.getSystemId(),
        err.getMessage());

    } catch (Throwable t) {
      textio.showError(t);
      t.printStackTrace();
    }

  }


  private static void usageError(String message) {
    textio.showError("Usage: " + programName + " [-source source_root] -classid class_id|id_filename [-platforms platform_names] {xml_filename}");
    textio.showError("     : " + message);
    System.exit(0);
  }

}

