package commands;

import structures.Directory;
import structures.*;

import java.io.BufferedWriter;
import java.io.IOException;

import driver.*;
import structures.DirectoryStack;
import structures.File;

//UTORID user_name: balamoni
//UT Student #: 1004428737
//Author: Samritha Balamoni

public class Tree implements Command {
	
	private String result; //resulting string
	public Tree() {
		
	}
	
	/**
	 * check if it is valid
	 * @param arguments arguments for tree
	 * @return true if no args else return false
	 */
	public boolean isValid(String[] arguments) {
		// Don't want any arguments
		boolean match = false;
		if(arguments == null || arguments.length == 0) {
			match = true;
		}
		return match;
	}
	
	/**
	 * Run tree command
	 * @param arguments the set of arguments
	 */
	public String runCommand(String[] arguments) {
		result = "";
		writeName(JShell.getRoot().getDirName(),0);
		recursiveFileSystem(driver.JShell.getRoot(), 1);
		return result;
	}
	
	/**
	 * this Function simply writes the name path
	 */
	private void writeName(String name, int level) {
		for (int i = 0; i < level; i++) {
			result+="\t";
		}
		result+=name + "\n";
	}
	

	/**
	 * recursive writing using out, for the file system
	 * using a pre-order traversal method
	 * @param dir directory to be added to the file
	 * @param level number of tabs for formatting
	 * @throws IOException writer issues
	 */
	private void recursiveFileSystem(Directory dir, int level) {
		for (File traversalFile:dir.getFileHead()) {
			writeName(traversalFile.getName(), level +1);
		}

		for(Directory traversalDir:dir.getDirHead()) {
			writeName(traversalDir.getDirName(), level + 1);
			recursiveFileSystem(traversalDir, level + 1);
		}
	}
	
	/**
	 * toString for tree
	 * @return String description of Tree
	 */
	public String toString() {
		return "display a tree style representation of the file system";
	}

}


