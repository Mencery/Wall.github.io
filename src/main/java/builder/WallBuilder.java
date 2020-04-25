package builder;

import entity.Architecture;
import entity.Brick;
import entity.Wall;
import spliter.WallSplitter;
import wrapper.Matrix;
import wrapper.Cell;

import java.util.*;

/**
 * checks whether it is possible to build wall
 */
public class WallBuilder implements Builder {
    private Architecture architecture;
    private Map<Brick, List<List<Cell>>> brickPositionMap;

    public WallBuilder(Architecture architecture) {
        this.architecture = architecture;
    }

    /**
     * @return true if wall can be built
     */
    @Override
    public boolean canBuild() {
        brickPositionMap = new WallSplitter(architecture).divideOnPart();

        if (brickPositionMap.keySet().size() != architecture.getBricks().size()) {
            return false;
        }
        trimBrickAmount();
        return iterateOverCombinations();
    }

    /**
     * iterates over all possible combinations
     *
     * @return true if wall can be built
     */
    private boolean iterateOverCombinations() {
        int brickSize = architecture.getBricks().size();
        int[] counters = new int[brickSize];

        for (int i = 0; i < getAmountCombination(); i++) {
            if (checkCombination(counters)) {
                return true;
            }
            for (int j = 0; j < brickSize; j++) {
                counters[j]++;
                if (counters[j] <= architecture.getBricks().get(j).getAmount())
                    break;
                counters[j] = 0;
            }

        }

        return false;
    }

    private boolean checkCombination(int[] counters) {
        Wall wall = architecture.getWall();
        Matrix wallMatrix = new Matrix(wall.getMatrix(), wall.getHeight(), wall.getWidth());
        int square = wallMatrix.getSquare();
        int brickSize = architecture.getBricks().size();
        if (checkSquare(counters, square)) {
            int[][] combinations = getCombinationsMatrix(counters);
            while (incrementCombinationsMatrix(combinations)) {
                List<Cell> addedList = new ArrayList<>();
                for (int k = 0; k < brickSize; k++) {
                    Brick brick = architecture.getBricks().get(k);
                    for (int l = 0; l < combinations[k].length; l++) {
                        if (!isIntersect(addedList, brickPositionMap.get(brick).get(combinations[k][l]))) {
                            addedList.addAll(brickPositionMap.get(brick).get(combinations[k][l]));
                        }
                    }

                }
                Matrix matrix = new Matrix(addedList, wall.getHeight(), wall.getWidth());
                if (wallMatrix.equals(matrix)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param combinations - matrix of cell numbers that use in combinations
     * @return true if search is not completed yet
     */
    private boolean incrementCombinationsMatrix(int[][] combinations) {
        boolean transition = false;
        for (int i = combinations.length - 1; i >= 0; i--) {
            Brick brick = architecture.getBricks().get(i);
            int s = brickPositionMap.get(brick).size();
            for (int j = combinations[i].length - 1; j >= 0; j--) {
                if (combinations[i][j] >= s - combinations[i].length + j - 1) {
                    transition = true;
                    if (j == 0) {
                        for (int k = 0; k < combinations[i].length; k++) {
                            combinations[i][k] = k;
                        }
                        i--;
                        if (i < 0) {
                            return false;
                        }
                        j = combinations[i].length - 1;
                        if (j < 0) {
                            return false;
                        }
                        combinations[i][j]++;
                        break;

                    } else {
                        for (int k = 0; k < combinations[i].length - 1; k++)
                            if (combinations[i][k + 1] >= s - combinations[i].length + k - 1) {
                                combinations[i][k + 1] = ++combinations[i][k] + 1;
                                break;
                            }
                    }
                }
            }
        }

        if (!transition) {
            for (int i = combinations.length - 1; i >= 0; i--) {
                if (combinations[i].length > 0) {
                    combinations[i][combinations[i].length - 1]++;
                    break;
                }
            }
        }
        return true;
    }

    /**
     * @param counters - for bricks
     * @return matrix of cell numbers that use in combinations
     */
    private int[][] getCombinationsMatrix(int[] counters) {
        int n = architecture.getBricks().size();
        int[][] combinations = new int[n][];
        for (int i = 0; i < n; i++) {
            combinations[i] = new int[counters[i]];
            for (int j = 0; j < combinations[i].length; j++) {
                combinations[i][j] = j;
            }
        }
        return combinations;
    }

    /**
     * @param counters   - for bricks
     * @param wallSquare - amount of "1" in wall
     * @return true if amount of "1" in wall equals amount of "1" in bricks
     */
    private boolean checkSquare(int[] counters, int wallSquare) {
        int totalSquare = 0;
        for (int i = 0; i < counters.length; i++) {
            Brick brick = architecture.getBricks().get(i);
            totalSquare += brick.getHeight() * brick.getWidth() * counters[i];
        }
        return totalSquare == wallSquare;
    }

    private int getAmountCombination() {
        return architecture.getBricks().stream().mapToInt(brick -> brick.getAmount() + 1).reduce(1, (a, b) -> a * b);
    }

    private void trimBrickAmount() {
        Wall wall = architecture.getWall();
        Matrix wallMatrix = new Matrix(wall.getMatrix(), wall.getHeight(), wall.getWidth());
        int square = wallMatrix.getSquare();

        architecture.getBricks().forEach(brick -> {
            int brickSquare = brick.getHeight() * brick.getWidth();
            if (brickSquare * brick.getAmount() > square) {
                brick.setAmount((int) Math.ceil(square / (double) brickSquare));
            }
        });
    }

    /**
     * @param cells1 - first set
     * @param cells2 - second set
     * @return true if they have common cell
     */
    private boolean isIntersect(List<Cell> cells1, List<Cell> cells2) {
        for (Cell cell : cells1) {
            if (cells2.contains(cell)) {
                return true;
            }
        }
        return false;
    }
}
