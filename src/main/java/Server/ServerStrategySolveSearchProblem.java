package Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private final Logger log = LogManager.getLogger();
    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;
    private Configurations configurations = Configurations.getInstance();


    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            toClient = new ObjectOutputStream(outToClient);
            fromClient = new ObjectInputStream(inFromClient);

            Maze mazeToSol = getMazeFromClient();
            Solution mazeSolution= getSolFromPersistence(mazeToSol);
            if(mazeSolution==null)
            {
                SearchableMaze searchableMaze = new SearchableMaze(mazeToSol);
                ISearchingAlgorithm Search = configurations.getMazeSearchingAlgorithm();
                mazeSolution = Search.solve(searchableMaze);
                saveSolution(mazeSolution,mazeToSol);
            }
            sendToClient(mazeSolution);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveSolution(Solution mazeSolution,Maze mazeToSol) {
        String path=System.getProperty("java.io.tmpdir") + "/Solutions" + "\\"+mazeToSol.toString().hashCode()+".sol";
        File solutions = new File(path);

        try {
            if (!solutions.createNewFile()){
                return;
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(mazeSolution);
            objectOut.close();
        }
        catch ( Exception e){
            e.printStackTrace();
        }
    }

    private void sendToClient(Solution mazeSolution)throws  Exception {
        toClient.writeObject(mazeSolution);
        toClient.flush();
        toClient.close();
    }

    private Solution getSolFromPersistence(Maze mazeToSol) {

        String path=System.getProperty("java.io.tmpdir")+"/Solutions/"+mazeToSol.toString().hashCode()+".sol";
        File solutions = new File(path);
        try {
            if (!solutions.exists()){
                return null;
            }
            else
            {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Solution solution =(Solution) objectIn.readObject();
                objectIn.close();
                return solution;
            }
        }
        catch ( Exception e){
            return null;
        }
    }

    private Maze getMazeFromClient( ) throws  Exception{
        return (Maze)fromClient.readObject();
    }


}