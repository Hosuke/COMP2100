import java.io.File;
import java.util.ArrayList;
import armidale.api.context.*;
import armidale.api.context.platform.*;
import armidale.api.gui.*;

class FileDisplay {
private ListBox      listBox;
private ListData     listBoxItems;
private BorderPanel  listPanel;
private String currentPath = "/Users/heyunen/";

public FileDisplay(Context context, TestImages testImages) 
{
    // Display directories in a directory
    File f = null;
    File[] paths;
    int fileNum = 0;
    String sPath = "";
    ArrayList<String> files = new ArrayList<String>();
    ArrayList<Boolean> isDirectory = new ArrayList<Boolean>();

    try{
        //reset 
        if (!files.isEmpty())
            files.clear();
        
        if (!isDirectory.isEmpty())
            isDirectory.clear();
       // create new file
       f = new File(currentPath);
       
       // returns pathnames for files and directory
       paths = f.listFiles();
       
       // for each pathname in pathname array
       for(File path:paths)
       {
          // prints file and directory paths
         if (!path.isHidden())
         {
             isDirectory.add(path.isDirectory());
             sPath = path.getName().toString();
             files.add(sPath);
             System.out.println(path.getAbsoluteFile());
         }
       }
    }catch(Exception e){
       // if any error occurs
       e.printStackTrace();
    }
    
    // count the number of files
    fileNum = files.size();
    System.out.println(fileNum);
    
    // UI
    listBox = ListBoxFactory.create(context);
    //listBox.addListBoxCallback(new ListBoxCallbackDebug(context));
    listBoxItems = ListDataFactory.create(context);
    listBox.setListData(listBoxItems);
    listBoxItems.setItemCount(fileNum);

    Label[]     tempLabels          = new Label[fileNum];
    
    for (int i = 0; i < tempLabels.length; i++) {
      tempLabels[i] = LabelFactory.create(context);
      tempLabels[i].addActionCallback(new ChangeDirectory());
      tempLabels[i].setText(files.get(i));
      
      if (isDirectory.get(i))
          tempLabels[i].setImage(testImages.rightArrow);
      
      tempLabels[i].setAlignment(Label.WEST);
      tempLabels[i].setTextPosition(Label.EAST);
      tempLabels[i].setBackgroundColor(Color.RED);
    }
    listBoxItems.setItems(0, tempLabels);
    listPanel = BorderPanelFactory.create(context);
    listPanel.addWidget(BorderPanelWidgetFactory.create(context, listBox, BorderPanelWidget.WEST));
  }

  public final static void main(String[] args) {
    SwingContext     swingContext  = new SwingContext();
    TestImages       testImages    = new TestImages(swingContext);
    testImages.loadImages();
    StandaloneFrame  frame         = new StandaloneFrame(swingContext, new FileDisplay(swingContext, testImages).widget());
 }
  
  public Widget widget() {
    return listPanel;
  }
  
  public  class ChangeDirectory implements ActionCallback
  {
      public ChangeDirectory()
      {
          //System.out.println("wuwu");
      }
    @Override
    public void actionPerformed(Action action) {
        System.out.println("haha");
    }
  }
