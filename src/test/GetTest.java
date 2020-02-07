package test;

import static org.junit.Assert.*;
import java.io.IOException;
import javax.naming.InvalidNameException;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import driver.CommandHandler;
import driver.JShell;
import structures.FileEmptyException;

public class GetTest {
    Get get = new Get();
    @Test
    /**
     * Validate if command returns an empty string
     */
    public void testReturnEmpty() throws Exception {
        String[] args = {"http://www.utsc.utoronto.ca/~nick/b36/t2.html"};
        String actual = get.runCommand(args);
        String expected = "";
        assertEquals(expected,actual);
    }
    @Test
    /**
     * Validate if contents of file is correct
     */
    public void testFileContents() throws Exception {
        String[] args = {"https://www.utsc.utoronto.ca/~nick/cscB36"};
        get.runCommand(args);
        Cat cat = new Cat();
        args[0] = "cscB36";
        String actual = cat.runCommand(args);
        String expected = "Boo!";
        assertEquals(expected,actual);
    }
    @Test
    /**
     * Validate if given proper message with invalid URL
     */
    public void testInvalidURL() throws Exception {
        String[] args = {"a"};
        String actual = get.runCommand(args);
        String expected = "Invalid URL";
        assertEquals(expected,actual);
    }
    /**
     * Validate if file was created
     */
    /*
    public void testFileCreate() throws Exception {
        String[] args = {"https://www.utsc.utoronto.ca/~nick/cscB36"};
        Get.runCommand(args);
        Ls ls = new Ls();
        String actual = ls.runCommand(null);
        String expected = "cscB36";
        assertEquals(expected,actual);
    }
    /*
    */
}
