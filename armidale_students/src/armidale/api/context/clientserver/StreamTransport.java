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

import armidale.api.io.AbstractTextOutput;
import armidale.api.UncheckedException;
import java.io.IOException;

public class StreamTransport implements Transport {

  private  MessageInputStream   inputStream;
  private  MessageOutputStream  outputStream;


  public StreamTransport(MessageInputStream anInputStream, MessageOutputStream anOutputStream) {
    super();
    inputStream = anInputStream;
    outputStream = anOutputStream;
  }


  public void setInputStream(MessageInputStream anInputStream) {
    inputStream = anInputStream;
  }


  public void setOutputStream(MessageOutputStream anOutputStream) {
    outputStream = anOutputStream;
  }


  public void writeMessage(Message aMessage) {
    outputStream.writeMessage(aMessage);
  }


  public Message readMessage() {
    return inputStream.readMessage();
  }


  public void close() {
    try {
      inputStream.close();
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      throw new UncheckedException("closing StreamTransport", e);
    }
  }

}

