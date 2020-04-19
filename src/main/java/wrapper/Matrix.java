package wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    private List<Cell> cells;
    private int[][] matrix;

    public Matrix(List<Cell> cells, int h, int w) {
        matrix = new int[h][w];
        this.cells = cells;
        for (Cell cell : cells) {
            matrix[cell.getI()][cell.getJ()] = 1;
        }

    }

    public Matrix(int[][] matrix, int h, int w) {
        this.matrix = matrix;
        cells = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (matrix[i][j] == 1) {
                    cells.add(new Cell(i, j));
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        boolean f = true;
        for (int i = 0; i < matrix.length; i++) {
            if (!Arrays.equals(matrix[i], matrix1.matrix[i])) {
                f = false;
            }
        }

        return f;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matrix);
    }

    public int getSquare() {
        return cells.size();
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] x : matrix) {
            for (int y : x) {
                stringBuilder.append(y);
            }

            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private boolean isIntersect(List<Cell> cells2) {
        for (Cell cell : cells) {
            if (cells2.contains(cell)) {
                return true;
            }
        }
        return false;
    }
}
