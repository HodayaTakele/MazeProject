package algorithms.search;

public class DepthFirstSearch extends ASearchingAlgorithm{
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
