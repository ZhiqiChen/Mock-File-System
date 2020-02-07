package commands;

import java.lang.reflect.Method;

import structures.*;

public class Man implements Command{
	  /**
	   * Prints out documentation for a command given by user
	   * @param String (argument)
	   */
  public static boolean run_man(String argument) {
    // Call tostring method for each
    String man = "";
    Class<?> commandClass;
	try {
		commandClass = Class.forName(argument);
		try {
			Method runcommand = commandClass.getMethod("toString", String[].class);
		} catch (NoSuchMethodException e) {
			System.out.println("no such method to String");
		} catch (SecurityException e) {
			
		}
	} catch (ClassNotFoundException e1) {
		System.out.println("no such class");
	}

    return true;
  }
  /**
   * Runs the command
   * @return void
   * @param String[] arguments
   */
  public String runCommand(String[] arguments) {
    Man.run_man(arguments[0]);
	return "";
  }
  	/**
	 * Returns True if the command ran successfully 
	 * @param arguments
	 * @return boolean
	 */
  public static boolean isValid(String[] arguments) {
	  boolean match = false;
	  if(arguments != null) {
        if (arguments.length == 1) {
          match = true;
        }
	  }
	  return match;
  }
  public String toString() {
   return "man CMD\r\n" + 
        "Print documentation for CMD";
  }
}
  
