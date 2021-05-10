package algorithms.search;

import java.util.HashSet;
import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm{

    public BreadthFirstSearch(){
        this.visitedNodesCount = 0;
        this.openList = new LinkedList<>();
        this.openHash = new HashSet<>();
        this.closeHash = new HashSet<>();
    }

}
