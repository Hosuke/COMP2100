// File/Module name: (FileTest.java)
// Author: <Yunen He>, u<5251400>
// Date: < 20 March 2013>
// Description: 
// Run as JUnit Test

package armidale.api.io;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FileTest {
	
	
	@Before
	public void createFiles() throws IOException
	{
		  String homeDir = System.getProperty("user.home");
		  File homef = new File(homeDir);
		  java.io.File f = new java.io.File (homeDir+"test.txt");
		  boolean fCreated = false;
		  fCreated = f.createNewFile();
		  
		  if (fCreated) {
			  System.out.println();
		  } else {
			  System.out.println();
		  }
		  
		  java.io.File f2 = new java.io.File (homeDir+"/.armidale/filesystem/test02.txt");
		  boolean f2Created = f2.createNewFile();
		  
		  if (f2Created) {
			  System.out.println();
		  } else {
			  System.out.println();
		  }
		  
		
	
	}
	
	@Test
	public void testFile() throws IOException
	{
		// the local computer's file system
		  String filePathOne = "/test.txt";
		  File fileOne = new File(filePathOne);
		  
		  // this file exits in home directory
		  assertTrue(fileOne.exists());

		  
		  // the '~/.armidale/filesystem' directory
		  String filePathTwo = "test02.txt";
		  File fileTwo = new File(filePathTwo);
		  
		  // this file exits in the '~/.armidale/filesystem' directory
		  assertTrue(fileTwo.exists());
	}
}
