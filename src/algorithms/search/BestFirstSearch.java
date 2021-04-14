package algorithms.search;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BestFirstSearch extends ASearchingAlgorithm{
    LinkedList<AState> closeList;

    public BestFirstSearch(){
        this.visitedNodesCount = 0;
        this.openList = new PriorityQueue<>(1, new StateCostComparator());
        this.closeList = new LinkedList<>();
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Solution solve(ISearchable searchable) {
        if(searchable == null){
            //throw exception
        }

        Solution sol = null;
        this.openList.add(searchable.getStartState());
        AState finalState = searchable.getFinalState();
        // Run until the opnList is Empty
        while (!(this.openList.isEmpty())){
            AState currState = this.openList.poll();
            AState[] stateSuccessors = searchable.getAllSuccessors(currState);
            // Run over currState successors and add them to openList while searching
            for (AState successor: stateSuccessors){
                if( (successor != null) && (successor.equals(finalState)) && (successor.getCost() < finalState.getCost() || finalState.getCost() == 0) ){
                    finalState = successor;
                }
                else if(successor != null && !(this.closeList.contains(currState))){
                    if(!this.openList.contains(successor)){
                        this.openList.add(successor);
                    }
                    else if(this.openList.contains(successor)){
                        //is there a easier way to find the successor without .stream().filter() which return a "set"
                        int olderCost = this.openList.stream().filter(o->o.equals(successor)).mapToInt(o->o.getCost()).sum();
                        if ( successor.getCost() <= olderCost ){
                            this.openList.remove(successor);
                            this.openList.add(successor);
                        }
                    }
                }
            }
            this.closeList.add(currState);
        }
        this.visitedNodesCount = this.closeList.size();
        return new Solution(finalState);
    }
}
