package commands;

import structures.*;

import java.util.ArrayList;

import javax.naming.InvalidNameException;

import driver.*;

//TODO: this'll break if you give it anything besides directories, errors????
//or just print out "directory not found"
//TODO: make it work for files too
// ie. check in runCommand if arguments[0] is a file before assigning it to start
//TODO: the recurse into child files part

public class Cp implements Command {
	/**
	 * runs cp 
	 * @param args for cp
	 */
	public String runCommand (String[] arguments) {
		// check for correct number of arguments, find the directories involved
		// then call copy with those directories
		Directory current = driver.JShell.getCurrDir();
		Directory root = driver.JShell.getRoot();
		Directory start = null;
		File startF = null;
		Directory destination = null;
		// TODO: make finding start and destination cleaner
		// find start: directory to be moved
		if(arguments[0].charAt(0)=='/') { //start String is a full path
			if (Directory.findDir(root,arguments[0]) != null) {
				start = Directory.findDir(root,arguments[0]);
			} else if (Directory.findFile(root, arguments[0]) != null) {
				startF = Directory.findFile(root,arguments[0]);
			} 
		} else if (current.getDirName().equals("/")) { // we are in root dir
			if (Directory.findDir(root, "/"+arguments[0]) != null) {
				start = Directory.findDir(root,"/"+arguments[0]);
			} else if (Directory.findFile(root, arguments[0]) != null) {
				startF = Directory.findFile(root, "/" + arguments[0]);
			}
		} else {
			// start is a relative path and we are not in root
			if(Directory.findDir(root,current.getFullPath()+"/"+arguments[0]) != null) {
				start = Directory.findDir(root,current.getFullPath()+"/"+arguments[0]);
			} else if (Directory.findFile(root,current.getFullPath()+"/"+arguments[0]) != null) {
				startF = Directory.findFile(root,current.getFullPath()+"/"+arguments[0]);
			}
		}
		// find destination: directory to house the moved "start" directory
		if(arguments[1].charAt(0)=='/') {
			destination = Directory.findDir(root,arguments[1]);
		} else if (current.getDirName().equals("/")) {
			destination = Directory.findDir(root,"/"+arguments[1]);
		} else {
			destination = Directory.findDir(root,current.getFullPath()+"/"+arguments[1]);
		}

		// now call copy with the two directories found
		if (start != null && destination != null) {
			copy(start,destination);
		} else if (startF != null && destination != null) {
			copy(startF,destination);
		} else {
			System.out.println("Could not copy "+arguments[0]+", one of the input files or directories doesn't exist");
		}
		return "";
	}
	
	// recursively copy a target directory to a new location, and all contained
	// files and directories
	private void copy(Directory start, Directory destination) {
		
		//first make the current directory
		String[] name = {destination.getFullPath() + "/" + start.getDirName()};
		Mkdir make = new Mkdir();
		make.runCommand(name);
		
		//now check for child directories
		if (start.getDirHead() != null) { // at least one child directory
			ArrayList<Directory> dirList = start.getDirHead();
			Directory recDestination = Directory.findDir(JShell.getRoot(), destination.getFullPath()+"/"+start.getDirName());
			for(Directory dir : dirList) {
			  copy(dir, recDestination);
			}
		}
		
		//now check for files
		if (start.getFileHead() != null) { // at least one child file
			ArrayList<File> fileList = start.getFileHead();
			Directory recDestination = Directory.findDir(JShell.getRoot(), destination.getFullPath()+"/"+start.getDirName());
			for(File childFile : fileList) {
				copy(childFile, recDestination);
			}
		}
		
	}
	
	// copy a child file to a new destination
	private void copy(File childFile, Directory destination) {
		Directory temp = driver.JShell.getCurrDir();
		driver.JShell.setCurrDir(destination);
		try {
			File f1 = new File(childFile.getName(), childFile.getContents());
			destination.addFile(f1);
		} catch (Exception e) {
		}
		JShell.setCurrDir(temp);
	}
	
	public boolean isValid(String[] arguments) {
	      // Check if there is only two arguments
	      boolean match = false;
	      if(arguments != null) {
	        if(arguments.length == 2) {
	          match = true;
	        }
	      }
	      return match;
	    }
	
	public String toString() {
		return "cp [oldPath] [newPath] copy everything from oldPath to newPath, could be "
				+ "relative or full path";
	}
}

