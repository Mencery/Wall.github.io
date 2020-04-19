package input;

import static constants.InputHandlerConstants.NULL;

public class InputHandlerFactory {

    public InputHandler getInputHandler(String[] args) {
        if (isManualInput(args)) {
            return new ManualInputHandler();
        }
        return new FileInputHandler(args[0]);
    }

    private boolean isManualInput(String[] args) {
        return args == null || args.length == 0 || NULL.equals(args[0]);
    }
}
