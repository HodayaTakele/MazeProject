package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns){
        long generationStartTime = System.currentTimeMillis();
        generate(rows, columns);
        long generationEndTime = System.currentTimeMillis();
        return (generationEndTime - generationStartTime);
    }

    @Override
    public abstract Maze generate(int rows, int columns);
}
