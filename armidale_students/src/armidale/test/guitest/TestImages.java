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
 
package armidale.test.guitest;


import armidale.api.context.Context;
import armidale.api.gui.*;
import armidale.api.gui.constants.*;
import armidale.api.io.*;

class TestImages {

  Image  kidsImage;
  Image  armidaleImage;

  Image  homeIcon;
  Image  stopIcon;
  Image  checkIcon;


  public TestImages(Context context) {
    kidsImage = ImageFactory.create(context);
    armidaleImage = ImageFactory.create(context);

    homeIcon = ImageFactory.create(context);
    stopIcon = ImageFactory.create(context);
    checkIcon = ImageFactory.create(context);

    homeIcon.setImageData(File.createByteArray("images/icons/navigation/Home16.gif"));
    stopIcon.setImageData(File.createByteArray("images/icons/media/Stop16.gif"));
    checkIcon.setImageData(File.createByteArray("images/icons/general/ComposeMail24.gif"));
  }


  public void loadImages() {
    kidsImage.setFile(new File("images/sara.jpg"));
    armidaleImage.setFile(new File("images/armidaledemo.png"));
  }

}

