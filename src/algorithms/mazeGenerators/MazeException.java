package algorithms.mazeGenerators;

public class MazeException extends IndexOutOfBoundsException{
    public MazeException(String indexType, int bound){
        super(String.format("Invalid %s, Maze bounds are: 0 <= %s <= %s.", indexType, indexType, bound));
    }
    public MazeException(String p1){
        super( String.format("Maze %s position must be on the maze limits", p1) );
    }
    public MazeException( String p1, String p2, int row, int col){
        super(String.format("Trying to set maze %s position with %s position values which are { %s ,%s }", p1, p2, row, col));
    }

}
