package commands;

import java.io.*;
import java.net.*;
import javax.naming.InvalidNameException;
import driver.JShell;
import structures.*;
import structures.File;


public class Get {
	/**
	 * default constructor
	 */
	public Get() {
		
	}
   /**
   * Runs the Get Command
   * @param List of String containing URL
   * @return String returning output of the Get Command
   * @throws FileEmptyException 
   * @throws InvalidNameException 
   */
    public String runCommand(String[] arguments) throws IOException, 
    InvalidNameException, FileEmptyException {
      String urlName = arguments[0]; // Declaring variables
      int i = urlName.length();
      boolean found = false;
      String inputline, fileName = "", fileContents = "", ret = "";
      try {// Connecting to the URL
        URL url = new URL(urlName);
        URLConnection urlConnect = url.openConnection();
        BufferedReader urlReader = new BufferedReader(
        		new InputStreamReader(urlConnect.getInputStream()));
        while((inputline = urlReader.readLine()) != null) {// Reading each line from the URL
          fileContents += inputline;
        }
      }
      catch(Exception error) {
        ret = "Invalid URL";
      }
      while(!found && i > 0) {// Get the name of the file (End of URL)
        if(urlName.substring(i-1, i).equals("/")) {
          found = true;
          fileName = urlName.substring(i, urlName.length());
        }
        i = i - 1;
      }
      if (!fileName.equals("")) {// Create that file and add it to the current dir
        Directory dir = JShell.getCurrDir();
        File newFile = new File(fileName, fileContents);
        dir.addFile(newFile);
      }
      return ret;
    }
    /**
     * Checks if this command is valid to run based on arguments passed
     * in
     * @param List of Strings containing arguments
     * @return boolean containing if the arguments are valid to call
     * function
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
    
}
