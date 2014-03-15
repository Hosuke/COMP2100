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
 
package armidale.utilities.install;

import armidale.api.Environment;
import armidale.api.ProductInfo;
import armidale.api.util.Zip;

import java.io.*;

public class UserInstaller {

  public static void main(String args[]) {
    install();
  }


  private static void error(String message, Throwable t) {
    System.out.println("ERROR: " + message);
    System.out.println("     : " + t);
    System.exit(0);
  }


  private static void install() {

    System.out.println();
    System.out.println(ProductInfo.copyrightTitle());

    File    armidaleDir  = new File(Environment.armidalePath());
    String  jarName      = Environment.javaClassPath();
    try {
      System.out.println("   - installing '" + jarName + "'");
      System.out.println("   - unpacking armidale jars");
      Zip.unzip(new File(jarName), new File("."), "jars", Zip.SILENT);
      if (!armidaleDir.exists()) {
        System.out.println("   - creating " + Environment.armidalePath());
        armidaleDir.mkdirs();
      }
      System.out.println("   - unpacking armidale file system to " + Environment.armidalePath());
      Zip.unzip(new File(jarName), new File(Environment.armidalePath()), "filesystem", Zip.SILENT);
    } catch (Throwable t) {
      error("unable to unpack archive", t);
    }

    try {
      if (!Environment.armidalePreferences().nameExists("armidale.launcher")) {
        System.out.println("   - adding some initial launcher bookmarks");
        Environment.armidalePreferences().setPreference("armidale.launcher.urls.bm0", "arm://armidale.anu.edu.au");
        Environment.armidalePreferences().setPreference("armidale.launcher.urls.bm1", "arm://armidale.anu.edu.au/armidale.test.guitest.Main");
        Environment.armidalePreferences().setPreference("armidale.launcher.urls.bm2", "arm://armidale.anu.edu.au/armidale.test.ArmidaleImage");
        Environment.armidalePreferences().setPreference("armidale.launcher.urls.bm3", "arm://armidale.anu.edu.au/armidale.test.SimpleApp");
        Environment.armidalePreferences().setPreference("armidale.launcher.urls.bm4", "armidale.test.guitest.Main");
        Environment.armidalePreferences().setPreference("armidale.launcher.urls.bm5", "armidale.test.SimpleApp");
        Environment.armidalePreferences().save();
      }
    } catch (Throwable t) {
      error("unable to set preferences", t);
    }

    System.out.println("   - armidale user system installed.");
    System.out.println();
    System.out.println("use 'java -jar jars/armidale.jar' to start the armidale launcher.");
    System.out.println();
  }

}

