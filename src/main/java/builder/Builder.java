package builder;

import entity.Architecture;
import entity.Brick;
import entity.Wall;
import wrapper.Matrix;
import wrapper.Cell;

import java.util.*;

/**
 * checks whether it is possible to build wall
 */
public class Builder {
    private Architecture architecture;
    private Map<Brick, List<List<Cell>>> brickPositionMap;

    public Builder(Architecture architecture) {
        this.architecture = architecture;
        brickPositionMap = new HashMap<>();
    }

    /**
     * @return true if wall can be built
     */
    public boolean canBuild() {
        divideOnAvailableWallPart();
        return iterateOverCombinations();
    }

    /**
     * iterates over all possible combinations
     *
     * @return true if wall can be built
     */
    private boolean iterateOverCombinations() {
        Wall wall = architecture.getWall();
        Matrix wallMatrix = new Matrix(wall.getMatrix(), wall.getHeight(), wall.getWidth());
        int square = wallMatrix.getSquare();

        List<List<Cell>> megaList = new ArrayList<>();
        brickPositionMap.values().forEach(megaList::addAll);

        for (int i = 1; i < totalAmount()+1; i++) {
            for (int k = 0; k < megaList.size(); k++) {
                List<Cell> cList = new ArrayList<>(megaList.get(k));
                for (int j = k + 1; j < megaList.size(); j++) {
                    List<Cell> addedList = new ArrayList<>();
                    for (int t = 0; t < i; t++) {
                        if (j + t < megaList.size()) {
                            if (!isIntersect(megaList.get(j + t), cList)) {
                                cList.addAll(megaList.get(j + t));
                                addedList.addAll(megaList.get(j + t));
                            }
                            if (square == cList.size()) {
                                Matrix matrix = new Matrix(cList, wall.getHeight(), wall.getWidth());
                                if (wallMatrix.equals(matrix)) {
                                    return true;
                                }
                            }
                        }
                    }
                    cList.removeAll(addedList);
                }
            }
        }
        return false;
    }

    /**
     * @return total amount of bricks
     */
    private int totalAmount() {
        int t = 0;
        for (Brick brick : brickPositionMap.keySet()) {
            t += brick.getAmount();
        }
        return t;
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

    /**
     * calls checkBrick for all bricks
     */
    private void divideOnAvailableWallPart() {
        architecture.getBricks().forEach(this::checkBrick);
    }

    /**
     * @param brick - that is checked
     */
    private void checkBrick(Brick brick) {
        for (int i = 0; i < architecture.getWall().getHeight(); i++) {
            for (int j = 0; j < architecture.getWall().getWidth(); j++) {
                left2right(brick, i, j);
                top2bottom(brick, i, j);
            }
        }
    }

    /**
     * if brick can be settle list of cells add to brickPositionMap
     * cells checks from left to right
     *
     * @param brick - that is checked
     * @param i     - index of row
     * @param j     - index of column
     */
    private void left2right(Brick brick, int i, int j) {
        if (inRangeLeft2Right(brick, i, j)) {
            List<Cell> cells = new ArrayList<>();
            for (int t = i; t < i + brick.getHeight(); t++) {
                for (int k = j; k < j + brick.getWidth(); k++) {
                    if (architecture.getWall().getMatrix()[t][k] == 0) {
                        return;
                    }
                    cells.add(new Cell(t, k));
                }
            }
            addListCell(brick, cells);
        }
    }

    /**
     * if brick can be settle list of cells add to brickPositionMap
     * cells checks from  top to bottom
     *
     * @param brick - that is checked
     * @param i     - index of row
     * @param j     - index of column
     */
    private void top2bottom(Brick brick, int i, int j) {

        if (inRangeTop2Bottom(brick, i, j)) {
            List<Cell> cells = new ArrayList<>();
            for (int t = i; t < i + brick.getWidth(); t++) {
                for (int k = j; k < j + brick.getHeight(); k++) {
                    if (architecture.getWall().getMatrix()[t][k] == 0) {
                        return;
                    }
                    cells.add(new Cell(t, k));
                }
            }
            addListCell(brick, cells);
        }
    }

    /**
     * @param brick - that is checked
     * @param i     - index of row
     * @param j     - index of column
     * @return true if indices + brick don't go beyond the wall
     */
    private boolean inRangeLeft2Right(Brick brick, int i, int j) {
        return i + brick.getHeight() <= architecture.getWall().getHeight()
                &&
                j + brick.getWidth() <= architecture.getWall().getWidth();

    }

    /**
     * @param brick - that is checked
     * @param i     - index of row
     * @param j     - index of column
     * @return true if indices + brick don't go beyond the wall
     */
    private boolean inRangeTop2Bottom(Brick brick, int i, int j) {
        return i + brick.getWidth() <= architecture.getWall().getHeight()
                &&
                j + brick.getHeight() <= architecture.getWall().getWidth();
    }

    /**
     * if cells already in brickPositionMap they don't add
     * @param brick - key of brickPositionMap
     * @param cells - cells that add to brickPositionMap
     */
    private void addListCell(Brick brick, List<Cell> cells) {
        if (brickPositionMap.get(brick) != null) {
            List<List<Cell>> list = brickPositionMap.get(brick);
            for (List<Cell> cList : list) {
                if (cList.containsAll(cells)) {
                    return;
                }
            }
            brickPositionMap.get(brick).add(cells);
        } else {
            List<List<Cell>> list = new ArrayList<>();
            list.add(cells);
            brickPositionMap.put(brick, list);
        }
    }
}
