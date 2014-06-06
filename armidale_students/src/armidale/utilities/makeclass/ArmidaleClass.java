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
import armidale.api.ArgumentException;
import armidale.api.ProductInfo;
import armidale.api.UncheckedException;
import armidale.api.XmlException;

import armidale.api.io.StreamTextOutput;

import armidale.api.util.Strings;
import java.io.BufferedReader;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.LinkedList;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

class ArmidaleClass extends DocumentedItem {

  public static   String         interfacePackage;
  public static   String         factoryPackage;
  public static   String         implPackage;
  public static   String         platformPackage;
  public static   String         clientServerpackage;
  public static   String         serverPackage;
  public static   String         clientPackage;
  public static   String         callbacksPackage;
  public static   String         adaptersPackage;

  private static  String         sourceRoot;
  private static  String         xmlRoot;

  public          LinkedList     attributes              = new LinkedList();
  public          LinkedList     listAttributes          = new LinkedList();
  public          LinkedList     tableAttributes         = new LinkedList();
  public          LinkedList     treeAttributes          = new LinkedList();
  public          LinkedList     methods                 = new LinkedList();
  public          LinkedList     createMethods           = new LinkedList();
  public          LinkedList     interfaces              = new LinkedList();
  public          LinkedList     localDefaults           = new LinkedList();

  public          QualifiedName  className;
  public          QualifiedName  parentName;

  public          boolean        isAbstract;
  public          String         classId;

  public          Callback       callback;

  private         boolean        noRestore               = false;
  private         boolean        listClasses             = false;
  private         String[]       platforms;

  private         ListIterator   attributeIterator;
  private         ListIterator   listIterator;
  private         ListIterator   callbackMethodIterator;
  private         ListIterator   importIterator;
  private         ListIterator   methodIterator;
  private         ListIterator   interfaceIterator;
  private         ListIterator   defaultIterator;
  private         ListIterator   parameterIterator;
  private         ListIterator   fieldIterator;
  private         ListIterator   constantsIterator;
  private         UserCode       userCode;


  public ArmidaleClass
    (Element root, boolean noRestore,
     String sourceRoot, String xmlRoot, String classId, String[] platforms) {

    super(root);

    Node  tempNode;
    int   dotIndex;

    this.noRestore = noRestore;
    this.sourceRoot = sourceRoot;
    this.xmlRoot = xmlRoot;
    this.classId = classId;
    this.platforms = platforms;

    attributes.clear();
    listAttributes.clear();
    methods.clear();
    createMethods.clear();
    interfaces.clear();
    localDefaults.clear();

    tempNode = root.getAttributes().getNamedItem("name");
    if (tempNode == null) {
      throw new XmlException("'name' attribute expected", root);
    } else {
      className = new QualifiedName(tempNode.getNodeValue());
      findClassId();
    }
    tempNode = root.getAttributes().getNamedItem("extends");
    if (tempNode == null) {
      throw new XmlException("'extends' attribute expected", root);
    } else {
      parentName = new QualifiedName(tempNode.getNodeValue());
    }
    tempNode = root.getAttributes().getNamedItem("abstract");
    if (tempNode == null) {
      throw new XmlException("'abstract' attribute expected", root);
    } else {
      isAbstract = (tempNode.getNodeValue().equals("yes"));
    }

    tempNode = root.getFirstChild();
    loadNodes(tempNode);
    buildImportList();

    interfacePackage = className.packageName;
    factoryPackage = className.packageName;
    implPackage = Strings.concat(className.packageName, "impl", ".");
    platformPackage = Strings.concat(implPackage, "platform", ".");
    clientServerpackage = Strings.concat(implPackage, "clientserver", ".");
    serverPackage = clientServerpackage;
    clientPackage = clientServerpackage;
    callbacksPackage = className.packageName;
    adaptersPackage = className.packageName;

  }


  public void generateCode(String sourceRoot) {
    writeInterface(sourceRoot);
    writeFactory(sourceRoot);
    writeCallbackClasses(sourceRoot);
    if (platforms != null) {
      for (int i = 0; i < platforms.length; i++) {
        writePlatformClass(sourceRoot, platforms[i]);
      }
    }
    writeClientServerIdsInterface(sourceRoot);
    writeClientClass(sourceRoot);
    writeServerClass(sourceRoot);
  }


  public String compositeName(Name a, Name b) {
    return Strings.lowercase(a.name) + Strings.capitalize(b.name);
  }


  public String compositeName(Name a, Name b, Name c) {
    return Strings.lowercase(a.name) + Strings.capitalize(b.name) + Strings.capitalize(c.name);
  }


  public String compositeName(String a, Name b) {
    return Strings.lowercase(a) + Strings.capitalize(b.name);
  }


  public void processIncludeFile(String includeFilename) {
    String  filename  = Strings.concat(xmlRoot, includeFilename, File.separator);
    try {
      DocumentBuilderFactory  docBuilderFactory  = DocumentBuilderFactory.newInstance();
      DocumentBuilder         docBuilder         = docBuilderFactory.newDocumentBuilder();
      //System.out.println("INCLUDE: " + includeFilename);
      Document                doc                = docBuilder.parse(new File(filename));
      doc.getDocumentElement().normalize();
      Node                    includeNode        = doc.getDocumentElement();
//System.out.println("loading: " + includeNode);
      loadNodes(includeNode);
    } catch (SAXParseException err) {
      throw new UncheckedException("file=" + filename
         + ", line " + err.getLineNumber() + ", uri " + err.getSystemId(),
        err);
    } catch (Exception t) {
      throw new UncheckedException("processIncludeFile", t);
    }
  }


  public void writeDocumentation(PrintWriter output) {
    openDocumentation(output);
    super.writeDocumentation(output);
    output.println("   *");
    writeAuthorVersion(output);
    closeDocumentation(output);
  }


  public void writeCopyright(PrintWriter output, String filename) {
    Date              now   = new Date();
    SimpleDateFormat  date  = new SimpleDateFormat("ddMMMyyyy");
    SimpleDateFormat  time  = new SimpleDateFormat("HH:mm:ss");

    writeLicense(output);

    output.println();
    output.println("/*");
    output.println(" * " + filename);
    output.println(" * Generated by " + Main.programName + " " + ProductInfo.title() + " on " + date.format(now) + " at " + time.format(now));
    output.println(" *");
    output.println(" */");
  }



  private void findClassId() {
    try {
      File            inputFile  = new File(classId);
      BufferedReader  input      = new BufferedReader(new FileReader(inputFile));
      String          line       = input.readLine();
      String          id;
      String          name;
      while (line != null) {
        if (line.length() > 14) {
          id = line.substring(0, 10);
          name = line.substring(13).trim();
          if (name.equals(className.name)) {
            classId = id;
            System.out.println("  - classId = " + classId);
            return;
          }
        }
        line = input.readLine();
      }
      throw new ArgumentException("'" + className.name + "' not found in file '" + classId + "'");
    } catch (IOException t) {
      System.out.println("  - classId = " + classId);
    }
  }


  private void loadNodes(Node node) {
    Node    tempNode         = node;
    String  includeFilename  = "";
    while (tempNode != null) {
      switch (tempNode.getNodeType()) {
        case Node.ELEMENT_NODE:
          if (tempNode.getNodeName().equals("attribute")) {
            attributes.add(new Attribute(tempNode, this));

          } else if (tempNode.getNodeName().equals("list")) {
            listAttributes.add(new ListAttribute(tempNode, this));

          } else if (tempNode.getNodeName().equals("table")) {
            tableAttributes.add(new TableAttribute(tempNode, this));

          } else if (tempNode.getNodeName().equals("tree")) {
            treeAttributes.add(new TreeAttribute(tempNode, this));

          } else if (tempNode.getNodeName().equals("callback")) {
            if (callback == null) {
              callback = new Callback(tempNode, this);
            } else {
              throw new XmlException("only one callback can be defined per class", node);
            }

          } else if (tempNode.getNodeName().equals("method")) {
            methods.add(new Method(tempNode, this));

          } else if (tempNode.getNodeName().equals("createMethod")) {
            createMethods.add(new CreateMethod(tempNode, this));

          } else if (tempNode.getNodeName().equals("implements")) {
            interfaces.add(new Interface(tempNode, this));

          } else if (tempNode.getNodeName().equals("newDefault")) {
            localDefaults.add(new NewDefault(tempNode, this));

          } else if (tempNode.getNodeName().equals("include")) {
            Node  includeNode  = tempNode.getAttributes().getNamedItem("filename");
            if (includeNode == null) {
              throw new XmlException("'filename' attribute expected with 'include' tag", node);
            } else {
              processIncludeFile(includeNode.getNodeValue());
            }
          }
          break;
        case Node.TEXT_NODE:
          break;
      }
      tempNode = tempNode.getNextSibling();
    }
  }


