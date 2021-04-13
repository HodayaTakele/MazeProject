package algorithms.search;

import java.util.Stack;
import java.util.LinkedList;

public class DepthFirstSearch implements ISearchingAlgorithm{
    protected int visitedNodesCount;
    protected Stack<AState> openList;
    protected LinkedList<AState> closeList; //visited

    public DepthFirstSearch(){
        this.visitedNodesCount = 0;
        openList = new Stack<>();
        closeList = new LinkedList<>();
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
            if (!(this.closeList.contains(currState))) {
                this.closeList.add(currState);
            }
            AState[] stateSuccessors = searchable.getAllSuccessors(currState);
            for (AState successor: stateSuccessors){
                if ( successor != null && successor.equals(searchable.getFinalState())){
                    sol = new Solution(successor);
                    this.closeList.add(currState);
                    this.visitedNodesCount = closeList.size();
                    break;
                }
                else if(successor != null && !(this.closeList.contains(successor))){
                    this.openList.add(successor);
                }
            }
        }
        return sol;
    }

}
