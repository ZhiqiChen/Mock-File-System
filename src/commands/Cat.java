package commands;

import structures.*;
import driver.*;

//UTORID user_name: marjerr1
//UT Student #: 1002548807
//Author: Daniel Marjerrison

//UTORID user_name: chenz285
//UT Student #: 1005269870
//Author: Zhiqi Chen
public class Cat implements Command{
	/**
	 * default constructor that does nothing
	 * simply initialize the object
	 */
	public Cat() {
		
	}
	/**
	 * Runs the Cat Command
	 * @return void
	 * @param String [] (arguments)
	 */
	public String runCommand (String[] arguments){
		String ret = "";
		for (int i=0; i<arguments.length; i++) {
			String path = arguments[i];
			//check if its full or relative
			if (Directory.findFile(driver.JShell.getRoot(), path) != null) {
				// print file if exist
				String out = Directory.findFile(
						driver.JShell.getRoot(), path).getContents();
				ret = ret.concat(out);
				ret = ret+"\n\n\n";
			}
			else {
				System.out.println("There is no file named "+arguments[i]+
						" in the that directory");
				System.out.println("\n\n");
			}
		}
		
		return ret;
	}
	/**
	 * Returns True if the command ran successfully 
	 * @param arguments
	 * @return boolean
	 */
	public boolean isValid(String[] arguments) {
	  // Check if there is at least one argument
      boolean match = false;
      if(arguments != null || arguments.length > 0) {
        match = true;
      }
      return match;
    }
	
	
	public String toString() {
	  return "cat FILE1 [FILE2 �]\r\n" + 
          "Display the contents of FILE1 and other files (i.e. File2 �.)"
          + " concatenated\r\n" + 
          "in the shell. You may want to use three line breaks to "
          + "separate the contents of\r\n" + 
          "one file from the other file.";
	  
	}
}