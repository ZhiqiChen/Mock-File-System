package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import commands.History;
import commands.Load;
import commands.Ls;
import commands.Tree;
import driver.CommandHandler;
import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;

public class LoadTest {
	private Load load;
	@Before
	public void setUp() {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
		JShell.setRoot(new Directory());
		JShell.setCurrDir(JShell.getRoot());
		JShell.setDirStack(new DirectoryStack());
		JShell.setUserEntries(new History());
		load  = new Load();
		String arguments[] = {"loadTest"};
		load.runCommand(arguments);
	}

	@Test
	/**
	 * tests the current dir of the jshell
	 */
	public void testPwd() {
		
		String actual = JShell.getCurrDir().getFullPath();
		String expected = "/a/b/c";
		assertEquals(expected, actual);
	}
	
	
	@Test
	/**
	 * tests history of loaded Jshell
	 */
	public void testDirStack() {
		DirectoryStack dirstackExpected = new DirectoryStack();
		dirstackExpected.push(Directory.findDir(JShell.getRoot(), "/a/b"));
		dirstackExpected.push(Directory.findDir(JShell.getRoot(), "/"));
		dirstackExpected.push(Directory.findDir(JShell.getRoot(), "/b/y/x/z"));
		DirectoryStack dirstackActual = JShell.getDirStack();
		for (int i = 0; i < 3 ; i++) {
			Directory actual = dirstackActual.peek(i);
			Directory expected = dirstackExpected.peek(i);
			assertEquals(expected, actual);
		}
	}
	
	@Test
	/**
	 * tests the history of jShell
	 */
	public void testHistory() {
		ArrayList<String> hisActual = JShell.getUserEntries().getAllEntries();
		ArrayList<String> hisExpected = new ArrayList<String>();
		hisExpected.add("mkdir /a /b/x/../y/x/z /c /a/b/c/d/../e");
		hisExpected.add("echo exampleText > exampleFile");
		hisExpected.add("cd /a/b/c");

		
		for (int i = 0; i < hisActual.size(); i++) {
			String actual = hisActual.get(i);
			String expected = hisExpected.get(i);
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * 
	 */
	public void testFileSystem() {
		Tree tree = new Tree();
		String actual = tree.runCommand(null);
		System.out.println(actual);
	}

}
