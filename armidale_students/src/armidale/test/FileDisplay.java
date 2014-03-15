package armidale.test;

import java.util.ArrayList;
import java.util.Date;

import armidale.api.Application;
import armidale.api.context.Context;
import armidale.api.context.platform.SwingContext;
import armidale.api.gui.Color;
import armidale.api.gui.Frame;
import armidale.api.gui.FrameFactory;
import armidale.api.gui.Image;
import armidale.api.gui.ImageFactory;
import armidale.api.gui.Label;
import armidale.api.gui.LabelFactory;
import armidale.api.gui.ListBox;
import armidale.api.gui.ListBoxCallback;
import armidale.api.gui.ListBoxFactory;
import armidale.api.gui.ListData;
import armidale.api.gui.ListDataFactory;
import armidale.api.gui.Size;
import armidale.api.gui.SplitPanel;
import armidale.api.gui.SplitPanelFactory;
import armidale.api.gui.Table;
import armidale.api.gui.TableData;
import armidale.api.gui.TableDataFactory;
import armidale.api.gui.TableFactory;
import armidale.api.gui.WindowCloseCallback;
import armidale.api.io.File;

public class FileDisplay extends Application {

	private static DisplayFiles displayFiles;
	private static String home = System.getProperty("user.home");
	private String currentPath = "";
	private ArrayList<String> files = new ArrayList<String>();
	//private ArrayList<Boolean> isDirectory = new ArrayList<Boolean>();
	private ArrayList<Boolean> isPicture = new ArrayList<Boolean>();
	private ArrayList<Boolean> isReadable = new ArrayList<Boolean>();
	private ArrayList<Boolean> isWritable = new ArrayList<Boolean>();
	private ArrayList<Boolean> isExecutable  = new ArrayList<Boolean>();
	private ArrayList<Integer> length  = new ArrayList<Integer>();
	private ArrayList<Long> lastModified  = new ArrayList<Long>();
	
	private int fileNum = 0;
	private SplitPanel  vSplitPanel;

	private  Table      table;
	private  TableData  tableItems;

	private  ListBox      listBox;
	private  ListData     listBoxItems;
	private  int[] indices;
	
	private  Label  noFoundLabel;

	public void init() {
		Frame frame = FrameFactory.create(context);
		frame.addWindowCallback
		( new WindowCloseCallback
				(context, WindowCloseCallback.STOP_APPLICATION_ON_CLOSE));
		frame.setTitle("File Display");
		frame.setSize(new Size(800, 500));
		frame.setAlignment(Frame.CENTER);
		frame.setResizable(true);
		
		// test image
		SwingContext swingContext = new SwingContext();
		TestImages testImages = new TestImages(swingContext);
		testImages.loadImages();

		// Display directories in a directory
		currentPath = home;
		displayFiles.listFiles();
		fileNum = displayFiles.getFileNum();
		//fileNum = 100;
		files = displayFiles.getFiles();
		//isDirectory = displayFiles.getisDirectory();
		isPicture = displayFiles.getisPicture();
		isReadable = displayFiles.getisReadable();
		isWritable = displayFiles.getisWritable();
		isExecutable = displayFiles.getisExecutable();
		length = displayFiles.getlength();
		lastModified = displayFiles.getlastModified();

		// file
		listBox = ListBoxFactory.create(context);
		listBoxItems = ListDataFactory.create(context);
		listBox.setListData(listBoxItems);
		indices = new int[1];
		listBoxItems.setItemCount(fileNum);

		Label[]     tempLabels  = new Label[fileNum];
		for (int i = 0; i < tempLabels.length; i++) {
			tempLabels[i] = LabelFactory.create(context);
			tempLabels[i].setText(files.get(i));
			tempLabels[i].setImage(testImages.rightArrow);
			
			tempLabels[i].setAlignment(Label.WEST);
			tempLabels[i].setTextPosition(Label.EAST);
			tempLabels[i].setBackgroundColor(Color.RED);
		}
		listBoxItems.setItems(0, tempLabels);
		listBox.addListBoxCallback(new DisplayProperty(context,indices));

		SplitPanel hSplitPanel = SplitPanelFactory.create(context);
		hSplitPanel.setOrientation(SplitPanel.HORIZONTAL);
		
		//
		hSplitPanel.setResizeWeight(0.3f);
		hSplitPanel.setDividerLocation(0.5f);
		hSplitPanel.setExpandable(true);

		vSplitPanel  = SplitPanelFactory.create(context);
		vSplitPanel.setOrientation(SplitPanel.VERTICAL);
		vSplitPanel.setResizeWeight(1f);
		vSplitPanel.setDividerLocation(0.5f);
		vSplitPanel.setExpandable(true);

		// table
		table = TableFactory.create(context);
	    tableItems = TableDataFactory.create(context);
	    tableItems.setItemRowCount(5);
	    tableItems.setItemColumnCount(2);
	    tableItems.setItemColumnTitle(0, "Property");
	    tableItems.setItemColumnTitle(1, "Value");
	    table.setTableData(tableItems);

	    Label[][]  tableLabels  = new Label[5][2];
	    for (int i = 0; i < 5; i++) {	        
	      for (int j = 0; j < 2; j++) {
	        tableLabels[i][j] = LabelFactory.create(context);
	        tableLabels[i][j].setAlignment(Label.WEST);
	        tableLabels[i][j].setTextPosition(Label.EAST);
	        tableLabels[i][j].setBackgroundColor(Color.RED);
	      }
	      tableItems.setItemRow(i, tableLabels[i]);
	    }	    
	    
	    // readable
	    tableLabels[0][0].setText("Can Read");
	    // writable
	    tableLabels[1][0].setText("Can write");
	    // can be executed
	    tableLabels[2][0].setText("Can Execute");

	    // Length
	    tableLabels[3][0].setText("Length");
	    // lastModified
	    tableLabels[3][0].setText("Last Modified");
	    
		vSplitPanel.setTopWidget(table);
		hSplitPanel.setLeftWidget(listBox);
		hSplitPanel.setRightWidget(vSplitPanel);

		frame.setContent(hSplitPanel);
		frame.show();
	}

