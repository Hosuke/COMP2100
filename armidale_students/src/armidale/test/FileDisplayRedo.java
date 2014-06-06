package armidale.test;

import armidale.api.Application;
import armidale.api.context.platform.*;
import armidale.api.gui.*;
import armidale.api.UncheckedException;
import armidale.api.structures.ByteArray;

import java.io.File;
import java.util.Date;
import java.io.FileInputStream;
import java.io.IOException;


public class FileDisplayRedo extends Application {
	
	private static final String FONT_NAME = "Dialog";
	private static final int    FONT_SIZE = 12;
	
	private Image crossIcon;
	private Image tickIcon;
	private Image parentIcon;
	private Image directoryIcon;
	private Image imageIcon;
	
	Frame       frame;
	BorderPanel mainPanel;
	SplitPanel  fileListDetailsSplitPanel;
	SplitPanel  filePropertyImageSplitPanel;
	Table       filePropertyTable;
	Label       fileImageLabel;
	ListBox     fileListBox;


	java.io.File[]      fileList;
	
	//count how many files under dir
	private int fileCount(java.io.File dir) {
		int count = 0;
		for (java.io.File file : dir.listFiles()){
			if (!file.getName().startsWith(".")){
				count ++;
			}
		}
		return count + 1;
	}
	
	private void updateFileList(java.io.File root) {
		int i = 0;
		fileList = new File[fileCount(root)];
		fileList[0] = root.getParentFile();
		i = 1;
		for (java.io.File file : root.listFiles()) {
			if (!file.getName().startsWith(".")) {
				fileList[i] = file;
				i++;
			}
		}
	}
	
	private boolean isImage(File file) {
		String name = file.getName().toLowerCase();
		return (name.endsWith(".jpg") || name.endsWith(".png"));
	}
	
	private Label booleanLabel(boolean state) {

		if (state)
			return LabelFactory.create(context, String.valueOf(state), tickIcon);
		else
			return LabelFactory.create(context, String.valueOf(state), crossIcon);
	}
	
	// Don't understand
	private ByteArray fileData(File file) {
		byte[]  bytes  = new byte[(int) file.length()];

		try {
			FileInputStream  input  = new FileInputStream(file);
			input.read(bytes, 0, bytes.length);
			input.close();
			return new ByteArray(bytes);
		}
		catch (IOException e) {
			throw new UncheckedException(e);
		}
	}
	
	// Set image to label
	private void updateFileImage(File file) {
		Image fileImage = ImageFactory.create(context);
		fileImageLabel = LabelFactory.create(context);
		if (isImage(file)) {
			fileImage.setImageData(fileData(file)); // call fileData
			fileImage.setSize(new Size(500,500));
			fileImageLabel.setImage(fileImage);
		}
	}

	
	private void updatePropertyTable(File file) {
		
		String[] columnNames = {"Property", "Value"};
		// create Label table
		Label[][] properties = new Label[5][2];

		properties[0][0] = LabelFactory.create(context, "Can read");
		properties[0][1] = booleanLabel(file.canRead());
		properties[1][0] = LabelFactory.create(context, "Can write");
		properties[1][1] = booleanLabel(file.canWrite());
		properties[2][0] = LabelFactory.create(context, "Can Execute");
		properties[2][1] = booleanLabel(file.canExecute());
		properties[3][0] = LabelFactory.create(context, "Length");
		properties[3][1] = LabelFactory.create(context, String.valueOf(file.length()) + " bytes");
		properties[4][0] = LabelFactory.create(context, "Last Modified");
		properties[4][1] = LabelFactory.create(context, (new Date(file.lastModified())).toString());

		// align text and set fonts
		for (int i=0; i<5; i++)
			for (int j=0; j<2; j++) {
				properties[i][j].setAlignment(Label.WEST);
				properties[i][j].setTextPosition(Label.EAST);
				properties[i][j].setFont(new Font(FONT_NAME, FONT_SIZE, false, false));
			}

		filePropertyTable = SimpleTableFactory.create(context, properties, columnNames);
		filePropertyTable.setAlignmentX(0.0f);
		filePropertyImageSplitPanel.setTopWidget(filePropertyTable);
	}
	
	private void updateFilePropertyImageSplitPanel(File file) {
		updatePropertyTable(file);
		updateFileImage(file);
		filePropertyImageSplitPanel.setOrientation(SplitPanel.VERTICAL);
		filePropertyImageSplitPanel.setTopWidget(filePropertyTable);
		filePropertyImageSplitPanel.setBottomWidget(fileImageLabel);
	}

