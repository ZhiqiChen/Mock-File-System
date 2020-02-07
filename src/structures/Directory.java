package structures;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import driver.JShell;

//UTORID user_name: chenz285
//UT Student #: 1005269870
//Author: Zhiqi Chen

/**
 * Directory class
 * @author chenz285
 *
 */
public class Directory {

	private String dirName; //name of directory
	private Directory parent = null; //parent directory to this directory
	private String dirFullPath; //full path from root for this directory
	//sub-directory is listed by linked list, head is the start
	//private Directory head = null; //the directories within this directory
	//private Directory next = null;//next directory in parent directory
	private ArrayList<Directory> dirHead = new ArrayList<Directory>();
	private ArrayList<File> fileHead = new ArrayList<File>();//file linkelist head
	private ArrayList<Object> children = new ArrayList<Object>();

	/**
	 * Root for Directory. this is the only dir that will
	 * have "/" as its dirName
	 */
	public Directory() {
		this.dirName= "/";
		this.dirFullPath = "/";
	}

	/**
	 * constructor for a Directory with a name and a parent provided on creation
	 * @param dirName name of Directory
	 * @param Parent parent of directory
	 */
	public Directory(String dirName, Directory Parent) {
		// check if the directory name is valid (legal characters, etc.)
		if (checkValid(dirName)) {
			// assign directory name, parents, full path from root
			this.dirName = dirName;
			if (Parent.getDirName().equals("/")) {
				this.dirFullPath = "/" + dirName;
			}
			else{
				this.dirFullPath = Parent.getFullPath() + "/" + dirName;
			}
			this.parent = Parent;
		}
	}

	/**
	 * check whether or not the directory's name is made of valid characters.
	 * @param name poential new directory name
	 */
	private boolean checkValid(String name) {
		for (int i = 0; i < name.length(); i++) {
			if ("\t/. !@#$%^&*(){}`|<>?".indexOf(name.charAt(i)) != -1){
				System.out.println("Dir cannot contain /. !@#$%^&*(){}`|<>?");
				return false;
			}
		}
		return true;
	}

	/**
	 * add a new directory under this directory.
	 * @param dir the new directory
	 * @return void
	 */
	public void addChild(Directory dir) {
		dirHead.add(dir);
	}
	

	/**
	 * add a new file under this directory.
	 * @param File the file to be added to this directory
	 * @return void
	 */

	public void addFile(File file) {
		if (file != null) {
			fileHead.add(file);
		}
	}
	

	/**
	 * find and return the directory from
	 * string path (the full path)
	 * return that directory or if it doesn't exist return null
	 * This method only checks THIS directory for that child.
	 * @param path the full path
	 * @return the directory for path or null if it doesn't exist
	 */
	public Directory findDir(String dirName){
		// Returns a child directory in this directory with name dirName
		// If there is no such child returns null
		if (dirHead == null) return null;
		for(Directory p:dirHead){
			if (p.dirName.equals(dirName)){
				return p;
			}
		}
		return null;
	}
	/**
	 * Find and return a file in THIS directory
	 * If that file isnt in the directory, return null
	 * @param fileName the file being searched for
	 * @return File file found or null if it doesn't exist
	 */
	public File findFile (String fileName) {
		for (File f:fileHead) {
			if (f.getName().equals(fileName)) {
				return f;
			}
		}
		return null;

	}

	/**
	 *  return the directory from the full path. 
	 *  if it doesn't exist return null. This searches the whole directory tree structure
	 *  If there are two directories with the same name, this method is not safe - the first one
	 *  found with that path will be returned.
	 * @param root the root of the file/directory structure
	 * @param path the full or relative path of directory to be found
	 * @return the found Directory, if no found directory returns null
	 */
	public static Directory findDir(Directory root, String path){
		path = completePath(path);
		String[] directoryList = path.split("/");
		Directory traversal = root;
		//ignore first one since its root
		for (int i = 1; i < directoryList.length; i++) { 
			if (directoryList[i].equals("..")) {
				
				if (traversal == root || traversal.getParent() == null) {
					System.out.println(traversal.getDirName() +
							" doesn't have a parent");
					return null;
				}
				traversal = traversal.getParent();
			} else if (directoryList[i].equals(".")) {
				//do nothing
			} else {
				traversal = traversal.findDir(directoryList[i]);
				if (traversal == null) return null;
			}
		}
		return traversal;
	}

	/**
	 * return the file given in the fullPath
	 * if it doesn't exist return null
	 * @param root root Directory 
	 * @param fullPath full path of the file, or relative path
	 * @return File the file specified in fullPath if found, null if not found
	 */
	public static File findFile(Directory root, String fullPath) {
		fullPath = completePath(fullPath);
		//get substring of path except the file
		int lastSlash = fullPath.lastIndexOf("/");
		String fileDirectoryName;
		if (lastSlash == 0)	 fileDirectoryName= "/"; 
		else {
			fileDirectoryName= fullPath.substring(0, lastSlash); 
		}
		String fileName = fullPath.substring(lastSlash+1); 
		//find the directory file belongs to and return it.
		Directory fileDirectory = findDir(root, fileDirectoryName);
		if (fileDirectory == null) {
			return null;
		}
		// finally, find the file in that directory and return the file
		return fileDirectory.findFile(fileName);
	}
	
	/**
	 * takes a relative or absolute path
	 * and return absolute path
	 * @param path the relative or absolute path
	 * @return absolute path
	 */
	private static String completePath(String path) {
		
		if (path.charAt(0) != '/') { //if relative path
			path = (path.equals("/") || JShell.getCurrDir().getFullPath() == "/") 
					? "/" + path : JShell.getCurrDir() + "/" + path;
		}
		return path;
	}
	
	
	
