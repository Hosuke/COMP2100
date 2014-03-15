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
 
package armidale.api;

import java.util.LinkedList;

public class ClassInformation {

  private  String      name             = "";
  private  String      author           = "";
  private  String      version          = "";
  private  String      homePageUrl      = "";
  private  LinkedList  requiredClasses  = new LinkedList();


  public ClassInformation(Class theClass) {
    this.name = theClass.getName();
  }


  public ClassInformation(String name) {
    this.name = name;
  }


  public ClassInformation(String name, String version, String author, String homePageUrl) {
    this.name = name;
    this.author = author;
    this.version = version;
    this.homePageUrl = homePageUrl;
  }


  public void setAuthor(String author) {
    this.author = author;
  }


  public void setVersion(String version) {
    this.version = version;
  }


  public void setHomePageUrl(String homePageUrl) {
    this.homePageUrl = homePageUrl;
  }


  public String getName() {
    return name;
  }


  public String getAuthor() {
    return author;
  }


  public String getVersion() {
    return version;
  }


  public String getHomePageUrl() {
    return homePageUrl;
  }


  public ClassInformation[] getRequiredClasses() {
    Object[]            temp    = requiredClasses.toArray();
    ClassInformation[]  result  = new ClassInformation[temp.length];
    for (int i = 0; i < temp.length; i++) {
      result[i] = (ClassInformation) temp[i];
    }
    return result;
  }


  public void addRequiredClass(ClassInformation classInformation) {
    requiredClasses.add(classInformation);
  }


  public String toString() {
    String  result  = getName();
    if (version.length() > 0) {
      result = result + " v" + getVersion();
    }
    if (author.length() > 0) {
      result = result + " by " + getAuthor();
    }
    if (homePageUrl.length() > 0) {
      result = result + " [more info: " + homePageUrl + "]";
    }
    return result;
  }

  public static String toString(ClassInformation[] classes) {
    String result = "";
    for (int i = 0; i < classes.length; i++) {
      result = result + "    - " + classes[i].toString() + "\n";
    }
    return result;
  }
}

