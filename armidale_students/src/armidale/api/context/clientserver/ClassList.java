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
 
package armidale.api.context.clientserver;

import armidale.api.Environment;
import armidale.api.UncheckedException;
import armidale.api.InstallationException;
import armidale.api.context.Context;
import armidale.api.io.Debug;
import armidale.api.io.StreamTextOutput;
import armidale.api.util.Strings;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassList {

  public final static  int               LAST_STANDARD     = 200;

  private static       Class[]           constructorTypes  = new Class[1];

  private static       StreamTextOutput  textOutput        = new StreamTextOutput(System.out);

  private static       CompositeArray    classes           = new CompositeArray(LAST_STANDARD + 10);
  private static       String            jarPath           = "";

  private static       Debug             debug             = new Debug();
  // allow 200 standard classes for now , event though 2**16-1 are
  // supported

  private static       Class             factoryClass;


  public static void loadClassList(String theJarPath) {
//System.out.println("loadClassList '" + theJarPath + "'");
    constructorTypes[0] = Context.class;
    try {
      factoryClass = Class.forName("armidale.api.ArmidaleObjectFactory");
    } catch (ClassNotFoundException e) {
      throw new UncheckedException(e);
    }
    if (theJarPath.length() > 0) {
      jarPath = theJarPath;
      loadClasses();
    }
    showList();
  }


  public static void loadClassList() {
    loadClassList(Environment.javaClassPath());
  }


  public static synchronized void deleteAll() {
    classes.clear();
  }


  public static synchronized int addClass(Class theClass) {
    int          classId      = -1;
    ClassRecord  classRecord;
    int          index;

    debug.message("ClassList", "trying to load class '" + theClass.toString() + "'");
    if (isFactory(theClass)) {
      classRecord = new ClassRecord(theClass);
      classId = classRecord.getClassId();
      classes.set(classId, classRecord);
    }
    return classId;
  }


  public static MessagingObject createObject(int classId, int objectId, ClientContext context) {
    MessagingObject  result;
    ClassRecord      theClass  = getClassRecord(classId);

    Object[]         parList   = new Object[1];
    parList[0] = (Object) context;

    debug.message("ClassList", "creating newObject classId=" + classId + " objectId=" + objectId, Debug.COURSE);

    if (theClass != null) {
      try {
        result = (MessagingObject) theClass.getClassFactory().invoke(null, parList);
        result.setId(objectId);
        return result;
      } catch (Throwable e) {
        textOutput.showError("newObject", e);
        return null;
      }
    } else {
      context.handleClassNotFound(classId);
    }
    return null;
  }


  public static void showList() {
    classes.show("ClassList");
  }


  public static boolean classAvailable(String name) {
    return false;
  }


  private static ClassRecord getClassRecord(int id) {
    return (ClassRecord) classes.get(id);
  }


  private static boolean isFactory(Class theClass) {
    return (factoryClass.isAssignableFrom(theClass) && (!Modifier.isAbstract(theClass.getModifiers())));
  }


  private static void loadClasses() {
    String    jarName;
    String[]  jarList           = Strings.split(jarPath, File.pathSeparator);
    File      file;
    JarFile   jarFile;
    JarEntry  jarEntry;
    String    className;
    Class     theClass;
    int       classId;
    boolean   foundArmidaleJar  = false;

    deleteAll();
    debug.message("ClassList", "loading jars in '" + jarPath + "'");
    if (jarList == null) {
      return;
    }
    for (int j = 0; j < jarList.length; j++) {
      jarName = jarList[j];
      if (jarName.endsWith(".jar")) {
        try {
          debug.message("ClassList", "loading " + java.io.File.separator + jarName);
          if (    jarName.equals(Environment.ARMIDALE_JAR)
               || jarName.endsWith("/" + Environment.ARMIDALE_JAR)
               || jarName.endsWith("\\" + Environment.ARMIDALE_JAR)
               || jarName.endsWith(File.separator + Environment.ARMIDALE_JAR)  ) {
            foundArmidaleJar = true;
          }
          file = new File(jarName);
          jarFile = new JarFile(file);
          for (Enumeration e = jarFile.entries(); e.hasMoreElements(); ) {
            jarEntry = (JarEntry) e.nextElement();
            className = jarEntry.getName();
            if (className.endsWith(".class")) {
              className = className.substring(0, className.length() - 6).replace('/', '.');
              try {
                theClass = Class.forName(className);
                classId = addClass(theClass);
                if (classId != -1) {
                  debug.message("ClassList", "      - loaded class '" + className + "', id=" + classId);
                }
              } catch (ClassNotFoundException e2) {
                debug.message("ClassList", "      - error: cannot load class '" + className + "'");
              }
            }
          }
        } catch (java.io.IOException e) {
          // ignore bad jar files
          //e.printStackTrace();
        }
      }
    }
    if (!foundArmidaleJar) {
      throw new InstallationException("Unable to find required jar file '" + Environment.ARMIDALE_JAR + "'");
    }
  }


  private static String removeParent(String str) {
    int  last   = str.lastIndexOf('.');
    int  first  = str.lastIndexOf('.', last - 1);
    if ((last >= -1) && (first >= -1)) {
      return str.substring(0, first + 1) + str.substring(last + 1);
    } else {
      return str;
    }
  }


  private static int findClassId(Class theClass) {
    Field   getClassIdField;
    int     result;

    String  fieldName        = new String(removeParent(theClass.getName()).toUpperCase().replace('.', '_'));
    if (fieldName.endsWith("IMPL")) {
      fieldName = fieldName.substring(0, fieldName.length() - 4);
    }
    fieldName = fieldName + "_CLASS_ID";

    fieldName = "CLASS_ID";
    try {
      getClassIdField = theClass.getField(fieldName);
      result = getClassIdField.getInt(null);
    } catch (Exception e1) {
      throw new UncheckedException(e1);
    }
    return result;
  }


  private static class ClassRecord {

    private  Class   theClass;
    private  int     classId;
    private  Method  classFactory;


    public ClassRecord(Class theClass) {
      this.theClass = theClass;
      this.classId = findClassId(theClass);

      try {
        this.classFactory = theClass.getMethod("create", constructorTypes);
      } catch (NoSuchMethodException e) {
        throw new UncheckedException("class=" + theClass.getName() + ", Id=" + classId, e);
      }
    }


    public int getClassId() {
      return classId;
    }


    public Class getTheClass() {
      return theClass;
    }


    public Method getClassFactory() {
      return classFactory;
    }


    public String toString() {
      return "id=" + classId + ", class=" + theClass.toString();
    }
  }

  static {
    loadClassList();
  }

}

