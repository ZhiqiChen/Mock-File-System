//author: Samritha Balamoni
//UTORID: 1004428737

package test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import commands.Exit;
import driver.CommandHandler;
import driver.JShell;


import org.junit.Test;

public class ExitTest {
	
	private Exit e = new Exit();
	
	@Before
	public void setUp() {
		JShell shell = new JShell();
		CommandHandler.runCommand("mkdir a/b/c");
		//NOTE: since Exit has a constructor that does nothing no testing is required.
	}

	@Test
	public void ExitTest() {
		String expected = "exit";
		String actual = e.runCommand(null);
		assertEquals(expected, actual);
	}

}
