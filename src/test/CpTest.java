package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import commands.Cp;
import driver.JShell;

public class CpTest {
	Cp cp;
	@Before
	public void test() {
		JShell jShell = new JShell();
		cp = new Cp();
	}
	
	@Test
	/**
	 * check if isValid returns the correct boolean value
	 */
	public void testIsValid() {
		String arguments[] = {"s", "b"};
		boolean actual = cp.isValid(arguments);
		boolean expected = true;
		assertEquals(expected, actual);
		
		String argument[] = {};
		actual = cp.isValid(argument);
		expected = false;
		assertEquals(expected, actual);
	}
	
	
	@Test
	/**
	 * check if toString returns the correct String
	 */
	public void testToString() {
		String actual = cp.toString();
		String expected = "cp [oldPath] [newPath] copy everything from oldPath to newPath, could be "
				+ "relative or full path";
		assertEquals(expected, actual);
	}

}
