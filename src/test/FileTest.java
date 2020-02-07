//author: Samritha Balamoni
//UTORID: 1004428737

package test;
import static org.junit.Assert.*;
import javax.naming.InvalidNameException;
import org.junit.Before;
import structures.*;
import org.junit.Test;

public class FileTest {
	
	File file;
	
	@Before
	public void setup() throws FileEmptyException, InvalidNameException {
		this.file = new File("file.txt", "Hi");
	}
	/**
	 * testing get name
	 */
	@Test
	public void testGetName() {
		String expected = "file.txt";
		String actual = file.getName();
		assertEquals(expected, actual);
	}
	/**
	 * testing set name
	 */
	@Test
	public void testSetName() {
		file.setName("cool.txt");
		String expected = "cool.txt";
		String actual = file.getName();
		assertEquals(expected, actual);
	}
	/**
	 * testing set empty name
	 */
	@Test(expected = InvalidNameException.class)
	public void testSetEmptyName() throws Exception {
		file.setName("");
	}
	/**
	 * testing get content of file
	 */
	@Test
	public void testGetContent() {
		String expected = "Hi";
		String actual = file.getContents();
		assertEquals(expected, actual);
	}
	/**
	 * testing add content to file
	 */
	@Test
	public void testAddContent() {
		file.addContent("all");
		String expected = "Hi all";
		String actual = file.getContents();
		assertEquals(expected, actual);
	}
	/**
	 * testing add empty content to file
	 */
	public void testAddEmptyContent() {
		file.addContent("");
		String expected = "Hi all";
		String actual = file.getContents();
		assertEquals(expected, actual);
	}
	/**
	 * testing replace contents of file
	 */
	@Test
	public void testReplaceContents() throws FileEmptyException {
		file.replaceContent("replace");
		String expected = "replace";
		String actual = file.getContents();
		assertEquals(expected, actual);
	}
	/**
	 * testing replace with empty content to file
	 */
	@Test
	public void testReplaceWithEmptyContent() throws FileEmptyException {
		file.replaceContent("");
		String expected = "";
		String actual = file.getContents();
		assertEquals(expected, actual);
	}
	/**
	 * testing replace with null content to file
	 */
	@Test
	public void testReplaceWithNullContent() throws FileEmptyException {
		file.replaceContent(null);
	}
	
	



}
