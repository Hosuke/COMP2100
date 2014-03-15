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
import armidale.api.ClassInformation;
import armidale.api.ProductInfo;
import armidale.api.context.*;
import armidale.api.context.platform.*;

import armidale.api.gui.*;
import armidale.api.gui.constants.*;

import armidale.api.io.Debug;
import armidale.api.io.File;

import java.io.IOException;
import java.util.Vector;
import javax.swing.DefaultListModel;

public class Main extends Application {

  private  Frame        frame;
  private  TabbedPanel  tabbedPanel;

  // MAIN
  //
  public static void main(String[] args) {
    Main          guiTest  = new Main();
    
    guiTest.setContext(new SwingContext());
    guiTest.setArgs(args);
    guiTest.start();
  }


  public ClassInformation getClassInformation() {
    ClassInformation  classInformation  = new ClassInformation("armidale.test.guitest.Main", "1.0", "Shayne R Flint", "");
//    classInformation.addRequiredClass(new ClassInformation("extra.Hello", "Shayne R Flint", "1.0", ""));
    return classInformation;
  }


  public void init() {

    // IMAGES
    //
    TestImages   testImages   = new TestImages(context);

    // MAIN FRAME
    //
    frame = FrameFactory.create(context);
    frame.addWindowCallback(new WindowCloseCallback(context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));
    frame.setTitle("Armidale GUI Widget Test " + ProductInfo.versionAsString());
    frame.setSize(new Size(700, 600));
    frame.setAlignment(Frame.CENTER);
    frame.setIcon(testImages.homeIcon);

    frame.setResizable(true);
    frame.setVisible(true);

    // TABBED PANEL
    //
    tabbedPanel = TabbedPanelFactory.create(context);

    frame.setContent(tabbedPanel);

    tabbedPanel.setBackgroundColor(new Color(255, 204, 153));
    tabbedPanel.setTabLayout(TabbedPanel.WRAP);
    tabbedPanel.setTabPlacement(TabbedPanel.NORTH);
    tabbedPanel.addTabbedPanelCallback(new TabbedPanelCallbackDebug(context));

    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new BorderPanelTest(context, testImages)).widget(), "BorderPanel", testImages.homeIcon));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new ImageButtonTest(context, testImages)).widget(), "ImageButton", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new LabelTest(context, testImages)).widget(), "Label and PopupMenu", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new TextFieldTest(context, testImages)).widget(), "TextFields", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new TextBoxTest(context, testImages)).widget(), "TextArea", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new GridPanelTest(context, testImages)).widget(), "GridPanel", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new SplitPanelTest(context, testImages)).widget(), "SplitPanel and ToolTips", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new FlowPanelTest(context, testImages)).widget(), "FlowPanel", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new BoxPanelTest(context, testImages)).widget(), "ScrollPanel and BoxPanel", null));
    DesktopTest  desktopTest  = new DesktopTest(context, testImages);
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, desktopTest.widget(), "Desktop", null));

    TabbedPanel  listPanel    = TabbedPanelFactory.create(context);
    listPanel.setTabPlacement(TabbedPanel.SOUTH);
    listPanel.addTab(TabbedPanelWidgetFactory.create(context, (new SimpleListBoxTest(context, testImages)).widget(), "SimpleListBox", null));
    listPanel.addTab(TabbedPanelWidgetFactory.create(context, (new ListBoxTest(context, testImages)).widget(), "ListBox", null));
    listPanel.addTab(TabbedPanelWidgetFactory.create(context, (new CallbackListBoxTest(context, testImages)).widget(), "CallbackListBox (20,000 items)", null));
    listPanel.addTab(TabbedPanelWidgetFactory.create(context, (new ModelledListBoxTest(context, testImages)).widget(), "ModelledListBox (20,000 items)", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, listPanel, "ListBoxes", null));

    TabbedPanel  tablePanel   = TabbedPanelFactory.create(context);
    tablePanel.setTabPlacement(TabbedPanel.SOUTH);
    tablePanel.addTab(TabbedPanelWidgetFactory.create(context, (new SimpleTableTest(context, testImages)).widget(), "SimpleTable", null));
    tablePanel.addTab(TabbedPanelWidgetFactory.create(context, (new TableTest(context, testImages)).widget(), "Table", null));
    tablePanel.addTab(TabbedPanelWidgetFactory.create(context, (new CallbackTableTest(context, testImages)).widget(), "CallbackTable [20,000 rows]", null));
    tablePanel.addTab(TabbedPanelWidgetFactory.create(context, (new ModelledTableTest(context, testImages)).widget(), "ModelledTable [20,000 rows]", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, tablePanel, "Tables", null));

    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new OptionBoxTest(context, testImages)).widget(), "OptionBoxes", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new SliderTest(context, testImages)).widget(), "Sliders", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new ProgressBarTest(context, testImages)).widget(), "ProgressBars", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new LabeledWidgetTest(context, testImages)).widget(), "LabeledWidgets and RadioButtonGroup", null));
    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, (new LinkLabelTest(context, testImages)).widget(), "Link to URL", null));

//    Hello hello = HelloFactory.create(context);
//    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, hello, "Hello", null));

    frame.setMenubar((new MenuBarTest(context, testImages, this)).menuBar());

    frame.setToolbar((new ToolBarTest(context, testImages)).widget());


    // Align internal frames in desktop tab now thjat we have the frame size established
    //
    desktopTest.alignFrames();

    // Load some images now that the main GUI is ready
    //
    testImages.loadImages();

  }

}

