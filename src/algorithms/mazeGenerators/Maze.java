package algorithms.mazeGenerators;

import java.util.Arrays;

public class Maze {
    private Position start;
    private Position goal;
    private int[][] data;
    private int rows;
    private int columns;

    public Maze( int rows, int columns, int set) throws IllegalArgumentException{
        if ( rows < 2  || columns < 2 ) throw new IllegalArgumentException("Maze size(rows x columns) must be at least 2x2");
        if ( !( set == 0  || set == 1) ) throw new IllegalArgumentException("Maze set value must be 0 or 1");
        this.rows = rows - 1;
        this.columns = columns - 1;
        this.data = new int[rows][columns];
        this.start = null;
        this.goal = null;
        if (set == 1){
            for (int[] row : this.data)
                Arrays.fill(row, 1);
        }
    }

    public Maze(Maze maze) {
        this.start = maze.start;
        this.goal = maze.goal;
        this.rows = maze.getRows() - 1;
        this.columns = maze.getColumns() - 1;
        this.data = new int[maze.getRows()][maze.getColumns()];
        for (int i = 0; i < maze.data.length; i++) {
            this.data[i] = maze.data[i].clone();
        }
    }

    public void setStart(int row, int column) throws MazeException {
        if ( row < 0 || row > this.rows ) throw new MazeException("row", this.rows);
        if ( column < 0 || column > this.columns ) throw new MazeException("column", this.columns);
        if ( !(row == 0 || row == this.rows ||column == 0 || column == this.columns) ) throw new MazeException("start");
        this.start = new Position(row,column);
    }

    public void setGoal(int row, int column) throws MazeException {
        if ( row < 0 || row > this.rows ) throw new MazeException("row", this.rows);
        if ( column < 0 || column > this.columns ) throw new MazeException("column", this.columns);
        if ( !(row == 0 || row == this.rows ||column == 0 || column == this.columns) ) throw new MazeException("goal");
        this.goal = new Position(row,column);
    }

    public Position getStartPosition() { return start; }

    public Position getGoalPosition() { return goal; }

    public int getRows() { return rows + 1; }

    public int getColumns() { return columns + 1; }

    public int getCellValue(int row, int column) throws MazeException {
        if ( row < 0 || row > this.rows ) throw new MazeException("row", this.rows);
        if ( column < 0 || column > this.columns ) throw new MazeException("column", this.columns);
        return this.data[row][column];
    }

    public void buildWall( int row, int column) throws MazeException {
        if ( row < 0 || row > this.rows ) throw new MazeException("row", this.rows);
        if ( column < 0 || column > this.columns ) throw new MazeException("column", this.columns);
        this.data[row][column] = 1;
    }

    public void breakWall( int row, int column) throws MazeException {
        if ( row < 0 || row > this.rows ) throw new MazeException("row", this.rows);
        if ( column < 0 || column > this.columns ) throw new MazeException("column", this.columns);
        this.data[row][column] = 0;
    }

    public void print(){
        for (int i = 0; i <= this.rows; i++) {
            System.out.print("{ ");
            for (int j = 0; j <= this.columns; j++) {
                if(this.start != null && i == this.start.getRowIndex() && j == this.start.getColumnIndex()){
                    System.out.print( "S ");
                }
                else if(this.goal!= null && i == this.goal.getRowIndex() && j == this.goal.getColumnIndex()){
                    System.out.print( "E ");
                }
                else {
                    System.out.print(this.data[i][j] + " ");
                }
            }
            System.out.println("}");
        }

    }

}
