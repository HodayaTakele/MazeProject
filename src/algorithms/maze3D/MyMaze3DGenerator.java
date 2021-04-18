package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator{

    @Override
    public Maze3D generate(int depth, int row, int column) {
        Maze3D maze = new Maze3D(depth, row, column, 1);
        List<Position3D> wallList = new ArrayList<>();
        maze.breakWall(0,0, 0);
        maze.setStart(0, 0,0);
        openCell(new Position3D(0, 0, 0), maze, wallList);
        while (!wallList.isEmpty()) {
            Position3D wall = getRandomWall(wallList);

            int choose = (int) Math.round(Math.random());

            if (choose == 1){
                chooseNeighbor(wall, maze, wallList);
            }
            else{
                List<Position3D> wallNeighbors = getWallNeighbors(wall, maze);
                Position3D Neighbor1 = wallNeighbors.get(0);
                int Neighbor1Value = maze.getCellValue(Neighbor1.getColumnIndex(),Neighbor1.getRowIndex(), Neighbor1.getColumnIndex());

                if (wallNeighbors.size() == 2) {
                    Position3D Neighbor2 = wallNeighbors.get(1);
                    int Neighbor2Value = maze.getCellValue(Neighbor2.getColumnIndex(), Neighbor2.getRowIndex(), Neighbor2.getColumnIndex());

                    if ((Neighbor1Value == 0 && Neighbor2Value == 1) || (Neighbor1Value == -1 && Neighbor2Value != 1)) {
                        changeDepth(wall, maze, wallList, Neighbor2);
                    }
                    else if ((Neighbor1Value == 1 && Neighbor2Value == 0) || (Neighbor1Value == 1 && Neighbor2Value == -1)) {
                        changeDepth(wall, maze, wallList, Neighbor1);
                    }
                    else if (Neighbor1Value == 1 && Neighbor2Value == 1) {
                        changeDepth(wall, maze, wallList, null);
                    }
                }
                else if (wallNeighbors.size() == 1 && Neighbor1Value == 1){
                    changeDepth(wall, maze, wallList, Neighbor1);
                }
                else if (wallNeighbors.size() == 1 && Neighbor1Value == 0){
                    changeDepth(wall, maze, wallList, null);
                }
            }
        }
        chooseFinalPosition(maze);
        return maze;
    }

    private Position3D getRandomWall(List<Position3D> wallList) {
        Random rand = new Random();
        return wallList.remove(rand.nextInt(wallList.size()));
    }

    private List<Position3D> getWallNeighbors(Position3D wall, Maze3D maze){
        List<Position3D> Neighbors = new ArrayList<>();
        if (wall.getRowIndex() % 2 == 0 && wall.getColumnIndex() % 2 != 0) {
            Neighbors.add( new Position3D(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex() - 1) );

            if(wall.getColumnIndex() < maze.getColumns() - 1) {
                Neighbors.add(new Position3D(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex() + 1));
            }
        }
        else if (wall.getColumnIndex() % 2 == 0 && wall.getRowIndex() % 2 != 0){
            Neighbors.add( new Position3D(wall.getDepthIndex(), wall.getRowIndex() - 1, wall.getColumnIndex()));

            if (wall.getRowIndex() < maze.getRows() - 1){
                Neighbors.add( new Position3D(wall.getDepthIndex(), wall.getRowIndex() + 1, wall.getColumnIndex()));
            }
        }
        return Neighbors;
    }

    private List<Position3D> getWallPasses(Position3D wall, Maze3D maze){
        List<Position3D> passes = new ArrayList<>();
        if (wall.getDepthIndex() < maze.getDepths() - 1 ){
            passes.add(new Position3D(wall.getDepthIndex() + 1, wall.getRowIndex(), wall.getColumnIndex() ));
        }
        if (wall.getDepthIndex() > 0 ){
            passes.add(new Position3D(wall.getDepthIndex() - 1, wall.getRowIndex(), wall.getColumnIndex() ));
        }
        return passes;
    }

    private void chooseNeighbor(Position3D wall, Maze3D maze,  List<Position3D> wallList){
        List<Position3D> wallNeighbors = getWallNeighbors(wall, maze);
        Position3D Neighbor1 = wallNeighbors.get(0);
        int Neighbor1Value = maze.getCellValue(Neighbor1.getColumnIndex(),Neighbor1.getRowIndex(), Neighbor1.getColumnIndex());
        if (wallNeighbors.size() == 2) {
            Position3D Neighbor2 = wallNeighbors.get(1);
            int Neighbor2Value = maze.getCellValue(Neighbor2.getColumnIndex(), Neighbor2.getRowIndex(), Neighbor2.getColumnIndex());

            if ((Neighbor1Value == 0 && Neighbor2Value == 1) || (Neighbor1Value == -1 && Neighbor2Value == 1)) {
                maze.breakWall(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex());
                openCell(Neighbor2, maze, wallList);
            } else if ((Neighbor1Value == 1 && Neighbor2Value == 0) || (Neighbor1Value == 1 && Neighbor2Value == -1)) {
                maze.breakWall(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex());
                openCell(Neighbor1, maze, wallList);
            } else if (Neighbor1Value == 1 && Neighbor2Value == 1) {
                changeDepth(wall, maze, wallList, null);
            }
        }
        else if (wallNeighbors.size() == 1 && Neighbor1Value == 1){
            maze.breakWall(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex());
            openCell(Neighbor1, maze, wallList);
        }
        else if (wallNeighbors.size() == 1 && Neighbor1Value == 0){
            maze.breakWall(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex());
        }
    }

    private void openCell(Position3D cell, Maze3D maze, List<Position3D> wallList){
        maze.breakWall(cell.getDepthIndex(), cell.getRowIndex(), cell.getColumnIndex());
        if (cell.getDepthIndex() < maze.getDepths() - 1 ) {
            maze.closeCell(cell.getDepthIndex() + 1, cell.getRowIndex(), cell.getColumnIndex());
        }
        if (cell.getDepthIndex() > 0 ) {
            maze.closeCell(cell.getDepthIndex() - 1, cell.getRowIndex(), cell.getColumnIndex());
        }
        if (cell.getColumnIndex() + 1 < maze.getColumns()){
            wallList.add(new Position3D(cell.getDepthIndex(), cell.getRowIndex(), cell.getColumnIndex() + 1));
        }
        if (cell.getColumnIndex() - 1 >= 0) {
            wallList.add(new Position3D(cell.getDepthIndex(), cell.getRowIndex(), cell.getColumnIndex() - 1));
        }
        if (cell.getRowIndex() + 1 < maze.getRows()) {
            wallList.add(new Position3D(cell.getDepthIndex(),cell.getRowIndex() + 1, cell.getColumnIndex()));
        }
        if (cell.getRowIndex() - 1 >= 0) {
            wallList.add(new Position3D(cell.getDepthIndex(),cell.getRowIndex() - 1, cell.getColumnIndex()));
        }
    }

    private void changeDepth(Position3D wall, Maze3D maze,  List<Position3D> wallList,Position3D Neighbor){
        if (Neighbor != null){
            maze.closeCell(Neighbor.getDepthIndex(), Neighbor.getRowIndex(), Neighbor.getColumnIndex());
        }
        maze.breakWall(wall.getDepthIndex(), wall.getRowIndex(), wall.getColumnIndex());
        List<Position3D> wallPasses = getWallPasses(wall, maze);
        Position3D Pass1 = wallPasses.get(0);
        int Pass1CellValue = maze.getCellValue(Pass1.getDepthIndex(), Pass1.getRowIndex(), Pass1.getColumnIndex());
        Position3D Pass2;
        if (wallPasses.size() == 2) {
            Pass2 = wallPasses.get(1);
            int Pass2CellValue = maze.getCellValue(Pass2.getDepthIndex(), Pass2.getRowIndex(), Pass2.getColumnIndex());
            if(Pass1CellValue == 1 && Pass2CellValue == 1) {
                int choose = (int) Math.round(Math.random());
                if (choose == 1) {
                    wallList.add(Pass1);
                    maze.closeCell(Pass2.getDepthIndex(), Pass2.getRowIndex(), Pass2.getColumnIndex());
                }
                else {
                    wallList.add(Pass2);
                    maze.closeCell(Pass1.getDepthIndex(), Pass1.getRowIndex(), Pass1.getColumnIndex());
                }
            }
            else if(Pass1CellValue == 0 && Pass2CellValue == 1){
                wallList.add(Pass1);
            }
            else if(Pass1CellValue == 1 && Pass2CellValue == 0){
                wallList.add(Pass2);
            }
        }
        else if (Pass1CellValue == 1){
            wallList.add(Pass1);
        }
    }

    private void chooseFinalPosition(Maze3D maze) {
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
                if (maze.getCellValue(maze.getDepths()-1, row, column) == 0) {
                    goalFound = true;
                    maze.setGoal(maze.getDepths()-1, row, column);
                }
            }
        }
    }

}
