package algorithms.search;

public interface ISearchingAlgorithm {
    int getNumberOfNodesEvaluated();
    String getName();
    Solution solve( ISearchable searchable);
}