  private void buildImportList() {
    Attribute      theAttribute;
    ListAttribute  theListAttribute;
    Method         theMethod;
    Interface      theInterface;
    Parameter      theParameter;
  }


  ////////////////////////////////
  //
  // write INTERFACE
  //
  ////////////////////////////////

  private void writeInterface(String sourceRoot) {
    PrintWriter    output;
    Attribute      theAttribute;
    ListAttribute  theListAttribute;
    Method         theMethod;
    Interface      theInterface;
    NewDefault     theNewDefault;
    Parameter      theParameter;
    String         filename;
    InterfaceList  interfaceList     = new InterfaceList();
    ImportList     imports           = new ImportList();

    int            id;
    boolean        done              = false;

    try {
      filename = sourceRoot + File.separatorChar
         + interfacePackage.replace('.', File.separatorChar)
         + File.separatorChar + className.name + ".java";

      System.out.println("  - writing interface to " + filename);

      output = new PrintWriter(new FileWriter(filename));

      writeCopyright(output, filename);
      output.println();
      output.println("package " + interfacePackage + ";");

      // class documentation
      writeDocumentation(output);

      // get imports
      imports.add(parentName);
      imports.addAttributes(attributes);
      imports.addListAttributes(listAttributes);
      imports.addMethods(methods);
      imports.addInterfaces(interfaces);

      imports.removeLocals(interfacePackage);

      imports.writeImports(output);

      // write interface declaration

      output.println();
      output.print("public interface " + className.name + " extends " + parentName.name);

      // get attribute constant interfaces
      interfaceList.clear();
      attributeIterator = attributes.listIterator(0);
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();
        interfaceList.add(theAttribute);
      }
      // get method parameter constant interfaces
      methodIterator = methods.listIterator(0);
      while (methodIterator.hasNext()) {
        theMethod = (Method) methodIterator.next();
        parameterIterator = theMethod.parameterList.listIterator(0);
        while (parameterIterator.hasNext()) {
          theParameter = (Parameter) parameterIterator.next();
          interfaceList.add(theParameter);
        }
      }
      // get imported interfaces
      interfaceIterator = interfaces.listIterator(0);
      while (interfaceIterator.hasNext()) {
        theInterface = (Interface) interfaceIterator.next();
        interfaceList.add(theInterface);
      }

      // write interfaces
      if (interfaceList.size() > 0) {
        output.print(", " + interfaceList.getNameList());
      }
      output.println(" {");

      // attribute defaults
      attributeIterator = attributes.listIterator(0);
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();
        if (theAttribute.defaultValue != null) {
          if (!done) {
            output.println();
            output.println("  // Attributes Defaults");
            output.println("  //");
            done = true;
          }
          output.println(theAttribute.defaultValueDeclaration());
        }
      }

      // local defaults
      defaultIterator = localDefaults.listIterator(0);
      while (defaultIterator.hasNext()) {
        theNewDefault = (NewDefault) defaultIterator.next();
        if (!done) {
          output.println();
          output.println("  // Override Defaults");
          output.println("  //");
          done = true;
        }
        output.println(theNewDefault.defaultValueDeclaration());
      }

