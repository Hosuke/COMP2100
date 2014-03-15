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
 
package armidale.api.gui;

public class Font {

  public final static  String   DIALOG        = "Dialog";
  public final static  String   DIALOG_INPUT  = "DialogInput";
  public final static  String   MONO_SPACED   = "Monospaced";
  public final static  String   SERIF         = "Serif";
  public final static  String   SANS_SERIF    = "SansSerif";

  private              String   name;
  private              int      size;
  private              boolean  isBoldFont;
  private              boolean  isItalicFont;


  public Font() {
    name = DIALOG;
    isBoldFont = false;
    isItalicFont = false;
    size = 12;
  }


  public Font(String name, int size) {
    this();
    this.name = name;
    this.size = size;
  }


  public Font(String name, int size, boolean isBold, boolean isItalic) {
    this();
    this.name = name;
    this.size = size;
    this.isBoldFont = isBold;
    this.isItalicFont = isItalic;
  }


  public void setName(String name) {
    this.name = name;
  }


  public void setSize(int size) {
    this.size = size;
  }


  public void setBold(boolean state) {
    isBoldFont = state;
  }


  public void setItalic(boolean state) {
    isItalicFont = state;
  }


  public String getName() {
    return name;
  }


  public int getSize() {
    return size;
  }


  public boolean isBold() {
    return isBoldFont;
  }


  public boolean isItalic() {
    return isItalicFont;
  }
}

