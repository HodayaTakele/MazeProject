package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import  static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    private BestFirstSearch bestFirstSearch = new BestFirstSearch();


    @BeforeAll
    static void TestSetUp() {
        // Create emptyDiagStepSolFlip maze
        Maze maze = new Maze(5, 5, 0);
        maze.setStart(4, 0);
        maze.setGoal(0, 4);
        emptyDiagStepSolFlip = new SearchableMaze(maze);

        // Create emptyDiagStepSol maze
        maze = new Maze(maze);
        maze.setStart(0, 0);
        maze.setGoal(4, 4);
        emptyDiagStepSol = new SearchableMaze(maze);

        // Create diagStep maze
        maze = new Maze(maze);
        Position[] positions = {new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(1, 3), new Position(2, 1), new Position(2, 4), new Position(3, 1), new Position(3, 2)};
        for (Position pos : positions) {
            maze.buildWall(pos.getRowIndex(), pos.getColumnIndex());
        }
        diagStep = new SearchableMaze(maze);

        // Create diagStepFlip maze
        maze = new Maze(maze);
        maze.setStart(4, 4);
        maze.setGoal(0, 0);
        diagStepFlip = new SearchableMaze(maze);

        // Create backForDiagStep maze
        maze = new Maze(5, 5, 0);
        maze.setStart(0, 0);
        maze.setGoal(2, 0);
        positions = new Position[]{new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(1, 0), new Position(1, 3), new Position(2, 1), new Position(2, 3), new Position(3, 4), new Position(4, 0), new Position(4, 2)};
        for (Position pos : positions) {
            maze.buildWall(pos.getRowIndex(), pos.getColumnIndex());
        }
        backForDiagStep = new SearchableMaze(maze);

        // Create backForDiagStepFlip maze
        maze = new Maze(maze);
        maze.setStart(2, 0);
        maze.setGoal(0, 0);
        backForDiagStepFlip = new SearchableMaze(maze);

        // Create twoPossibleSol maze
        maze = new Maze(5, 3, 0);
        maze.setStart(0, 0);
        maze.setGoal(4, 1);
        positions = new Position[]{new Position(0, 2), new Position(1, 0), new Position(1, 2), new Position(3, 1)};
        for (Position pos : positions) {
            maze.buildWall(pos.getRowIndex(), pos.getColumnIndex());
        }
        twoPossibleSol = new SearchableMaze(maze);

        // twoPossibleSolFlip maze
        maze = new Maze(maze);
        maze.setStart(2, 0);
        maze.setGoal(0, 0);
        twoPossibleSolFlip = new SearchableMaze(maze);

        //emptyOneStepSolFlip
        maze = new Maze(2, 2, 0);
        maze.setStart(0, 0);
        maze.setGoal(1, 1);
        emptyOneStepSolFlip = new SearchableMaze(maze);

        //emptyOneStepSol
        maze = new Maze(2, 2, 0);
        maze.setStart(1, 1);
        maze.setGoal(0, 1);
        emptyOneStepSol = new SearchableMaze(maze);

        //oneStepSol
        maze = new Maze(maze);
        maze.buildWall(1, 0);
        oneStepSol = new SearchableMaze(maze);

        //oneStepSolFlip
        maze = new Maze(maze);
        maze.setStart(1, 1);
        maze.setGoal(0, 0);
        oneStepSolFlip = new SearchableMaze(maze);

        //verticalStep
        maze = new Maze(5, 2, 0);
        maze.setStart(0, 0);
        maze.setGoal(4, 0);
        verticalStep = new SearchableMaze(maze);

        //verticalStepFlip
        maze = new Maze(5, 2, 0);
        maze.setStart(4, 1);
        maze.setGoal(0, 1);
        verticalStepFlip = new SearchableMaze(maze);

        //horizontalStep
        maze = new Maze(2, 5, 0);
        maze.setStart(0, 0);
        maze.setGoal(0, 4);
        horizontalStep = new SearchableMaze(maze);

        //horizontalStepFlip
        maze = new Maze(2, 5, 0);
        maze.setStart(1, 4);
        maze.setGoal(1, 0);
        horizontalStepFlip = new SearchableMaze(maze);

        //noSol
        maze = new Maze(2, 2, 1);
        maze.breakWall(0, 0);
        maze.setStart(0, 0);
        maze.breakWall(1, 1);
        maze.setGoal(1, 1);
        noSol = new SearchableMaze(maze);
    }

    @Test
    void getNumberOfNodesEvaluated() {
    }

    @Test
    void getName() {
    }

    @Test
    void solve_diagStep() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(0, 0)), null);
        MazeState state1 = new MazeState((new Position(1, 1)), state0);
        MazeState state2 = new MazeState((new Position(2, 2)), state1);
        MazeState state3 = new MazeState((new Position(3, 3)), state2);
        MazeState state4 = new MazeState((new Position(4, 4)), state3);
        //checking diagStep
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);
        Solution sol1 = bestFirstSearch.solve(diagStep);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(60, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_diagStepFlip() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(4, 4)), null);
        MazeState state1 = new MazeState((new Position(3, 3)), state0);
        MazeState state2 = new MazeState((new Position(2, 2)), state1);
        MazeState state3 = new MazeState((new Position(1, 1)), state2);
        MazeState state4 = new MazeState((new Position(0, 0)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);

        Solution sol1 = bestFirstSearch.solve(diagStepFlip);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(60, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_emptyDiagStepSol() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(0, 0)), null);
        MazeState state1 = new MazeState((new Position(1, 1)), state0);
        MazeState state2 = new MazeState((new Position(2, 2)), state1);
        MazeState state3 = new MazeState((new Position(3, 3)), state2);
        MazeState state4 = new MazeState((new Position(4, 4)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);
        Solution sol1 = bestFirstSearch.solve(emptyDiagStepSol);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(60, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_emptyDiagStepSolFlip() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(4, 0)), null);
        MazeState state1 = new MazeState((new Position(3, 1)), state0);
        MazeState state2 = new MazeState((new Position(2, 2)), state1);
        MazeState state3 = new MazeState((new Position(1, 3)), state2);
        MazeState state4 = new MazeState((new Position(0, 4)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);

        Solution sol1 = bestFirstSearch.solve(emptyDiagStepSolFlip);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(60, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_backForDiagStep() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(0, 0)), null);
        MazeState state1 = new MazeState((new Position(1, 1)), state0);
        MazeState state2 = new MazeState((new Position(2, 2)), state1);
        MazeState state3 = new MazeState((new Position(3, 1)), state2);
        MazeState state4 = new MazeState((new Position(2, 0)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);
        Solution sol1 = bestFirstSearch.solve(backForDiagStep);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(60, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_backForDiagStepFlip() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(2, 0)), null);
        MazeState state1 = new MazeState((new Position(3, 1)), state0);
        MazeState state2 = new MazeState((new Position(2, 2)), state1);
        MazeState state3 = new MazeState((new Position(1, 1)), state2);
        MazeState state4 = new MazeState((new Position(0, 0)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);
        Solution sol1 = bestFirstSearch.solve(backForDiagStepFlip);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(60, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_horizontalStep() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(0, 0)), null);
        MazeState state1 = new MazeState((new Position(0, 1)), state0);
        MazeState state2 = new MazeState((new Position(0, 2)), state1);
        MazeState state3 = new MazeState((new Position(0, 3)), state2);
        MazeState state4 = new MazeState((new Position(0, 4)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);

        Solution sol1 = bestFirstSearch.solve(horizontalStep);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(40, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_horizontalStepFlip() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(1, 4)), null);
        MazeState state1 = new MazeState((new Position(1, 3)), state0);
        MazeState state2 = new MazeState((new Position(1, 2)), state1);
        MazeState state3 = new MazeState((new Position(1, 1)), state2);
        MazeState state4 = new MazeState((new Position(1, 0)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);

        Solution sol1 = bestFirstSearch.solve(horizontalStepFlip);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(40, (solutionPath1.get(4).cost));
    }

    @Test
    void solve_twoPossibleSol() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(0, 0)), null);
        MazeState state1 = new MazeState((new Position(1, 1)), state0);
        MazeState state2 = new MazeState((new Position(2, 0)), state1);
        MazeState state3 = new MazeState((new Position(3, 0)), state2);
        MazeState state4 = new MazeState((new Position(4, 1)), state3);
        MazeState state5 = new MazeState((new Position(2, 2)), state1);
        MazeState state6 = new MazeState((new Position(3, 2)), state2);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);
        targetSol.add(5, state5);
        targetSol.add(5, state6);

        Solution sol1 = bestFirstSearch.solve(twoPossibleSol);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        assertEquals(state0,sol1.getSolutionPath().get(0));
        assertEquals(state1,sol1.getSolutionPath().get(1));
        assertFalse((state2.equals(sol1.getSolutionPath().get(2)))||(state4.equals(sol1.getSolutionPath().get(2))));
        assertFalse((state3.equals(sol1.getSolutionPath().get(3)))||(state5.equals(sol1.getSolutionPath().get(3))));
        assertEquals(state4,sol1.getSolutionPath().get(4));
        assertEquals(55, (solutionPath1.get(4).cost));

    }

    @Test
    void solve_verticalStep() {
        ArrayList<AState> targetSol = new ArrayList<>();
        MazeState state0 = new MazeState((new Position(0, 0)), null);
        MazeState state1 = new MazeState((new Position(1, 0)), state0);
        MazeState state2 = new MazeState((new Position(2, 0)), state1);
        MazeState state3 = new MazeState((new Position(3, 0)), state2);
        MazeState state4 = new MazeState((new Position(4, 0)), state3);
        targetSol.add(0, state0);
        targetSol.add(1, state1);
        targetSol.add(2, state2);
        targetSol.add(3, state3);
        targetSol.add(4, state4);
        Solution sol1 = bestFirstSearch.solve(verticalStep);
        ArrayList<AState> solutionPath1 = sol1.getSolutionPath();
        for (int i = 0; i < solutionPath1.size(); i++) {
            assertEquals(targetSol.get(i), solutionPath1.get(i));
        }
        assertEquals(40, (solutionPath1.get(4).cost));
    }

}


