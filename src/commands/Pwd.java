package commands;

import structures.*;
import structures.Directory;
import driver.*;

//UTORID user_name: chenz285
//UT Student #: 1005269870
//Author: Zhiqi Chen
public class Pwd implements Command{
	/**
	 * default constructor
	 */
	public Pwd() {
		
	}
	
	   /**
	   * Runs the PWD command
	   * @return void
	   * @param String[] arguments all of the arguments
	   */
	public String runCommand(String[] arguments){
		Directory dir = driver.JShell.getCurrDir();
		return dir.getFullPath();
		
	}
	/**
	 * check number of arguments is equal to zero
	 * @param arguments arguments for pwd
	 * @return true if num of arguments is zero else return false
	 */
	public boolean isValid(String[] arguments) {
	  // Don't want any arguments here
		  boolean match = false;
		  if(arguments == null ||arguments.length== 0) {
		    match = true;
		  }
		  return match;
		}
	/**
	 * toString for pwd
	 * String description for pwd
	 */
	public String toString() {
      return "pwd\r\n" + 
          "Print the current working directory (including the whole path).";
    }
		
}
