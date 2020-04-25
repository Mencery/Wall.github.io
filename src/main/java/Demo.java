import builder.Builder;
import builder.WallBuilder;
import entity.Architecture;
import input.InputHandlerFactory;
import parser.Parser;
import parser.WallParser;

public class Demo {

    public static void main(String[] args) {
        InputHandlerFactory ihf = new InputHandlerFactory();

        Parser parser = new WallParser(ihf.getInputHandler(args).getInput());

        Architecture architecture = parser.parse();
        Builder builder = new WallBuilder(architecture);

        System.out.print(builder.canBuild() ? "yes" : "no");
    }

}
