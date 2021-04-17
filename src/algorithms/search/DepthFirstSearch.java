package algorithms.search;

import java.util.HashSet;
import java.util.Stack;

public class DepthFirstSearch implements ISearchingAlgorithm{
    protected int visitedNodesCount;
    protected Stack<AState> openList;
    protected HashSet<AState> closeHash; //visited

    public DepthFirstSearch(){
        this.visitedNodesCount = 0;
        openList = new Stack<>();
        closeHash = new HashSet<>();
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return this.visitedNodesCount;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Solution solve(ISearchable searchable) {
        Solution sol = null;
        AState StartState = searchable.getStartState();
        this.openList.push(StartState);
        while(!this.openList.empty() && (sol==null))
        {
            // Pop the cell from stack and add to close list
            AState currState = this.openList.pop();
            if (!(this.closeHash.contains(currState))) {
                this.closeHash.add(currState);
            }
            AState[] stateSuccessors = searchable.getAllSuccessors(currState);
            for (AState successor: stateSuccessors){
                if ( successor != null) {
                    if (successor.equals(searchable.getGoalState())) {
                        sol = new Solution(successor);
                        this.closeHash.add(successor);
                        this.visitedNodesCount = closeHash.size();
                        break;
                    } else if (!(this.closeHash.contains(successor))) {
                        this.openList.add(successor);
                    }
                }
            }
        }
        return sol;
    }

}
