package algorithms.search;

import java.util.ArrayList;

public class Solution {
    private ArrayList<AState> solutionPath;

    public Solution(AState firstState){
        this.addState(firstState);
    }

    public void addState(AState state){
        solutionPath.add(state);
    }

    public ArrayList<AState> getSolutionPath(){
        return solutionPath;
    }
}
