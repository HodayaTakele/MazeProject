package algorithms.maze3D;
import algorithms.search.AState;
import algorithms.search.ISearchable;
public class SearchableMaze3D implements ISearchable {

    @Override
    public AState getStartState() {
        return null;
    }

    @Override
    public AState getGoalState() {
        return null;
    }

    @Override
    public AState[] getAllSuccessors(AState currState) {
        return new AState[0];
    }
}
