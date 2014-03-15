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
 *  notice, this list of conditions and the following disclaimer Sin the
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
 
package armidale.launcher;

import java.io.*;
import java.util.*;

import armidale.api.*;
import armidale.api.Application;
import armidale.api.ArmidaleException;
import armidale.api.ClassInformation;
import armidale.api.ProductInfo;
import armidale.api.RemoteApplication;
import armidale.api.Environment;
import armidale.api.context.*;
import armidale.api.context.platform.*;
import armidale.api.gui.*;
import armidale.api.gui.constants.*;
import armidale.api.io.Debug;
import armidale.api.io.File;
import armidale.api.io.FrameTextOutput;
import armidale.api.util.Strings;

public class Main extends Application {

  private  String           homeUrl               = "";
  private  int              selectedTab           = 0;

  private  FrameTextOutput  textOutput            = new FrameTextOutput();

  private  Frame            frame;
  private  Position         framePosition;
  private  Size             frameSize;
  private  boolean          debugEnabled;
  private  boolean          debugPauseEnabled;
  private  int              debugLevel;

  private  TextField        urlBox;
  private  ListBox          bookmarks;
  private  ListData         bookmarkListData;
  private  LinkedList       bookmarkStrings;

  private  Image            startIcon;
  private  Image            prefsIcon;
  private  Image            smallPrefsIcon;
  private  Image            bookmarkIcon;
  private  Image            smallBookmarkIcon;
  private  Image            homeIcon;
  private  Image            setHomeIcon;
  private  Image            exitIcon;
  private  Image            smallInformationIcon;


  public Main(Context aContext) {
    super(aContext);
  }


  public Main() {
    super();
  }


  public static void main(String[] args) {
    Main  launcher  = new Main(new SwingContext());
    launcher.start();
  }


  public ClassInformation getClassInformation() {
    ClassInformation  classInformation  = new ClassInformation("armidale.launcher.Main", ProductInfo.versionAsString(), "Shayne R Flint", "");
    return classInformation;
  }


