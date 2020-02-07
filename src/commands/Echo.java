package commands;

import structures.*;

import driver.*;

public class Echo implements Command{

	public String runCommand(String[] arguments) {
		String retVal;
		retVal = arguments[0];
		for(int i = 1; i<arguments.length; i++) {
			retVal = retVal + " " + arguments[i];
		}
		retVal = retVal.substring(1, retVal.length()-1);
		retVal = retVal + " ";
		return retVal.trim();
	}

	
	/**
	 * Returns True if the command ran successfully 
	 * @param arguments
	 * @return boolean
	 */
	
	public boolean isValid(String[] arguments) {
      // Check if there is 1 argument. 
      boolean match = false;
      if(arguments.length > 0 ) {
    	  match = true;
      }
      return match;
    }
	
	public String toString() {
		return "Echo [String] : prints the string to console";
	}
      
}