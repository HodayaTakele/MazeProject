package algorithms.search;

import java.util.HashSet;
import java.util.PriorityQueue;

public class BestFirstSearch extends ASearchingAlgorithm{

    public BestFirstSearch(){
        this.visitedNodesCount = 0;
        this.openList = new PriorityQueue<>(1, new StateCostComparator());
        this.openHash = new HashSet<>();
        this.closeHash = new HashSet<>();
    }

}
