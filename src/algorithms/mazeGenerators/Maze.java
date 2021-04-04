package algorithms.mazeGenerators;

import java.util.Arrays;

public class Maze {
    private Position start;
    private Position goal;
    private int[][] data;
    private int rows;
    private int columns;
    private boolean isSolved;

    public Maze(int rows, int columns, Position s, Position g) {
        if (rows < 0  || columns < 0 || s == null || g == null) {
            //throw exception
        }
        this.rows = rows - 1;
        this.columns = columns - 1;
        this.data = new int[rows][columns];
        this.start = s;
        this.goal = g;
    }

    public Maze(int rows, int columns) {
        new Maze( rows, columns, new Position(0, 0), new Position(this.rows, this.columns) );

    }

    public Position getStart() {
        return start;
    }

    public Position getGoal() {
        return goal;
    }

    public int[][] getData() {
        return data;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void buildWall( int row, int column){
        if ((row >= 0 && row <= this.rows) || (column >=0 && column <= this.columns)) {
            //throw exception
        }
        this.data[row][column] = 1;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void print(){
        for (int i = 0; i <= this.rows; i++) {
            System.out.print("{ ");
            for (int j = 0; j <= this.columns; j++) {
                if(i == this.start.getRow() && j == this.start.getColumn()){
                    System.out.print( "S ");
                }
                else if(i == this.goal.getRow() && j == this.goal.getColumn()){
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
