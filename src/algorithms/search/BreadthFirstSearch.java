package algorithms.search;

import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    LinkedList<AState> closeList;

    public BreadthFirstSearch(){
        this.visitedNodesCount = 0;
        this.openList = new LinkedList<>();
        this.closeList = new LinkedList<>();
    }

    @Override
    public String getName(){
        return getClass().getSimpleName();
    }

    @Override
    public Solution solve(ISearchable searchable) {
        if(searchable == null){
            //throw exception
        }
        Solution sol = null;
        this.openList.add(searchable.getStartState());

        while(!(this.openList.isEmpty()) && sol == null) {
            AState currState = this.openList.poll();
            AState[] stateSuccessors = searchable.getAllSuccessors(currState);
            for (AState successor: stateSuccessors){
                if(successor != null && successor.equals(searchable.getFinalState())){
                    sol = new Solution(successor);
                    this.closeList.add(successor);
                    break;
                }
                else if( successor != null && !(this.openList.contains(successor)) && !(this.closeList.contains(successor)) ){
                    this.openList.add(successor);
                }
            }
            this.closeList.add(currState);
        }
        this.visitedNodesCount = this.closeList.size();
        return sol;
    }
}
