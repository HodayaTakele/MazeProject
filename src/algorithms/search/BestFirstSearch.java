package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends ASearchingAlgorithm{

    public BestFirstSearch(){
        this.visitedNodesCount = 0;
        this.openList = new PriorityQueue<>(2, new StateCostComparator());
    }
    private AState search( AState currState){

        return null;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Solution solve(ISearchable searchable) {
        //AState startState = searchable.getStartState();
        AState[] stateSuccessors = searchable.getAllSuccessors(searchable.getStartState());


        return null;
    }
}
