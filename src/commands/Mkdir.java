package commands;

import structures.*;
import java.util.NoSuchElementException;
import driver.JShell;

//UTORID user_name: chenz285
//UT Student #: 1005269870
//Author: Zhiqi Chen

/**
 * make new directory(ies) based on the arguments
 * @author chenz285
 *
 */
public class Mkdir implements Command {

	/**
	 * constructor for mkdir
	 */
	public Mkdir() {

	}

	/**
	 * add Directory using absolute path
	 * @param root root directory within the file system
	 * @param path absolute path to be created
	 * @return directory that has been created
	 */
	private Directory addDirFullPath(Directory root, String path) {
		String[] directoryList = path.split("/");
		Directory traversal = root;
		for (int i = 1; i < directoryList.length; i++) {
			if (directoryList[i].equals("..")) {

				if (traversal == root || traversal.getParent() == null) {
					System.out.println(traversal.getDirName() + "doesn't have a parent");
					return null;
				}
				traversal = traversal.getParent();
			} else if (directoryList[i].equals(".")) {
				// do nothing
			} else {
				// the path starts with / meaning first element
				// of directoryList is "", skip that one
				Directory temp = traversal.findDir(directoryList[i]);
				if (temp == null) {
					traversal.addChild(new Directory(directoryList[i], traversal));
					traversal = traversal.findDir(directoryList[i]);
				} else {
					traversal = temp;
				}
			}
		}
		return traversal;
	}
	
	/**
	 * add Directory using relative path
	 * @param currentDir current directory within the file system
	 * @param path relative path to be created
	 * @return directory that has been created
	 */
	private Directory addDirRelativePath(Directory currentDir, String path) {
		String[] directoryList = path.split("/");
		Directory traversal = currentDir;
		for (int i = 0; i < directoryList.length; i++) {
			if (directoryList[i].equals("..")) {

				if (traversal.getDirName().equals("/") || traversal.getParent() == null) {
					System.out.println(traversal.getDirName() + "doesn't have a parent");
					return null;
				}
				traversal = traversal.getParent();
			} else if (directoryList[i].equals(".")) {
				// do nothing
			} else {
				Directory temp = traversal.findDir(directoryList[i]);
				if (temp == null) {
					traversal.addChild(new Directory(directoryList[i], traversal));
					traversal = traversal.findDir(directoryList[i]);
				} else {
					traversal = temp;
				}
			}
		}
		return currentDir;
	}

	/**
	 * Runs the command
	 * 
	 * @return void
	 * @param String[] arguments
	 */
	public String runCommand(String[] arguments) {
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].charAt(0) == '/') {
				addDirFullPath(JShell.getRoot(), arguments[i]);
			} else {
				addDirRelativePath(JShell.getCurrDir(), arguments[i]);
			}
		}
		return "";
	}
	
	/**
	 * check if num of args is correct
	 * @param arguments
	 * @return
	 */
	public boolean isValid(String[] arguments) {
		  // Check if number of arguments is greater than 0
			  boolean match = false;
			  if(arguments != null && arguments.length != 0) {
			    match = true;
			  }
			  return match;
	}
	
	/**
	 * to String of mkDir and displays its description
	 */
	public String toString() {
		return "mkdir DIR �\r\n" + "Create directories,"
				+ " each of which may be relative to "
				+ "the current directory\r\n" + "or may be a full path.";
	}

}
