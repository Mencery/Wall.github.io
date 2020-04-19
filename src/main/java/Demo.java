import builder.Builder;
import entity.Architecture;
import input.InputHandlerFactory;
import parser.Parser;

public class Demo {

    public static void main(String[] args) {
        InputHandlerFactory ihf = new InputHandlerFactory();

        Parser parser = new Parser(ihf.getInputHandler(args).getInput());

        Architecture architecture = parser.parse();
        Builder builder = new Builder(architecture);

        System.out.print(builder.canBuild() ? "yes" : "no");
    }

}
