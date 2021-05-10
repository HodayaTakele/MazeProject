package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Solution implements Serializable {
    private AState finalState;
    private ArrayList<AState> solutionPath;

    public Solution(AState finalState){
        this.finalState = finalState;
        this.solutionPath = new ArrayList<>();
        createSolutionPath(finalState);
    }

    private void createSolutionPath(AState state)
    {
        Stack<AState> tempPath = new Stack<>();
        AState currState = state;
        while ( currState!= null ){
            tempPath.add(currState);
            currState = currState.getCameFrom();
        }
        while(!(tempPath.isEmpty())){ solutionPath.add(tempPath.pop()); }

    }

    public ArrayList<AState> getSolutionPath(){
        return solutionPath;
    }
}
