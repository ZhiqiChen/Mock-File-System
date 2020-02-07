package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.Cat;
import commands.History;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;

public class CatTest {
	Cat cat;
	@Before
	public void setUp() {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
				JShell.setRoot(new Directory());
				JShell.setCurrDir(JShell.getRoot());
				JShell.setDirStack(new DirectoryStack());
				JShell.setUserEntries(new History());

		CommandHandler.runCommand("echo \"yes\" > me");
		CommandHandler.runCommand("mkdir /a/b");
		CommandHandler.runCommand("cd /a/b");
		CommandHandler.runCommand("echo \"yess\" > you");
		CommandHandler.runCommand("cd /");
		cat = new Cat();
		//NOTE: Since Cat have constructor that only initialize no testing required.
	}

	@Test
	/**
	 * check if returns empty string for non eixting file
	 */
	public void testNonExistingFile() {
		String arguments[] = {"randomFile"};
		String actual = cat.runCommand(arguments);
		String expected = "";
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * test simple output of a file
	 */
	public void testOutput() {
		String arguments[] = {"me"};
		String expected = "yes\n\n\n";
		String actual =  cat.runCommand(arguments);;
		assertEquals(expected, actual);
	}

	@Test
	/**
	 * relative and full path testing
	 */
	public void testOutputWithDifferentPath() {
		//test concatenation and 
		String arguments[] = {"/a/b/you"};
		String expected = "yess\n\n\n";
		String actual =  cat.runCommand(arguments);
		assertEquals(expected, actual);
		String argument[] = {"a/b/you"};
		actual =  cat.runCommand(argument);
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * test more than one arguments
	 */
	public void testOutputConcatentation() {
		//test concatenation
		String arguments[] = {"/me", "a/b/you", "me"};
		String expected = "yes\n\n\nyess\n\n\nyes\n\n\n";
		String actual =  cat.runCommand(arguments);;
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * check if isValid returns the correct boolean value
	 */
	public void testIsValid() {
		String arguments[] = {"me"};
		boolean actual = cat.isValid(arguments);
		boolean expected = true;
		assertEquals(expected, actual);
		
		String argument[] = {};
		actual = cat.isValid(argument);
		expected = true;
		assertEquals(expected, actual);
	}
	
	
	@Test
	/**
	 * check if toString returns the correct String
	 */
	public void testToString() {
		String actual = cat.toString();
		String expected = "cat FILE1 [FILE2 �]\r\n" + 
		          "Display the contents of FILE1 and other files (i.e. File2 �.)"
		          + " concatenated\r\n" + 
		          "in the shell. You may want to use three line breaks to "
		          + "separate the contents of\r\n" + 
		          "one file from the other file.";
		assertEquals(expected, actual);
	}
}
