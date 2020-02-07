package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import commands.Cd;
import commands.History;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;

public class CdTest {
	Cd cd = new Cd();
	@Before
	public void setUp() {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
		JShell.setRoot(new Directory());
		JShell.setCurrDir(JShell.getRoot());
		JShell.setDirStack(new DirectoryStack());
		JShell.setUserEntries(new History());
		CommandHandler.runCommand("mkdir /a /b/x/../y/x/z /c /a/b/c/d/../e");
	}
	
	@Test
	/**
	 * tests cd to root
	 */
	public void testRoot() {
		String arguments[] = {"/"};
		cd.runCommand(arguments);
		Directory expected = JShell.getCurrDir();
		Directory actual = JShell.getRoot();
		assertEquals(expected, actual);
		
	}
	@Test
	/**
	 * tests cd returning ""
	 */
	public void testReturn() {
		String arguments[] = {"/"};
		String actual = cd.runCommand(arguments);
		String expected = "";
		assertEquals(expected, actual);
		
	}
	
	@Test
	/**
	 * tests cd to other dir using relative path
	 */
	public void testRelative() {
		String arguments[] = {"a/b/c"};
		cd.runCommand(arguments);
		Directory actual = JShell.getCurrDir();
		Directory  expected= Directory.findDir(JShell.getRoot(), "/a/b/c");
		assertEquals(expected, actual);
		
	}
	
	@Test
	/**
	 * tests cd to other dir using absolute path
	 */
	public void testFull() {
		String arguments[] = {"/a/b/c"};
		cd.runCommand(arguments);
		Directory actual = JShell.getCurrDir();
		Directory expected = Directory.findDir(JShell.getRoot(), "/a/b/c");
		assertEquals(expected, actual);
		
	}
	
	@Test
	/**expected
	 * tests cd for none existent directories check for no change in current dir
	 */
	public void testNoDirs() {
		String arguments[] = {"/a/x"};
		cd.runCommand(arguments);
		Directory expected = null;
		Directory actual = JShell.getCurrDir();
		assertEquals(expected, actual);
		
	}
	
	@Test
	/**
	 * check if isValid returns the correct boolean value
	 */
	public void testIsValid() {
		String arguments[] = {"s"};
		boolean actual = cd.isValid(arguments);
		boolean expected = true;
		assertEquals(expected, actual);
		
		String argument[] = {};
		actual = cd.isValid(argument);
		expected = false;
		assertEquals(expected, actual);
	}
	
	
	@Test
	/**
	 * check if toString returns the correct String
	 */
	public void testToString() {
		String actual = cd.toString();
		String expected = "cd DIR\r\n" + 
		          "Change directory to DIR, which may be relative to "
		          + "the current directory or\r\n" + 
		          "may be a full path. As with Unix, .. means a parent "
		          + "directory and a .\r\n" + 
		          "means the current directory. The directory must be /, "
		          + "the forward slash.\r\n" + 
		          "The foot of the file system is a single slash: /.";
		assertEquals(expected, actual);
	}

}
