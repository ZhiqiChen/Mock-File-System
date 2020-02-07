package commands;

import java.util.ArrayList;
import java.util.List;
import structures.Directory;

public class Find {
  
  public String runCommand(String[] arguments) {
    // Finds and checks if the files or directories are present
    // Loop through arguments to get all the paths, save indexes. Also find type
    int i = 0;
    String path, ret = "";
    List<Directory> directories = new ArrayList<Directory>();
    // Getting the file/directory name
    String name = arguments[arguments.length - 1];
    // Remove quotes
    name = name.substring(1, name.length() - 1);
    while (arguments[i] != null && !arguments[i].equals("-type")) {
      // Find and store the directory
      // Get the path
      path = arguments[i];
      // Get the directory for the path, finds the dir recursively
      Directory dir = Directory.findDir(driver.JShell.getRoot(), path);
      // If null is returned, the directory cannot be found
      if(dir == null) {
        ret += "Path " + path + " does not exist";
        ret += "\n";
      }
      else {
        // Add it to the list of directories
        directories.add(dir);
      }
      i++;
    }
    i++;
    // Check if user looking for a file or a directory
    if(arguments[i].equals("f")) {
      
      // Loop through each path given
      for (int j = 0; j < directories.size(); j++) {
        // Add findFile and findDir in Directory class so don't have to
        // make methods static
        ret += (directories.get(j)).findFiles(name, ret);
      }
    }
    if(arguments[i].equals("d")) {
      // Loop through each path given
      for (int j = 0; j < directories.size(); j++) {
        // Add findFile and findDir in Directory class so don't have to
        // make methods static
        ret += (directories.get(j)).findDirectories(name, ret);
      }
    }
    return ret;
  }
  public boolean isValid(String[] arguments) {
    boolean match = false;
    // Make sure there are more than 5 arguments
    if (arguments.length >= 5) {
      match = true;
    }
    return match;
  }
  public String toString() {
    return "Find directory or file given";
  }
}