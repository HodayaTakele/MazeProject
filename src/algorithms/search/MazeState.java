package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.util.Objects;

public class MazeState extends AState {
    private Position State;

    public MazeState(Position State, MazeState cameFrom) {
        super(cameFrom);
        this.State = State;
        //Determining the cost of the State:
        if(cameFrom == null){
            this.cost = 0;
        }//steps of :  left / right / down / up
        else if ((cameFrom.getState().getColumnIndex() == this.State.getColumnIndex()) || (cameFrom.getState().getRowIndex() == this.State.getRowIndex())) {
            this.cost = cameFrom.getCost() + 10;
        }//step : slant
        else {
            this.cost = cameFrom.getCost() + 15;
        }
    }
    public Position getState() {return this.State;}

    @Override
    public String toString(){ return this.State.toString();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MazeState)) return false;
        MazeState mazeState = (MazeState) o;
        return State.equals(mazeState.State);
    }

    @Override
    public int hashCode() {
        return Objects.hash(State);
    }
}
