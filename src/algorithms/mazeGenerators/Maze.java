package algorithms.mazeGenerators;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Maze {
    private Position start;
    private Position goal;
    @SuppressWarnings("FieldMayBeFinal")
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

    public Maze(byte[] maze){
        int infoEnd=0;
        if (maze[0] == 0){
            infoEnd = 7;
            this.rows = Byte.toUnsignedInt(maze[1]);
            this.columns = Byte.toUnsignedInt(maze[2]);
            this.start = new Position(Byte.toUnsignedInt(maze[3]), Byte.toUnsignedInt(maze[4]));
            this.goal = new Position(Byte.toUnsignedInt(maze[5]), Byte.toUnsignedInt(maze[6]));
        }
        else if (maze[0] == 1){
            infoEnd = 25;
            this.rows = ByteBuffer.wrap(maze, 1,4).getInt();
            this.columns = ByteBuffer.wrap(maze, 5,4).getInt();
            this.start = new Position(ByteBuffer.wrap(maze, 9,4).getInt(), ByteBuffer.wrap(maze, 13,4).getInt());
            this.goal = new Position(ByteBuffer.wrap(maze, 17,4).getInt(), ByteBuffer.wrap(maze, 21,4).getInt());
        }

        this.data = new int[rows + 1][columns + 1];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                this.data[i][j] = Byte.toUnsignedInt(maze[infoEnd+(i*getColumns())+j]);
            }
        }
    }

    /**
     * mazeByte[0] - represent the number of cells we use for maze information
     * if mazeByte[0] = 0 : we use the first 7 bytes for information.
     *          mazeByte[1] = rows; mazeByte[2] = columns; mazeByte[3] = start.getRowIndex(); mazeByte[4] = start.getColumnIndex();
     *          mazeByte[5] = goal.getRowIndex(); mazeByte[6] = goal.getColumnIndex(); mazeByte[7: ] = maze data
     * if mazeByte[0] = 1 : we use the first 25 bytes for information.
     *          mazeByte[1:4] = rows; mazeByte[5:8] = columns; mazeByte[9:12] = start.getRowIndex(); mazeByte[13:16] = start.getColumnIndex();
     *          mazeByte[17:20] = goal.getRowIndex(); mazeByte[21:24] = goal.getColumnIndex(); mazeByte[25: ] = maze data
     * @return byte[] = [rows, columns, goal, maze data] - with the above decomposition
     */
    public byte[] toByteArray(){
        byte[] mazeByte;
        int infoEnd;
        if (rows <= 255 && columns <= 255 && goal.getRowIndex() <= 255 && goal.getColumnIndex() <= 255 && start.getRowIndex() <= 255 && start.getColumnIndex() <= 255) {
            infoEnd = 7;
            mazeByte = new byte[infoEnd + (getRows() * getColumns())];
            mazeByte[0] = 0;
            mazeByte[1] = (byte)rows;
            mazeByte[2] = (byte)columns;
            mazeByte[3] = (byte)start.getRowIndex();
            mazeByte[4] = (byte)start.getColumnIndex();
            mazeByte[5] = (byte)goal.getRowIndex();
            mazeByte[6] = (byte)goal.getColumnIndex();
        }
        else{
            infoEnd = 25;
            mazeByte = new byte[infoEnd + (getRows() * getColumns())];
            mazeByte[0] = 1;
            ArrayList<Integer> info = new ArrayList<>(Arrays.asList(rows, columns, start.getRowIndex(), start.getColumnIndex(), goal.getRowIndex(), goal.getColumnIndex()));
            ArrayList<byte[]> mazeInfo = new ArrayList<>();
            for (Integer in: info) {
                mazeInfo.add(ByteBuffer.allocate(4).putInt(in).array());
            }
            for (int i = 0; i < mazeInfo.size(); i++) {
                byte[] temp = mazeInfo.get(i);
                for (int j = 0; j < 4; j++) {
                    mazeByte[(i*4)+j+1] = temp[j];
                }
            }
        }

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                mazeByte[infoEnd + (i * getColumns()) + j] = (byte) data[i][j];
            }
        }
        return mazeByte;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maze)) return false;
        Maze maze = (Maze) o;
        return rows == maze.rows && columns == maze.columns && start.equals(maze.start) && goal.equals(maze.goal) && Arrays.deepEquals(data, maze.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(start, goal, rows, columns);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
