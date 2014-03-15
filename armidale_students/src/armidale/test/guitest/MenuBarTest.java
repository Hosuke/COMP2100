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

import armidale.api.Application;
import armidale.api.context.*;
import armidale.api.gui.*;
import armidale.api.gui.constants.*;

class MenuBarTest {

  private  MenuBar      menuBar;
  private  Application  application;


  public MenuBarTest(Context context, TestImages testImages, Application app) {
    this.application = app;
    menuBar = MenuBarFactory.create(context);
    Menu              fileMenu           = MenuFactory.create(context);
    MenuItem          exitMenuItem       = MenuItemFactory.create(context);
    exitMenuItem.setIcon(testImages.stopIcon);
    exitMenuItem.addActionCallback(
      new ActionCallbackDebug(context) {
        public void actionPerformed(Action action) {
          super.actionPerformed(action);
          application.close();
        }
      });

    exitMenuItem.setText("Exit");
    fileMenu.setText("File");
    fileMenu.addMenuItem(exitMenuItem);
    menuBar.addMenu(fileMenu);

    Menu              testMenu           = MenuFactory.create(context);
    testMenu.setText("TestMenu");

    MenuItem          simpleMenuItems[]    = new MenuItem[4];
    for (int i = 0; i < simpleMenuItems.length; i++) {
      simpleMenuItems[i] = MenuItemFactory.create(context);
      simpleMenuItems[i].setText("Simple Menu Item " + i);
      simpleMenuItems[i].addActionCallback(new ActionCallbackDebug(context));
      testMenu.addMenuItem(simpleMenuItems[i]);
    }

    testMenu.addSeparator();

    Menu              checkBoxSubMenu    = MenuFactory.create(context);
    checkBoxSubMenu.setText("Check Boxes");
    CheckBoxMenuItem  checkBoxMenuItems[]  = new CheckBoxMenuItem[3];
    for (int i = 0; i < checkBoxMenuItems.length; i++) {
      checkBoxMenuItems[i] = CheckBoxMenuItemFactory.create(context);
      checkBoxMenuItems[i].setText("Checkbox " + i);
      checkBoxMenuItems[i].addCheckBoxMenuItemCallback(new CheckBoxMenuItemCallbackDebug(context));
      checkBoxSubMenu.addMenuItem(checkBoxMenuItems[i]);
    }
    testMenu.addMenuItem(checkBoxSubMenu);

    menuBar.addMenu(testMenu);
  }



  public MenuBar menuBar() {
    return menuBar;
  }

}


