package commands;

import structures.*;
import driver.*;

//UTORID user_name: chenz285
//UT Student #: 1005269870
//Author: Zhiqi Chen



//mkdir a b/x/../y/x/z c a/b/c/d/../e
public class Ls implements Command{
	private String result =""; //result of Ls
	/**
	 * // constructor that does nothing for commandhandler
	 */
	public Ls() {
		
	}
	
	/**
	 * if path is a directory
	 * 
	 * @param dir the directory that the contents will be printed to terminal
	 * @param isRecursive if -R is entered
	 */
	private void list(Directory dir, boolean isRecursive) {
		if (isRecursive) result +=dir.getFullPath() + ": \n";
		// list all Directories under dir
		for (File tempFile :dir.getFileHead()) {
			result+=tempFile.getName() + "\n";
		}
		for (Directory tempDir:dir.getDirHead()) {
			result+= tempDir.getDirName() + "\n";
		}
	}
	
	/**
	 * recursively call directories's children directories
	 * therefore using ls on all directories
	 * @param dir current directory
	 */
	private void recursiveLs(Directory dir) {
		int i = 0;
		if (dir.getDirHead().size() != 0)list(dir, true);
		for (Directory traversal:dir.getDirHead()) {
			list(traversal, true);
			//System.out.println("Recursive traversal: " + traversal.getFullPath());
			if (traversal.getDirHead().size() != 0)recursiveLs(traversal.getDirHead().get(i));
			i++;
		} 
	}

	/**
	 * Runs the Ls with correct information
	 * 
	 * @return void
	 * @param String[] arguments of ls
	 */
	public String runCommand(String[] arguments) {
		if (arguments != null) {
			boolean recursive = false;
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i].equals("-R")) {
					recursive = true;
					continue;
				}
				String fullPath = arguments[i];// get the path
				Directory path = Directory.findDir(JShell.getRoot(), fullPath);
				if (path != null) { //if directory exists
					if (recursive) {
						recursiveLs(path);
					}else {
						list(path, false);
					}
				} else { //check if it's file
					File file = Directory.findFile(JShell.getRoot(), fullPath);
					if (file != null) {
						result+=file.getContents() + "\n";
					} else {
						System.out.println("file or directory doesn't exist: " + arguments[i]);
					}
				}
				recursive = false;
			}
			if (recursive)recursiveLs(JShell.getCurrDir());
		} else list(JShell.getCurrDir(), false);
		return result;
	}
	
	/**
	 * it will be true
	 * @param arguments args for ls
	 * @return
	 */
	public boolean isValid(String[] arguments) {
	  // Nothing to check here. Arguments will always be valid
      return true;
	}
	/**
	 * description for ls, in form of toString
	 */
	public String toString() {
      return "If no paths are given, print the contents "
          + "(file or directory) of the current\r\n" + 
        "directory, with a new line following each of the content "
        + "(file or directory).\r\n" + 
        "Otherwise, for each path p, the order listed:\r\n" + 
        "� If p specifies a file, print p\r\n" + 
        "� If p specifies a directory, print p, a colon, then the "
        + "contents of that\r\n" + 
        "directory, then an extra new line.\r\n" + 
        "� If p does not exist, print a suitable message.";
    }
}
