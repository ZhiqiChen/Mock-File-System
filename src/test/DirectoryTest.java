//author: Samritha Balamoni
//UTORID: 1004428737

package test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import structures.*;
import org.junit.Test;
import driver.JShell;

public class DirectoryTest {
	Directory dir;
	ArrayList<Directory> children_dir = new ArrayList<Directory>();
	ArrayList<File> children_file = new ArrayList<File>();
	File f;
	
	@Before
	public void setup() throws javax.naming.InvalidNameException, FileEmptyException {
		dir = new Directory("dir", JShell.getRoot());
		children_dir.add(new Directory("dir1", dir));
		children_dir.add(new Directory("dir2", dir));
		for(Directory child: children_dir) {
			dir.addChild(child);
		}
		
		children_file.add(new File("file1", "file1"));
		children_file.add(new File("file1", "file1"));
		for(File child: children_file) {
			dir.addFile(child);
		}
		
		f = new File("testFile", "oldtext");
		
	}
	
	/**
	 * testing set name
	 */
	
	@Test
	  public void testSetGetName() throws InvalidNameException {
	    dir.SetDirName("new");
	    String expected = "new";
	    String actual = dir.getDirName();
	    assertEquals(expected, actual);
	  }
	
	/**
	 * testing set empty name
	 */
	
	  @Test(expected = InvalidNameException.class)
	  public void testSetEmptyName() throws Exception {
	    dir.SetDirName("");
	  }
	  /**
		 * testing set null name
		 */

	  @Test(expected = InvalidNameException.class)
	  public void testSetNullName() throws Exception {
	    dir.SetDirName(null);
	  }
	  
	  /**
		 * testing get file by name
		 */
	  
	  @Test
	  public void testGetFileByName() {
		File expected = children_file.get(0);
		File actual = dir.findFile("file1");
	    assertEquals(expected, actual);
	  }
	  
	  /**
		 * testing get dir by name
		 */
	  
	  
	  @Test
	  public void testGetDirByName() {
		Directory expected = children_dir.get(0);
		Directory actual = dir.findDir("dir1");
		assertEquals(expected, actual);
	  }
	  
	  /**
		 * testing overwriting the file
		 */
	  
	  
	  @Test
	  public void testOverwriteFile() {
		dir.overwriteFile("file1", "new");
		String expected = children_file.get(0).getContents();
		String actual = "new";
		assertEquals(expected, actual);
	  }
	  /**
		 * testing appending to the file
		 */
	  @Test
	  public void testAppendToFile() {
		  dir.appendToFile("file1", "text");
		  String expected = children_file.get(0).getContents();
		  String actual = "new text";
		  assertEquals(expected, actual);
	  }


	

}
