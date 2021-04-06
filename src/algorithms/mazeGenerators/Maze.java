package algorithms.mazeGenerators;

import java.util.Arrays;

public class Maze {
    private Position start;
    private Position goal;
    private int[][] data;
    private int rows;
    private int columns;

    public Maze( int rows, int columns, int set){
        if ( rows < 0  || columns < 0 || !( set == 0  || set == 1)) {
            //throw exception
        }
        this.rows = rows - 1;
        this.columns = columns - 1;
        this.data = new int[rows][columns];
        this.start = new Position(0, 0);
        this.goal = new Position(rows-1,columns-1);
        if (set == 1){
            for (int[] row : this.data)
                Arrays.fill(row, 1);
        }
    }

    public void setGoal(int row, int column){
        if ( row < 0 || row > this.rows || column < 0 || column > this.columns ) {
            //throw out of range exception
        }
        this.goal = new Position(row,column);
    }

    public Position getStartPosition() {
        return start;
    }

    public Position getGoalPosition() {
        return goal;
    }

    public int[][] getData() {
        return data;
    }

    public int getRows() {
        return rows + 1;
    }

    public int getColumns() {
        return columns + 1;
    }

    public int getCellValue(int row, int column) {
        if (row < 0 || row > this.rows || column < 0 || column > this.columns) {
            //throw out of range exception
        }
        return this.data[row][column];
    }

    public void buildWall( int row, int column){
        if ( row < 0 || row > this.rows || column < 0 || column > this.columns ) {
            //throw out of range exception
        }
        this.data[row][column] = 1;
    }

    public void breakWall( int row, int column){
        if ( row < 0 || row > this.rows || column < 0 || column > this.columns ) {
            //throw out of range exception
        }
        this.data[row][column] = 0;
    }

    public void print(){
        for (int i = 0; i <= this.rows; i++) {
            System.out.print("{ ");
            for (int j = 0; j <= this.columns; j++) {
                if(i == this.start.getRowIndex() && j == this.start.getColumnIndex()){
                    System.out.print( "S ");
                }
                else if(i == this.goal.getRowIndex() && j == this.goal.getColumnIndex()){
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
