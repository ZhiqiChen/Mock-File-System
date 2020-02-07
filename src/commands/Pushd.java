package commands;

import structures.*;
import driver.*;

//UTORID user_name: balamoni
//UT Student #: 1004428737
//Author: Samritha Balamoni

/**
 * Saves current working directory by pushing onto a stack
 * This is so that the current working directory can be returned to at any time
 * @return void
 */


public class Pushd implements Command{
	
	   /**
	   * Pushes directory to the directory stack
	   * Runs the command
	   * @return void
	   * @param String[] arguments
	   */
	public Pushd() {}
	
	public String runCommand(String[] arguments) {
		DirectoryStack dirStack = driver.JShell.getDirStack();
		Directory currDir = driver.JShell.getCurrDir();
		Directory root = driver.JShell.getRoot();
		
		dirStack.push(currDir);
		driver.JShell.setCurrDir(Directory.findDir(root, arguments[0]));
		return "";
	}
	/**
	 * Returns True if the command ran successfully 
	 * @param arguments arguments for pushd
	 * @return boolean true if number of arguments is one else return false
	 */
	public boolean isValid(String[] arguments) {
		  boolean match = false;
		  if(arguments != null) {
		    if(arguments.length == 1) {
		      match = true;
		    }
		  }
		  return match;
		}
	/**
	 * toString for pushd
	 * @return String description of pushd
	 */
	public String toString() {
      return"pushd DIR\r\n" + 
          "Saves the current working directory by pushing onto "
          + "directory stack and\r\n" + 
          "then changes the new current working directory to DIR. "
          + "The push must be\r\n" + 
          "consistent as per the LIFO behavior of a stack. The pushd "
          + "command\r\n" + 
          "saves the old current working directory in directory stack "
          + "so that it can be\r\n" + 
          "returned to at any time (via the popd command). The size of "
          + "the directory\r\n" + 
          "stack is dynamic and dependent on the pushd and the popd commands.";
    }
}
