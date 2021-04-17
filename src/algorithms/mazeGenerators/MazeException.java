package algorithms.mazeGenerators;

public class MazeException extends IndexOutOfBoundsException{
    public MazeException(String indexType, int bound){
        super(String.format("Invalid %s, Maze bounds are: 0 <= %s <= %s.", indexType, indexType, bound));
    }

    public MazeException(){
        super("Maze Goal Position must be different from start position which is {0,0}");
    }

}
