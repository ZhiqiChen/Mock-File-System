package commands;

import structures.*;
import driver.*;

//UTORID user_name: balamoni
//UT Student #: 1004428737
//Author: Samritha Balamoni

public class Popd implements Command{
	/**
	 * A user command that changes into the directory that is on 
	 * top of the saved directories stack
	 * It must also remove that directory from the stack
	 * @return the directory at the top of the stack
	 */
	private DirectoryStack s;
		
	   /**
	   * Runs the command popd
	   * @return void
	   * @param String[] arguments
	   */
		public String runCommand(String[] arguments) {
			DirectoryStack dirStack = driver.JShell.getDirStack();
			driver.JShell.setCurrDir(dirStack.pop());
			return "";
		}
	
		
		/**
		 * Returns True if the command ran successfully 
		 * @param arguments
		 * @return boolean
		 */
		public boolean isValid(String[] arguments) {
		  // Don't want any arguments
			  boolean match = false;
			  if(arguments == null) {
			    match = true;
			  }
			  return match;
			}
		/**
		 * toString for popd describing the function.
		 * @return String description of the function
		 */
		public String toString() {
	      return("popd\r\n" + 
	          "Remove the top entry from the directory stack, "
	          + "and cd into it. The removal\r\n" + 
	          "must be consistent as per the LIFO behavior of a"
	          + " stack. The popd\r\n" + 
	          "command removes the top most directory from the "
	          + "directory stack and\r\n" + 
	          "makes it the current working directory. If there "
	          + "is no directory onto the\r\n" + 
	          "stack, then give appropriate error message.");
	    }

}
