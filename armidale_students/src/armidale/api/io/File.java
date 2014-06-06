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
 
package armidale.api.io;

import armidale.api.UncheckedException;
import armidale.api.Environment;
import armidale.api.structures.ByteArray;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class File {

  private final static  String        armidaleRoot      = Environment.armidaleFileSystemPath() + java.io.File.separator;
  private final static  String        localRoot = System.getProperty("user.home");
  
  private               String        filename;
  private               java.io.File  javaFile;
  private static Boolean isLocalRoot = false;

  public File(String filename) {
    this.filename = filename;
  
    if (filename.startsWith("/"))
    {
    	// the local computer's file system
    	javaFile = new java.io.File(localRoot + filename);
    	isLocalRoot = true;
    }
    else
    {
    	// the '~/.armidale/filesystem' directory
    	javaFile = new java.io.File(armidaleRoot + filename);
    	isLocalRoot = false;
    }
  }


  public static byte[] createBytes(String filename) {
    File  theFile  = new File(filename);
    return theFile.readBytes();
  }


  public static ByteArray createByteArray(String filename) {
    return new ByteArray(createBytes(filename));
  }


  public void setLastModified(int lastModified) {
    javaFile.setLastModified((long) lastModified * 1000);
  }

//changed
  public static String getRoot() {
	  if (isLocalRoot)
		  return localRoot;
	  else
		  return armidaleRoot;
  }
  
  public String getName() {
    return filename;
  }

// changed, may have bugs
  public String getPath() {
	  if (isLocalRoot)
		  return localRoot + filename;
	  else
		  return armidaleRoot + filename;
  }


  public boolean isCurrent(int lastModified) {
    if (exists()) {
      return lastModified() == lastModified;
    } else {
      return false;
    }
  }


  public boolean exists() {
    return javaFile.exists();
  }


  public int lastModified() {
    return (int) (javaFile.lastModified() / 1000);
  }


  public byte[] readBytes() {
    byte[]  bytes  = new byte[(int) javaFile.length()];

    try {
      FileInputStream  input  = new FileInputStream(javaFile);
      input.read(bytes, 0, bytes.length);
      input.close();
      return bytes;
    }
    catch (IOException e) {
      throw new UncheckedException(e);
    }
  }


  public ByteArray readByteArray() { 
    return new ByteArray(readBytes());
  }


  public void writeBytes(byte[] fileData) {
    try {
      if (!javaFile.getParentFile().exists()) {
        if (!javaFile.getParentFile().mkdirs()) {
          throw new DirectoryException("cannot create '" + javaFile.getName() + "'");
        }
      }
      FileOutputStream  output  = new FileOutputStream(javaFile);
      output.write(fileData);
      output.close();
    }
    catch (IOException e) {
      throw new UncheckedException(e);
    }
  }


  public void writeByteArray(ByteArray fileData) {
    writeBytes(fileData.getBytes());
  }
}

