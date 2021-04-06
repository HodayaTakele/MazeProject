package algorithms.search;

import algorithms.mazeGenerators.Maze;

public class SearchableMaze implements ISearchable {
    private MazeState startState;
    private MazeState finalState;
    private Maze maze;
    private boolean isSolved;

    public SearchableMaze(Maze maze) {
        this.startState = new MazeState(maze.getStartPosition(), null);
        this.finalState = new MazeState(maze.getGoalPosition(), null);
        this.maze = maze;
        this.isSolved = false;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public boolean isSolved() {
        return isSolved;
    }

    @Override
    public MazeState getStartState() {
        return this.startState;
    }

    @Override
    public MazeState getFinalState() {
        return this.finalState;
    }

    @Override
    public MazeState[] getAllSuccessors(AState currState) {
        MazeState[] successorsMazeState = new MazeState[8];

        return successorsMazeState;

    }
}
