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

  import java.util.LinkedList;
  import java.util.ListIterator;
  
public class Strings {

  public static boolean isVowel(char c) {
    switch (Character.toLowerCase(c)) {
      case 'a' | 'e' | 'i' | 'o' | 'u':
        return true;
      default:
        return false;
    }
  }


  public static boolean isEmpty(String str) {
    if (str == null) {
      return true;
    }
    if (str.equals("")) {
      return true;
    }
    return false;
  }


  public static String capitalize(String s) {
    if (s.length() > 0) {
      return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    } else {
      return "";
    }
  }


  public static String lowercase(String s) {
    if (s.length() > 0) {
      return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    } else {
      return "";
    }
  }


  public static String name(String s) {
    int  idx  = s.lastIndexOf('.');
    if (idx < 0) {
      return s;
    } else {
      return s.substring(idx + 1);
    }
  }


  public static String replace(String source, String oldString, String newString) {
    String  result  = source;
    int     index;
    index = result.indexOf(oldString);
    while (index >= 0) {
      result = result.substring(0, index) + newString + result.substring(index + oldString.length());
      index = index + newString.length();
      index = result.indexOf(oldString, index);
    }
    return result;
  }


  public static String concat(String a, String b, String separator) {
    if (a.length() > 0 && b.length() > 0) {
      return a + separator + b;
    } else {
      return a + b;
    }
  }

  public static String concat(String a, String b, String c, String separator) {
    return concat(a, concat(b, c, separator), separator);
  }
  
  public static boolean isQualifiedName(String str) {
    if (str==null || str.length()==0) {
      return false;
    }
    if (!Character.isJavaIdentifierStart(str.charAt(0))) {
      return false;
    }
    for (int i=1; i<str.length(); i++) {
      if (str.charAt(i)=='.') {
        i++;
        if (    i < str.length() 
             && Character.isJavaIdentifierStart(str.charAt(i))) 
        {
          i++;
        } else {
          return false;
        }
      } else {
        if (!Character.isJavaIdentifierPart(str.charAt(i))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean charIn(String str, char c) {
    for (int i=0; i<str.length(); i++) {
      if (str.charAt(i) == c) {
        return true;
      }
    }
    return false;
  }
  
  public static String[] stringArray(LinkedList list) {
    String[] stringArray = new String[list.size()];
    ListIterator iterator = list.listIterator(0);
    int i=0;
    while (iterator.hasNext()) {
      stringArray[i] = iterator.next().toString();
      i++;
    }
    return stringArray;
  }
  
  public static String[] split(String str, String separators) {
    LinkedList result = new LinkedList();
    int i = 0;
    int j;
    while (i < str.length()) {
      while (i<str.length() && charIn(separators, str.charAt(i))) {
        i++;
      }
      j = i;
      while (j<str.length() && !charIn(separators, str.charAt(j))) {
        j++;
      }
      if (j > i) {
        result.add(str.substring(i, j));
      }
      i = j+1;
    }
    
    return stringArray(result);
  }
  
}