      // get and set methods for attributes
      attributeIterator = attributes.listIterator(0);
      if (attributeIterator.hasNext()) {
        output.println();
        output.println("  // attribute set/get/is methods");
        output.println("  //");
      }
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();
        if (theAttribute.isReadable) {
          output.println();
          theAttribute.writeGetDocumentation(output);
          output.println(theAttribute.getSpec() + ";");
        }
        if (theAttribute.isWriteable) {
          output.println();
          theAttribute.writeSetDocumentation(output);
          output.println(theAttribute.setSpec() + ";");
        }
        if (theAttribute.hasAlternateName) {
          if (theAttribute.isReadable) {
            output.println();
            theAttribute.writeAlternateGetDocumentation(output);
            output.println(theAttribute.alternateGetSpec() + ";");
          }
          if (theAttribute.isWriteable) {
            output.println();
            theAttribute.writeAlternateSetDocumentation(output);
            output.println(theAttribute.alternateSetSpec() + ";");
          }
        }
      }

      // callback methods
      if (callback != null) {
        output.println();
        output.println("  // callback");
        output.println("  //");
        output.println();
        callback.writeAddDocumentation(output);
        output.println(callback.addCallbackSpec() + ";");
        output.println();
        callback.writeRemoveDocumentation(output);
        output.println(callback.removeCallbackSpec() + ";");
        output.println();
        callback.writeRemoveAllDocumentation(output);
        output.println(callback.removeAllCallbacksSpec() + ";");
        output.println();
        callback.writeGetDocumentation(output);
        output.println(callback.getCallbackListSpec() + ";");
      }

      // other methods
      methodIterator = methods.listIterator(0);
      if (methodIterator.hasNext()) {
        output.println();
        output.println("  // other methods");
        output.println("  //");
      }
      while (methodIterator.hasNext()) {
        theMethod = (Method) methodIterator.next();
        output.println();
        theMethod.writeDocumentation(output);
        output.println(theMethod.spec() + ";");
      }

      // done
      output.println("}");
      output.flush();
      output.close();
    } catch (IOException e) {
      throw new UncheckedException("writInterface", e);
    }
    super.writeDocumentation(output);

  }


  ////////////////////////////////
  //
  // write FACTORY
  //
  ////////////////////////////////

  private void writeFactory(String sourceRoot) {
    PrintWriter    output;
    Attribute      theAttribute;
    ListAttribute  theListAttribute;
    CreateMethod   theCreateMethod;
    Parameter      theParameter;
    int            id                = 0;
    String         filename;
    InterfaceList  interfaceList     = new InterfaceList();
    ImportList     imports           = new ImportList();

    if (isAbstract) {
      return;
    }

    try {
      filename = sourceRoot + File.separatorChar
         + factoryPackage.replace('.', File.separatorChar)
         + File.separatorChar + className.name + "Factory.java";

      System.out.println("  - writing factory to " + filename);

      userCode = new UserCode(filename);

      output = new PrintWriter(new FileWriter(filename));

      writeCopyright(output, filename);
      output.println();
      output.println("package " + factoryPackage + ";");

      // get imports
      imports.add("armidale.api.ArmidaleObjectFactory");
      imports.add("armidale.api.context.*");
      imports.add("armidale.api.context.clientserver.*");
      imports.add("armidale.api.context.platform.*");

      imports.addCreateMethods(createMethods);

      imports.removeLocals(factoryPackage);
      imports.writeImports(output);

      // Docoumentation
      output.println();
      openDocumentation(output);
      output.println("   * This factory class is used to create {@link " + interfacePackage + "." + className.name + " " + className.name + "} instances.");
      output.println("   *");
      writeAuthorVersion(output);
      closeDocumentation(output);

      // write class declaration
      output.println();
      output.print("public class " + className.name + "Factory extends ArmidaleObjectFactory");

      // factory method parameter constant interfaces
      interfaceList.clear();
      methodIterator = createMethods.listIterator(0);
      while (methodIterator.hasNext()) {
        theCreateMethod = (CreateMethod) methodIterator.next();
        attributeIterator = theCreateMethod.attributeList.listIterator(0);
        while (attributeIterator.hasNext()) {
          theAttribute = (Attribute) attributeIterator.next();
          interfaceList.add(theAttribute);
        }
      }

      if (interfaceList.size() > 0) {
        output.print(" implements ");
        output.print(interfaceList.getNameList());
      }
      output.println(" {");

      //Class ID
      output.println();
      output.println(classIdDeclaration());
      output.println();

      // constructors
      output.println();

      openDocumentation(output);
      output.println("   * Creates an object that implements the {@link "
         + interfacePackage + "." + className.name + " " + className.name + "} interface for the specified context.");
      output.println("   *");
      output.println("   * @param  context context for which the object will be created");
      output.println("   * @return created object");
      output.println("   *");
      output.println("   * @see armidale.api.context.Context");
      output.println("   * @see armidale.api.context.clientserver.ServerContext");
      if (platforms != null) {
        for (int i = 0; i < platforms.length; i++) {
          output.println("   * @see armidale.api.context.platform." + Strings.capitalize(platforms[i]) + "Context");
          output.println("   * @see armidale.api.context.clientserver." + Strings.capitalize(platforms[i]) + "ClientContext");
        }
        for (int i = 0; i < platforms.length; i++) {
            output.println("    } else if (context instanceof " + Strings.capitalize(platforms[i]) + "Context) {");
            output.println("        return new " + platformPackage + "." + platforms[i] + "." + className.name + "Impl();");
            output.println("    } else if (context instanceof " + Strings.capitalize(platforms[i]) + "ClientContext) {");
            output.println("        " + className.name + " " + platforms[i] + className.name + " = new " + platformPackage + "." + platforms[i] + "." + className.name + "Impl();");
            output.println("        return new " + clientPackage + "." + className.name + "ClientImpl((ClientContext)context, " + platforms[i] + className.name + ");");
          }
          output.println("    } else {");
          output.println("        return null;");
          output.println("    }");
          output.println("  }");
      }
      closeDocumentation(output);

      output.println("  public static " + className.name + " create(Context context) {");
      output.println("    if (context instanceof ServerContext) {");
      output.println("        return new " + serverPackage + "." + className.name + "ServerImpl((ServerContext)context);");

      // create methods
      methodIterator = createMethods.listIterator(0);
      while (methodIterator.hasNext()) {
        theCreateMethod = (CreateMethod) methodIterator.next();
        output.println();
        theCreateMethod.writeDocumentation(output);
        output.println(theCreateMethod.spec() + " {");
        output.println("    " + className.name + " " + Strings.lowercase(className.name) + " = create(context);");
        attributeIterator = theCreateMethod.attributeList.listIterator(0);
        while (attributeIterator.hasNext()) {
          theAttribute = (Attribute) attributeIterator.next();
          output.println("    " + Strings.lowercase(className.name) + ".set" + Strings.capitalize(theAttribute.name) + "(" + Strings.lowercase(theAttribute.name) + ");");
        }
//        userCode.writeUserCode(output, "    ", theFactoryMethod.spec());
        output.println("    return " + Strings.lowercase(className.name) + ";");
        output.println("  }");
      }

      // done
      output.println();
      output.println("}");
      output.flush();
      output.close();
    } catch (IOException e) {
      throw new UncheckedException("writeFactory", e);
    }

  }


  ////////////////////////////////
  //
  // write CALLBACK classes
  //
  ////////////////////////////////

  private void writeCallbackClasses(String sourceRoot) {
    PrintWriter     output;
    CallbackMethod  theCallbackMethod;
    Parameter       theParameter;
    String          filename;

    int             id;
    boolean         done               = false;

    ImportList      imports            = new ImportList();

    if (callback != null) {

      // interface <className.name>Callback

      try {
        filename = sourceRoot + File.separatorChar
           + callbacksPackage.replace('.', File.separatorChar)
           + File.separatorChar + Strings.capitalize(callback.callbackName()) + ".java";

        System.out.println("  - writing callback interface to " + filename);

        output = new PrintWriter(new FileWriter(filename));

        writeCopyright(output, filename);
        output.println();
        output.println("package " + callbacksPackage + ";");

        // class documentation
        callback.writeDocumentation(output);

        // get imports
        imports.addCallback(callback);

        imports.removeLocals(callbacksPackage);
        imports.writeImports(output);

        // write interface declaration
        output.println();
        output.println("public interface " + Strings.capitalize(callback.callbackName()) + " {");
        output.println();
        methodIterator = callback.methodList.listIterator(0);
        while (methodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) methodIterator.next();
          output.println();
          theCallbackMethod.writeDocumentation(output);
          output.println(theCallbackMethod.spec() + ";");
        }
        // done
        output.println();
        output.println("}");
        output.flush();
        output.close();
      } catch (IOException e) {
        throw new UncheckedException("writeCallback", e);
      }

      // class <className.name>CallbackAdapter

      try {
        filename = sourceRoot + File.separatorChar
           + callbacksPackage.replace('.', File.separatorChar)
           + File.separatorChar + Strings.capitalize(callback.callbackName()) + "Adapter.java";

        System.out.println("  - writing callback adapter class to " + filename);

        output = new PrintWriter(new FileWriter(filename));

        writeCopyright(output, filename);
        output.println();
        output.println("package " + callbacksPackage + ";");

        imports.writeImports(output);

        output.println();
        openDocumentation(output);
        output.println("   * An implementation of the {@link " + callbacksPackage + "." + Strings.capitalize(callback.callbackName()) + " "
           + Strings.capitalize(callback.callbackName()) + "} interface.");
        output.println("   * All methods in this class are empty. The class exists as a convenience for creating callback objects");
        closeDocumentation(output);

        output.println();
        output.println("public class " + Strings.capitalize(callback.callbackName()) + "Adapter implements "
           + Strings.capitalize(callback.callbackName()) + " {");

        methodIterator = callback.methodList.listIterator(0);
        while (methodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) methodIterator.next();
          output.println();
          openDocumentation(output);
          output.println("   * @see " + callbacksPackage + "." + Strings.capitalize(callback.callbackName()) + "#" + theCallbackMethod.name);
          closeDocumentation(output);
          output.println(theCallbackMethod.spec() + " {");
          output.println("  }");
        }
        // done
        output.println();
        output.println("}");
        output.flush();
        output.close();
      } catch (IOException e) {
        throw new UncheckedException("writeCallbackAdapter", e);
      }

      // class <className.name>CallbackDebug

      try {
        filename = sourceRoot + File.separatorChar
           + callbacksPackage.replace('.', File.separatorChar)
           + File.separatorChar + Strings.capitalize(callback.callbackName()) + "Debug.java";

        System.out.println("  - writing callback debug class to " + filename);

        output = new PrintWriter(new FileWriter(filename));

        writeCopyright(output, filename);
        output.println();
        output.println("package " + callbacksPackage + ";");

        imports.add("armidale.api.io.Debug");
        imports.add("armidale.api.context.Context");
        imports.writeImports(output);

        output.println();
        openDocumentation(output);
        output.println("   * An implementation of the {@link " + callbacksPackage + "." + Strings.capitalize(callback.callbackName()) + " "
           + Strings.capitalize(callback.callbackName()) + "} interface.");
        output.println("   * All methods in this class call the {@link armidale.api.io.Debug Debug} class with a message describing");
        output.println("   * the method called. The class exists as a convenience for debugging callbacks.");
        closeDocumentation(output);

        output.println();
        output.print("public");
        //if (isAbstract) {
        //  output.print(" abstract");
        //}
        output.println(" class " + Strings.capitalize(callback.callbackName()) + "Debug implements "
           + Strings.capitalize(callback.callbackName()) + " {");

        output.println();
        output.println("  private Debug debug;");
        output.println("  private int   debugLevel;");
        output.println();
        openDocumentation(output);
        output.println("   * Constructor which creates an object that will display messages on the specified context's output");
        output.println("   * when the current debug level is >= a specified level.");
        output.println("   *");
        output.println("   * @param context the context for display of messages (see {@link armidale.api.context.Context Context}");
        output.println("   * @param debugLevel the debug level (see {@link armidale.api.io.Debug Debug}");
        closeDocumentation(output);

        output.println("  public " + Strings.capitalize(callback.callbackName()) + "Debug(Context context, int debugLevel) {");
        output.println("    super();");
        output.println("    debug = new Debug(context.getTextOutput());");
        output.println("    this.debugLevel = debugLevel;");
        output.println("  }");
        output.println();
        openDocumentation(output);
        output.println("   * Constructor which creates an object that will display messages on the specified context's output");
        output.println("   * irrespective of debug level.");
        output.println("   *");
        output.println("   * @param context the context for display of messages (see {@link armidale.api.context.Context Context}");
        closeDocumentation(output);
        output.println("  public " + Strings.capitalize(callback.callbackName()) + "Debug(Context context) {");
        output.println("    debug = new Debug(context.getTextOutput());");
        output.println("    this.debugLevel = Debug.ALLWAYS;");
        output.println("  }");
        methodIterator = callback.methodList.listIterator(0);
        while (methodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) methodIterator.next();

          parameterIterator = theCallbackMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            if (theParameter.isArray) {
              output.println();
              output.println("  private String " + theParameter.name + "String(" + theParameter.typeDeclaration() + " " + theParameter.name + ") {");
              output.println("    String result = \"\";");
              output.println("    for (int i=0; i < " + theParameter.name + ".length; i++) {");
              output.print("      result = result + \"[\" + i + \"]=\" + " + theParameter.name + "[i]");
              if (theParameter.isPrimitive) {
                output.println(" + \" \";");
              } else {
                output.println(".toString() + \" \";");
              }
              output.println("    }");
              output.println("    return result;");
              output.println("  }");
            }
          }

          output.println();
          openDocumentation(output);
          output.println("   * @see " + callbacksPackage + "." + Strings.capitalize(callback.callbackName()) + "#" + theCallbackMethod.name);
          closeDocumentation(output);
          output.println(theCallbackMethod.spec() + " {");
          output.print("    debug.message(\"" + Strings.capitalize(callback.callbackName()) + "."
             + Strings.lowercase(theCallbackMethod.name) + "\" , new String[] {this.toString()");
          output.print(", " + Strings.lowercase(className.name) + ".toString()");
          parameterIterator = theCallbackMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            output.print(", \"" + theParameter.name + " = \"" + " + ");
            if (theParameter.isArray) {
              output.print(theParameter.name + "String(" + theParameter.name + ")");
            } else {
              if (theParameter.isPrimitive) {
                output.print(Strings.lowercase(theParameter.name));
              } else {
                output.print(Strings.lowercase(theParameter.name) + ".toString()");
              }
            }
          }
          output.println("}, debugLevel);");
          output.println("  }");
        }
        // done
        output.println();
        output.println("}");
        output.flush();
        output.close();
      } catch (IOException e) {
        throw new UncheckedException("writeCallbackDebug", e);
      }

      // class <className.name>CallbackList

      try {
        filename = sourceRoot + File.separatorChar
           + callbacksPackage.replace('.', File.separatorChar)
           + File.separatorChar + Strings.capitalize(callback.callbackName()) + "List.java";

        System.out.println("  - writing callback list class to " + filename);

        output = new PrintWriter(new FileWriter(filename));

        writeCopyright(output, filename);
        output.println();
        output.println("package " + callbacksPackage + ";");

        imports.add("java.util.LinkedList");
        imports.add("java.util.ListIterator");
        imports.add(callback.qualifiedName());

        imports.removeLocals(callbacksPackage);
        imports.writeImports(output);

        output.println();
        openDocumentation(output);
        output.println("   * An implementation of the {@link " + callbacksPackage + "." + Strings.capitalize(callback.callbackName()) + " "
           + Strings.capitalize(callback.callbackName()) + "} interface which operates on lists of callbacks.");
        closeDocumentation(output);

        output.println();
        output.println("public class " + Strings.capitalize(callback.callbackName()) + "List extends LinkedList implements "
           + Strings.capitalize(callback.callbackName()) + " {");

        methodIterator = callback.methodList.listIterator(0);
        while (methodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) methodIterator.next();
          output.println();
          openDocumentation(output);
          output.println("   * @see " + callbacksPackage + "." + Strings.capitalize(callback.callbackName()) + "#" + theCallbackMethod.name);
          closeDocumentation(output);
          output.println(theCallbackMethod.spec() + " {");
          output.println("    synchronized(this) {");
          output.println("      " + Strings.capitalize(callback.callbackName()) + " theCallback;");
          output.println("      ListIterator iterator = listIterator(0);");
          output.println("      while (iterator.hasNext()) {");
          output.println("        theCallback = (" + Strings.capitalize(callback.callbackName()) + ")iterator.next();");
          output.print("        theCallback." + theCallbackMethod.name + "(" + Strings.lowercase(className.name));
          parameterIterator = theCallbackMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            output.print(", " + theParameter.name);
          }
          output.println(");");
          output.println("      }");
          output.println("    }");
          output.println("  }");
        }
        output.println();
        
        output.println("  public boolean add(Object o) {");
        output.println("    synchronized(this) {");
        output.println("      return super.add(o);");
        output.println("    }");
        output.println("  }");
        output.println();
        output.println("  public boolean remove(Object o) {");
        output.println("    synchronized(this) {");
        output.println("      return super.remove(o);");
        output.println("    }");
        output.println("  }");
        output.println();
        output.println("  public int size() {");
        output.println("    synchronized(this) {");
        output.println("      return super.size();");
        output.println("    }");
        output.println("  }");
        output.println();
        output.println("  public void clear() {");
        output.println("    synchronized(this) {");
        output.println("      super.clear();");
        output.println("    }");
        output.println("  }");
        output.println();
        
        // done
        output.println();
        output.println("}");
        output.flush();
        output.close();
      } catch (IOException e) {
        throw new UncheckedException("writeCallbackList", e);
      }

    }
  }


  ////////////////////////////////
  //
  // write CLIENT SERVER IDs INTERFACE
  //
  ////////////////////////////////

  private void writeClientServerIdsInterface(String sourceRoot) {
    PrintWriter     output;
    Attribute       theAttribute;
    ListAttribute   theListAttribute;
    CallbackMethod  theCallbackMethod;
    Method          theMethod;
    int             id                 = 0;
    String          filename;
    ImportList      imports            = new ImportList();

    try {
      filename = sourceRoot + File.separatorChar
         + clientServerpackage.replace('.', File.separatorChar)
         + File.separatorChar + className.name + "ClientServerIds.java";

      System.out.println("  - writing client server IDs interface to " + filename);

      output = new PrintWriter(new FileWriter(filename));

      writeCopyright(output, filename);
      output.println();
      output.println("package " + clientServerpackage + ";");

      // get imports
      imports.add(className);
      imports.add(clientServerIdsName(className));
      imports.add("armidale.api.context.clientserver.*");
      if (!isAbstract) {
        imports.add(factoryPackage + "." + className.name + "Factory");
      }
      imports.removeLocals(clientPackage);
      imports.writeImports(output);

      output.println();
      openDocumentation(output);
      output.println("   * Defines various constants used in communications between "
         + "{@link " + interfacePackage + "." + Strings.capitalize(className.name) + " "
         + Strings.capitalize(className.name) + "} clients and servers");
      closeDocumentation(output);

      // write interface declaration
      output.println();
      output.println("public interface " + className.name + "ClientServerIds extends " + className.name + " {");

      // Base value for Ids
      output.println();
      if (isAbstract) {
        output.println("  public final static int BASE_ID = " + classId + " + 1;");
      } else {
        output.println("  public final static int BASE_ID = " + Strings.capitalize(className.name) + "Factory.CLASS_ID + 1;");
      }

      // attribute IDs
      attributeIterator = attributes.listIterator(0);
      id = 0;
      if (attributeIterator.hasNext()) {
        output.println();
        output.println("  // Attribute IDs");
        output.println("  //");
      }
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();
        output.println("  public static final int " + theAttribute.idName() + " = BASE_ID + " + id + ";");
        id++;
      }

      // Callback IDs
      if (callback != null) {
        output.println();
        output.println("  // Callback method IDs");
        output.println("  //");
        output.println();
        callbackMethodIterator = callback.methodList.listIterator(0);
        while (callbackMethodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) callbackMethodIterator.next();
          output.println("  public static final int " + theCallbackMethod.idName() + " = BASE_ID + " + id + ";");
          id++;
        }
        output.println();
      }

      // other method IDs
      methodIterator = methods.listIterator(0);
      if (methodIterator.hasNext()) {
        output.println();
        output.println("  // Other Method IDs");
        output.println("  //");
      }
      while (methodIterator.hasNext()) {
        theMethod = (Method) methodIterator.next();
        output.println("  public static final int " + theMethod.idName() + " = BASE_ID + " + id + ";");
        id++;
      }

      // done
      output.println("}");
      output.flush();
      output.close();
    } catch (IOException e) {
      throw new UncheckedException("writeClientServerIdsInterface", e);
    }
  }


  ////////////////////////////////
  //
  // write CLIENT
  //
  ////////////////////////////////

  private void writeClientClass(String sourceRoot) {
    PrintWriter     output;
    Interface       theInterface;
    Attribute       theAttribute;
    ListAttribute   theListAttribute;
    CallbackMethod  theCallbackMethod;
    Method          theMethod;
    Parameter       theParameter;
    int             id                 = 0;
    String          filename;
    ImportList      imports            = new ImportList();

    try {
      filename = sourceRoot + File.separatorChar
         + clientPackage.replace('.', File.separatorChar)
         + File.separatorChar + className.name + "ClientImpl.java";

      System.out.println("  - writing client class to " + filename);

      output = new PrintWriter(new FileWriter(filename));

      writeCopyright(output, filename);
      output.println();
      output.println("package " + clientPackage + ";");

      // get imports
      imports.add(clientName(parentName));
      imports.add("armidale.api.context.clientserver.*");
      imports.add("armidale.api.ImplementationException");
      imports.add(clientServerpackage + "." + className.name + "ClientServerIds");
      imports.add(interfacePackage + "." + className.name);
      if (!isAbstract) {
        imports.add(interfacePackage + "." + className.name + "Factory");
      }
      imports.addAttributes(attributes);
      imports.addListAttributes(listAttributes);
      imports.addMethods(methods);
      imports.addInterfaces(interfaces);
      imports.addCallback(callback);

      if (callback != null) {
        imports.add(callback.qualifiedName());
        imports.add(callback.qualifiedName() + "List");
      }

      imports.removeLocals(clientPackage);
      imports.writeImports(output);

      // write class declaration
      output.println();
      output.print("public ");
      if (isAbstract) {
        output.print("abstract ");
      }
      output.println("class " + className.name + "ClientImpl extends " + parentName.name
         + "ClientImpl implements " + className.name + "ClientServerIds {");

      // constructor
      output.println();
      output.println("  // constructor");
      output.println("  //");
      output.println("  public " + className.name + "ClientImpl(ClientContext context, " + className.name + " peer) {");
      output.println("    super(context, peer);");
      output.println("  }");

      // Class ID
      writeClassId(output);

      // get and set methods for attributes
      attributeIterator = attributes.listIterator(0);
      if (attributeIterator.hasNext()) {
        output.println();
        output.println("  // attribute set/get/is methods");
        output.println("  //");
      }
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();

        if (theAttribute.isReadable) {
          output.println();
          output.println(theAttribute.getSpec() + " {");
          output.println("    throw new ImplementationException(\"attribute get/is methods are not supported in client context\");");
          output.println("  }");
        }

        if (theAttribute.isWriteable) {
          output.println();
          output.println(theAttribute.setSpec() + " {");
          if (theAttribute.isArmidale) {
            output.println("    if (" + theAttribute.name + "==null) {");
            output.println("      ((" + className.name + ")peer).set" + Strings.capitalize(theAttribute.name) + "(null);");
            output.println("    } else {");
            output.println("      ((" + className.name + ")peer).set" + Strings.capitalize(theAttribute.name) + "("
               + "(" + theAttribute.typeName.name + ")((" + Strings.capitalize(theAttribute.typeName.name) + "ClientImpl)" + theAttribute.name + ").peer);");
            output.println("    }");
          } else {
            output.println("    ((" + className.name + ")peer).set" + Strings.capitalize(theAttribute.name) + "(" + theAttribute.name + ");");
          }
          output.println("  }");
        }

        if (theAttribute.hasAlternateName) {
          if (theAttribute.isReadable) {
            output.println();
            output.println(theAttribute.alternateGetSpec() + " {");
            output.println(theAttribute.alternateGetStatement());
            output.println("  }");
          }
          if (theAttribute.isWriteable) {
            output.println();
            output.println(theAttribute.alternateSetSpec() + " {");
            output.println(theAttribute.alternateSetStatement());
            output.println("  }");
          }
        }
      }

      // Callbacks
      if (callback != null) {
        output.println();
        output.println("  // Callback methods");
        output.println("  //");
        output.println();
        output.println(callback.addCallbackSpec() + " {");
        output.println("    ((" + className.name + ")peer)." + callback.addCallbackName() + "(callback);");
        output.println("  }");
        output.println();
        output.println(callback.removeCallbackSpec() + " {");
        output.println("    ((" + className.name + ")peer)." + callback.removeCallbackName() + "(callback);");
        output.println("  }");
        output.println();
        output.println(callback.removeAllCallbacksSpec() + " {");
        output.println("    ((" + className.name + ")peer)." + callback.removeAllCallbacksName() + "();");
        output.println("  }");
        output.println();
        output.println(callback.getCallbackListSpec() + " {");
        output.println("    throw new ImplementationException(\"callback get methods are not supported in client context\");");
        output.println("  }");
      }

      // other methods
      methodIterator = methods.listIterator(0);
      if (methodIterator.hasNext()) {
        output.println();
        output.println("  // other methods");
        output.println("  //");
      }
      while (methodIterator.hasNext()) {
        theMethod = (Method) methodIterator.next();
        output.println();
        output.println(theMethod.spec() + " {");
        parameterIterator = theMethod.parameterList.listIterator(0);
        while (parameterIterator.hasNext()) {
          theParameter = (Parameter) parameterIterator.next();
          if (theParameter.isArmidale) {
            if (theParameter.isArray) {
              output.println("    " + theParameter.typeName.name + "[] " + theParameter.name + "PeerArray = new "
                 + theParameter.typeName.name + "[" + theParameter.name + ".length];");
              output.println("    for (int i=0; i < " + theParameter.name + ".length; i++) {");
              output.println("      if (" + theParameter.name + "[i]==null) {");
              output.println("       " + theParameter.name + "PeerArray[i] = null;");
              output.println("      } else {");
              output.println("       " + theParameter.name + "PeerArray[i] = "
                 + "(" + theParameter.typeName.name + ")((" + Strings.capitalize(theParameter.typeName.name) + "ClientImpl)"
                 + theParameter.name + "[i]).peer;");
              output.println("      }");
              output.println("    }");
            } else {
              output.println("    " + theParameter.typeName.name + " " + theParameter.name + "Peer;");
              output.println("    if (" + theParameter.name + "==null) {");
              output.println("     " + theParameter.name + "Peer = null;");
              output.println("    } else {");
              output.println("     " + theParameter.name + "Peer = "
                 + "(" + theParameter.typeName.name + ")((" + Strings.capitalize(theParameter.typeName.name) + "ClientImpl)"
                 + theParameter.name + ").peer;");
              output.println("    }");
            }
          }
        }
        output.print("    ((" + className.name + ")peer)." + theMethod.name + "(");
        parameterIterator = theMethod.parameterList.listIterator(0);
        while (parameterIterator.hasNext()) {
          theParameter = (Parameter) parameterIterator.next();
          if (theParameter.isArmidale) {
            if (theParameter.isArray) {
              output.print(theParameter.name + "PeerArray");
            } else {
              output.print(theParameter.name + "Peer");
            }
          } else {
            output.print(theParameter.name);
          }
          if (parameterIterator.hasNext()) {
            output.print(", ");
          }
        }
        output.println(");");
        output.println("  }");
      }

      // Transport Callbacks
      if (callback != null) {
        output.println();
        output.println("  // local callback handlers");
        output.println("  //");
        output.println();
        output.println("  private class LocalCallback implements "
           + Strings.capitalize(className.name) + "Callback {");
        callbackMethodIterator = callback.methodList.listIterator(0);
        while (callbackMethodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) callbackMethodIterator.next();
          output.println();
          output.println("  " + theCallbackMethod.spec() + " {");
          output.println("      Message message = new CallbackMessage(" + className.name + "ClientImpl.this.getId(), " + theCallbackMethod.idName() + ");");
          parameterIterator = theCallbackMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            if (theParameter.isArray) {
              output.println("      message.writeInt(" + theParameter.name + ".length);");
              output.println("      for (int i=0; i<" + theParameter.name + ".length; i++) {");
              if (theParameter.isArmidale) {
                output.println("        message.writeObjectId((MessagingObject)" + theParameter.name + "[i]);");
              } else {
                output.println("        message.write" + Strings.capitalize(theParameter.typeName.name) + "(" + theParameter.name + "[i]);");
              }
              output.println("      }");
            } else {
              if (theParameter.isArmidale) {
                output.println("      message.writeObjectId((MessagingObject)" + theParameter.name + ");");
              } else {
                output.println("      message.write" + Strings.capitalize(theParameter.typeName.name) + "(" + theParameter.name + ");");
              }
            }
          }
          output.println("      transport.writeMessage(message);");
          output.println("    }");
        }
        output.println("  }");
      }

      // handleSetAttribute()
      if ((attributes.size() + listAttributes.size()) > 0) {
        output.println();
        output.println("  public void handleSetAttribute(int attributeId, Message message) {");
        output.println("    int index;");
        output.println("    int first;");
        output.println("    int count;");

        // temp array attribute values
        boolean  headerDone  = false;
        attributeIterator = attributes.listIterator(0);
        while (attributeIterator.hasNext()) {
          theAttribute = (Attribute) attributeIterator.next();
          if (theAttribute.isArray) {
            if (!headerDone) {
              output.println();
              output.println("    // temporary array attribute values");
              output.println("    //");
              headerDone = true;
            }
            output.println("    " + theAttribute.typeDeclaration() + " " + compositeName("temp", theAttribute) + ";");
          }
        }

        output.println();
        output.println("    switch (attributeId) {");

        // Attributes
        attributeIterator = attributes.listIterator(0);
        while (attributeIterator.hasNext()) {
          theAttribute = (Attribute) attributeIterator.next();
          output.println("      case " + theAttribute.idName() + ":");
          if (theAttribute.isFile) {
            output.println("        set" + Strings.capitalize(theAttribute.name) + "(new File(message.readString()));");
          } else {
            if (theAttribute.isArray) {
              output.println("        " + compositeName("temp", theAttribute) + " = new " + theAttribute.typeName.name + "[message.readInt()];");
              output.println("        for (int i=0; i < " + compositeName("temp", theAttribute) + ".length; i++) {");
              output.print("          " + compositeName("temp", theAttribute) + "[i] = ");
              output.println("message.read" + Strings.capitalize(theAttribute.typeName.name) + "();");
              output.println("        }");
              output.println("        set" + Strings.capitalize(theAttribute.name) + "(" + compositeName("temp", theAttribute) + ");");
            } else {
              if (theAttribute.isArmidale) {
                output.println("        set" + Strings.capitalize(theAttribute.name)
                   + "((" + theAttribute.typeName.name + ")objectRegistry.findObject(message.readObjectId()));");
              } else {
                output.println("        set" + Strings.capitalize(theAttribute.name) + "(message.read" + Strings.capitalize(theAttribute.typeName.name) + "());");
              }
            }
          }
          output.println("        break;");
          id = id + 1;
        }

        // default to parent
        output.println("      default:");
        output.println("        super.handleSetAttribute(attributeId, message);");

        output.println("    }");
        output.println("  }");
      }

      // handleMethodCall()
      if (methods.size() > 0) {
        output.println();
        output.println("  public void handleMethodCall(int methodId, Message message) {");

        // method parameters
        methodIterator = methods.listIterator(0);
        if (methodIterator.hasNext()) {
          output.println();
          output.println("    // method parameter values");
          output.println("    //");
        }
        UniqueStringList  uniqueParameterList      = new UniqueStringList();
        while (methodIterator.hasNext()) {
          theMethod = (Method) methodIterator.next();
          parameterIterator = theMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            uniqueParameterList.add(theParameter.typeDeclaration() + " " + compositeName(theMethod, theParameter));
          }
        }
        ListIterator      uniqueParameterIterator  = uniqueParameterList.listIterator(0);
        while (uniqueParameterIterator.hasNext()) {
          output.println("    " + uniqueParameterIterator.next().toString() + ";");
        }

        output.println();
        output.println("    switch (methodId) {");

        methodIterator = methods.listIterator(0);
        while (methodIterator.hasNext()) {
          theMethod = (Method) methodIterator.next();
          output.println("      case " + theMethod.idName() + ":");
          // get parameter values
          parameterIterator = theMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            if (theParameter.isArray) {
              output.println("        " + compositeName(theMethod, theParameter) + " = new " + theParameter.typeName.name + "[message.readInt()];");
              output.println("        for (int i=0; i < " + compositeName(theMethod, theParameter) + ".length; i++) {");
              output.print("          " + compositeName(theMethod, theParameter) + "[i] = ");
            } else {
              output.print("        " + compositeName(theMethod, theParameter) + " = ");
            }
            if (theParameter.isArmidale) {
              output.println("(" + theParameter.typeName.name + ")objectRegistry.findObject(message.readObjectId());");
            } else {
              output.println("message.read" + Strings.capitalize(theParameter.typeName.name) + "();");
            }
            if (theParameter.isArray) {
              output.println("        }");
            }
          }
          // call the method
          output.print("        " + theMethod.name + "(");
          parameterIterator = theMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            output.print(compositeName(theMethod, theParameter));
            //if (theParameter.isArmidale) {
            //  output.print("Peer");
            //}
            if (parameterIterator.hasNext()) {
              output.print(", ");
            }
          }
          output.println(");");
          output.println("        break;");
        }

        // default to parent
        output.println("      default:");
        output.println("        super.handleMethodCall(methodId, message);");

        output.println("    }");
        output.println("  }");
      }

      // handle Enable/DisableCallbacks()
      if (callback != null) {
        output.println();
        output.println("  public void handleEnableCallbacks() {");
        output.println("    " + callback.addCallbackName() + "(new LocalCallback());");
        output.println("  }");

        output.println();
        output.println("  public void handleDisableCallbacks() {");
        output.println("    " + callback.removeAllCallbacksName() + "();");
        output.println("  }");
      }

      // done
      output.println("}");
      output.flush();
      output.close();

    } catch (IOException e) {
      throw new UncheckedException("writeClientClass", e);
    }

  }


  ////////////////////////////////
  //
  // write SERVER
  //
  ////////////////////////////////

  private void writeServerClass(String sourceRoot) {
    int             id                 = 0;
    PrintWriter     output;
    Interface       theInterface;
    Attribute       theAttribute;
    ListAttribute   theListAttribute;
    CallbackMethod  theCallbackMethod;
    Method          theMethod;
    Parameter       theParameter;
    NewDefault      theNewDefault;
    String          filename;
    ImportList      imports            = new ImportList();

    try {
      filename = sourceRoot + File.separatorChar
         + serverPackage.replace('.', File.separatorChar)
         + File.separatorChar + className.name + "ServerImpl.java";

      System.out.println("  - writing server class to " + filename);

      output = new PrintWriter(new FileWriter(filename));

      writeCopyright(output, filename);
      output.println();
      output.println("package " + serverPackage + ";");

      // get imports
      imports.add(serverName(parentName));
      imports.add("armidale.api.context.clientserver.*");
      imports.add(clientServerpackage + "." + className.name + "ClientServerIds");
      imports.add("java.io.IOException");
      imports.add("armidale.api.context.clientserver.*");
      if (!isAbstract) {
        imports.add(interfacePackage + "." + className.name + "Factory");
      }
      imports.addAttributes(attributes);
      imports.addListAttributes(listAttributes);
      imports.addMethods(methods);
      imports.addInterfaces(interfaces);
      imports.addCallback(callback);

      if (callback != null) {
        imports.add(callback.qualifiedName());
        imports.add(callback.qualifiedName() + "List");
        imports.add("java.util.LinkedList");
      }

      imports.removeLocals(serverPackage);
      imports.writeImports(output);

      // write class declaration
      output.println();
      output.print("public ");
      if (isAbstract) {
        output.print("abstract ");
      }
      output.println("class " + className.name + "ServerImpl extends " + parentName.name
         + "ServerImpl implements " + className.name + "ClientServerIds {");

      // attributes
      boolean  headerDone  = false;
      attributeIterator = attributes.listIterator(0);
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();
        if (theAttribute.isReadable) {
          if (!headerDone) {
            headerDone = true;
            output.println();
            output.println("  // readable attributes");
            output.println("  //");
          }
          output.println(theAttribute.protectedDeclaration());
        }
      }

      // Callback object
      if (callback != null) {
        output.println();
        output.println("  // Callback object");
        output.println("  //");
        output.println();
        output.println(callback.callbackObjectDeclaration());
      }

      // constructor
      output.println();
      output.println("  // constructor");
      output.println("  //");
      output.println("  public " + className.name + "ServerImpl(ServerContext context) {");
      output.println("    super(context);");
      // attribute defaults
      defaultIterator = localDefaults.listIterator(0);
      while (defaultIterator.hasNext()) {
        theNewDefault = (NewDefault) defaultIterator.next();
        output.println("    set" + Strings.capitalize(theNewDefault.name) + "(" + theNewDefault.defaultName() + ");");
      }
      output.println("  }");

      // Class ID
      writeClassId(output);

      // get and set methods for attributes
      attributeIterator = attributes.listIterator(0);
      if (attributeIterator.hasNext()) {
        output.println();
        output.println("  // attribute set/get/is methods");
        output.println("  //");
      }
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();

        if (theAttribute.isReadable) {
          output.println();
          output.println(theAttribute.getSpec() + " {");
          output.println("    return " + theAttribute.name + ";");
          output.println("  }");
        }

        if (theAttribute.isWriteable) {
          output.println();
          output.println(theAttribute.setSpec() + " {");
          if (theAttribute.isReadable) {
            output.println("    this." + theAttribute.name + " = " + theAttribute.name + ";");
          }
          if (theAttribute.isFile) {
            output.println("    Message msg = new SetFileAttributeMessage(this.getId(), " + theAttribute.name + "Id, " + theAttribute.name + ");");
          } else {
            output.println("    Message msg = new SetAttributeMessage(this.getId(), " + theAttribute.name + "Id);");
            if (theAttribute.isArray) {
              output.println("    msg.writeInt(" + theAttribute.name + ".length);");
              output.println("    for (int i=0; i<" + theAttribute.name + ".length; i++) {");
              output.println("      msg.write" + Strings.capitalize(theAttribute.typeName.name) + "(" + theAttribute.name + "[i]);");
              output.println("    }");
            } else {
              if (theAttribute.isArmidale) {
                output.println("    msg.writeObjectId((MessagingObject)" + theAttribute.name + ");");
              } else {
                output.println("    msg.write" + Strings.capitalize(theAttribute.typeName.name) + "(" + theAttribute.name + ");");
              }
            }
          }
          output.println("    transport.writeMessage(msg);");
          output.println("  }");
        }

        if (theAttribute.hasAlternateName) {
          if (theAttribute.isReadable) {
            output.println();
            output.println(theAttribute.alternateGetSpec() + " {");
            output.println(theAttribute.alternateGetStatement());
            output.println("  }");
          }
          if (theAttribute.isWriteable) {
            output.println();
            output.println(theAttribute.alternateSetSpec() + " {");
            output.println(theAttribute.alternateSetStatement());
            output.println("  }");
          }
        }
      }

      // callback methods
      if (callback != null) {
        output.println();
        output.println("  // Callback");
        output.println("  //");
        output.println();
        output.println(callback.addCallbackSpec() + " {");
        output.println("    Message msg;");
        output.println("    " + callback.callbackListName() + ".add(callback);");
        output.println("    if (" + callback.callbackListName() + ".size() == 1) {");
        output.println("      msg = new EnableCallbacksMessage(this.getId());");
        output.println("      transport.writeMessage(msg);");
        output.println("    }");
        output.println("  }");
        output.println();
        output.println(callback.removeCallbackSpec() + " {");
        output.println("    Message msg;");
        output.println("    " + callback.callbackListName() + ".remove(callback);");
        output.println("    if (" + callback.callbackListName() + ".size() == 0) {");
        output.println("      msg = new DisableCallbacksMessage(this.getId());");
        output.println("      transport.writeMessage(msg);");
        output.println("    }");
        output.println("  }");
        output.println();
        output.println(callback.removeAllCallbacksSpec() + " {");
        output.println("    Message msg;");
        output.println("    " + callback.callbackListName() + ".clear();");
        output.println("    msg = new DisableCallbacksMessage(this.getId());");
        output.println("    transport.writeMessage(msg);");
        output.println("  }");
        output.println();
        output.println(callback.getCallbackListSpec() + " {");
        output.println("    return " + callback.callbackListName() + ";");
        output.println("  }");
      }

      // other methods
      methodIterator = methods.listIterator(0);
      if (methodIterator.hasNext()) {
        output.println();
        output.println("  // other methods");
        output.println("  //");
      }
      while (methodIterator.hasNext()) {
        theMethod = (Method) methodIterator.next();
        output.println();
        output.println(theMethod.spec() + " {");

        parameterIterator = theMethod.parameterList.listIterator(0);
        while (parameterIterator.hasNext()) {
          theParameter = (Parameter) parameterIterator.next();
          if (theParameter.setLocal) {
            output.println("    this." + theParameter.name + " = " + theParameter.name + ";");
          }
        }

        output.println("    Message msg = new MethodCallMessage(this.getId(), " + theMethod.idName() + ");");

        parameterIterator = theMethod.parameterList.listIterator(0);
        while (parameterIterator.hasNext()) {
          theParameter = (Parameter) parameterIterator.next();
          if (theParameter.isArray) {
            output.println("    msg.writeInt(" + theParameter.name + ".length);");
            output.println("    for (int i=0; i<" + theParameter.name + ".length; i++) {");
            if (theParameter.isArmidale) {
              output.println("      msg.writeObjectId((MessagingObject)" + theParameter.name + "[i]);");
            } else {
              output.println("      msg.write" + Strings.capitalize(theParameter.typeName.name) + "(" + theParameter.name + "[i]);");
            }
            output.println("    }");
          } else {
            if (theParameter.isArmidale) {
              output.println("    msg.writeObjectId((MessagingObject)" + theParameter.name + ");");
            } else {
              output.println("    msg.write" + Strings.capitalize(theParameter.typeName.name) + "(" + theParameter.name + ");");
            }
          }
        }
        output.println("    transport.writeMessage(msg);");
        output.println("  }");
      }

      // Callback message handler
      if (callback != null) {
        output.println();
        output.println("  // transport");
        output.println("  //");
        output.println("");
        output.println("  public void handleCallback(int callbackMethodId, Message message) {");

        // Callback parameters
        callbackMethodIterator = callback.methodList.listIterator(0);
        while (callbackMethodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) callbackMethodIterator.next();
          parameterIterator = theCallbackMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            output.println("    " + theParameter.typeDeclaration() + " " + compositeName(theCallbackMethod, theParameter) + ";");
          }
        }
        output.println();
        output.println("    switch (callbackMethodId) {");
        callbackMethodIterator = callback.methodList.listIterator(0);
        while (callbackMethodIterator.hasNext()) {
          theCallbackMethod = (CallbackMethod) callbackMethodIterator.next();
          output.println("      case " + theCallbackMethod.idName() + ":");
          parameterIterator = theCallbackMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            if (theParameter.isArray) {
              output.println("        " + compositeName(theCallbackMethod, theParameter) + " = new " + theParameter.typeName.name + "[message.readInt()];");
              output.println("        for (int i=0; i < " + compositeName(theCallbackMethod, theParameter) + ".length; i++) {");
              output.print("          " + compositeName(theCallbackMethod, theParameter) + "[i] = ");
              if (theParameter.isArmidale) {
                output.println("(" + theParameter.typeName.name + ")objectRegistry.findObject(message.readObjectId());");
              } else {
                output.println("message.read" + Strings.capitalize(theParameter.typeName.name) + "();");
              }
              output.println("        }");
            } else {
              output.print("        " + compositeName(theCallbackMethod, theParameter) + " = ");
              if (theParameter.isArmidale) {
                output.println("(" + theParameter.typeName.name + ")objectRegistry.findObject(message.readObjectId());");
              } else {
                output.println("message.read" + Strings.capitalize(theParameter.typeName.name) + "();");
              }
            }
          }
          output.print("        " + Strings.lowercase(callback.callbackListName()) + "." + Strings.lowercase(theCallbackMethod.name) + "(this");
          parameterIterator = theCallbackMethod.parameterList.listIterator(0);
          while (parameterIterator.hasNext()) {
            theParameter = (Parameter) parameterIterator.next();
            output.print(", " + compositeName(theCallbackMethod, theParameter));
          }
          output.println(");");
          output.println("        break;");
        }
        output.println("    }");
        output.println("  }");
      }

      // done
      output.println();
      output.println("}");
      output.flush();
      output.close();
    } catch (IOException e) {
      throw new UncheckedException("writeServerClass", e);
    }

  }


  ////////////////////////////////
  //
  // write PLATFORM
  //
  ////////////////////////////////

  private void writePlatformClass(String sourceRoot, String platform) {
    PrintWriter     output;
    Attribute       theAttribute;
    ListAttribute   theListAttribute;
    CallbackMethod  theCallbackMethod;
    Method          theMethod;
    Interface       theInterface;
    Parameter       theParameter;
    NewDefault      theNewDefault;
    int             id                 = 0;
    String          methodSpec;
    String          filename;
    boolean         someOutput         = false;
    ImportList      imports            = new ImportList();

    try {
      filename = sourceRoot
         + File.separatorChar
         + platformPackage.replace('.', File.separatorChar)
         + File.separatorChar
         + platform
         + File.separatorChar
         + className.name
         + "Impl.java";

      System.out.println("  - writing " + platform + " platform class to " + filename);

      userCode = new UserCode(filename);
      output = new PrintWriter(new FileWriter(filename));

      writeCopyright(output, filename);
      output.println();
      output.println("package " + platformPackage + "." + platform + ";");

      // get imports
      imports.add(platformName(parentName, platform));
      imports.add(className);
      imports.add("armidale.api.context.*");

      imports.addAttributes(attributes);
      imports.addListAttributes(listAttributes);
      imports.addMethods(methods);
      imports.addInterfaces(interfaces);

      if (callback != null) {
        imports.add(callback.qualifiedName());
//        imports.add(callback.qualifiedName() + "Adapter");
        imports.add(callback.qualifiedName() + "List");
        imports.add("java.util.LinkedList");
      }

      imports.removeLocals(platformPackage);
      imports.writeImports(output);

      userCode.writeUserCode(output, "  ", "imports");

      // write class declaration
      output.println();
      output.print("public ");
      if (isAbstract) {
        output.print("abstract ");
      }
      output.print("class " + className.name + "Impl extends " + parentName.name
         + "Impl implements " + className.name);
      interfaceIterator = interfaces.listIterator(0);
      while (interfaceIterator.hasNext()) {
        theInterface = (Interface) interfaceIterator.next();
        output.print(", " + theInterface.name);
      }
      output.println(" {");

      output.println();
      userCode.writeUserCode(output, "  ", "declarations");

      // Callback objects
      if (callback != null) {
        output.println();
        output.println("  // Callback object");
        output.println("  //");
        output.println();
        output.println(callback.callbackObjectDeclaration());
      }

      // constructor
      output.println();
      output.println("  public " + className.name + "Impl() {");
      userCode.writeUserCode(output, "    ", "constructor()");
      // attributes defaults
      attributeIterator = attributes.listIterator(0);
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();
        if (theAttribute.defaultValue != null) {
          output.print("    set" + Strings.capitalize(theAttribute.name) + "(");
          output.println(theAttribute.defaultName() + ");");
          someOutput = true;
        }
      }
      // local defaults
      defaultIterator = localDefaults.listIterator(0);
      while (defaultIterator.hasNext()) {
        theNewDefault = (NewDefault) defaultIterator.next();
        output.print("    set" + Strings.capitalize(theNewDefault.name) + "(");
        output.println(theNewDefault.defaultName() + ");");
      }

      userCode.writeUserCode(output, "    ", "constructor() - end");
      output.println("  }");

      // setPeer
      output.println();
      output.println("  protected void setPeer() {");
      userCode.writeUserCode(output, "    ", "setPeer()");
      output.println("  }");

      // callback methods
      if (callback != null) {
        output.println();
        output.println("  // Callbacks");
        output.println("  //");
        output.println();
        output.println(callback.addCallbackSpec() + " {");
        output.println("    " + callback.callbackListName() + ".add(callback);");
        userCode.writeUserCode(output, "    ", callback.addCallbackSpec());
        output.println("  }");
        output.println();
        output.println(callback.removeCallbackSpec() + " {");
        output.println("    " + callback.callbackListName() + ".remove(callback);");
        userCode.writeUserCode(output, "    ", callback.removeCallbackSpec());
        output.println("  }");
        output.println();
        output.println(callback.removeAllCallbacksSpec() + " {");
        output.println("    " + callback.callbackListName() + ".clear();");
        userCode.writeUserCode(output, "    ", callback.removeAllCallbacksSpec());
        output.println("  }");
        output.println();
        output.println(callback.getCallbackListSpec() + " {");
        userCode.writeUserCode(output, "    ", callback.getCallbackListSpec());
        output.println("    return " + callback.callbackListName() + ";");
        output.println("  }");
      }

      // get and set methods for attributes
      attributeIterator = attributes.listIterator(0);
      if (attributeIterator.hasNext()) {
        output.println();
        output.println("  // set/get/is methods");
        output.println("  //");
      }
      while (attributeIterator.hasNext()) {
        theAttribute = (Attribute) attributeIterator.next();

        if (theAttribute.isReadable) {
          output.println();
          output.println(theAttribute.getSpec() + " {");
          userCode.writeUserCode(output, "    ", theAttribute.getSpec());
          output.println("  }");
          output.println();
        }
        if (theAttribute.isWriteable) {
          output.println(theAttribute.setSpec() + " {");
          userCode.writeUserCode(output, "    ", theAttribute.setSpec());
          output.println("  }");
        }
        if (theAttribute.hasAlternateName) {
          if (theAttribute.isReadable) {
            output.println();
            output.println(theAttribute.alternateGetSpec() + " {");
            output.println(theAttribute.alternateGetStatement());
            output.println("  }");
          }
          if (theAttribute.isWriteable) {
            output.println();
            output.println(theAttribute.alternateSetSpec() + " {");
            output.println(theAttribute.alternateSetStatement());
            output.println("  }");
          }
        }
      }

      // other methods
      methodIterator = methods.listIterator(0);
      if (methodIterator.hasNext()) {
        output.println();
        output.println("  // other methods");
        output.println("  //");
      }
      while (methodIterator.hasNext()) {
        theMethod = (Method) methodIterator.next();
        output.println();
        output.println(theMethod.spec() + " {");
        userCode.writeUserCode(output, "    ", theMethod.spec());
        output.println("  }");
        output.println();
      }

      // Unused User Code
      if (!noRestore) {
        userCode.writeUnusedUserCode(output);
      }

      // done
      output.println("}");
      output.flush();
      output.close();
    } catch (IOException e) {
      throw new UncheckedException("writePlatformClass", e);
    }

  }



  private void writeClassId(PrintWriter output) {
    if (!isAbstract) {
      output.println();
      output.println("  // Class ID");
      output.println("  //");
      output.println("  public int getClassId() {");
      output.println("    return " + className.name + "Factory.CLASS_ID;");
      output.println("  }");
    }
  }


  private String classIdDeclaration() {
    return "  public static final int CLASS_ID = " + classId + ";";
  }


  private void writeNonDefaultCreate(PrintWriter output) {
    ListIterator  iterator;
    Attribute     theAttribute;

    if (hasNonDefaultAttributes()) {
      output.print("  public static " + className.name + " create(Context context");
      iterator = attributes.listIterator(0);
      while (iterator.hasNext()) {
        theAttribute = (Attribute) iterator.next();
        if (theAttribute.defaultValue == null) {
          output.print(", " + theAttribute.declaration());
        }
      }
      output.println(") {");
      output.println("    " + className.name + " result = create(context);");
      iterator = attributes.listIterator(0);
      while (iterator.hasNext()) {
        theAttribute = (Attribute) iterator.next();
        if (theAttribute.defaultValue == null) {
          output.println("    result.set" + Strings.capitalize(theAttribute.name) + "(" + Strings.lowercase(theAttribute.name) + ");");
        }
      }
      output.println("    return result;");
      output.println("  }");
    }
  }


  private boolean hasNonDefaultAttributes() {
    ListIterator  iterator;
    Attribute     theAttribute;
    iterator = attributes.listIterator(0);
    while (iterator.hasNext()) {
      theAttribute = (Attribute) iterator.next();
      if (theAttribute.defaultValue == null) {
        return true;
      }
    }
    return false;
  }


  private String clientName(QualifiedName qualifiedName) {
    return qualifiedName.packageName + ".impl.clientserver." + qualifiedName.name + "ClientImpl";
  }


  private String serverName(QualifiedName qualifiedName) {
    return qualifiedName.packageName + ".impl.clientserver." + qualifiedName.name + "ServerImpl";
  }


  private String platformName(QualifiedName qualifiedName, String platform) {
    return qualifiedName.packageName + ".impl.platform." + platform + "." + qualifiedName.name + "Impl";
  }


  private String clientServerIdsName(QualifiedName qualifiedName) {
    //System.out.println("clientServerIdsname: " + qualifiedName.packageName + ".impl.clientserver." + qualifiedName.name + "ClientServerIds");
    return qualifiedName.packageName + ".impl.clientserver." + qualifiedName.name + "ClientServerIds";
  }

}


