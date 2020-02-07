package structures;

//UTORID user_name: balamoni
//UT Student #: 1004428737
//Author: Samritha Balamoni

public class FileEmptyException extends Exception {
	
	/**
	 * If the file is empty output exception
	 * @return void
	 * @param String message
	 */
	public FileEmptyException(String message) {
		super(message);
	}

}
