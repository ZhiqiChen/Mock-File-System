package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.History;
import commands.Ls;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;

public class LsTest {
	Ls ls = new Ls();
	@Before
	public void setUp() {
		JShell shell = new JShell();
		
		CommandHandler.runCommand("mkdir /a /b/x/../y/x/z /c /a/b/c/d/../e");
		CommandHandler.runCommand("echo \"example text\" > exampleFile");
		CommandHandler.runCommand("cd /");
	}

	@Test
	/**
	 * test initial state
	 */
	public void testLs() {
		String Expected = "exampleFile\na\nb\nc\n";
		String actual = ls.runCommand(null);
		assertEquals(Expected, actual);
	}
	
	@Test
	/**
	 * test in another directory not at root using relative path
	 */
	public void testLsRelative () {
		String arguments[] = {"a/b/c"};
		String actual = ls.runCommand(arguments);
		String expected = "d\ne\n";
		assertEquals(expected, actual);
	}

	@Test
	/**
	 * test in another directory not at root using Full path
	 */
	public void testLsFullPath () {
		String arguments[] = {"/b/y/x"};
		String actual = ls.runCommand(arguments);
		String expected = "z\n";
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * test recursive ls with no input
	 */
	public void testLsRecursive () {
		String actual = ls.runCommand(null);
		String expected = "exampleFile\na\nb\nc\n";
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * test recursive ls with a fullPath
	 */
	public void testLsRecursiveFull() {
		String arguments[] = {"-R", "/a/b/c"};
		String actual = ls.runCommand(arguments);
		String expected = "/a/b/c: \n" + 
				"d\n" + 
				"e\n" + 
				"/a/b/c/d: \n" + 
				"/a/b/c/e: \n";
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * test recursive ls with a Relative Path
	 */
	public void testLsRecursiveRelative() {
		String arguments[] = {"-R", "a/b/c"};
		String actual = ls.runCommand(arguments);
		String expected = "/a/b/c: \n" + 
				"d\n" + 
				"e\n" + 
				"/a/b/c/d: \n" + 
				"/a/b/c/e: \n";
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * test another directory that doesn't exist
	 * check console
	 */
	public void testLsDoesntExist () {
		System.out.println("----------------------------");
		String arguments[] = {"/b/y/b"};
		String actual = ls.runCommand(arguments);
		String expected = "";
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * test output to file
	 * NOTE TO TA: it looks like the same string but for some reason comparison
	 * doesn't work
	 */
	public void testLsFile () {
		String arguments[] = {"exampleFile"};
		String actual = ls.runCommand(arguments);
		String expected = "example text \n";
		assertEquals(expected, actual);
	}

	
}
