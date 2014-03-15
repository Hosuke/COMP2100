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

import java.io.File;
import armidale.api.util.Preferences;

public class Environment {

  public final static  String  ARMIDALE_JAR     = "armidale.jar";
  public final static  String  FILESYSTEM_NAME  = "filesystem";


  public static String javaClassPath() {
    return System.getProperty("java.class.path", ".");
  }


  public static String userHomePath() {
    return System.getProperty("user.home", ".");
  }


  public static String armidalePath() {
    return userHomePath() + File.separator + ".armidale";
  }


  public static String armidaleFileSystemPath() {
    return armidalePath() + File.separator + FILESYSTEM_NAME;
  }


  public static int defaultPort() {
    return armidalePreferences().getIntPreference("armidale.default_port", 3000);
  }


  public static String javaVersion() {
    return System.getProperty("java.vm.version", "unknown");
  }


  public static String hostOperatingSystem() {
    return System.getProperty("os.name", "unknown") + " " + System.getProperty("os.version", "unknown");
  }

  
  private static Preferences theArmidalePreferences;
  
  public static Preferences armidalePreferences() {
    return theArmidalePreferences;
  }
  

  public static void checkJdkVersion() {
    final  double  REQUIRED_VERSION  = 1.3;
           String  version;
           double  versionNo;
    try {
      version   = System.getProperty("java.specification.version");
      versionNo = Double.parseDouble(version);
      if (versionNo < REQUIRED_VERSION) {
        System.out.println("Sorry, armidale requires Java2 version " + REQUIRED_VERSION + " or later.");
        System.out.println("You are currently running Java " + javaVersion() + ".");
        System.exit(0);
      }
    } catch (Throwable t) {
      System.out.println("Unable to check JDK version.");
      System.exit(0);
    }
  }

  
  static {
    checkJdkVersion();
    theArmidalePreferences = new Preferences(armidalePath() + File.separator + ".preferences");
  }

}

