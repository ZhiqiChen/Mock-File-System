package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import commands.History;
import commands.Pwd;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;
public class PwdTest {
	private Pwd pwd = new Pwd();
	@Before
	public void setUp() {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
		JShell.setRoot(new Directory());
		JShell.setCurrDir(JShell.getRoot());
		JShell.setDirStack(new DirectoryStack());
		JShell.setUserEntries(new History());
		
		CommandHandler.runCommand("mkdir a/b/c");
		//NOTE: since Pwd has a constructor that does nothing no testing is required.
	}
	@Test
	/**
	 * test root directory or start of jshell
	 */
	public void testRoot() {
		String actual = pwd.runCommand(null);
		String expected = "/";
		assertEquals(expected,actual);
	}
	
	@Test
	/**
	 * if you go into a directory test pwd changed or not
	 */
	public void testDifferentDirectory() {
		CommandHandler.runCommand("cd a/b/c");
		String actual = pwd.runCommand(null);
		String expected = "/a/b/c";
		assertEquals(expected,actual);
	}
	
	@Test
	/**
	 * test if current dir in Jshell is the same value as pwd
	 * another directory
	 */
	public void testCurrentDir() {

		CommandHandler.runCommand("cd a/b/c");
		String actual = pwd.runCommand(null);
		String expected = JShell.getCurrDir().getFullPath();
		assertEquals(expected,actual);
	}
	
	@Test
	/**
	 * test is valid for null input and not for some random input
	 */
	public void testIsValid() {
		boolean actual = pwd.isValid(null);
		boolean expected = true;
		assertEquals(expected,actual);
		String arguments[] = {"example", "text"};
		expected = false;
		actual = pwd.isValid(arguments);
	}
	
	@Test
	/**
	 * testing to String returns
	 */
	public void testToString() {
		String actual = pwd.toString();
		String expected = "pwd\r\n" + 
		          "Print the current working directory (including the whole path).";
		assertEquals(expected,actual);
	}

}