	/**
	 * toString prints out name of directory
	 * @return the name of the directory
	 */
	public String toString() {
		return dirName;
	}
	
	/**
	 * Sets name of the Directory
	 * @param name the new name of the directory
	 * @return void
	 */
	public void SetDirName(String name) {
		this.dirName = name;
	}
	

	/**
	 * Gets name of the Directory
	 * @return String the name of the directory
	 */
	public String getDirName() {
		return dirName;
	}
	
	/**
	 * Gets full path of directory
	 * @return String the full path
	 */
	public String getFullPath() {
		return this.dirFullPath;
	}
	
	/**
	 * Sets full path of directory, necessary if directory parent changed
	 * @param fullPath the full path of the directory to be set
	 * @return void
	 */
	public void setFullPath(String fullPath) {
		this.dirFullPath = fullPath;
	}
	
	/**
	 * Gets the head of the Directory linked list
	 * @return Directory (head)
	 */
	public ArrayList<Directory> getDirHead() {
		return dirHead;
	}
	
	
	/**
	 * Gets the File Head in linked list
	 * @return File
	 */
	public ArrayList<File> getFileHead() {
		return fileHead;
	}
	
	/**
	 * Sets the File Head in linked list
	 * @param File (file head)
	 * @return void
	 */
	public void setFileHead(ArrayList<File> fileHead) {
		this.fileHead = fileHead;
	}

	
	/**
	 * get parent of the current directory
	 * @return Directory the parent directory
	 */
	public Directory getParent() {
		return parent;
	}
	
	/**
	 * Set the parent directory of this directory
	 * @param newParent the new parent directory
	 */
	public void setParent(Directory newParent) {
		this.parent = newParent;
	}
	
	/**
	 * Delete child directory in this directory if it has name dirName
	 * @param dirName the name of the directory to be deleted
	 */
	public void deleteChildDir(String dirName) {
		if (dirHead != null) {
			int i = 0;
			for (Directory p:dirHead) {
				if (p.getDirName().equals(dirName)) {
					dirHead.remove(i);
					break;
				}
				i++;
			}
		}
	}
	
	/**
	 * Get the children of this directory
	 * @return the list of all the children
	 */
	public ArrayList<Object> Children() {
		return children;
	}
	
	/**
	 * If the file specified exists in this directory, this method overwrites 
	 * its contents with those provided. If not, it creates that file
	 * @param fileName the file to be overwritten
	 * @contents the new contents of the file
	 */
	public void overwriteFile(String fileName, String contents) {
		if (findFile(fileName) != null) {
			// file exists, overwrite its contents
			File f1 = findFile(fileName);
			f1.replaceContent(contents);
		} else {
			// file not in this directory, create with contents
			try {
				File f1 = new File(fileName, contents);
				addFile(f1);
			} catch (Exception e) {
				System.out.println("cannot create file");
			}
		}
	}
	
	/**
	 * If the file specified exists in this directory, appends to its contents.
	 * If not, create file with the contents.
	 * @param fileName the name of the file to be appended to
	 * @param contents the new contents of the file
	 */
	public void appendToFile(String fileName, String contents) {
		if (findFile(fileName) != null) {
			//file exists, append to it
			File f1 = findFile(fileName);
			f1.addContent(contents);
		} else {
			// file not in this directory, create with contents
			try {
				File f1 = new File(fileName, contents);
				addFile(f1);
		
			} catch (Exception e) {
				System.out.println("cannot create file");
			}
		}
	}
	
	/**
     * Finds all files in the directory with name fileName
     * and returns a string that has the path of all files
     * containing that name
     * @param fileName which is a String with The file name
     * @param filePaths which is a String containing filePaths
     * @return String containing filePaths
     */
	public String findFiles(String fileName, String filePaths) {
	  // Check if the file is in the current directory
	  File file = this.findFile(fileName);
	  // Check if the file was found
	  if (file != null) {
	    // Get the path of this file and store in string
	    filePaths += this.dirFullPath + "\n";
	  }
	  // Loop through each directory and call findFiles on them
	  ArrayList<Directory> directories = this.getDirHead();
	  if (directories != null) {
	    for (int i = 0; i < directories.size(); i++) {
	        filePaths = directories.get(i).findFiles(fileName, filePaths);
	      }
	  }
	  return filePaths;
	}
	/**
	 * Aamir
     * Finds all directories in the directory with name dirName
     * and returns a string that has the path of all files
     * containing that name
     * @param dirName which is a String with The dir name
     * @param dirPaths string containing directory paths
     * @return String containing directory paths
     */
    public String findDirectories(String dirName, String dirPaths) {
      // Check if the file is in the current directory
      Directory directory = this.findDir(dirName);
      // Check if the file was found
      if (directory != null) {
        // Get the path of this file and store in string
        dirPaths += this.dirFullPath + "\n";
      }
      // Loop through each directory and call findFiles on them
      ArrayList<Directory> directories = this.getDirHead();
      if (directories != null) {
        for (int i = 0; i < directories.size(); i++) {
            dirPaths = directories.get(i).findDirectories(dirName, dirPaths);
          }
      }
      return dirPaths;
    }
	
	/**
	 * say location of directory was changed, this will fix the full path to reflect that change
	 * recursively does so for all children (directories and files) as well.
	 * 
	 */
	public void fixFullPathRec() {
		this.dirFullPath = this.parent.getFullPath()+"/"+this.dirName;
		if(this.dirHead != null) {
			for (Directory dir : dirHead) {
				dir.fixFullPathRec();
			}
		}
	}

}
