package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    private int visitedNodesCount;

    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodesCount;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Solution solve(ISearchable searchable) {
        return null;
    }
}
