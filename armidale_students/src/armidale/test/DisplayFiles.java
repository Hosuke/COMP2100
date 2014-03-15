package armidale.test;

import java.io.File;
import java.util.ArrayList;

public class DisplayFiles {
	private File f = null;
	private File[] paths;
	private String sPath = "";
	private ArrayList<String> files = new ArrayList<String>();
	//private ArrayList<Boolean> isDirectory = new ArrayList<Boolean>();
	private ArrayList<Boolean> isPicture = new ArrayList<Boolean>();
	private ArrayList<Boolean> isReadable = new ArrayList<Boolean>();
	private ArrayList<Boolean> isWritable = new ArrayList<Boolean>();
	private ArrayList<Boolean> isExecutable  = new ArrayList<Boolean>();
	private ArrayList<Integer> length  = new ArrayList<Integer>();
	private ArrayList<Long> lastModified  = new ArrayList<Long>();
	private int fileNum = 0;
	//private String parent = "";
	private String currentPath = "";

	public DisplayFiles(String currentPath)
	{
		this.currentPath = currentPath;
	}

	public void listFiles()
	{
		try{      
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
					isReadable.add(path.canRead());
					//isDirectory.add(path.isDirectory());
					sPath = path.getName().toString();
					files.add(sPath);

					// check whether the file is a picture
					if (sPath.contains(".png") || sPath.contains(".jpg") || sPath.contains(".bmp") || sPath.contains(".gif") ||sPath.contains(".PNG") || sPath.contains(".JPG") || sPath.contains(".BMP") || sPath.contains(".GIF"))
					{
						isPicture.add(true);
						path.getAbsoluteFile();
					}
					else
						isPicture.add(false);

					// check whether the file is readable 
					isReadable.add(path.canRead());
					// check whether the file can be written
					isWritable.add(path.canWrite());
					//check whether the ban be executed
					isExecutable.add(path.canExecute()); 
					// return the length of the file
					length.add((int) path.length());
					// return lastModified time
					lastModified.add(path.lastModified());
				}
			}
		}catch(Exception e){
			// if any error occurs
			e.printStackTrace();
		}

		// count the number of files
		fileNum = files.size();
	}

	public int getFileNum()
	{
		return fileNum;
	}

	public ArrayList<String> getFiles()
	{
		return files;
	}
/**
	public ArrayList<Boolean> getisDirectory()
	{
		return isDirectory;
	}
**/
	public ArrayList<Boolean> getisPicture()
	{
		return isPicture;
	}

	public void changeDirectory(String fileName)
	{
		currentPath += "/" + fileName;
		System.out.println("currentPath " + currentPath);
	}

	public String getNewPath()
	{
		return currentPath;
	}

	public ArrayList<Boolean> getisReadable()
	{
		return isReadable;
	}
	
	
	public ArrayList<Boolean> getisWritable()
	{
		return isWritable;
	}
	
	public ArrayList<Boolean> getisExecutable()
	{
		return isExecutable;
	}
	
	public ArrayList<Integer> getlength()
	{
		return length;
	}
	
	public ArrayList<Long> getlastModified()
	{
		return lastModified;
	}
}
