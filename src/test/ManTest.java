package test;

import static org.junit.Assert.*;

import org.junit.Test;
import commands.Man;
import driver.CommandHandler;
import driver.JShell;

public class ManTest {
  Man man = new Man();
	public void setUp() {
		JShell shell = new JShell();
		CommandHandler.runCommand("");
	}

	@Test
	public void testMan() {
	    String[] args2 = {"cd"};
	    String actual = man.runCommand(args2);
	    String expected = "\"cd DIR\\r\\n\" + \r\n" + 
	        "          \"Change directory to DIR, which may be relative to \"\r\n" + 
	        "          + \"the current directory or\\r\\n\" + \r\n" + 
	        "          \"may be a full path. As with Unix, .. means a parent \"\r\n" + 
	        "          + \"directory and a .\\r\\n\" + \r\n" + 
	        "          \"means the current directory. The directory must be /, \"\r\n" + 
	        "          + \"the forward slash.\\r\\n\" + \r\n" + 
	        "          \"The foot of the file system is a single slash: /.\";";
	    assertEquals(expected,actual);
	}

}
