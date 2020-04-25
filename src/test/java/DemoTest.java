import builder.WallBuilder;
import entity.Architecture;
import input.InputHandlerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.WallParser;

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
        //given
        WallParser parser = new WallParser(ihf
                .getInputHandler(new String[]{"src/main/resources/data/data1.txt"}).getInput());

        Architecture architecture = parser.parse();
        //when
        WallBuilder builder = new WallBuilder(architecture);
        String expected = "yes";
        String actual = builder.canBuild() ? "yes" : "no";
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNo_whenFileInput() {
        //given
        WallParser parser = new WallParser(ihf
                .getInputHandler(new String[]{"src/main/resources/data/data2.txt"}).getInput());

        Architecture architecture = parser.parse();
        //when
        WallBuilder builder = new WallBuilder(architecture);
        String expected = "no";
        String actual = builder.canBuild() ? "yes" : "no";
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnYes_whenManualInput() {
        //given
        String in = "6 3" + System.lineSeparator() +
                "101101" + System.lineSeparator() +
                "111111" + System.lineSeparator() +
                "111111" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "1 1 4" + System.lineSeparator() +
                "2 1 5" + System.lineSeparator() +
                "1 3 1" + System.lineSeparator() +
                System.lineSeparator();
        input = new ByteArrayInputStream(in.getBytes());
        System.setIn(input);
        WallParser parser = new WallParser(ihf
                .getInputHandler(new String[]{"null"}).getInput());

        Architecture architecture = parser.parse();
        //when
        WallBuilder builder = new WallBuilder(architecture);

        String expected = "yes";
        String actual = builder.canBuild() ? "yes" : "no";
        //then
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void shouldReturnYes_whenManualInput2() {
        //given
        String in = "4 4" + System.lineSeparator() +
                "1011" + System.lineSeparator() +
                "1111" + System.lineSeparator() +
                "1111" + System.lineSeparator() +
                "1111" + System.lineSeparator() +
                "4" + System.lineSeparator() +
                "1 1 4" + System.lineSeparator() +
                "2 2 4" + System.lineSeparator() +
                "2 1 6" + System.lineSeparator() +
                "1 3 1" + System.lineSeparator() +
                System.lineSeparator();
        input = new ByteArrayInputStream(in.getBytes());
        System.setIn(input);
        WallParser parser = new WallParser(ihf
                .getInputHandler(new String[]{"null"}).getInput());

        Architecture architecture = parser.parse();
        //when
        WallBuilder builder = new WallBuilder(architecture);

        String expected = "yes";
        String actual = builder.canBuild() ? "yes" : "no";
        //then
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void shouldReturnNo_whenManualInput2() {
        //given
        String in = "4 4" + System.lineSeparator() +
                "1001" + System.lineSeparator() +
                "0000" + System.lineSeparator() +
                "1111" + System.lineSeparator() +
                "0000" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "1 2 4" + System.lineSeparator() +
                "2 2 4" + System.lineSeparator() +
                System.lineSeparator();
        input = new ByteArrayInputStream(in.getBytes());
        System.setIn(input);
        WallParser parser = new WallParser(ihf
                .getInputHandler(new String[]{"null"}).getInput());

        Architecture architecture = parser.parse();
        //when
        WallBuilder builder = new WallBuilder(architecture);

        String expected = "no";
        String actual = builder.canBuild() ? "yes" : "no";
        //then
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void shouldReturnNo_whenManualInput() {
        //given
        String in = "6 3" + System.lineSeparator() +
                "001100" + System.lineSeparator() +
                "110011" + System.lineSeparator() +
                "111111" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "2 2 3" + System.lineSeparator() +
                "1 3 1" + System.lineSeparator() +
                "1 4 90" + System.lineSeparator() +
                System.lineSeparator();
        input = new ByteArrayInputStream(in.getBytes());
        System.setIn(input);
        WallParser parser = new WallParser(ihf
                .getInputHandler(new String[]{"null"}).getInput());

        Architecture architecture = parser.parse();
        //when
        WallBuilder builder = new WallBuilder(architecture);
        String expected = "no";
        String actual = builder.canBuild() ? "yes" : "no";
        //then
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