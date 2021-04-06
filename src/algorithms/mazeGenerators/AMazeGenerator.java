package algorithms.mazeGenerators;
//import algorithms.mazeGenerators.IMazeGenerator;

public abstract class AMazeGenerator implements IMazeGenerator {
    public AMazeGenerator() { }

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns){
        long generationStartTime = System.currentTimeMillis();
        generate(rows, columns);
        long generationEndTime = System.currentTimeMillis();
        return (generationEndTime - generationStartTime) / 1000;
    }
}
