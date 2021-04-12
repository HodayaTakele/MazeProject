package algorithms.search;

import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    LinkedList<AState> closeList;

    public BreadthFirstSearch(){
        this.visitedNodesCount = 0;
        this.openList = new LinkedList<AState>();
        this.closeList = new LinkedList<AState>();
    }


    @Override
    public Solution solve(ISearchable searchable) {
        this.openList.add(searchable.getStartState());

        while(!(this.openList.isEmpty())) {
            AState currState = this.openList.poll();
            this.visitedNodesCount++;
            AState[] stateSuccessors = searchable.getAllSuccessors(currState);
            for (AState successor: stateSuccessors){
                if(!(this.openList.contains(successor)) && !(this.closeList.contains(successor))){
                    this.openList.add(successor);
                    this.visitedNodesCount++;
                }
            }
            this.closeList.add(currState);
        }

        
        return null;
    }
}
