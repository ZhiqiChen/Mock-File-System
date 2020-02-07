package driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Ensure that the command entered is valid
 */
public class CommandValidator {
  /**
   * Test if the command is able to run successfully.
   * @param command entered by user
   * @param arguments an array of the arguments entered by user
   * @return true if the command ran successfully, false otherwise
   */
  public static boolean isValid(String command, String arguments[]) {
    // A complete list of implemented commands
    String[] commands = {"exit", "mkdir", "cd", "ls", "pwd", "pushd",
       "popd", "history", "cat", "echo", "man", "cp", "save", "load", "mv", "tree",
       "get", "find"};

  //Verify that the user input is a valid command
   boolean match = false;
   for (int i=0; i<commands.length; i++) {
     if(command.equals(commands[i])) {
       match = true;
     }
   }
   if(match) {
     // Check if arguments are correct
     String cap = command.substring(0, 1).toUpperCase() + command.substring(1);
     String classname = "commands." + cap;
     Class<?> commandClass;
     try {
    	// create instance of command class, get method to run the command
    	commandClass = Class.forName(classname);
		Constructor<?> commandConstructor = commandClass.getConstructor();
		Object commandInstance = commandConstructor.newInstance(new Object[] {});
    	Method runcommand = commandClass.getMethod("isValid", String[].class);
    	
    	// run the command, if it fails, match will get false
    	int argLen = 1;
    	if (arguments != null) argLen = arguments.length;
    	if(!(arguments==null) && argLen > 1 && (arguments[argLen - 2].equals(">") || arguments[argLen - 2].equals(">>"))) {
    		// a redirection, only do isValid with all but the last two arguments
    		String[] trimmedArguments = Arrays.copyOfRange(arguments, 0, arguments.length - 2);
    		match = (boolean) runcommand.invoke(commandInstance, (Object) trimmedArguments);
    	} else { // not a redirection
    		match = (boolean) runcommand.invoke(commandInstance, (Object) arguments);
    	}
     } catch(Exception e) {
         match = false;
         // if the command fails, let the user know
         System.out.println("Invalid input - Command Validator xxx");
         System.out.println( e.getClass().getCanonicalName());
         // System.out.println(e.getCause());
         
       }
   }
   return match;
   
  }
}
