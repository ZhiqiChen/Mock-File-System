package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import commands.Find;
import commands.History;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;

public class FindTest {
  Find find = new Find();
  @Before
  public void setUp() {
      JShell shell = new JShell();
      //i must manually reset variables because they are static
      JShell.setRoot(new Directory());
      JShell.setCurrDir(JShell.getRoot());
      JShell.setDirStack(new DirectoryStack());
      JShell.setUserEntries(new History());
      
      CommandHandler.runCommand("echo \"hi\" > a");
      CommandHandler.runCommand("echo \"hi\" > b");
      CommandHandler.runCommand("mkdir b");
      CommandHandler.runCommand("mkdir a");
      CommandHandler.runCommand("cd b");
      CommandHandler.runCommand("echo \"hi\" > b");
      // NOTE: since Find has a constructor that does nothing 
      // no testing is required.
  }
  @Test
  /**
   * Validate if command works for directories
   */
  public void testFindDir() throws Exception {
    /*
    String[] args = {"a"};
    Mkdir mkdir = new Mkdir();
    mkdir.runCommand(args);
    */
    String[] args2 = {"/", "-type", "d", "-name", "\"a\""};
    String actual = "";
    actual = find.runCommand(args2);
    String expected = "/\n";
    assertEquals(expected,actual);
  }
  @Test
  /**
   * Validate if command works for files
   */
  public void testFindFile() throws Exception {
    String[] args2 = {"/", "-type", "f", "-name", "\"a\""};
    String actual = "";
    actual = find.runCommand(args2);
    String expected = "/\n";
    assertEquals(expected,actual);
  }
  @Test
  /**
   * Validate if command works for files recursively
   */
  public void testFindFileR() throws Exception {
    String[] args2 = {"/", "-type", "f", "-name", "\"b\""};
    String actual = "";
    actual = find.runCommand(args2);
    String expected = "/\n/b\n";
    assertEquals(expected,actual);
  }
	
	@Test
	/**
	 * check if isValid returns the correct boolean value
	 */
	public void testIsValid() {
		String arguments[] = {"s", "b", "c", "c", "b"};
		boolean actual = find.isValid(arguments);
		boolean expected = true;
		assertEquals(expected, actual);
		
		String argument[] = {};
		actual = find.isValid(argument);
		expected = false;
		assertEquals(expected, actual);
	}
	
	
	@Test
	/**
	 * check if toString returns the correct String
	 */
	public void testToString() {
		String actual = find.toString();
		String expected ="Find directory or file given";

		assertEquals(expected, actual);
	}

}
