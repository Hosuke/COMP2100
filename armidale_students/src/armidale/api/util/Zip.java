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
package armidale.api.util;

import armidale.api.UncheckedException;
import armidale.api.io.DirectoryException;
import java.io.*;
import java.util.zip.*;

public class Zip {

  public final static   int  SILENT       = 0;
  public final static   int  SHOW_DOTS    = 1;
  public final static   int  VERBOSE      = 2;

  private static        int  dotCount     = 0;
  private final static  int  LINE_LENGTH  = 75;


  public static void unzip(File zipFile, File destination, String name, int feedback) {
    final  int                   BUFFER          = 2048;
           BufferedOutputStream  outputStream    = null;
           ZipInputStream        zipInputStream;
           ZipEntry              zipEntry;

    try {
      zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
    } catch (IOException e) {
      throw new UncheckedException("Cannot open zip file '" + zipFile + "'", e);
    }
    try {
      while ((zipEntry = zipInputStream.getNextEntry()) != null) {
        if (zipEntry.getName().startsWith(name)) {
          int   count;
          byte  data[]     = new byte[BUFFER];
          File  theFile;
          theFile = new File(destination.getPath() + File.separator + zipEntry.getName());
          if (zipEntry.isDirectory()) {
            if (!theFile.exists()) {
              if (!theFile.mkdirs()) {
                throw new DirectoryException("cannot create '" + theFile.getName() + "'");
              }
              switch (feedback) {
                case SHOW_DOTS:
                  showDot("+");
                  break;
                case VERBOSE:
                  System.out.println("created directory: " + theFile);
              }
            }
          } else {
            outputStream = new BufferedOutputStream(new FileOutputStream(theFile), BUFFER);
            while ((count = zipInputStream.read(data, 0, BUFFER)) != -1) {
              outputStream.write(data, 0, count);
            }
            outputStream.flush();
            outputStream.close();
            switch (feedback) {
              case SHOW_DOTS:
                showDot(".");
                break;
              case VERBOSE:
                System.out.println("Extracted: " + theFile.getPath());
            }
          }
        }
      }
      zipInputStream.close();
    } catch (Exception e) {
      throw new UncheckedException(e);
    }
    if (feedback == SHOW_DOTS) {
      System.out.println();
    }
  }


  private static void showDot(String dot) {
    System.out.print(dot);
    dotCount++;
    if (dotCount > LINE_LENGTH) {
      System.out.println();
      dotCount = 0;
    }
  }

}

