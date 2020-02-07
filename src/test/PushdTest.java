//author: Samritha Balamoni
//UTORID: 1004428737
package test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import commands.Pushd;
import commands.Pwd;
import driver.CommandHandler;
import driver.JShell;

import org.junit.Test;

public class PushdTest {
	private Pushd p;
	
	@Before
	public void setUp() {
		JShell shell = new JShell();	
	}

	@Test
	public void testDirectory() {
		CommandHandler.runCommand("mkdir hi");
		CommandHandler.runCommand("pushd hi");
		String actual = p.runCommand(null);
		String expected = "hi" + System.lineSeparator();
		assertEquals(expected, actual);
	}
	
	public void testToString() {
		String actual = p.toString();
		String expected = "pushd DIR\r\n" + 
		          "Saves the current working directory by pushing onto "
		          + "directory stack and\r\n" + 
		          "then changes the new current working directory to DIR. "
		          + "The push must be\r\n" + 
		          "consistent as per the LIFO behavior of a stack. The pushd "
		          + "command\r\n" + 
		          "saves the old current working directory in directory stack "
		          + "so that it can be\r\n" + 
		          "returned to at any time (via the popd command). The size of "
		          + "the directory\r\n" + 
		          "stack is dynamic and dependent on the pushd and the popd commands.";
		assertEquals(expected,actual);

}
}
