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

public class MessageTypes {

  public final static  int  ERROR                = 000;

  public final static  int  START_APPLICATION    = 100;
  public final static  int  CLASS_INFORMATION    = 110;
  public final static  int  CLIENT_OK            = 112;
  public final static  int  STOP_APPLICATION     = 210;
  public final static  int  APPLICATION_STOPPED  = 211;

  public final static  int  CONSTRUCT            = 300;
  public final static  int  FINALIZE             = 305;

  public final static  int  SET_ATTRIBUTE        = 310;
  public final static  int  SET_FILE_ATTRIBUTE   = 311;

  public final static  int  METHOD_CALL          = 320;
  public final static  int  CALLBACK             = 321;

  public final static  int  ENABLE_CALLBACKS     = 330;
  public final static  int  DISABLE_CALLBACKS    = 331;

  public final static  int  GET_FILE             = 340;
  public final static  int  FILE                 = 341;


  public static String toString(int item) {
    switch (item) {
      case ERROR:
        return "ERROR";
      case START_APPLICATION:
        return "START_APPLICATION";
      case CLASS_INFORMATION:
        return "CLASS_INFORMATION";
      case CLIENT_OK:
        return "CLIENT_OK";
      case STOP_APPLICATION:
        return "STOP_APPLICATION";
      case APPLICATION_STOPPED:
        return "APPLICATION_STOPPED";
      case CONSTRUCT:
        return "CONSTRUCT";
      case FINALIZE:
        return "FINALIZE";
      case SET_ATTRIBUTE:
        return "SET_ATTRIBUTE";
      case SET_FILE_ATTRIBUTE:
        return "SET_FILE_ATTRIBUTE";
      case METHOD_CALL:
        return "METHOD_CALL";
      case CALLBACK:
        return "CALLBACK";
      case ENABLE_CALLBACKS:
        return "ENABLED_CALLBACKS";
      case DISABLE_CALLBACKS:
        return "DISABLE_CALLBACKS";
      case GET_FILE:
        return "GET_FILE";
      case FILE:
        return "FILE";
      default:
        return "<unknown " + item + ">";
    }
  }

}

