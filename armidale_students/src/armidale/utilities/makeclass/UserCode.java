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

import armidale.api.UncheckedException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

import java.util.LinkedList;
import java.util.ListIterator;

class UserCode {

  private final static  String      userCodeStartSig    = "//--ARM-- ";
  private final static  String      userCodeEndSig      = "//--ARM-- ";

  private               LinkedList  userCodeRecordList;


  public UserCode(String fileName) {
    BufferedReader  input;
    String          line;
    String          name;
    int             idx;
    UserCodeRecord  codeRecord;

    userCodeRecordList = new LinkedList();
    try {
      input = new BufferedReader(new FileReader(fileName));
    } catch (Throwable t) {
      return;
    }
    try {
      while (true) {
        line = input.readLine();
        if (line == null) {
          input.close();
          return;
        }
        idx = line.indexOf(userCodeStartSig);
        if (idx != -1) {
          name = line.substring(idx + userCodeStartSig.length()).trim();
          codeRecord = new UserCodeRecord(name);
          idx = -1;
          while (idx == -1) {
            line = input.readLine();
            if (line == null) {
              input.close();
              return;
            }
            idx = line.indexOf(userCodeEndSig);
            //+name);
            if (idx == -1) {
              codeRecord.code.add(line);
            }
          }
          userCodeRecordList.add(codeRecord);
        }
      }
    } catch (Exception e) {
      throw new UncheckedException("cannot extract user code from '" + fileName + "'", e);
    }
  }


  public void writeUserCode(PrintWriter output, String indent, String name) {
    UserCodeRecord  codeRecord;
    ListIterator    iterator      = userCodeRecordList.listIterator(0);
    ListIterator    codeIterator;
    output.println(indent + userCodeStartSig + name.trim());
    while (iterator.hasNext()) {
      codeRecord = (UserCodeRecord) iterator.next();
      if (name.trim().equals(codeRecord.name)) {
        codeRecord.used = true;
        codeIterator = codeRecord.code.listIterator(0);
        while (codeIterator.hasNext()) {
          output.println((String) codeIterator.next());
        }
      }
    }
    output.println(indent + userCodeEndSig + name.trim());
  }


  public void writeUnusedUserCode(PrintWriter output) {
    UserCodeRecord  codeRecord;
    ListIterator    iterator       = userCodeRecordList.listIterator(0);
    ListIterator    codeIterator;
    boolean         headerPrinted  = false;
    while (iterator.hasNext()) {
      codeRecord = (UserCodeRecord) iterator.next();
      if (!codeRecord.used) {
        if (!headerPrinted) {
          headerPrinted = true;
          output.println();
          output.println("  // The following code was recovered from the previous version of this file");
          output.println("  // but was not used in the generation of this version");
          output.println();
        }
        output.println("  " + userCodeStartSig + codeRecord.name);
        codeIterator = codeRecord.code.listIterator(0);
        while (codeIterator.hasNext()) {
          output.println("  // " + (String) codeIterator.next());
        }
        output.println("  " + userCodeEndSig + codeRecord.name);
        output.println();
      }
    }
  }
}

