package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import commands.Echo;
import commands.History;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;

public class EchoTest {
	Echo echo;
	@Before
	public void setUp() {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
		JShell.setRoot(new Directory());
		JShell.setCurrDir(JShell.getRoot());
		JShell.setDirStack(new DirectoryStack());
		JShell.setUserEntries(new History());
		echo = new Echo();
	}
	@Test
	/**
	 * check if echo with no redirection returns the string
	 */
	public void testConsole() {
		String arguments[] = {"\"example with great feeel\""};
		String actual = echo.runCommand(arguments);
		String expected = "example with great feeel";
		assertEquals(expected, actual);
		
	}
	
	@Test
	/**
	 * check if echo with no value
	 */
	public void testNoValue() {
		String arguments[] = {"\"\""};
		String actual = echo.runCommand(arguments);
		String expected = "";
		assertEquals(expected, actual);
		
	}
	
	
	@Test
	/**
	 * check if isValid returns the correct boolean value
	 */
	public void testIsValid() {
		String arguments[] = {"s", "b"};
		boolean actual = echo.isValid(arguments);
		boolean expected = true;
		assertEquals(expected, actual);
		
		String argument[] = {};
		actual = echo.isValid(argument);
		expected = false;
		assertEquals(expected, actual);
	}
	
	
	@Test
	/**
	 * check if toString returns the correct String
	 */
	public void testToString() {
		String actual = echo.toString();
		String expected = "Echo [String] : prints the string to console";
		assertEquals(expected, actual);
	}
	
	

}
