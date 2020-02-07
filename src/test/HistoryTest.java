//author: Samritha Balamoni
//UTORID: 1004428737
package test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import commands.History;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;

import org.junit.Test;

public class HistoryTest {

private History h;
ArrayList<String> allEntries = new ArrayList<String>();
	@Before
	public void setUp() {
		JShell shell = new JShell();
		JShell.setRoot(new Directory());
		h.add("mkdir d");
		h.add("ls");
		allEntries.add("mkdir");
		allEntries.add("ls");
		
	}
	
	@Test
	public void testGetAllEntries() {
		assertEquals(allEntries, h.getAllEntries());
	}

	@Test
	public void testHistory() {
		String actual = h.runCommand(null);
		String expected = "mkdir" + System.lineSeparator() +
				"ls" + System.lineSeparator();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAfterAddingMoreCommands() {
		h.add("pushd d");
		h.add("popd");
		String actual = h.runCommand(null);
		String expected = "1.mkdir" + System.lineSeparator() +
				"2.ls" + System.lineSeparator() + "3.pushd" + System.lineSeparator()
				+ "4.popd" + System.lineSeparator();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testUsingHistoryDepth() {
		String[] args = {"0"};
		String actual = h.runCommand(args);
		String expected =  "4.popd" + System.lineSeparator();
		assertEquals(expected, actual);
	}

}
