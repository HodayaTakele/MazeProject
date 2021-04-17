package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {
    /*
    walls are represented with ood columns and rows
    cells are represented with evan columns and rows
     */
    @Override
    public Maze generate(int rows, int columns) {
        // Create maze filled with walls
        Maze maze = new Maze(rows, columns, 1);
        // List that contain all the passages that have not yet been explored
        List<Position> wallList = new ArrayList<>();
        // Open the start position
        openCell(maze.getStartPosition(), maze, wallList);
        // Move through and open pass and cell until all cells are covered and the wall list is empty
        while (!wallList.isEmpty()) {
            // Get a random wall from the available wall list
            Position wall = getRandomWall(wallList);
            // Get wall Neighbors(available cells)
            List<Position> wallNeighbors = getWallNeighbors(wall, maze);
            // Check if there is a cell have 2 Neighbors and try to open the close cell if it's possible
            if (wallNeighbors.size() == 2){
                Position Neighbor1 = wallNeighbors.get(0);
                Position Neighbor2 = wallNeighbors.get(1);
                // Open the pass if exactly one cell is open
                if (maze.getCellValue(Neighbor1.getRowIndex(), Neighbor1.getColumnIndex()) + maze.getCellValue(Neighbor2.getRowIndex(), Neighbor2.getColumnIndex()) == 1) {
                    // Open the pass
                    maze.breakWall(wall.getRowIndex(), wall.getColumnIndex());
                    // Open Neighbor2 cell
                    if (maze.getCellValue(Neighbor1.getRowIndex(), Neighbor1.getColumnIndex()) == 0 && maze.getCellValue(Neighbor2.getRowIndex(), Neighbor2.getColumnIndex()) == 1) { openCell(Neighbor2, maze, wallList); }
                    // Open Neighbor1 cell
                    else if (maze.getCellValue(Neighbor1.getRowIndex(), Neighbor1.getColumnIndex()) == 1 && maze.getCellValue(Neighbor2.getRowIndex(), Neighbor2.getColumnIndex()) == 0) { openCell(Neighbor1, maze, wallList); }
                }
            }
            // The pass is in the maze limits, no available cell to move in so we just break the wall.
            else if (wallNeighbors.size() == 1){ maze.breakWall(wall.getRowIndex(), wall.getColumnIndex()); }
        }
        // Set a random goal position in the limits of the maze.
        chooseFinalPosition(maze);
        return maze;
    }

    private Position getRandomWall(List<Position> wallList) {
        Random rand = new Random();
        return wallList.remove(rand.nextInt(wallList.size()));
    }

    /*check if it's a vertical pass or horizontal:

      vertical pass: (row,column) -> row = evan ; column = odd
      neighbor1 is the left side neighbor:
            always - > neighbor1 = ( row, column - 1 )
      neighbor2 is the right side neighbor:
            if column < maze.columns : neighbor2 = ( row, column + 1 ) else if column = maze.columns : neighbor2 = null

      horizontal pass: (row,column) -> row = odd ; column = evan
      neighbor1 is the up side neighbor:
            always - > neighbor1 = ( row - 1, column )
      neighbor2 is the down side neighbor:
            if row < maze.rows : neighbor2 = ( row + 1, column) else if row = maze.rows : neighbor2 = null
    */
    private List<Position> getWallNeighbors(Position wall, Maze maze){
        List<Position> Neighbors = new ArrayList<>();
        if (wall.getRowIndex() % 2 == 0 && wall.getColumnIndex() % 2 != 0) {
            Neighbors.add( new Position(wall.getRowIndex(), wall.getColumnIndex() - 1) );

            if(wall.getColumnIndex() < maze.getColumns() - 1) {
                Neighbors.add(new Position(wall.getRowIndex(), wall.getColumnIndex() + 1));
            }
        }
        else if (wall.getColumnIndex() % 2 == 0 && wall.getRowIndex() % 2 != 0){
            Neighbors.add( new Position(wall.getRowIndex() - 1, wall.getColumnIndex()));

            if (wall.getRowIndex() < maze.getRows() - 1){
                Neighbors.add( new Position(wall.getRowIndex() + 1, wall.getColumnIndex()));
            }
        }
        return Neighbors;
    }

    private void openCell(Position cell, Maze maze, List<Position> wallList){
        maze.breakWall(cell.getRowIndex(), cell.getColumnIndex());
        if (cell.getColumnIndex() + 1 < maze.getColumns()){
            wallList.add(new Position(cell.getRowIndex(), cell.getColumnIndex() + 1));
        }
        if (cell.getColumnIndex() - 1 >= 0) {
            wallList.add(new Position(cell.getRowIndex(), cell.getColumnIndex() - 1));
        }
        if (cell.getRowIndex() + 1 < maze.getRows()) {
            wallList.add(new Position(cell.getRowIndex() + 1, cell.getColumnIndex()));
        }
        if (cell.getRowIndex() - 1 >= 0) {
            wallList.add(new Position(cell.getRowIndex() - 1, cell.getColumnIndex()));
        }
    }

    private void chooseFinalPosition(Maze maze) {
        if (maze.getColumns() > 2 && maze.getRows() > 2) {
            int columnsRange = (maze.getColumns() - 1) / 2;
            int rowsRange = (maze.getRows() - 1) / 2;
            Random rand = new Random();
            int row, column;
            boolean goalFound = false;
            while (!goalFound) {
                if (maze.getColumns() < maze.getRows()) {
                    column = maze.getColumns() - 1 - rand.nextInt(columnsRange);
                    row = maze.getRows() - 1;
                } else {
                    row = maze.getRows() - 1 - rand.nextInt(rowsRange);
                    column = maze.getColumns() - 1;
                }
                if (maze.getCellValue(row, column) == 0) {
                    goalFound = true;
                    maze.setGoal(row, column);
                }
            }
        }
    }

}
