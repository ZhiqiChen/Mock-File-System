package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import commands.History;
import commands.Popd;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;

public class PopdTest {

	private Popd pop = new Popd();
	@Before
	public void setUp() {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
		JShell.setRoot(new Directory());
		JShell.setCurrDir(JShell.getRoot());
		JShell.setDirStack(new DirectoryStack());
		JShell.setUserEntries(new History());
		
		//NOTE: since Popd has a constructor that does nothing no testing is required.
	}
	@Test
	/**
	 * test empty stack
	 */
	public void testEmptyStack() {
		String actual = pop.runCommand(null);
		String expected = "popd" + System.lineSeparator() + "Sorry, stack is empty";
		assertEquals(expected,actual);
	}
	
	@Test
	/**
	 * testing to String returns
	 */
	public void testToString() {
		String actual = pop.toString();
		String expected = "popd\r\n" + 
		          "Remove the top entry from the directory stack, "
		          + "and cd into it. The removal\r\n" + 
		          "must be consistent as per the LIFO behavior of a"
		          + " stack. The popd\r\n" + 
		          "command removes the top most directory from the "
		          + "directory stack and\r\n" + 
		          "makes it the current working directory. If there "
		          + "is no directory onto the\r\n" + 
		          "stack, then give appropriate error message.";
		assertEquals(expected,actual);
	}
}

