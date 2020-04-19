package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileInputHandler implements InputHandler {
    String path;

    public FileInputHandler(String path) {
        this.path = path;
    }

    @Override
    public String getInput() {
        String input = "";
        try {
            Scanner s = new Scanner(new File(path));
            while (s.hasNextLine()) {
                String read = s.nextLine();
                input = input.concat(read).concat(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return input;
    }
}
