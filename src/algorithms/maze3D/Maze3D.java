package algorithms.maze3D;

import algorithms.mazeGenerators.MazeException;
import algorithms.mazeGenerators.Position;

import java.util.Arrays;

public class Maze3D {
    private Position3D start;
    private Position3D goal;
    private int[][][] data;
    private int depths;
    private int rows;
    private int columns;

    public Maze3D(int depths, int rows, int columns, int set) throws IllegalArgumentException {
        if (rows < 2 || columns < 2 || depths < 2)
            throw new IllegalArgumentException("Maze size(depth x rows x columns) must be at least 2x2x2");
        if (!(set == 0 || set == 1)) throw new IllegalArgumentException("Maze set value must be 0 or 1");
        this.depths = depths - 1;
        this.rows = rows - 1;
        this.columns = columns - 1;
        this.data = new int[depths][rows][columns];
        this.start = null;
        this.goal = null;
        if (set == 1) {
            for (int[][] row : this.data)
                for (int[] rowCol : row)
                    Arrays.fill(rowCol, 1);
        }
    }

    public int[][][] getMap() {
        return this.data;
    }

    public void setStart(int depth, int row, int column) throws MazeException {
        if (row < 0 || row > this.rows) throw new MazeException("row", this.rows);
        if (column < 0 || column > this.columns) throw new MazeException("column", this.columns);
        if (depth < 0 || depth > this.depths) throw new MazeException("depth", this.depths);
        if ( !(row == 0 || row == this.rows || column == 0 || column == this.columns) ) throw new MazeException("start");
        this.start = new Position3D(depth, row, column);
    }

    public void setGoal(int depth, int row, int column) throws MazeException {
        if (row < 0 || row > this.rows) throw new MazeException("row", this.rows);
        if (column < 0 || column > this.columns) throw new MazeException("column", this.columns);
        if (depth < 0 || depth > this.depths) throw new MazeException("depth", this.depths);
        if ( !(row == 0 || row == this.rows || column == 0 || column == this.columns) ) throw new MazeException("goal");
        this.goal = new Position3D(depth, row, column);
    }

    public Position3D getStartPosition() {
        return this.start;
    }

    public Position3D getGoalPosition() {
        return this.goal;
    }

    public int getDepths() {
        return depths + 1;
    }

    public int getRows() {
        return rows + 1;
    }

    public int getColumns() {
        return columns + 1;
    }

    public int getCellValue(int depth, int row, int column) throws MazeException {
        if (row < 0 || row > this.rows) throw new MazeException("row", this.rows);
        if (column < 0 || column > this.columns) throw new MazeException("column", this.columns);
        if (depth < 0 || depth > this.rows) throw new MazeException("depth", this.depths);
        return this.data[depth][row][column];
    }

    public void buildWall(int depth, int row, int column) throws MazeException {
        if (row < 0 || row > this.rows) throw new MazeException("row", this.rows);
        if (column < 0 || column > this.columns) throw new MazeException("column", this.columns);
        if (depth < 0 || depth > this.rows) throw new MazeException("depth", this.depths);
        this.data[depth][row][column] = 1;
    }

    public void breakWall(int depth, int row, int column) throws MazeException {
        if (row < 0 || row > this.rows) throw new MazeException("row", this.rows);
        if (column < 0 || column > this.columns) throw new MazeException("column", this.columns);
        if (depth < 0 || depth > this.rows) throw new MazeException("depth", this.depths);
        this.data[depth][row][column] = 0;
    }

    public void print() {

        System.out.println("{");
        for (int d = 0; d <= this.depths; d++) {
            for (int i = 0; i <= this.rows; i++) {
                System.out.print("{ ");
                for (int j = 0; j <= this.columns; j++) {
                    if ( this.start != null && d == this.start.getDepthIndex() && i == this.start.getRowIndex() && j == this.start.getColumnIndex()) {
                        System.out.print("S ");
                    } else if (this.goal != null && d == this.goal.getDepthIndex() && i == this.goal.getRowIndex() && j == this.goal.getColumnIndex()) {
                        System.out.print("E ");
                    } else {
                        System.out.print(this.data[d][i][j] + " ");
                    }
                }
                System.out.println("}");
            }
            if (d < this.depths) {
                System.out.println("_____________");
            }
        }
        System.out.print("}");
    }
}
