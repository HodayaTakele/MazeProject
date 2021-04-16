package algorithms.search;

import java.util.HashSet;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected int visitedNodesCount;
    protected Queue<AState> openList;
    protected HashSet<AState> closeHash;
    protected HashSet<AState> openHash;

    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodesCount;
    }

    @Override
    public  String getName(){
        return getClass().getSimpleName();
    }

    @Override
    public Solution solve(ISearchable searchable){
        if(searchable == null){
            //throw exception
        }

        Solution sol = null;
        this.openList.add(searchable.getStartState());
        this.openHash.add(searchable.getStartState());
        // Run until the opnList is Empty
        while (!(this.openList.isEmpty()) && sol == null){
            AState currState = this.openList.poll();

            AState[] stateSuccessors = searchable.getAllSuccessors(currState);
            // Run over currState successors and add them to openList while searching
            for (AState successor : stateSuccessors) {
                if (successor != null ) {
                    if (successor.equals(searchable.getGoalState())) {
                        sol = new Solution(successor);
                        this.closeHash.add(successor);
                        break;
                    }
                    else if (!(this.openHash.contains(successor)) && !(this.closeHash.contains(successor))){
                        this.openList.add(successor);
                        this.openHash.add(successor);
                    }
                }
            }
            this.closeHash.add(currState);
            this.openHash.remove(currState);
        }
        this.visitedNodesCount = this.closeHash.size();
        return sol;
    }
}
