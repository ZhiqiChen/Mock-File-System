package commands;

import structures.*;

public class Exit implements Command{

	/**
	 * This method runs the command
	 * @param String[] arguments
	 * @return void
	 */
	public String runCommand(String[] arguments) {
		System.exit(0);
		return "";
	}
	
	/**
	 * Returns True if the command ran successfully 
	 * @param arguments
	 * @return boolean
	 */
	public static boolean isValid(String[] arguments) {
	  boolean match = false;
	  if(arguments == null) {
	    match = true;
	  }
	  return match;
	}
	public void toString2() {
      String ret = "";
      System.out.println("Exits the program");
    }
}