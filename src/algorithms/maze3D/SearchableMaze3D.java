package algorithms.maze3D;
import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.HashMap;

public class SearchableMaze3D implements ISearchable {

    private Maze3DState startState;
    private Maze3DState goalState;
    private Maze3D maze;

    public SearchableMaze3D(Maze3D maze) throws IllegalArgumentException{
        if ( maze == null) throw new IllegalArgumentException( "Maze can't be null" );
        if ( maze.getStartPosition() == null || maze.getGoalPosition() == null ) throw new NullPointerException( "Maze must have Start and Goal positions" );
        if ( maze.getGoalPosition().equals(maze.getStartPosition())) throw new IllegalArgumentException( "Maze must have different Start and Goal positions" );
        this.startState = new Maze3DState(maze.getStartPosition(), null);
        this.goalState = new Maze3DState(maze.getGoalPosition(), null);
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        return this.startState;
    }

    @Override
    public AState getGoalState() {
        return this.goalState;
    }

    /**return the optional moves that the State can move to.
     * @param currState the current AState
     * @return array within 6 MazeState (can be also null) that the currState can move to.
     * the optional state will be organize in the array according to this :
     * MazeState[0]<--Internal cell, MazeState[1]<--Upper cell, MazeState[2]<--Right cell, MazeState[3]<--Lower cell, MazeState[4]<--Left cell, MazeState[5]<--External cell.
     * adding to the suitable index in the array the MazeState if the currState can move to the cell (the cell exist and his value is 0), else keep it null
     */
    @Override
    public AState[] getAllSuccessors(AState currState) {
        // Set mazeState by casting currState from AState to MazeState so we can access its position
        Maze3DState mazeState;
        if (currState instanceof Maze3DState) { mazeState = (Maze3DState) currState; }
        else{ throw new NullPointerException("SearchableMaze getAllSuccessors() must get a Maze3DState that hold Position"); }
        // Initialize the successors array (null values by default)
        Maze3DState[] successors = new Maze3DState[6];
        // Set mazeState Position
        Position3D currPosition = ((Maze3DState)currState).getState();
        // Get mazeState limits
        HashMap<String, Boolean> stateLimits = getStateLimits(currPosition);

        // Insert the internal cell to the array in index 0:
        setSuccessors( successors, currPosition.getDepthIndex()+1, currPosition.getRowIndex(), currPosition.getColumnIndex(), mazeState, stateLimits.get("internal"),0 );
        // Insert the up cell to the array in index 1:
        setSuccessors( successors, currPosition.getDepthIndex(), currPosition.getRowIndex()-1, currPosition.getColumnIndex(), mazeState, stateLimits.get("up"),1 );
        // Insert the right cell to the array in index 2:
        setSuccessors( successors, currPosition.getDepthIndex(), currPosition.getRowIndex(), currPosition.getColumnIndex()+1, mazeState, stateLimits.get("right"),2 );
        // Insert the lower cell to the array in index 3:
        setSuccessors( successors, currPosition.getDepthIndex(), currPosition.getRowIndex()+1, currPosition.getColumnIndex(), mazeState, stateLimits.get("down"),3 );
        // Insert the left cell to the array in index 4:
        setSuccessors( successors, currPosition.getDepthIndex(), currPosition.getRowIndex(), currPosition.getColumnIndex()-1, mazeState, stateLimits.get("left"),4 );
        // Insert the external cell to the array in index 5:
        setSuccessors( successors, currPosition.getDepthIndex()-1, currPosition.getRowIndex(), currPosition.getColumnIndex(), mazeState, stateLimits.get("external"),5 );
        // return successors array with the accessible MazeState successors
        return successors;
    }

    /**return the limits of the State.
     * @param position the current Maze3DState Position
     * @return HashMap <String, Boolean>:
     * Keys - 6 limits of the position: right, down, left, up, internal, external.
     * Value: true - can move in the direction, false - can't move to the direction
     */
    private HashMap<String, Boolean> getStateLimits(Position3D position){
        HashMap<String, Boolean> limits = new HashMap<>();
        // Check right bound
        if( position.getColumnIndex() < (this.maze.getColumns()-1) ) { limits.put("right", true); }
        else{ limits.put("right", false); }
        // Check down bound
        if ( position.getRowIndex() < (this.maze.getRows()-1) ){ limits.put("down", true); }
        else{ limits.put("down", false); }
        // Check left bound
        if ( position.getColumnIndex() > 0 ){ limits.put("left", true); }
        else { limits.put("left", false); }
        // Check up bound
        if ( position.getRowIndex() > 0 ){ limits.put("up", true); }
        else{ limits.put("up", false); }
        // Check internal bound
        if ( position.getDepthIndex() < (this.maze.getDepths()-1) ){ limits.put("internal", true); }
        else{ limits.put("internal", false); }
        // Check external bound
        if ( position.getDepthIndex() > 0 ){ limits.put("external", true); }
        else{ limits.put("external", false); }

        return limits;
    }
    /**void method that add successor i and to the successors Maze3DState Array if they’re accessible .
     * @param successors Maze3DState Array that will hold the current Maze3DState successors MazeStates array.
     * @param depth the current Maze3DState's depth;
     * @param row the current Maze3DState's row;
     * @param column the current Maze3DState's column;
     * @param state the current Maze3DState.
     * @param L1 boolean that says if we can or can't move in the horizontal\vertical\internal\external cell direction
     * @param i represent the the horizontal\vertical\internal\external cell index in the successors MazeState Array
     */
    private void setSuccessors(Maze3DState[] successors, int depth, int row, int column, Maze3DState state , boolean L1, int i ){
        if ( L1 ){
            // Insert the cell to the array if possible in index i:
            if( this.maze.getCellValue(depth,row,column) != 1 ) {
                successors[i] = new Maze3DState ( new Position3D(depth,row,column) , state );
            }
        }
    }
}
