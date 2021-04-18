package algorithms.maze3D;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;

import java.util.Objects;

public class Maze3DState extends AState {
    private Position3D State;

    public Maze3DState(Position3D State, Maze3DState cameFrom) throws NullPointerException{
        super( cameFrom );
        if ( State == null ) throw new NullPointerException("Maze3DState must hold a position");

        this.State = State;
        //Determining the cost of the State:
        if( cameFrom == null ){ this.cost = 0; }
        //steps of :  left / right / down / up / depth
        else { this.cost = cameFrom.getCost() + 10; }
    }

    public Position3D getState() {return this.State;}

    @Override
    public String toString() {
        return this.State.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MazeState)) return false;
        Maze3DState Maze3DState = (Maze3DState) o;
        return State.equals(Maze3DState.State);
    }

    @Override
    public int hashCode() {
        return Objects.hash(State);
    }
}
