package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BestFirstSearchTest {
    private static SearchableMaze diagStep;
    private static SearchableMaze diagStepFlip;
    private static SearchableMaze emptyDiagStepSol;
    private static SearchableMaze backForDiagStep;
    private static SearchableMaze backForDiagStepFlip;
    private static SearchableMaze twoPossibleSol;
    private static SearchableMaze twoPossibleSolFlip;
    private static SearchableMaze oneStepSol;
    private static SearchableMaze oneStepSolFlip;
    private static SearchableMaze emptyOneStepSol;
    private static SearchableMaze verticalStep;
    private static SearchableMaze horizontalStep;
    private static SearchableMaze emptyDiagStepSolFlip;
    private static SearchableMaze horizontalStepFlip;
    private static SearchableMaze verticalStepFlip;
    private static SearchableMaze emptyOneStepSolFlip;
    private static SearchableMaze noSol;


    @BeforeAll
    static void TestSetUp(){
        // Create emptyDiagStepSolFlip maze
        Maze maze = new Maze(5,5, 0);
        maze.setStart(4,0);
        maze.setGoal(0,4);
        emptyDiagStepSolFlip = new SearchableMaze(maze);

        // Create emptyDiagStepSol maze
        maze = new Maze(maze);
        maze.setStart(0,0);
        maze.setGoal(4,4);
        emptyDiagStepSol = new SearchableMaze(maze);

        // Create diagStep maze
        maze = new Maze(maze);
        Position[] positions = { new Position(0,2), new Position(0,3), new Position(0,4), new Position(1,3),new Position(2,1), new Position(2,4),new Position(3,1), new Position(3,2)};
        for (Position pos: positions) {
            maze.buildWall(pos.getRowIndex(),pos.getColumnIndex());
        }
        diagStep = new SearchableMaze(maze);

        // Create diagStepFlip maze
        maze = new Maze(maze);
        maze.setStart(4,4);
        maze.setGoal(0,0);
        diagStepFlip = new SearchableMaze(maze);

        // Create backForDiagStep maze
        maze = new Maze(5,5, 0);
        maze.setStart(0,0);
        maze.setGoal(2,0);
        positions = new Position[]{new Position(0,2), new Position(0,3), new Position(0,4), new Position(1,0), new Position(1,3), new Position(2,1), new Position(2,3), new Position(3,4), new Position(4,0), new Position(4,2)};
        for (Position pos: positions) {
            maze.buildWall(pos.getRowIndex(),pos.getColumnIndex());
        }
        backForDiagStep = new SearchableMaze(maze);

        // Create backForDiagStepFlip maze
        maze = new Maze(maze);
        maze.setStart(2,0);
        maze.setGoal(0,0);
        backForDiagStepFlip = new SearchableMaze(maze);

        // Create twoPossibleSol maze
        maze = new Maze(5, 3, 0);
        maze.setStart(0,0);
        maze.setGoal(4,1);
        positions = new Position[]{new Position(0,2), new Position(1,0), new Position(1,2), new Position(3,1)};
        for (Position pos: positions) {
            maze.buildWall(pos.getRowIndex(),pos.getColumnIndex());
        }
        twoPossibleSol = new SearchableMaze(maze);

        // twoPossibleSolFlip maze
        maze = new Maze(maze);
        maze.setStart(2,0);
        maze.setGoal(0,0);
        twoPossibleSolFlip = new SearchableMaze(maze);

        //emptyOneStepSolFlip
        maze = new Maze(2,2,0);
        maze.setStart(0,0);
        maze.setGoal(1,1);
        emptyOneStepSolFlip = new SearchableMaze(maze);

        //emptyOneStepSol
        maze = new Maze(2,2,0);
        maze.setStart(1,1);
        maze.setGoal(0,1);
        emptyOneStepSol = new SearchableMaze(maze);

        //oneStepSol
        maze = new Maze(maze);
        maze.buildWall(1,0);
        oneStepSol = new SearchableMaze(maze);

        //oneStepSolFlip
        maze = new Maze(maze);
        maze.setStart(1,1);
        maze.setGoal(0,0);
        oneStepSolFlip = new SearchableMaze(maze);

        //verticalStep
        maze = new Maze(5,2,0);
        maze.setStart(0,0);
        maze.setGoal(4,0);
        verticalStep = new SearchableMaze(maze);

        //verticalStepFlip
        maze = new Maze(5,2,0);
        maze.setStart(4,1);
        maze.setGoal(0,1);
        verticalStepFlip = new SearchableMaze(maze);

        //horizontalStep
        maze = new Maze(2,5,0);
        maze.setStart(0,0);
        maze.setGoal(0,4);
        horizontalStep = new SearchableMaze(maze);

        //horizontalStepFlip
        maze = new Maze(2,5,0);
        maze.setStart(1,4);
        maze.setGoal(1,0);
        horizontalStepFlip = new SearchableMaze(maze);

        //noSol
        maze = new Maze(2,2,1);
        maze.breakWall(0,0);
        maze.setStart(0,0);
        maze.breakWall(1,1);
        maze.setGoal(1,1);
        noSol = new SearchableMaze(maze);
    }

    @Test
    void getNumberOfNodesEvaluated() {
    }

    @Test
    void getName() {
    }

    @Test
    void solve() {
    }
}