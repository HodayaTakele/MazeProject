package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class SearchableMaze implements ISearchable {
    private MazeState startState;
    private MazeState finalState;
    private Maze maze;
    private boolean isSolved;

    public SearchableMaze(Maze maze) {
        this.startState = new MazeState(maze.getStartPosition(), null);
        this.finalState = new MazeState(maze.getGoalPosition(), null);
        this.maze = maze;
        this.isSolved = false;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public boolean isSolved() {
        return isSolved;
    }

    @Override
    public MazeState getStartState() {
        return this.startState;
    }

    @Override
    public MazeState getFinalState() {
        return this.finalState;
    }


    /**return the optional moves that the State can move to.
     * @param currState the current AState
     * @return array within 8 MazeState (can be also null) that the currState can move to.
     * the optional state will be organize in the array according to this :
     * MazeState[0]<--Upper cell, MazeState[1]<--Upper right diagonal, MazeState[2]<--Right cell, MazeState[3]<--lower right diagonal cell, MazeState[4]<--Lower cell,
     * MazeState[5]<--Lower left diagonal cell, MazeState[6]<--Left cell, MazeState[7]<--Upper left diagonal cell,
     * adding to the suitable index in the array the MazeState if the currState can move to the cell (the cell exist and his value is 0), else keep it null
     */
    @Override
    public MazeState[] getAllSuccessors(AState currState) {
        MazeState mazeState = (MazeState) currState;
        boolean up = false;
        boolean right = false;
        boolean down = false;
        boolean left = false;
        //creating the MazeState array that will be return:
        MazeState[] successorsMazeState = new MazeState[8];
        //saving the current Position:
        Position currPosition = ((MazeState)currState).getState();
        //States:
        //the up cell :
        int UpperCellRow = (currPosition.getRowIndex() - 1);
        int UpperCellCol = currPosition.getColumnIndex();
        //the Upper right diagonal cell:
        int UpperRightDiagonalRow = (currPosition.getRowIndex()-1);
        int UpperRightDiagonalCol = (currPosition.getColumnIndex()+1);
        //the Right cell:
        int RightCellRow = currPosition.getRowIndex();
        int RightCellCol = (currPosition.getColumnIndex()+1);
        //the lower right diagonal cell:
        int LowerRightDiagonalRow = (currPosition.getRowIndex()+1);
        int LowerRightDiagonalCol = (currPosition.getColumnIndex()+1);
        //insert the Lower cell:
        int LowerCellRow = (currPosition.getRowIndex() + 1);
        int LowerCellCol = currPosition.getColumnIndex();
        //insert the Lower left diagonal cell:
        int LowerLeftDiagonalCellRow = (currPosition.getRowIndex() + 1);
        int LowerLeftDiagonalCellCol = (currPosition.getColumnIndex() - 1);
        //insert the Left cell:
        int LeftCellRow = currPosition.getRowIndex();
        int LeftCellCol = (currPosition.getColumnIndex() - 1);
        //insert the Upper left diagonal cell:
        int UpperLeftDiagonalCellRow = (currPosition.getRowIndex() - 1);
        int UpperLeftDiagonalCellCol = (currPosition.getColumnIndex() - 1);


        //update the booleans and update the array with the relevant MazeStates:
        //right:
        if(currPosition.getColumnIndex() < (this.maze.getColumns()-1)) {right = true;}
        //down:
        if(currPosition.getRowIndex() < (this.maze.getRows()-1)) {down = true;}
        //left:
        if(currPosition.getColumnIndex() > 0) {left = true;}
        //up:
        if(currPosition.getRowIndex()>0)
        {
            up = true;
            //insert the up cell to the array in index 0:
            if (this.maze.getCellValue(UpperCellRow,UpperCellCol) != 1)
            {
                successorsMazeState[0] = new MazeState(new Position(UpperCellRow, UpperCellCol), (MazeState) currState);
            }
            //insert the Upper right diagonal cell:
            if(right) {
                if ((this.maze.getCellValue(UpperRightDiagonalRow, UpperRightDiagonalCol) != 1) && ((this.maze.getCellValue(UpperCellRow, UpperCellCol) != 1) || (this.maze.getCellValue(RightCellRow, RightCellCol) != 1))) {
                    successorsMazeState[1] = new MazeState(new Position(UpperRightDiagonalRow, UpperRightDiagonalCol), (MazeState) currState);
                }
            }
        }
        //insert the Right cell:
        if(right)
        {
            if (this.maze.getCellValue(RightCellRow,RightCellCol) != 1)
            {
                successorsMazeState[2] = new MazeState(new Position(RightCellRow, RightCellCol), (MazeState) currState);
            }
            //insert the lower right diagonal cell:
            if(down)
            {
                if ((this.maze.getCellValue(LowerRightDiagonalRow,LowerRightDiagonalCol) != 1) && ((this.maze.getCellValue(RightCellRow,RightCellCol) != 1) || (this.maze.getCellValue(LowerCellRow,LowerCellCol) != 1)))
                {
                    successorsMazeState[3] = new MazeState(new Position(LowerRightDiagonalRow, LowerRightDiagonalCol), (MazeState) currState);
                }
            }
        }
        //insert the Lower cell:
        if(down) {
            if (this.maze.getCellValue(LowerCellRow, LowerCellCol) != 1)
            {
                successorsMazeState[4] = new MazeState(new Position(LowerCellRow, LowerCellCol), (MazeState) currState);
            }
            //insert the Lower left diagonal cell:
            if (left) {
                if ((this.maze.getCellValue(LowerLeftDiagonalCellRow, LowerLeftDiagonalCellCol) != 1) && ((this.maze.getCellValue(LeftCellRow, LeftCellCol) != 1) || (this.maze.getCellValue(LowerCellRow, LowerCellCol) != 1)))
                {
                    successorsMazeState[5] = new MazeState(new Position(LowerLeftDiagonalCellRow, LowerLeftDiagonalCellCol), (MazeState) currState);
                }
            }
        }
        //insert the Left cell:
        if(left)
        {
            if(this.maze.getCellValue(LeftCellRow,LeftCellCol) != 1)
            {
                successorsMazeState[6] = new MazeState(new Position(LeftCellRow, LeftCellCol), (MazeState) currState);
            }
            //insert the Upper left diagonal cell:
            if(up)
            {
                if ((this.maze.getCellValue(UpperLeftDiagonalCellRow,UpperLeftDiagonalCellCol) != 1) && ((this.maze.getCellValue(LeftCellRow,LeftCellCol) != 1) || (this.maze.getCellValue(UpperCellRow,UpperCellCol) != 1)))
                {
                    successorsMazeState[7] = new MazeState(new Position(UpperLeftDiagonalCellRow, UpperLeftDiagonalCellCol), (MazeState) currState);
                }
            }
        }

        return successorsMazeState;

    }
}
