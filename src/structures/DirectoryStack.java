 package structures;

 
	//UTORID user_name: balamoni
	//UT Student #: 1004428737
	//Author: Samritha Balamoni

import java.util.ArrayList;


	public class DirectoryStack {
		/**
		 * Represents the stack of directories for pushd in popd. Directories are stored as an 
		 * array list.
		 */
		
		private ArrayList<Directory> ds; //stack of directories
		
		/**
		 * make a new stack of directories 
		 */
		public DirectoryStack() {
			ds = new ArrayList<>();
		}
		
		/**
		 * Creates a directory stack with the directories specified in ds.
		 * @param ds the stack of directories
		 */
		public DirectoryStack(ArrayList<Directory> ds) {
			this.ds = ds;
		}
		
		/**
		 * Take in a Directory and push it into the stack
		 * @param Directory to be pushed
		 * @return void
		 */ 
		public void push(structures.Directory dir) {
			ds.add(dir);
		}
		
		/**
		 * Pop a directory out of the stack (returns it to the user and removes it from the stack
		 * If the stack is empty provide an error message
		 * @return Directory popped out of the stack, or current directory if there is no directory
		 * in the stack
		 */
		public Directory pop() {
			if(ds.size() != 0) {
				Directory d1 = ds.remove(ds.size()-1);
				return d1;
			}
			else {
				System.out.println("Sorry, stack is empty");
				return driver.JShell.getCurrDir();
			}
		}
		
		/**
		 * return the last Directory from the stack
		 * ie the top of the stack but not remove it
		 * @return the last Directory of ds
		 */
		public Directory peek() {
			return peek(ds.size()-1);
		}
		
		/**
		 * return the ith Directory from the bottom of the stack
		 * without removing it
		 * @param i the index of the directory that will be returned
		 * @return Directory that is at the ith index of ds
		 */
		public Directory peek(int i) {
			if (ds.size() != 0 && 0<=i && i<ds.size()) {
				return ds.get(i);
			} else {
				return null;
			}
		}
		
		/**
		 * return size of ds
		 * @return the size of ds
		 */
		public int getSize() {
			return ds.size();
		}

}