  public void init() {

    homeUrl     = Environment.armidalePreferences().getPreference("armidale.launcher.urls.home", "");
    selectedTab = Environment.armidalePreferences().getIntPreference("armidale.launcher.selected_tab", 2);
    frame = FrameFactory.create(context);
    frame.addWindowCallback(new WindowCloseCallback(context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));
    frame.addWindowCallback
      (
      new WindowCallbackAdapter() {
        public void windowMoved(Window window, Position position) {
          framePosition = position;
        }


        public void windowResized(Window window, Size size) {
          frameSize = size;
        }
      });

    frame.setTitle("armidaleLAUNCHER " + ProductInfo.versionAsString());

    int            width                   = Environment.armidalePreferences().getIntPreference("armidale.launcher.geometry.width", 600) - 8;
    int            height                  = Environment.armidalePreferences().getIntPreference("armidale.launcher.geometry.height", 600)- 77;
    frameSize = new Size(width, height);
    frame.setSize(frameSize);

    int            xPos                    = Environment.armidalePreferences().getIntPreference("armidale.launcher.geometry.xpos", 200);
    int            yPos                    = Environment.armidalePreferences().getIntPreference("armidale.launcher.geometry.ypos", 200);
    framePosition = new Position(xPos, yPos);
    frame.setPosition(framePosition);

    BorderPanel    mainPanel               = BorderPanelFactory.create(context);

    mainPanel.setBackgroundColor(Color.WHITE);

    // Icons
    //
    startIcon = ImageFactory.create(context);
    prefsIcon = ImageFactory.create(context);
    smallPrefsIcon = ImageFactory.create(context);
    bookmarkIcon = ImageFactory.create(context);
    smallBookmarkIcon = ImageFactory.create(context);
    homeIcon = ImageFactory.create(context);
    setHomeIcon = ImageFactory.create(context);
    exitIcon = ImageFactory.create(context);
    smallInformationIcon = ImageFactory.create(context);

    startIcon.setImageData(File.createByteArray("images/icons/media/Play24.gif"));
    prefsIcon.setImageData(File.createByteArray("images/icons/general/Preferences24.gif"));
    smallPrefsIcon.setImageData(File.createByteArray("images/icons/general/Preferences16.gif"));
    bookmarkIcon.setImageData(File.createByteArray("images/icons/general/Bookmarks24.gif"));
    smallBookmarkIcon.setImageData(File.createByteArray("images/icons/general/Bookmarks16.gif"));
    homeIcon.setImageData(File.createByteArray("images/icons/navigation/Home24.gif"));
    setHomeIcon.setImageData(File.createByteArray("images/icons/general/Save24.gif"));
    exitIcon.setImageData(File.createByteArray("images/icons/general/Stop16.gif"));
    smallInformationIcon.setImageData(File.createByteArray("images/icons/general/About16.gif"));

    // Tabbed Panel
    //
    TabbedPanel    tabbedPanel             = TabbedPanelFactory.create(context);
    tabbedPanel.setTabPlacement(TabbedPanel.SOUTH);

    // Bookmark List
    //
    bookmarks = ListBoxFactory.create(context);
    bookmarkStrings = new LinkedList();
    bookmarkListData = ListDataFactory.create(context);
    bookmarks.setListData(bookmarkListData);
    bookmarks.addListBoxCallback(new UrlListBoxCallback());

    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, bookmarks, "Bookmarks", smallBookmarkIcon));

    String[]       urls;

    urls = Environment.armidalePreferences().getPreferenceNames("armidale.launcher.urls");
    for (int i = 0; i < urls.length; i++) {
      if (urls[i].startsWith("armidale.launcher.urls.bm")) {
        addBookmark(Environment.armidalePreferences().getPreference(urls[i], "error getting bookmark from user preferences"));
      }
    }

    // Options
    //
    String[]       debugLevels             = {"All", "Finest", "Finer", "Fine", "Course", "Courser", "Coursest"};

    debugEnabled      = Environment.armidalePreferences().getBooleanPreference("armidale.debug.enabled", false);
    debugPauseEnabled = Environment.armidalePreferences().getBooleanPreference("armidale.debug.pause", false);
    debugLevel        = Environment.armidalePreferences().getIntPreference("armidale.debug.level", 0);

    context.getDebug().setEnabled(debugEnabled);
    context.getDebug().setPause(debugPauseEnabled);
    context.getDebug().setLevel(debugLevel);

    BoxPanel       optionsPanel            = BoxPanelFactory.create(context);
    optionsPanel.setOrientation(BoxPanel.HORIZONTAL);

    CheckBox       enableDebugCheckBox     = CheckBoxFactory.create(context);
    enableDebugCheckBox.setText("enable debug messages");
    enableDebugCheckBox.setAlignmentX(0.0f);
    enableDebugCheckBox.setSelected(debugEnabled);
    enableDebugCheckBox.addToggleCallback
      (
      new ToggleCallbackAdapter() {
        public void stateChange(Toggle toggle, boolean selected) {
          debugEnabled = selected;
          context.getDebug().setEnabled(debugEnabled);
        }
      });

    CheckBox       enablePauseCheckBox     = CheckBoxFactory.create(context);
    enablePauseCheckBox.setText("pause after each message");
    enablePauseCheckBox.setAlignmentX(0.0f);
    enablePauseCheckBox.setSelected(debugPauseEnabled);
    enablePauseCheckBox.addToggleCallback
      (
      new ToggleCallbackAdapter() {
        public void stateChange(Toggle toggle, boolean selected) {
          debugPauseEnabled = selected;
          context.getDebug().setPause(debugPauseEnabled);
        }
      });

    LabeledWidget  debugLevelWidget        = LabeledWidgetFactory.create(context);
    debugLevelWidget.setText("debug level");
    ComboBox       debugLevelComboBox      = ComboBoxFactory.create(context);
    debugLevelComboBox.setItems(debugLevels);
    debugLevelWidget.setWidget(debugLevelComboBox);
    debugLevelComboBox.setSelectedItem(debugLevel);
    debugLevelComboBox.setSize(new Size(150, 25));
    debugLevelComboBox.addComboBoxCallback
      (
      new ComboBoxCallbackAdapter() {
        public void indexSelected(ComboBox comboBox, int index) {
          debugLevel = index;
          context.getDebug().setLevel(debugLevel);
        }
      });

    BoxPanel       debugCheckBoxPanel      = BoxPanelFactory.create(context);
    debugCheckBoxPanel.setOrientation(BoxPanel.VERTICAL);

    debugCheckBoxPanel.addWidget(enableDebugCheckBox);
    debugCheckBoxPanel.addWidget(enablePauseCheckBox);

    optionsPanel.addHorizontalSpring();
    optionsPanel.addWidget(debugCheckBoxPanel);
    optionsPanel.addHorizontalStrut(20);
    optionsPanel.addWidget(debugLevelWidget);
    optionsPanel.addHorizontalSpring();

    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, optionsPanel, "Options", smallPrefsIcon));

    // About
    //
    Label          armidaleLogo            = LabelFactory.create(context);
    Image          armidaleLogoImage       = ImageFactory.create(context);
    armidaleLogoImage.setImageData(File.createByteArray("images/armidalelauncher.png"));
    armidaleLogo.setImage(armidaleLogoImage);
    armidaleLogo.setTextPosition(Label.SOUTH);
    armidaleLogo.setText
      ("<html><font color=#c687c6 size=+1>"
       + ProductInfo.copyrightTitle() + "</font><p>"
       + "<font color=#c687c6 size=-1>running on " + Environment.hostOperatingSystem()
       + " and JVM version " + Environment.javaVersion() + "<p>"
       + "</font></html>");

    tabbedPanel.addTab(TabbedPanelWidgetFactory.create(context, armidaleLogo, "About", smallInformationIcon));

    tabbedPanel.setSelectedIndex(selectedTab);
    tabbedPanel.addTabbedPanelCallback
      (
      new TabbedPanelCallbackAdapter() {
        public void indexSelected(TabbedPanel tabbedPanel, int index) {
          selectedTab = index;
        }
      });

    mainPanel.addWidget(BorderPanelWidgetFactory.create(context, tabbedPanel, BorderPanelWidget.CENTER));

    frame.setContent(mainPanel);

    // Menu Bar
    //
    MenuItem       exitMenuItem            = MenuItemFactory.create(context);
    exitMenuItem.setText("Exit");
    //exitMenuItem.setIcon(exitIcon);
    exitMenuItem.addActionCallback(new ExitCallback());

    Menu           fileMenu                = MenuFactory.create(context);
    fileMenu.setText("File");
    fileMenu.addMenuItem(exitMenuItem);

    MenuItem       startMenuItem           = MenuItemFactory.create(context);
    startMenuItem.setText("Start URL");
    //startMenuItem.setIcon(startIcon);
    startMenuItem.addActionCallback(new StartApplicationCallback());

    MenuItem       homeMenuItem            = MenuItemFactory.create(context);
    homeMenuItem.setText("Start Home URL");
    //homeMenuItem.setIcon(homeIcon);
    homeMenuItem.addActionCallback(new HomeCallback());

    MenuItem       saveURLMenuItem         = MenuItemFactory.create(context);
    saveURLMenuItem.setText("Bookmark URL");
    //saveURLMenuItem.setIcon(bookmarkIcon);
    saveURLMenuItem.addActionCallback(new BookmarkCallback());

    MenuItem       setHomeMenuItem         = MenuItemFactory.create(context);
    //setHomeMenuItem.setIcon(setHomeIcon);
    setHomeMenuItem.setText("Set Home URL");
    setHomeMenuItem.addActionCallback(new SetHomeCallback());

    MenuItem       deleteBookmarkMenuItem  = MenuItemFactory.create(context);
    deleteBookmarkMenuItem.setText("Delete selected bookmark");
    deleteBookmarkMenuItem.addActionCallback(new DeleteSelectedBookmarkCallback());

    MenuItem       clearBookmarksMenuItem  = MenuItemFactory.create(context);
    clearBookmarksMenuItem.setText("Delete all bookmarks");
    clearBookmarksMenuItem.addActionCallback(new DeleteAllBookmarksCallback());

    Menu           bookmarksMenu           = MenuFactory.create(context);
    bookmarksMenu.setText("Bookmarks");
    bookmarksMenu.addMenuItem(startMenuItem);
    bookmarksMenu.addMenuItem(homeMenuItem);
    bookmarksMenu.addSeparator();
    bookmarksMenu.addMenuItem(saveURLMenuItem);
    bookmarksMenu.addMenuItem(setHomeMenuItem);
    bookmarksMenu.addSeparator();
    bookmarksMenu.addMenuItem(deleteBookmarkMenuItem);
    bookmarksMenu.addMenuItem(clearBookmarksMenuItem);

    MenuBar        menuBar                 = MenuBarFactory.create(context);
    menuBar.addMenu(fileMenu);
    menuBar.addMenu(bookmarksMenu);

    frame.setMenubar(menuBar);

    // Tool Bar
    //
    BoxPanel       toolbar                 = BoxPanelFactory.create(context, BoxPanel.HORIZONTAL);
    toolbar.addVerticalStrut(29);

    Image          logo                    = ImageFactory.create(context);
    logo.setFile(new File("images/armidalelauncher-sml.png"));
    Label          logoLabel               = LabelFactory.create(context);
    logoLabel.setImage(logo);
    toolbar.addWidget(logoLabel);

    urlBox = TextFieldFactory.create(context);
    urlBox.setText(Environment.armidalePreferences().getPreference("armidale.launcher.urls.current", ""));
    urlBox.addTextCallback(new StartApplicationTextCallback());
    toolbar.addWidget(urlBox);

    PushButton     startButton             = PushButtonFactory.create(context);
    startButton.setText("");
    startButton.setToolTip("start application");
    startButton.setIcon(startIcon);
    startButton.setSize(new Size(30, 30));
    startButton.addActionCallback(new StartApplicationCallback());
    toolbar.addHorizontalStrut(4);
    toolbar.addWidget(startButton);

    PushButton     homeButton              = PushButtonFactory.create(context);
    homeButton.setText("");
    homeButton.setToolTip("start home application");
    homeButton.setIcon(homeIcon);
    homeButton.setSize(new Size(30, 30));
    homeButton.addActionCallback(new HomeCallback());
    toolbar.addHorizontalStrut(4);
    toolbar.addWidget(homeButton);

    PushButton     bookmarkButton          = PushButtonFactory.create(context);
    bookmarkButton.setText("");
    bookmarkButton.setToolTip("add the application to bookmarks");
    bookmarkButton.setIcon(bookmarkIcon);
    bookmarkButton.setSize(new Size(30, 30));
    bookmarkButton.addActionCallback(new BookmarkCallback());
    toolbar.addHorizontalStrut(4);
    toolbar.addWidget(bookmarkButton);
    toolbar.addHorizontalSpring();

    PushButton     setHomeButton           = PushButtonFactory.create(context);
    setHomeButton.setText("");
    setHomeButton.setToolTip("set home application");
    setHomeButton.setIcon(setHomeIcon);
    setHomeButton.setSize(new Size(30, 30));
    setHomeButton.addActionCallback(new SetHomeCallback());
    toolbar.addHorizontalStrut(4);
    toolbar.addWidget(setHomeButton);

    PushButton     exitButton              = PushButtonFactory.create(context);
    exitButton.setText("");
    exitButton.setToolTip("exit the launcher");
    exitButton.setIcon(exitIcon);
    exitButton.setSize(new Size(30, 30));
    exitButton.addActionCallback(new ExitCallback());
    toolbar.addHorizontalStrut(4);
    toolbar.addWidget(exitButton);

    frame.setToolbar(toolbar);

    frame.show();
  }


  private void startApplication() {
    String[]  tempArgs  = Strings.split(urlBox.getText().trim(), " \t");
    String[]  args      = new String[tempArgs.length - 1];
    for (int i = 0; i < args.length; i++) {
      args[i] = tempArgs[i + 1];
    }
    if (Strings.isQualifiedName(tempArgs[0])) {
      try {
        LocalApplication  app  = new LocalApplication(tempArgs[0], args, textOutput);
        app.start();
      } catch (ArmidaleException e) {
        textOutput.showError("Start Local Application", e);
      }
    } else {
      try {
        RemoteApplication  app  = new RemoteApplication(tempArgs[0], args);
        app.start();
      } catch (ArmidaleException e) {
        textOutput.showError("Start Remote Application", e);
      }
    }
  }


  private void addBookmark(String url) {
    for (Iterator iterator = bookmarkStrings.listIterator(0); iterator.hasNext(); ) {
      if (((String) iterator.next()).equals(url)) {
        SimpleOptionBox.showWarningBox(context, "'" + url + "' is already bookmarked");
        return;
      }
    }

    bookmarkStrings.add(url);
    updateUrlList();
  }


  private void updateUrlList() {
    Label  theLabel;

    Collections.sort(bookmarkStrings);
    bookmarkListData.removeAllItems();
    for (Iterator iterator = bookmarkStrings.listIterator(0); iterator.hasNext(); ) {
      theLabel = LabelFactory.create(context, (String) iterator.next());
      theLabel.setAlignment(Label.WEST);
      theLabel.setTextPosition(Label.EAST);
      theLabel.setImage(smallBookmarkIcon);
      bookmarkListData.addItem(theLabel);
    }
    bookmarks.refresh();
  }


  private void notImplemented(String title) {
    SimpleOptionBox.showInformationBox(context, "'" + title + "' is not yet implemented");
  }


  private void saveState() {
    int     index  = 0;
    String  url;

    // delete old launcher preferences
    Environment.armidalePreferences().clearPreferences();
    
    // save bookmarks
    for (Iterator iterator = bookmarkStrings.listIterator(); iterator.hasNext(); ) {
      Environment.armidalePreferences().setPreference("armidale.launcher.urls.bm" + index, (String) iterator.next());
      index++;
    }

    // save current and home URL
    Environment.armidalePreferences().setPreference("armidale.launcher.urls.current", urlBox.getText());
    Environment.armidalePreferences().setPreference("armidale.launcher.urls.home", homeUrl);

    // save geometry
    Environment.armidalePreferences().setIntPreference("armidale.launcher.geometry.width", frameSize.getWidth());
    Environment.armidalePreferences().setIntPreference("armidale.launcher.geometry.height", frameSize.getHeight());
    Environment.armidalePreferences().setIntPreference("armidale.launcher.geometry.xpos", framePosition.getX());
    Environment.armidalePreferences().setIntPreference("armidale.launcher.geometry.ypos", framePosition.getY());

    // save selected tab
    Environment.armidalePreferences().setIntPreference("armidale.launcher.selected_tab", selectedTab);

    // save debug selections
    Environment.armidalePreferences().setBooleanPreference("armidale.debug.enabled", debugEnabled);
    Environment.armidalePreferences().setBooleanPreference("armidale.debug.pause", debugPauseEnabled);
    Environment.armidalePreferences().setIntPreference("armidale.debug.level", debugLevel);

    Environment.armidalePreferences().save();
  }


  private class ExitCallback implements ActionCallback {
    public void actionPerformed(Action action) {
      saveState();
      System.exit(0);
    }
  }


  private class StartApplicationCallback implements ActionCallback {
    public void actionPerformed(Action action) {
      startApplication();
    }
  }


  private class StartApplicationTextCallback implements TextCallback {
    public void textChanged(Text text, String theText) {
      startApplication();
    }
  }


  private class BookmarkCallback implements ActionCallback {
    public void actionPerformed(Action action) {
      addBookmark(urlBox.getText());
    }
  }


  private class SetHomeCallback implements ActionCallback {
    public void actionPerformed(Action action) {
      homeUrl = urlBox.getText();
    }
  }


  private class HomeCallback implements ActionCallback {
    public void actionPerformed(Action action) {
      urlBox.setText(Environment.armidalePreferences().getPreference("armidale.launcher.urls.home", ""));
      startApplication();
    }
  }


  private class DeleteSelectedBookmarkCallback implements ActionCallback {
    public void actionPerformed(Action action) {
      notImplemented("Delete Selected Bookmark");
    }
  }


  private class DeleteAllBookmarksCallback implements ActionCallback {
    public void actionPerformed(Action action) {
      bookmarkStrings.clear();
      updateUrlList();
    }
  }


  private class UrlListBoxCallback implements ListBoxCallback {
    public void itemsSelected(ListBox listBox, int[] indices) {
      if (indices == null || indices.length == 0) {
        return;
      }
      int     index   = indices[0];
      if (index < 0 || index >= bookmarkStrings.size()) {
        return;
      }
      String  theUrl  = (String) bookmarkStrings.get(indices[0]);
      urlBox.setText(theUrl);
    }
  }

}

