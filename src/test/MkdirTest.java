package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import commands.Mkdir;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;

public class MkdirTest {
	Mkdir mkdir;
	@Before
	public void setUp() {
		JShell shell = new JShell();
		CommandHandler.runCommand("");
		mkdir = new Mkdir();
	}

	@Test
	public void testMkdir() {
		String arguments[] = {"/a"};
		
		mkdir.runCommand(arguments);
		Directory expectedDir = JShell.getRoot().getDirHead().get(0);
		String actual = expectedDir.getDirName();
		String expected = "a";
		assertEquals(expected, actual);
	}

}
