package driver;

import structures.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import commands.*;

/**
 * Handles taking user input and running it as a command with the 
 * given arguments.
 */
public class CommandHandler {
	
	
	/**
	 * Check if the command provided is one of the implemented commands.
	 * @param command entered by user
	 * @param arguments the arguments provided by user along with the command
	 * @return boolean whether or not the command is valid.
	 */
	public static boolean isValid(String command, String arguments[]) {
		// all implemented commands
		String[] commands = { "exit", "mkdir", "cd", "ls", "pwd", "pushd", "popd", "history", 
				"cat", "echo", "man","save", "cp", "load", "mv", "tree"};

		//Verify that the user input is a valid command
		boolean match = false;
		for (int i = 0; i < commands.length; i++) {
			if (command.equals(commands[i])) {
				match = true;
			}
		}

		return match;
	}

	/**
	 * Takes the input provided by the user, parses it into command and arguments, and handles
	 * running that command with those arguments. First makes sure command and arguments valid,
	 * then creates instance of command class, then calls necessary methods.
	 * Also stores user inputs for history
	 * @param input what the user inputs, as one long string
	 * @return whether or not the command ran successfully
	 */
	public static boolean runCommand(String input) {
		JShell.getUserEntries().add(input);

		//clean up command, remove trailing/leading spaces and duplicate spaces
		input = input.trim();
		input = removeDuplicateSpaces(input);
		String command;
		String[] arguments;
		//split up input into command, and array of arguments
		if (hasSpace(input)) {
			String[] parts = input.split(" ", 2);
			command = parts[0];
			arguments = parts[1].split(" ");
		} else {
			command = input;
			arguments = null; // no arguments
		}
		// Verify that the user input is a valid command (b-2)
		// This is also where we deal with redirection
		boolean match = CommandValidator.isValid(command, arguments);
		if (match) {
			// print to console user's command, followed by user's arguments provided
			System.out.println(command);
			if(!(arguments == null)) {
			   for (int i=0; i<arguments.length; i++) {
				  System.out.print(arguments[i]+" ");
			   }
			   System.out.println("");
			}
			if (!(arguments == null) && arguments.length > 1 && 
					arguments[arguments.length - 2].equals(">")) {
				// redirection, OVERWRITE file
				// use all but the last two arguments: ">" and the name of  the file to write to
	    		String[] trimmedArguments = Arrays.copyOfRange(arguments, 0, arguments.length - 2);
				String ret = callCommand(command, trimmedArguments);
				Directory currDir = JShell.getCurrDir();
				currDir.overwriteFile(arguments[arguments.length-1], ret);
			} else if (!(arguments == null) && arguments.length > 1 &&
					arguments[arguments.length - 2].equals(">>")) {
				// redirection, APPEND to file
				// same thing with arguments
	    		String[] trimmedArguments = Arrays.copyOfRange(arguments, 0, arguments.length - 2);
				String ret = "\n" + callCommand(command, trimmedArguments);
				Directory currDir = JShell.getCurrDir();
				currDir.appendToFile(arguments[arguments.length-1], ret);
			} else {
				// no redirection, print to terminal
				String ret = callCommand(command, arguments);
				if (!(ret.equals(""))) {
					System.out.println(ret);
				}
			}
		} else {
			// if the command with arguments fails, tell the user they were unsuccessful
			System.out.println("Invalid command or arguments");
		}
		return match;
	}
	/**
	 * Handles just running the command. The command has already been validified, and input has 
	 * been split. returns the output of the command - for some commands this will be "".
	 * @param command the command provided by user, validified.
	 * @param arguments arguments provided by user in the form of an array of strings
	 * @return the output of the command 
	 */
	private static String callCommand(String command, String[] arguments) {
		// make command capitalized so we can call the class
		String cap = command.substring(0, 1).toUpperCase() + command.substring(1);
		String classname = "commands." + cap;
		// get command class
		Class<?> commandClass;
		try {
			commandClass = Class.forName(classname);
			// get constructor from command class
			Constructor<?> commandConstructor = commandClass.getConstructor();
			// create instance of command class - so we can use non static methods
  			Object commandInstance = commandConstructor.newInstance(new Object[] {});
  			// run command in the command class
			Method runcommand = commandClass.getMethod("runCommand", String[].class);
			String returnedVal = (String) runcommand.invoke(commandInstance, (Object) arguments);
			// return whatever string the command output
			return returnedVal;
		} catch (Exception e) {
			// if the command call failed, let the user know
			System.out.println("Invalid input.");
			// System.out.println(e.getClass().getCanonicalName());
			// System.out.println(e.getCause());
		}
		return "";
	}

	/**
	 * Remove all duplicates of spaces within str trim spaces in front of str and
	 * after it any double spaces will be reduced to one ie. y s => y s
	 * 
	 * @param str string that will be trimed
	 * @return String that have no duplicates of spaces
	 */
	private static String removeDuplicateSpaces(String str) {
		// trim leading/trailing spaces
		str = str.trim();
		boolean space = false;
		String result = "";
		// walk through the string, if double spaces, handle
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ' && space) {
				continue;
			} else if (str.charAt(i) == ' ') {
				space = true;
			} else {
				space = false;
			}
			result += str.charAt(i);
		}

		return result;
	}

	/**
	 * check if the str have space within it return true if so and false if there is
	 * no space.
	 * 
	 * @param str string that will be checked for spaces
	 * @return boolean true if there are space false otherwise
	 */
	private static boolean hasSpace(String str) {
		boolean space = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				space = true;
			}
		}
		return space;
	}

	
}