	private void updateFileListDetailsSplitPanel(File file) {
		updateFileListBox(file);
		updateFilePropertyImageSplitPanel(file);
		fileListDetailsSplitPanel.setOrientation(SplitPanel.HORIZONTAL);
		fileListDetailsSplitPanel.setLeftWidget(fileListBox);
		fileListDetailsSplitPanel.setRightWidget(filePropertyImageSplitPanel);
	}
	
	private void updateFileListBox(java.io.File file) {
		
		updateFileList(file);

		Label[] fileNameList = new Label[fileList.length];
		int i=0;
		
		for (java.io.File f : fileList) {
			
			// create Label
			fileNameList[i] = LabelFactory.create(context);
			fileNameList[i].setTextPosition(Label.EAST);
			fileNameList[i].setAlignment(Label.WEST);
			
			if (f != null)
				fileNameList[i].setText(f.getName());
			else
				fileNameList[i].setText("nullFile");
			
			// Assign icons to different types of the file
			if (isImage(f)) {
				fileNameList[i].setFont(new Font(FONT_NAME, FONT_SIZE, false, true));
				fileNameList[i].setImage(imageIcon);
			} else if (f.isDirectory()) {
				fileNameList[i].setFont(new Font(FONT_NAME, FONT_SIZE, false, false));
				fileNameList[i].setImage(directoryIcon);
			} else {
				fileNameList[i].setFont(new Font(FONT_NAME, FONT_SIZE, false, false));
			}

			i++;
		}
		
		if (fileList[0] != null) {
			fileNameList[0].setText("<parent>");
			fileNameList[0].setImage(parentIcon);
		}

		fileListBox = SimpleListBoxFactory.create(context, fileNameList);

		fileListBox.addListBoxCallback(new ListBoxCallback() {
			@Override
			public void itemsSelected(ListBox listBox, int[] indices) {
				File f = fileList[indices[0]];
				if (f != null) {
					if (f.isDirectory()) {
						updateFileListDetailsSplitPanel(f);
					} else
						updateFilePropertyImageSplitPanel(f);
				}
			}
		});
	}
	
	public void updateMainPanel(File file) {
		updateFileListDetailsSplitPanel(file);
		mainPanel.setBackgroundColor(Color.WHITE);
		mainPanel.addWidget
		( BorderPanelWidgetFactory.create
				(context, fileListDetailsSplitPanel, BorderPanelWidget.CENTER));
	}
	
	public void init() {

		crossIcon     = ImageFactory.create(context, new armidale.api.io.File("images/icons/general/Stop16.gif"));
		tickIcon      = ImageFactory.create(context, new armidale.api.io.File("images/icons/general/Properties16.gif"));
		parentIcon    = ImageFactory.create(context, new armidale.api.io.File("images/icons/navigation/Up16.gif"));
		directoryIcon = ImageFactory.create(context, new armidale.api.io.File("images/icons/media/Play16.gif"));
		imageIcon     = ImageFactory.create(context, new armidale.api.io.File("images/icons/development/WebComponent16.gif"));

		frame                       = FrameFactory.create(context);
		mainPanel                   = BorderPanelFactory.create(context);
		fileListDetailsSplitPanel   = SplitPanelFactory.create(context);
		filePropertyImageSplitPanel = SplitPanelFactory.create(context);

		frame.addWindowCallback
		( new WindowCloseCallback
				(context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));


		frame.setTitle("File Display");
		frame.setSize(new Size(420, 300));
		frame.setAlignment(Frame.CENTER);
		frame.setResizable(true);

		File cwd = new File(System.getProperty("user.home"));
		updateMainPanel(cwd);

		frame.setContent(mainPanel);

		frame.show();
	}

	
		// A main method is required if you want to be able to run an application
		// stand-alone without using the armidale.api.LocalApplication class.
		// The pattern of this main is fairly common in Java applications,
		// in that it creates an instance of the enclosing class. In the case of
		// armidale, it must also set the context in which to run the application.
		// To run the application locally, we need to use the SwingContext which
		// will cause the various widget factories to create objects that are
		// implemented using swing. These object will therefore be displayed on the
		// local computer.
		//
	public static void main(String[] args) {

			// create an instance of SimpleApp
			//
		FileDisplayRedo  app  = new FileDisplayRedo();

			// set the apps context to an instance of SwingContext.
			//
		app.setContext(new SwingContext());

			// the application is now ready to go. So start it.
			//
		app.start();
	}
}