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

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class Preferences {

  private  LinkedList  preferences          = new LinkedList();

  private  String      preferencesFilename;


  public Preferences(String preferencesFilename) {
    this.preferencesFilename = preferencesFilename;
    load();
  }


  public void setPreference(String name, String value) {
    Preference  preference  = findPreference(name);
    if (preference != null) {
      preference.setValue(value);
    } else {
      preferences.add(new Preference(name, value));
    }
  }


  public void setIntPreference(String name, int value) {
    Preference  preference  = findPreference(name);
    if (preference != null) {
      preference.setIntValue(value);
    } else {
      preferences.add(new Preference(name, value));
    }
  }


  public void setBooleanPreference(String name, boolean value) {
    Preference  preference  = findPreference(name);
    if (preference != null) {
      preference.setBooleanValue(value);
    } else {
      preferences.add(new Preference(name, value));
    }
  }


  public String[] getPreferenceNames() {
    String        preferenceName;
    Preference    preference;
    LinkedList    result          = new LinkedList();
    ListIterator  iterator        = preferences.listIterator(0);
    while (iterator.hasNext()) {
      preference = (Preference) iterator.next();
      preferenceName = preference.getName();
      result.add(preferenceName);
    }
    return Strings.stringArray(result);
  }


  public String[] getPreferenceNames(String root) {
    String        preferenceName;
    Preference    preference;
    LinkedList    result          = new LinkedList();
    ListIterator  iterator        = preferences.listIterator(0);
    while (iterator.hasNext()) {
      preference = (Preference) iterator.next();
      preferenceName = preference.getName();
      if (preferenceName.startsWith(root)) {
        result.add(preferenceName);
      }
    }
    return Strings.stringArray(result);
  }


  public String getPreference(String name, String defaultValue) {
    Preference  preference  = findPreference(name);
    if (preference != null) {
      return preference.getValue();
    } else {
      return defaultValue;
    }
  }


  public int getIntPreference(String name, int defaultValue) {
    Preference  preference  = findPreference(name);
    if (preference != null) {
      return preference.getIntValue();
    } else {
      return defaultValue;
    }
  }


  public boolean getBooleanPreference(String name, boolean defaultValue) {
    Preference  preference  = findPreference(name);
    if (preference != null) {
      return preference.getBooleanValue();
    } else {
      return defaultValue;
    }
  }


  public void clearPreferences() {
    preferences.clear();
  }


  public boolean nameExists(String name) {
    String        preferenceName;
    Preference    preference;
    ListIterator  iterator        = preferences.listIterator(0);
    while (iterator.hasNext()) {
      preference = (Preference) iterator.next();
      preferenceName = preference.getName();
      if (preferenceName.startsWith(name)) {
        return true;
      }
    }
    return false;
  }


  public void load() {
    File  preferencesFile  = new File(preferencesFilename);
    if (preferencesFile.exists()) {
      try {
        BufferedReader  input  = new BufferedReader(new FileReader(preferencesFilename));
        String          name;
        String          value;
        clearPreferences();
        name = input.readLine();
        //get and forget header
        while ((name = input.readLine()) != null) {
          value = input.readLine();
          preferences.add(new Preference(name, value));
        }
      } catch (IOException e) {
        throw new UncheckedException("cannot load preferences", e);
      }
    }
  }


  public void save() {
    try {
      PrintWriter   output      = new PrintWriter(new FileWriter(preferencesFilename));
      output.println("// armidale preferences");
      Preference    preference;
      ListIterator  iterator    = preferences.listIterator(0);
      while (iterator.hasNext()) {
        preference = (Preference) iterator.next();
        output.println(preference.getName());
        output.println(preference.getValue());
      }
      output.close();
    } catch (IOException e) {
      throw new UncheckedException("Cannot save preferences", e);
    }
  }


  public void listPreferences(PrintStream output) {
    Preference    preference;
    ListIterator  iterator    = preferences.listIterator(0);
    while (iterator.hasNext()) {
      preference = (Preference) iterator.next();
      output.println(preference.getName() + " = " + preference.getValue());
    }
  }


  private Preference findPreference(String name) {
    String        preferenceName;
    Preference    preference;
    ListIterator  iterator        = preferences.listIterator(0);
    while (iterator.hasNext()) {
      preference = (Preference) iterator.next();
      preferenceName = preference.getName();
      if (preferenceName.equals(name)) {
        return preference;
      }
    }
    return null;
  }


  private class Preference {
    private  String  name;
    private  String  value;


    public Preference(String name, String value) {
      setName(name);
      setValue(value);
    }


    public Preference(String name, int value) {
      setName(name);
      setIntValue(value);
    }


    public Preference(String name, boolean value) {
      setName(name);
      setBooleanValue(value);
    }


    public void setName(String name) {
      this.name = name;
    }


    public void setValue(String value) {
      this.value = value;
    }


    public void setIntValue(int value) {
      this.value = Integer.toString(value);
    }


    public void setBooleanValue(boolean value) {
      if (value) {
        this.value = "true";
      } else {
        this.value = "false";
      }
    }


    public String getName() {
      return name;
    }


    public String getValue() {
      return value;
    }


    public int getIntValue() {
      try {
        return Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new UncheckedException("'" + value + "' is not an integer", e);
      }
    }


    public boolean getBooleanValue() {
      return value.equalsIgnoreCase("true");
    }

  }

}

