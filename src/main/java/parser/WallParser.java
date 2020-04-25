package parser;

import entity.Architecture;
import entity.Brick;
import entity.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.ParserConstants.*;

public class WallParser implements Parser {
    private String input;

    public WallParser(String input) {
        this.input = input;
    }

    @Override
    public Architecture parse() {
        Wall wall = new Wall();
        parseWallWidthHeight(wall);
        parseWallMatrix(wall);

        List<Brick> bricks = new ArrayList<>(parseBricksCount());
        parseBricks(bricks);

        Architecture architecture = new Architecture();
        architecture.setWall(wall);
        architecture.setBricks(bricks);
        return architecture;
    }

    private void parseWallWidthHeight(Wall wall) {
        Pattern pattern = Pattern.compile(WALL_WIDTH_HEIGHT_PATTERN);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String[] widthHeight = matcher.group().trim().split(" ");
            wall.setWidth(Integer.parseInt(widthHeight[0]));
            wall.setHeight(Integer.parseInt(widthHeight[1]));
        } else {
            throw new IllegalStateException("cannot parse wall width and height ");
        }
    }

    private void parseWallMatrix(Wall wall) {
        Pattern pattern = Pattern.compile(WALL_MATRIX_PATTERN);
        Matcher matcher = pattern.matcher(input);
        int[][] matrix = new int[wall.getHeight()][wall.getWidth()];
        int i = 0;
        int j = 0;
        while (matcher.find()) {
            if (i == wall.getHeight()) {
                break;
            }
            char[] lineNumbers = matcher.group().trim().toCharArray();
            for (char number : lineNumbers) {
                matrix[i][j] = Character.getNumericValue(number);
                j++;
            }
            j = 0;
            i++;
        }
        wall.setMatrix(matrix);
    }

    private int parseBricksCount() {
        Pattern pattern = Pattern.compile(BRICKS_COUNT_PATTERN);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group().trim());
        } else {
            throw new IllegalStateException("cannot bricks count");
        }
    }

    private void parseBricks(List<Brick> bricks) {
        Pattern pattern = Pattern.compile(BRICKS_PATTERN);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            Brick brick = new Brick();
            String[] characteristics = matcher.group().trim().split(" ");
            brick.setWidth(Integer.parseInt(characteristics[0]));
            brick.setHeight(Integer.parseInt(characteristics[1]));
            brick.setAmount(Integer.parseInt(characteristics[2]));
            bricks.add(brick);
        }
    }
}
