package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.maze3D.SearchableMaze3D;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            //to check
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            Maze mazeToSol = (Maze)fromClient.readObject();
            SearchableMaze searchableMaze = new SearchableMaze(mazeToSol);
            BestFirstSearch Search = new BestFirstSearch();
            Search.solve(searchableMaze);
            Solution mazeSolution = Search.solve(searchableMaze);
            toClient.writeObject(mazeSolution);
            toClient.flush();
            toClient.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

