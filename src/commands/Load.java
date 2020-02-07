package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import javax.naming.InvalidNameException;

import driver.JShell;
import structures.Directory;
import structures.DirectoryStack;
import structures.File;
import structures.FileEmptyException;

//UTORID user_name: chenz285
//UT Student #: 1005269870
//Author: Zhiqi Chen

/**
 * load the JShell state into the program with the specified text file made from
 * load
 * 
 * @author chenz285
 *
 */
public class Load implements Command{
	/**
	 * default constructor initialize
	 */
	public Load() {
	}
	/**
	 * run load with the correct commands and make sure it has a path to load 
	 * @param arguments the arguments for load
	 */
	public String runCommand(String[] arguments) {
		if (arguments[0] == null) {
			System.out.println("Please enter a valid full file path");
		} else {
			try {
				loading(arguments[0]);
			} catch (IOException e) {
				System.out.println("Reading error: " + arguments[0]);
			} catch (FileEmptyException e) {
				System.out.println("file is empty " + arguments[0]);
			}
		}
		return "";
	}

	/**
	 * this function handles loading. it check if the file exist or not at path
	 * as well as read the file in the correct order by sending the a reader to other functions
	 * @param path path of the file
	 * @throws IOException issues with reading
	 * @throws InvalidNameException invalid name for file
	 * @throws FileEmptyException file is empty
	 */
	private void loading(String path) throws IOException, FileEmptyException{
		java.io.File file = new java.io.File(path);
		if (!file.exists()) {
			System.out.println("The file doesn't exist ");
		} else {

			FileReader fw = new FileReader(file);
			BufferedReader reader = new BufferedReader(fw);
			try {
				readFileSystem(reader);
			} catch (InvalidNameException e) {
				System.out.println("invalid name for file");
			}
			readHistory(reader);
			readStack(reader);
			reader.close();
			fw.close();
		}

	}

	/**
	 * Reads the file from reader and load files and directories into the Jshell
	 * as well as current directory
	 * @param reader file that needs to be read
	 * @throws IOException issues with reading
	 * @throws InvalidNameException invalid name for file
	 * @throws FileEmptyException file is empty
	 */
	private void readFileSystem(BufferedReader reader) throws IOException,
	InvalidNameException, FileEmptyException {
		String line = "";
		line = reader.readLine().trim();
		Directory currentDir = new Directory();
		JShell.setRoot(currentDir);
		while (line != null && !line.equals("PWD")) {
			if (line.charAt(0) != '/') { // if it is a file
				File file = readFile(reader, line);
				if (file != null) {
					currentDir.addFile(file);
				}
			} else { // if it is a directory
				Mkdir newDir = new Mkdir();
				String[] path = { line };
				newDir.runCommand(path);
			}
			line = reader.readLine().trim();
		}
		// after reading PWD the next line is the path of current dir
		JShell.setCurrDir(Directory.findDir(JShell.getRoot(), reader.readLine()));
	}

	/**
	 * reads the file from reader, currently reader has a file with its content to be read
	 * this function reads that file and return a file with file name "name" and
	 * the content.
	 * @param reader reader that will be reading file contents
	 * @param name name of the file
	 * @return File file with file name as "name" and content that is read in this 
	 * @throws IOException issues with reading
	 * @throws InvalidNameException invalid name for file
	 * @throws FileEmptyException file is empty
	 */
	private File readFile(BufferedReader reader, String name)
			throws InvalidNameException, FileEmptyException, IOException {
		reader.readLine(); // read EOD
		String temp = reader.readLine();
		String text = "";
		while (!temp.equals("ENDEOD")) {
			text += temp + "\n";
			temp = reader.readLine();
		}
		File file = new File(name, text);
		if (file.getName() != null) { // if it is valid file
			return file;
		}
		return null;
	}

	/**
	 * read the history part of the file
	 * and setting the history in jshell
	 * @param reader reading file for load
	 * @throws IOException reading error
	 */
	private void readHistory(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		ArrayList<String> newEntries = new ArrayList<String>();
		line = reader.readLine();
		while (line.equals("DIRECTORYSTACK")) {
			newEntries.add(line);
			line = reader.readLine();
		}
		History newHistory = new History();
		newHistory.setAllEntries(newEntries);
		JShell.setUserEntries(newHistory);
	}

	/**
	 * reading the stack of directory for pushd and popd
	 * load the stack into jshell
	 * @param reader reading the file for load
	 * @throws IOException reading error
	 */
	private void readStack(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		ArrayList<Directory> newStack = new ArrayList<Directory>(); 
		while(line != null) {
			Directory newDir = Directory.findDir(JShell.getRoot(), line);
			newStack.add(newDir);
			line = reader.readLine();
		}
		JShell.setDirStack(new DirectoryStack(newStack));
	}
	/**
	 * check if the arguments has exactly one argument
	 * @param arguments
	 * @return
	 */
	public static boolean isValid(String[] arguments) {
		return arguments.length == 1;
	}

}
