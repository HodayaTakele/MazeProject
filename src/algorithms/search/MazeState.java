package algorithms.search;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    private Position State;
    private MazeState cameFrom;
    private int cost;

    public MazeState(Position State, MazeState cameFrom) {
        this.State = State;
        this.cameFrom =  cameFrom;
        //Determining the cost of the State:
        if(cameFrom == null){
            this.cost = 0;
        }
        //steps of :  left / right / down / up
        else if ((this.cameFrom.getState().getColumnIndex() == this.State.getColumnIndex()) || (this.cameFrom.getState().getRowIndex() == this.State.getRowIndex())) {
            this.cost = 10;
        }
        //step : slant
        else {this.cost = 15;}
    }
    public Position getState() {return this.State;}
}
