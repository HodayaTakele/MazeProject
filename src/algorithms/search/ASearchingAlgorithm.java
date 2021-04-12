package algorithms.search;

import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected int visitedNodesCount;
    protected Queue<AState> openList;

    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodesCount;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public abstract Solution solve(ISearchable searchable);
}
