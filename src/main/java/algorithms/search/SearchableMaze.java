package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchableMaze implements ISearchable {
    private MazeState startState;
    private MazeState goalState;
    public Maze maze;

    public SearchableMaze(Maze maze) throws IllegalArgumentException{
        if ( maze == null ) throw new IllegalArgumentException( "Maze can't be null" );
        if ( maze.getStartPosition() == null ||  maze.getGoalPosition() == null ) throw new IllegalArgumentException( "Maze must have Start and Goal positions" );
        if ( maze.getGoalPosition().equals(maze.getStartPosition()) ) throw new IllegalArgumentException( "Maze must have different Start and Goal positions");
        this.startState = new MazeState(maze.getStartPosition(), null);
        this.goalState = new MazeState(maze.getGoalPosition(), null);
        this.maze = maze;
    }

    @Override
    public MazeState getStartState() { return this.startState; }

    @Override
    public MazeState getGoalState() { return this.goalState; }

    /**return the optional moves that the State can move to.
     * @param currState the current AState
     * @return array within 8 MazeState (can be also null) that the currState can move to.
     * the optional state will be organize in the array according to this :
     * MazeState[0]<--Upper cell, MazeState[1]<--Upper right diagonal, MazeState[2]<--Right cell, MazeState[3]<--lower right diagonal cell, MazeState[4]<--Lower cell,
     * MazeState[5]<--Lower left diagonal cell, MazeState[6]<--Left cell, MazeState[7]<--Upper left diagonal cell,
     * adding to the suitable index in the array the MazeState if the currState can move to the cell (the cell exist and his value is 0), else keep it null
     */
    @Override
    public MazeState[] getAllSuccessors(AState currState) throws NullPointerException {
        if (currState == null) throw new NullPointerException("currState can't be null");

        // Set mazeState by casting currState from AState to MazeState so we can access its position
        MazeState mazeState;
        if (currState instanceof MazeState) { mazeState = (MazeState) currState; }
        else{ throw new NullPointerException("SearchableMaze getAllSuccessors() must get a MazeState that hold Position"); }

        // Initialize the successors array (null values by default)
        MazeState[] successors = new MazeState[8];
        // Set mazeState Position
        Position currPosition = ((MazeState)currState).getState();
        // Get mazeState limits
        HashMap<String, Boolean> stateLimits = getStateLimits(currPosition);
        // Get mazeState successors Positions
        ArrayList<Position> cellP = getSuccessorsPositions(currPosition, stateLimits);
        // Insert the up cell to the array in index 0 and the upper right diagonal cell to the array in index 1:
        setSuccessors( successors, cellP, mazeState , stateLimits.get("up"), stateLimits.get("right"), 0 );
        // Insert the right cell to the array in index 2 and the lower right diagonal cell to the array in index 3:
        setSuccessors( successors, cellP, mazeState , stateLimits.get("right"), stateLimits.get("down"), 2 );
        // Insert the lower cell to the array in index 4 and the lower left diagonal cell to the array in index 5:
        setSuccessors( successors, cellP, mazeState , stateLimits.get("down"), stateLimits.get("left"), 4 );
        // Insert the left cell to the array in index 6 and the upper left diagonal cell to the array in index 7:
        setSuccessors( successors, cellP, mazeState , stateLimits.get("left"), stateLimits.get("up"), 6 );
        // return successors array with the accessible MazeState successors
        return successors;
    }

    /**return the limits of the State.
     * @param position the current MazeState Position
     * @return HashMap <String, Boolean>:
     * Keys - 4 limits of the position: right, down, left, up.
     * Value: true - can move in the direction, false - can't move to the direction
     */
    private HashMap<String, Boolean> getStateLimits(Position position){
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
        if ( position.getRowIndex()>0 ){ limits.put("up", true); }
        else{ limits.put("up", false); }

        return limits;
    }

    /**return ArrayList with the successors positions in the maze if the position is inaccessible the value is null .
     * @param position the current MazeState Position
     * @return ArrayList <Position> - the mapping of the successors:
     * cellP[0]<--Upper cell, cellP[1]<--Upper right diagonal, cellP[2]<--Right cell, cellP[3]<--lower right diagonal cell,
     * cellP[4]<--Lower cell, cellP[5]<--Lower left diagonal cell, cellP[6]<--Left cell, cellP[7]<--Upper left diagonal cell, cellP[8]<--Upper cell
     */
    private ArrayList<Position> getSuccessorsPositions(Position position, HashMap<String, Boolean> limits) {
        ArrayList<Position> cellP = new ArrayList<>();
        // Initialize cellP with 9 null values for all the positions.
        for (int i = 0; i < 9; i++) { cellP.add(i, null); }
        // Set the up cell position as value in index 0 and 8 and the upper right diagonal cell position in index 1:
        if (limits.get("up")) {
            cellP.set(0, new Position(position.getRowIndex() - 1, position.getColumnIndex()));
            cellP.set(8, new Position(position.getRowIndex() - 1, position.getColumnIndex()));
            if (limits.get("right")) { cellP.set(1, new Position(position.getRowIndex() - 1, position.getColumnIndex() + 1)); }
        }
        // Set the right cell position as value in index 2 and the lower right diagonal cell position in index 3:
        if (limits.get("right")) {
            cellP.set(2, new Position(position.getRowIndex(), position.getColumnIndex() + 1));
            if (limits.get("down")) { cellP.set(3, new Position(position.getRowIndex() + 1, position.getColumnIndex() + 1)); }
        }
        // Set the lower cell position as value in index 4 and the lower left diagonal cell position in index 5:
        if (limits.get("down")) {
            cellP.set(4, new Position(position.getRowIndex() + 1, position.getColumnIndex()));
            if (limits.get("left")) { cellP.set(5, new Position(position.getRowIndex() + 1, position.getColumnIndex() - 1)); }
        }
        // Set the left cell position as value in index 6 and the upper left diagonal cell position in index 7:
        if (limits.get("left")) {
            cellP.set(6, new Position(position.getRowIndex(), position.getColumnIndex() - 1));
            if (limits.get("up")) { cellP.set(7, new Position(position.getRowIndex() - 1, position.getColumnIndex() - 1)); }
        }
        return cellP;
    }

    /**void method that add successor i and i+1 to the successors MazeState Array if they’re accessible .
     * @param successors MazeState Array that will hold the current MazeState successors MazeStates array.
     * @param cellP ArrayList that holds the current MazeState successors positions.
     * @param state the current MazeState.
     * @param L1 boolean that says if we can or can't move in the horizontal\vertical cell direction
     * @param L2 boolean that says if we can or can't move in the diagonal cell direction
     * @param i represent the the horizontal\vertical cell index in the successors MazeState Array
     *        i+1 represent the the next diagonal cell index in the successors MazeState Array
     */
    private void setSuccessors( MazeState[] successors, ArrayList<Position> cellP, MazeState state ,boolean L1, boolean L2, int i ){
        if ( L1 ){
            // Insert the cell to the array if possible in index i:
            if( this.maze.getCellValue(cellP.get(i).getRowIndex(), cellP.get(i).getColumnIndex()) != 1 ) {
                successors[i] = new MazeState( cellP.get(i) , state );
            }
            // Insert the diagonal cell to the array if possible in index i+1:
            if ( L2 && this.maze.getCellValue(cellP.get(i+1).getRowIndex(), cellP.get(i+1).getColumnIndex()) != 1 && (this.maze.getCellValue(cellP.get(i).getRowIndex(), cellP.get(i).getColumnIndex()) + this.maze.getCellValue(cellP.get(i+2).getRowIndex(), cellP.get(i+2).getColumnIndex()) <= 1) ){
                successors[i+1] = new MazeState( cellP.get(i+1), state );
            }
        }
    }

}
