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


public class URL {

  private final String PROTOCOL_NAME    = "arm";
  private final String PROTOCOL_SPEC    = PROTOCOL_NAME + "://";

  private final String DEFAULT_APP_NAME = "Home";
  
  private int     port;
  private String  host;
  private String  appName;
  
  /**
   *Constructor for the URL object
   *
   * @param  url             Description of Parameter
   * @exception  InvaliPortdURL  Description of Exception
   * @since
   */
  public URL(String url) {
    
    int hostStart = PROTOCOL_SPEC.length();
    
    if (!url.startsWith(PROTOCOL_SPEC)) {
      throw new ArgumentException("'" + PROTOCOL_SPEC + "' expected at start of '" + url + "'");
    }
    
    int hostEnd = url.indexOf('/', hostStart);
    if (hostEnd == -1) {
      host    = url.substring(hostStart);
      appName = DEFAULT_APP_NAME;
    } else {
      host    = url.substring(hostStart, hostEnd);
      appName = url.substring(hostEnd+1);
      if (appName.equals("")) {
        appName = DEFAULT_APP_NAME;
      }
    }

    int  colonIndex  = host.indexOf(':');
    if (colonIndex == -1) {
      port = -1;
    } else {
      try {
        port = Integer.parseInt(host.substring(colonIndex + 1));
      } catch (NumberFormatException exc) {
        throw new ArgumentException("invalid port number in '" + url + "'");
      }
      host = host.substring(0, colonIndex);
    }
  }

  /**
   *Gets the protocol attribute of the URL object
   *
   * @return    The protocol value
   * @since
   */
  public String getProtocol() {
    return PROTOCOL_NAME;
  }

  /**
   *Gets the port attribute of the URL object
   *
   * @return    The port value
   * @since
   */
  public int getPort() {
    return port;
  }

  /**
   *Gets the host attribute of the URL object
   *
   * @return    The host value
   * @since
   */
  public String getHost() {
    return host;
  }

  /**
   *Gets the appName attribute of the URL object
   *
   * @return    The appName value
   * @since
   */
  public String getAppName() {
    return appName;
  }

  public String getProtocolHostPort() {
    return PROTOCOL_SPEC + host + ":" + port;
  }
  
  public String toString() {
    return getProtocolHostPort() + "/" + appName;
  }
  
  
}

