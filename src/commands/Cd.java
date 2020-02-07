package commands;

import java.util.ArrayList;
import java.util.Vector;

import structures.*;
import structures.Directory;
import driver.*;

//UTORID user_name: balamoni
//UT Student #: 1004428737
//Author: Samritha Balamoni

public class Cd implements Command{

	//ArrayList<Directory> childDir;
	//ArrayList<File> childFile;
	/**
	 * default constructor for initializing
	 */
	public Cd() {
		
	}

	   /**
	   * Runs the command
	   * @return void
	   * @param String[] arguments
	   */
	public String runCommand (String[] arguments) {
		if(arguments != null) {
			// if somewhere to switch to is provided
			switchDirectories(arguments[0]);
		}
		else {
			// otherwise just go to root
			switchToRoot();
		}
		return "";
	}
	/**
	 * switch to the root
	 */
	private void switchToRoot() {
		// for when Cd is called with no argument
		driver.JShell.setCurrDir(driver.JShell.getRoot());
	}
	
	/**
	 * Switches the directory to the target directory given
	 *  a string name for the target dictionary
	 * @param String representation of the target dictionary
	 * @return void
	 */
	private void switchDirectories(String target) {

		Directory current = driver.JShell.getCurrDir();
		Directory root = driver.JShell.getRoot();
		if (target.charAt(0)=='/') {
			// we are dealing with a FULL PATH FROM THE ROOT
			// findDir is called with the 
			driver.JShell.setCurrDir(Directory.findDir(root,target));
		}
		else { // we are dealing with a target that is a relative path
			if (current.getDirName().equals("/")) { // currently in root dir
				if (Directory.findDir(root, "/"+target) != null) {
					// that directory exists, switch into it
					driver.JShell.setCurrDir(Directory.findDir(root, "/"+target));
				}
				else {
					System.out.println("That directory cannot be found");
				}
			}
			else {
				// we are dealing with a RELATIVE PATH, and we are not in root
				// findDir is called on current.fullPath()+"/"+target
				driver.JShell.setCurrDir(
						Directory.findDir(root,current.getFullPath()+"/"+target));
			}
		}
	}

	/**
	 * Returns True if the command ran successfully 
	 * @param arguments
	 * @return boolean
	 */
	public boolean isValid(String[] arguments) {
      // Check if there is only one argument
      boolean match = false;
      if(arguments != null) {
        if(arguments.length == 1) {
          match = true;
        }
      }
      return match;
    }
	/**
	 * toString for cd
	 */
	public String toString() {
      String ret = "cd DIR\r\n" + 
          "Change directory to DIR, which may be relative to "
          + "the current directory or\r\n" + 
          "may be a full path. As with Unix, .. means a parent "
          + "directory and a .\r\n" + 
          "means the current directory. The directory must be /, "
          + "the forward slash.\r\n" + 
          "The foot of the file system is a single slash: /.";
	return ret;
      
    }

}
