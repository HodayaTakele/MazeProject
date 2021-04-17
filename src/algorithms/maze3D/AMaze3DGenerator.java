package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMazeGenerator3D{
    public AMaze3DGenerator() { }

    @Override
    public long measureAlgorithmTimeMillis(int depth, int rows, int columns){
        long generationStartTime = System.currentTimeMillis();
        generate(depth, rows, columns);
        long generationEndTime = System.currentTimeMillis();
        return (generationEndTime - generationStartTime);
    }
}
