package algorithms.mazeGenerators;
//import algorithms.mazeGenerators.IMazeGenerator;

public abstract class AMazeGenerator implements IMazeGenerator {
    public AMazeGenerator() { }

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns){ return 1;}
}
