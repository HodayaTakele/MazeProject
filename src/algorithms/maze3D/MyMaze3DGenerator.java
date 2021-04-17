package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

public class MyMaze3DGenerator extends AMaze3DGenerator{
    @Override
    public Maze3D generate(int depth, int row, int column) {
//        Maze maze = new Maze3D(depth, rows, columns, 1);
//        List<Position> wallList = new ArrayList<>();
///*        maze.breakWall(0, 0);
//        wallList.add(new Position(1, 0));
//        wallList.add(new Position(0, 1));*/
//        openCell(new Position(0,0), maze, wallList);
//        while (!wallList.isEmpty()) {
//            Position wall = getRandomWall(wallList);
//            List<Position> wallNeighbors = getWallNeighbors(wall, maze);
//            if (wallNeighbors.size() == 2){
//                Position Neighbor1 = wallNeighbors.get(0);
//                Position Neighbor2 = wallNeighbors.get(1);
//                int Neighbor1CellValue = maze.getCellValue(Neighbor1.getRowIndex(), Neighbor1.getColumnIndex());
//                int Neighbor2CellValue = maze.getCellValue(Neighbor2.getRowIndex(), Neighbor2.getColumnIndex());
//
//                //open the pass if exactly one cell is open
//                if (Neighbor1CellValue + Neighbor2CellValue == 1) {
//                    maze.breakWall(wall.getRowIndex(), wall.getColumnIndex());
//
//                    if (Neighbor1CellValue == 0 && Neighbor2CellValue == 1) {
//                        openCell(Neighbor2, maze, wallList);
//
//                    } else if (Neighbor1CellValue == 1 && Neighbor2CellValue == 0) {
//                        openCell(Neighbor1, maze, wallList);
//                    }
//                }
//            }
//            else if (wallNeighbors.size() == 1){
//                maze.breakWall(wall.getRowIndex(), wall.getColumnIndex());
//            }
//        }
//        chooseFinalPosition(maze);
//        return maze;
        return null;
    }


    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        return 0;
    }

    //generate
}
