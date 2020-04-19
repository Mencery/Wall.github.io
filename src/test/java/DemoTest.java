import builder.Builder;
import entity.Architecture;
import input.InputHandlerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;


public class DemoTest {
    private ByteArrayInputStream input;
    private InputHandlerFactory ihf;

    @Before
    public void setUp() {
        ihf = new InputHandlerFactory();

    }

    @Test
    public void shouldReturnYes_whenFileInput() {
        Parser parser = new Parser(ihf
                .getInputHandler(new String[]{"src/main/resources/data/data1.txt"}).getInput());

        Architecture architecture = parser.parse();
        Builder builder = new Builder(architecture);


        String expected = "yes";
        String actual = builder.canBuild() ? "yes" : "no";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNo_whenFileInput() {
        Parser parser = new Parser(ihf
                .getInputHandler(new String[]{"src/main/resources/data/data2.txt"}).getInput());

        Architecture architecture = parser.parse();
        Builder builder = new Builder(architecture);


        String expected = "no";
        String actual = builder.canBuild() ? "yes" : "no";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnYes_whenManualInput() {
        String in = "6 3"+System.lineSeparator() +
                "101101"+System.lineSeparator() +
                "111111"+System.lineSeparator() +
                "111111"+System.lineSeparator() +
                "3"+System.lineSeparator() +
                "1 1 4"+System.lineSeparator() +
                "2 1 6"+System.lineSeparator() +
                "1 3 1"+System.lineSeparator()+
                System.lineSeparator();
        input = new ByteArrayInputStream(in.getBytes());
        System.setIn(input);
        Parser parser = new Parser(ihf
                .getInputHandler(new String[]{"null"}).getInput());

        Architecture architecture = parser.parse();
        Builder builder = new Builder(architecture);


        String expected = "yes";
        String actual = builder.canBuild() ? "yes" : "no";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNo_whenManualInput() {
        String in = "6 3"+System.lineSeparator() +
                "001100"+System.lineSeparator() +
                "110011"+System.lineSeparator() +
                "111111"+System.lineSeparator() +
                "3"+System.lineSeparator() +
                "2 2 3"+System.lineSeparator() +
                "1 3 1"+System.lineSeparator() +
                "1 4 90"+System.lineSeparator()+
                System.lineSeparator();
        input = new ByteArrayInputStream(in.getBytes());
        System.setIn(input);
        Parser parser = new Parser(ihf
                .getInputHandler(new String[]{"null"}).getInput());

        Architecture architecture = parser.parse();
        Builder builder = new Builder(architecture);


        String expected = "no";
        String actual = builder.canBuild() ? "yes" : "no";
        Assert.assertEquals(expected, actual);
    }

    @After
    public void cleanUpStream() {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}