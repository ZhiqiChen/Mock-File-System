package commands;

import structures.*;
import driver.*;

// TODO: make this command work for files too

public class Mv implements Command {
	
	/**
	 * run mv command which moves the directory to another path
	 */
	public String runCommand (String[] arguments) {
		
		// check for correct number of arguments, find the directories involved
		// then call moveDirectory with those directories
		Directory current = driver.JShell.getCurrDir();
		Directory root = driver.JShell.getRoot();
		Directory start = null;
		Directory destination = null;
		// TODO: make finding start and destination cleaner
		// find start: directory to be moved
		if(arguments[0].charAt(0)=='/') { //start String is a full path
			start = Directory.findDir(root,arguments[0]);
		} else if (current.getDirName().equals("/")) { // we are in root dir
			start = Directory.findDir(root,"/"+arguments[0]);
		} else {
			// start is a relative path and we are not in root
			start = Directory.findDir(root,current.getFullPath()+"/"+arguments[0]);
		}
			
		// find destination: directory to house the moved "start" directory
		if(arguments[1].charAt(0)=='/') {
			destination = Directory.findDir(root,arguments[1]);
		} else if (current.getDirName().equals("/")) {
			destination = Directory.findDir(root,"/"+arguments[1]);
		} else {
			destination = Directory.findDir(root,current.getFullPath()+"/"+arguments[1]);
		}

		// now call moveDirectory with the two directories found
		moveDirectory(start,destination);
		return "";
	}
	/**
	 * move the directory from start directory to destination directory
	 * @param start directory to be moved from
	 * @param destination directory to be moved to
	 */
	private void moveDirectory(Directory start,Directory destination) {
		// first delete start as a child of its parent directory
		Directory startParent = start.getParent();
		destination.addChild(start);
		start.setParent(destination);
		start.fixFullPathRec();
		startParent.deleteChildDir(start.getDirName());
	}
	
	/**
	 * check if number of arguments are equal to two
	 * @param arguments arguments for Mv
	 * @return boolean true if equal to two else return false
	 */
	public boolean isValid(String[] arguments) {
		  // Check if number of arguments is equal to 2
			  boolean match = false;
			  if(arguments.length == 2) {
			    match = true;
			  }
			  return match;
	}
	/**
	 * toString for Mv
	 * @return String description of Mv
	 */
	public String toString() {
		return "mv [old] [new] move the old directory to the new directory"
				+ "the path could be relative or absolute";
	}
}
