package structures;

//UTORID user_name: balamoni
//UT Student #: 1004428737
//Author: Samritha Balamoni

import javax.naming.InvalidNameException;

/**
 * Initializing
 * The file name
 * The file contents
 */
public class File {
	

	private String name; //name of file
	private String contents; //contents in string
	
	/**
	 * Takes in the filename and contents (text) and throws exceptions 
	 * if the name or contents are empty/null
	 * @param filename the name of the file being created
	 * @param text the text to be included in that file
	 */
	public File(String filename, String text)  throws InvalidNameException, FileEmptyException {
		
		if (checkValidName(filename)) {
			this.name = filename;
			this.contents = text;
		} else {
			this.name = null;
			this.contents = null;
		}
	}
	
	/**
	 * toString overrides to file name
	 * @return the file name
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * Checks if filename is empty/null
	 * @param String name
	 * @return true if the name is valid, false otherwise
	 */
	private boolean checkValidName(String name) {
		if (name == "" || name == null) {
			System.out.println("file name cannot be nothing");
			return false;
		} else {
			for (int i = 0; i < name.length(); i++) {
				if ("\t/ !@#$%^&*(){}`|<>?".indexOf(name.charAt(i)) != -1){
					System.out.println("File name cannot contain / !@#$%^&*(){}`|<>?");
					return false;
				}
			}
			return true;
		}

	}
	
	/**
	 * Sets name of the file
	 * @param name the new name of the file
	 * @return void
	 */
	public void setName(String name) {
		if (checkValidName(name)) {
			this.name = name;
		}
	}
	
	
	/**
	 * Gets name of the file
	 * @return String name of the file
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets contents of file
	 * @return String content of file
	 */
	public String getContents() {
		return this.contents;
	}
	
	/**
	 * Adds content to file, but does not overwrite. Just appends.
	 * @param String text to be added
	 * @return void
	 */
	public void addContent(String text) {
		this.contents = this.contents.concat(text);
	}
	
	/**
	 * Replace existing content of file to given text. 
	 * @param String text the new text to be added
	 * @return void
	 */
	public void replaceContent(String text) {
		this.contents = text;
	}
	


}
