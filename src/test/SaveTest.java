package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import commands.History;
import commands.Save;
import driver.*;
import structures.Directory;
import structures.DirectoryStack;
//Zhiqi Chen 1005269870
public class SaveTest {
	private Save save;
	@Before
	public void setUp() throws Exception {
		JShell shell = new JShell();
		//i must manually reset variables because they are static
		JShell.setRoot(new Directory());
		JShell.setCurrDir(JShell.getRoot());
		JShell.setDirStack(new DirectoryStack());
		JShell.setUserEntries(new History());
		
		save = new Save();
		CommandHandler.runCommand("mkdir /a /b/x/../y/x/z /c /a/b/c/d/../e");
		CommandHandler.runCommand("echo \"exampleText\" > exampleFile");
		
		Directory dir1, dir2, dir3;
		dir1 = Directory.findDir(JShell.getRoot(), "/a/b");
		dir2 = Directory.findDir(JShell.getRoot(), "/");
		dir3 = Directory.findDir(JShell.getRoot(), "/b/y/x/z");
		CommandHandler.runCommand("cd /a/b/c");
		DirectoryStack dirStack = new DirectoryStack();
		dirStack.push(dir1);
		dirStack.push(dir2);
		dirStack.push(dir3);
		JShell.setDirStack(dirStack);

		//NOTE: default constructor only inialize no need for testing.
	}

	@Test
	/**
	 * While this is only one function this tests 
	 * saving of directory, files, current directory, history, directory stack
	 * as well as formatting of them
	 * 
	 * for relative path
	 */
	public void testSaveRelative() throws IOException{

		String arguments[] = {"saveTestActual.txt"};
		save.runCommand(arguments);
		
		File fileActual = new File ("saveTestActual.txt");
		File fileExpected = new File ("saveTestExpected.txt");
		if (!fileExpected.exists()) {
			fileExpected.createNewFile();
		}
		BufferedReader brActual = new BufferedReader(new FileReader(fileActual));
		BufferedReader brExpected = new BufferedReader(new FileReader(fileExpected));
		String lineActual = brActual.readLine();
		String lineExpected = brExpected.readLine();
		while (lineActual != null) {
			assertEquals(lineExpected, lineActual);
			lineActual = brActual.readLine();
			lineExpected = brExpected.readLine();
		}
		
		brExpected.close();;
		brActual.close();
		
	}
	
	
	/**
	 * check if save works with a full path
	 * NOTE TO TA: THIS MIGHT NOT WORK ON YOUR COMPUTER because the file location
	 * is specific to me simply change the path, and the expected file content 
	 * is the same as saveTestExpected.txt for relative testing above
	 */
	@Test
	public void testSaveFullPath() throws IOException{
		String arguments[] = {"/cmshome/chenz285/cscb07s19_space/example.txt"};
		save.runCommand(arguments);
		File fileActual = new File ("/cmshome/chenz285/cscb07s19_space/example.txt");
		File fileExpected = new File ("/cmshome/chenz285/cscb07s19_space/answer.txt");

		
		BufferedReader brActual = new BufferedReader(new FileReader(fileActual));
		BufferedReader brExpected = new BufferedReader(new FileReader(fileExpected));
		String lineActual = brActual.readLine();
		String lineExpected = brExpected.readLine();
		while (lineActual != null && lineExpected!=null) {
			assertEquals(lineExpected, lineActual);
			lineActual = brActual.readLine();
			lineExpected = brExpected.readLine();
		}


		brExpected.close();;
		brActual.close();
	}
	
	@Test
	/**
	 * check if save returns invalid path for no parameters or 
	 * invalid path This must be checked with console.
	 */
	public void testSaveInvalidPath() throws IOException{
		String arguments[] = {"/cmshome/chenz285/cscb07s19_space/example.txt"};
		save.runCommand(arguments);
		
		String argument[] = {};
		save.runCommand(argument);
	}
	
	@Test
	/**
	 * check if isValid returns true if one argument and only one argument is given.
	 */
	public void testSaveIsValid() throws IOException{
		//no args 
		String arguments[] = {};
		boolean actual = save.isValid(arguments);
		boolean expected = false;
		assertEquals(expected, actual);
		
		//one arguments
		String arguments2[] = {"example"};
		 actual = save.isValid(arguments2);
		 expected = true;
		assertEquals(expected, actual);
		
		//more than one arguments
		String arguments3[] = {"example", "example2"};
		 actual = save.isValid(arguments3);
		 expected = false;
		assertEquals(expected, actual);
	}

}
