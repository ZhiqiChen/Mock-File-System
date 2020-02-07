// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: marjerr1
// UT Student #: 1002548807
// Author: Daniel Marjerrison
//
// Student2:
// UTORID user_name: balamoni
// UT Student #: 1004428737
// Author: Samritha Balamoni
//
// Student3:
// UTORID user_name: haroona6
// UT Student #: 1004360648
// Author: Aamir Haroon
//
// Student4:
// UTORID user_name: chenz285
// UT Student #: 1005269870
// Author: Zhiqi Chen
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.Scanner;
import structures.*;
import commands.*;

/*
 * The class that houses the in-out of our project.
 * Handles calling command handler to run user inputs
 * Also houses static variables used across the project
 */
public class JShell {

	// These are our required static variables, referenced from
	// many different commands
	private static boolean exitShell = false; //set exit shell to false
	private static Directory root = new Directory();
	private static Directory currDir = root;
	private static History userEntries = new History();
	private static DirectoryStack dirStack = new DirectoryStack();

/**
 * This method run continuously, receiving input from the user to be tried as a command.
 * This method only exits when the user uses the command "exit"
 * @param args the inputs of the user
 */
  public static void main(String[] args) {
	  Scanner prompt = new Scanner(System.in);
	  boolean success = true;


	  while(true) {
	      // Printing prompt
	      System.out.print("/# ");
	      // taking command from the user and storing it as "input"
		  String input = prompt.nextLine();

		  if(!input.isEmpty()) {  //if the input typed by user is not empty
		    success = CommandHandler.runCommand(input);
		  }
		  if (!success) { // bad command
		    System.out.println("Invalid command");
		  }
	  }
	  
  }

/**
 * return the root of the file systemSystem.out.println("yes");
 * @return root Directory
 */
public static Directory getRoot() {
	return root;
}

/**
 * set the root of the file system
 * @param root new root Directory
 */
public static void setRoot(Directory root) {
	JShell.root = root;
}

/**
 * return the current directory of the file system
 * @return return the currentDir of the file system
 */
public static Directory getCurrDir() {
	return currDir;
}

/**
 * set the current directory of the file system
 * @param currDir the new current directory of the file system
 */
public static void setCurrDir(Directory currDir) {
	JShell.currDir = currDir;
}

/**
 * return the userEntries or history of the shell
 * @return History of the user entries
 */
public static History getUserEntries() {
	return userEntries;
}

/**
 * set the userEntries or the history of the shell
 * @param userEntries new History
 */
public static void setUserEntries(History userEntries) {
	JShell.userEntries = userEntries;
}

/**
 * return the Directory stack of the shell
 * @return the directory stack (dirStack) of the shell
 */
public static DirectoryStack getDirStack() {
	return dirStack;
}

/**
 * set the directory stack of the shell
 * @param dirStack new directory stack (dirStack) of the shell
 */
public static void setDirStack(DirectoryStack dirStack) {
	JShell.dirStack = dirStack;
}



}
