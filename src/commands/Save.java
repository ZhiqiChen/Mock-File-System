package commands;

import structures.*;
import structures.Directory;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import driver.*;

//UTORID user_name: chenz285
//UT Student #: 1005269870
//Author: Zhiqi Chen

/**
 * save the current state of Jshell into a file with specified location the
 * location must be valid within the computer
 * 
 * @author chenz285
 *
 */
public class Save implements Command{
	/**
	 * runs the command save making sure it gets sent to the save constructor
	 */
	public String runCommand(String[] arguments) {
		if (arguments.length != 1 || arguments[0] == null) {
			System.out.println("Please enter a valid full file path");
		} else {
			try {
				saving(arguments[0]);
			} catch (IOException e) {
				System.out.println("Invalid file Path");
			}
		}
		return "";
	}
	/**
	 * default constructor that does nothing
	 */
	public Save() {
		
	}

	/**
	 * the function that facilitate when each function is runned
	 * therefore determining the format of the output
	 * make a writer from location and pass it to each function for
	 * writing to location
	 * @param location path to be written
	 * @throws IOException if file handling have errors
	 */
	private void saving(String location) throws IOException {
		java.io.File file = new java.io.File(location);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Invalid path for save");
			}
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fw);

		writeFileSystem(out);
		writeHistory(out);
		writeStack(out);

		out.close();
		fw.close();
	}

	/**
	 * write the stack of directories for pushd and popd into the stream out
	 * @param out the output stream for the file
	 * @throws IOException if writer have error
	 */
	private void writeStack(BufferedWriter out) throws IOException {
		out.write("DIRECTORYSTACK");
		out.newLine();
		
		DirectoryStack stack = JShell.getDirStack();
		for (int i = 0; i < stack.getSize(); i++) {
			out.write(stack.peek(i).getFullPath());
			out.newLine();
		}
	}

	/**
	 * write the history of commands into the stream
	 * 
	 * @param out output stream for the file
	 * @throws IOException if writer have error
	 */
	private void writeHistory(BufferedWriter out) throws IOException {
		ArrayList<String> entries = JShell.getUserEntries().getAllEntries();
		out.write("HISTORY\n");
		for (int i = 0; i < entries.size(); i ++) {
			out.write(entries.get(i));
			out.newLine();
		}
	}

	/**
	 * write the whole file system including: file and file content, directory and
	 * current directory into the writer out
	 * also write current directory
	 * this is formated to a certain system
	 * @param out output stream for the file
	 * @throws IOException if writer have error
	 * 
	 */
	private void writeFileSystem(BufferedWriter out) throws IOException {
		writeDirectory(out, driver.JShell.getRoot(), 0); //for root directory
		recursiveFileSystem(out, driver.JShell.getRoot(), 0);
		out.write("PWD\n");
		out.write(JShell.getCurrDir().getFullPath()); //pwd or current dir
		out.newLine();
	}

	/**
	 * recursive writing using out, for the file system
	 * using a pre-order traversal method
	 * @param out output stream for the file
	 * @param dir directory to be added to the file
	 * @param level number of tabs for formatting
	 * @throws IOException writer issues
	 */
	private void recursiveFileSystem(BufferedWriter out, Directory dir, int level)
			throws IOException {
		for (File traversalFile:dir.getFileHead()) {
			writeFile(out, traversalFile, level + 1);
		}

		for(Directory traversalDir:dir.getDirHead()) {
			writeDirectory(out, traversalDir, level + 1);
			recursiveFileSystem(out, traversalDir, level + 1);
		}
	}

	/**
	 * this Function simply writes the directory's full path with the level being num of tabs
	 * @param out output stream writer to file
	 * @param dir directory that will be written
	 * @param level number of tabs
	 * @throws IOException issues with writting
	 */
	private void writeDirectory(BufferedWriter out, Directory dir, int level) throws IOException {
		for (int i = 0; i < level; i++) {
			out.write("\t");
		}
		out.write(dir.getFullPath());
		out.newLine();
	}

	/**
	 * Write the file name and file content of the given file
	 * with the level being number of tabs to the file
	 * @param out output stream writer
	 * @param file the file that will be written into the file
	 * @param level number of tabs
	 * @throws IOException issues with writting
	 */
	private void writeFile(BufferedWriter out, File file, int level) throws IOException {
		for (int i = 0; i < level; i++) {
			out.write("\t");
		}
		out.write(file.getName());
		out.newLine();
		out.write("EOD");
		out.newLine();
		out.write(file.getContents());
		out.newLine();
		out.write("ENDEOD");
		out.newLine();
	}
	
	/**
	 * check if it is valid input having exactly one arguments
	 * @param arguments
	 * @return
	 */
	public boolean isValid(String[] arguments) {
		return arguments.length == 1;
	}

}
