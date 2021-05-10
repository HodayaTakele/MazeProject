package algorithms.mazeGenerators;


public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns, 0 );
        maze.setStart(0,0);
        maze.setGoal(rows-1,columns-1);
        return maze;
    }
}
