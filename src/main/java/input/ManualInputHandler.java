package input;

import java.util.Scanner;

public class ManualInputHandler implements InputHandler {

    @Override
    public String getInput() {
        Scanner s = new Scanner(System.in);
        String input = "";
        while (s.hasNextLine()) {
            String read = s.nextLine();
            if (read == null || read.isEmpty()) {
                break;
            }
            input = input.concat(read).concat(System.lineSeparator());
        }
        return input;
    }
}