	public static void main(String[] args) {
		FileDisplay  app  = new FileDisplay();
		displayFiles = new DisplayFiles(home);
		app.setContext(new SwingContext());
		app.start();
	}

	public class DisplayProperty implements ListBoxCallback
	{
		Context context;
		int[] indices = new int[1];
		int index = 0;

		public DisplayProperty(Context context,int[] indices)
		{
			this.context = context;
			this.indices = indices;
		}

		@Override
		public void itemsSelected(ListBox listBox, int[] indices) {
			index = indices[0];
			System.out.println(index);
			System.out.println(isPicture.get(index));
			
			if (isPicture.get(index))
			{
				// image label
				// image
				Size size = new Size(1,1);
				Image image = ImageFactory.create(context);
				Label label = LabelFactory.create(context);
				File f = new File("../../" + files.get(index));
				image.setFile(f);
				size = image.getSize();
				
				image.setSize(new Size(500,350));
				
				label = LabelFactory.create(context);
				label.setImage(image);
				label.setAlignment(Label.CENTER);
				label.setTextPosition(Label.SOUTH);
				
				vSplitPanel.setBottomWidget(label);
			}
			else
			{
				noFoundLabel = LabelFactory.create(context);
				noFoundLabel.setText("No image found");
				noFoundLabel.setAlignment(Label.CENTER);
				noFoundLabel.setTextPosition(Label.SOUTH);
				vSplitPanel.setBottomWidget(noFoundLabel);
			}
			
			// display property
			// table
			table = TableFactory.create(context);
		    tableItems = TableDataFactory.create(context);
		    tableItems.setItemRowCount(5);
		    tableItems.setItemColumnCount(2);
		    tableItems.setItemColumnTitle(0, "Property");
		    tableItems.setItemColumnTitle(1, "Value");
		    table.setTableData(tableItems);
		    
		    Label[][]  tableLabels  = new Label[5][2];
		    for (int i = 0; i < 5; i++) {	        
		      for (int j = 0; j < 2; j++) {
		        tableLabels[i][j] = LabelFactory.create(context);
		        //tableLabels[i][j].setImage(testImages.checkIcon);
		        tableLabels[i][j].setAlignment(Label.WEST);
		        tableLabels[i][j].setTextPosition(Label.EAST);
		        tableLabels[i][j].setBackgroundColor(Color.RED);
		      }
		      tableItems.setItemRow(i, tableLabels[i]);
		    }
		    // readable
		    tableLabels[0][0].setText("Can Read");
		    tableLabels[0][1].setText(isReadable.get(index).toString());
		    
		    // writable
		    tableLabels[1][0].setText("Can write");
		    tableLabels[1][1].setText(isWritable.get(index).toString());
	
		    // can be executed
		    tableLabels[2][0].setText("Can Execute");
		    tableLabels[2][1].setText(isExecutable.get(index).toString());
		    
		    // Length
		    tableLabels[3][0].setText("Length");
		    tableLabels[3][1].setText(length.get(index).toString() + " bytes");
		    
		    // lastModified
		    tableLabels[3][0].setText("Last Modified");
		    long millisec;
	        // date and time
		    millisec = lastModified.get(index);
	        Date dt = new Date(millisec);
		    tableLabels[3][1].setText(dt.toString());
		    
		    vSplitPanel.setTopWidget(table);
		}
	}
}

