//author: Samritha Balamoni
//UTORID: 1004428737


package test;

import static org.junit.Assert.*;

import javax.naming.InvalidNameException;

import org.junit.Before;
import org.junit.Test;
import commands.Tree;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;
import structures.File;
import structures.FileEmptyException;

public class TreeTest {

	private Tree tree = new Tree();
	@Before
	public void setUp() {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
		JShell.setRoot(new Directory());
		JShell.setCurrDir(JShell.getRoot());
		//NOTE: since Pwd has a constructor that does nothing no testing is required.
	}
	@Test
	/**
	 * test root directory or start of jshell
	 */
	public void testRoot() {
		String actual = JShell.getRoot().toString();
		String expected = "/";
		assertEquals(expected, actual);
	}
	
	@Test
	/**
	 * File and Directory within root directory
	 */
	public void testDifferentDirectoryAndFile() throws InvalidNameException, FileEmptyException {
		Directory d1 = new Directory("d1", JShell.getRoot());
		File d2 = new File("d2", "lol");
		JShell.getRoot().addChild(d1);
		JShell.getRoot().addFile(d2);
		String actual = tree.runCommand(null);
		String expected = "/" + System.lineSeparator() + "\td1"
				+ System.lineSeparator() + "\td2";
		
		assertEquals(expected, actual);
		
	}
	/**
	 * testing directories within directories
	 */
	
	public void testDirectoryWithinDirectory() {
		Directory d1 = new Directory("d1", JShell.getRoot());
		Directory d2 = new Directory("d2", JShell.getRoot());
		JShell.getRoot().addChild(d1);
		JShell.getRoot().addChild(d2);
		d1.addChild(new Directory ("d3", d1));
		d1.addChild(new Directory ("d4", d2));
		String actual = tree.runCommand(null);
		String expected = "/" + System.lineSeparator() 
		+ "\td1"+ System.lineSeparator() 
		+ "\t\td3" + System.lineSeparator()
		+ "\td2" + System.lineSeparator()
		+"\t\td4" + System.lineSeparator();
		
		assertEquals(expected, actual);
		}
	
	/**
	 * testing to String returns
	 */
	
	public void testToString() {
		String actual = tree.toString();
		String expected = "display a tree style representation of the file system";
		assertEquals(expected,actual);
	}

	
	
}


