package Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private final Logger log = LogManager.getLogger();

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Maze mazeToSol = (Maze)fromClient.readObject();
            // create directories for solved mazes and Their solution
            createServerDir("Mazes");
            createServerDir("Solutions");
            File mazes = new File(System.getProperty("java.io.tmpdir")+"/Mazes");
            File solutions = new File(System.getProperty("java.io.tmpdir")+"/Solutions");

            // search the maze and if found get the solution name
            String solName = searchMaze(mazes, mazeToSol);
            Solution mazeSolution;

            if (solName != null) // get the solution
                mazeSolution = searchSolution(solutions, solName);
            else { // solve the maze and add it to the directories
                String mazeName = "maze" + mazes.listFiles().length;
                solName = "sol" + solutions.listFiles().length;
                SearchableMaze searchableMaze = new SearchableMaze(mazeToSol);
                BestFirstSearch Search = new BestFirstSearch();
                mazeSolution = Search.solve(searchableMaze);
                saveToDir("Mazes", mazeName, mazeToSol);
                saveToDir("Solutions", solName, mazeSolution);
            }
            // write the solution to the client and close the connection
            toClient.writeObject(mazeSolution);
            toClient.flush();
            toClient.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
    }

   private void createServerDir(String dirName) throws IOException{
        File parentDir = new File(System.getProperty("java.io.tmpdir"));
        File dir = new File(parentDir, dirName);
        if (!dir.exists() && !dir.mkdirs()){
            throw new IOException("Dir creation failed in the following path: " + dir.getAbsolutePath());
        }
   }

   private void saveToDir(String dirName, String objectName, Object object){
        if (object != null){
            try{
                File file = new File(System.getProperty("java.io.tmpdir") + dirName + "\\" + objectName + ".tmp");
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getPath()));
                out.writeObject(object);
                out.flush();
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
       }

   }

   private String searchMaze(File mazesDir, Maze mazeToSearch){
        String solName = null;
        File[] mazes = mazesDir.listFiles();
        //List<File> mazesList = new ArrayList<File>();
        List<File> mazesList = Arrays.asList(mazes.clone());
        for (File file: mazesList) {
           try{
               ObjectInputStream fileMaze = new ObjectInputStream(new FileInputStream(file.getPath()));
               Maze mazeToCompare = (Maze)fileMaze.readObject();
               if(mazeToCompare.equals(mazeToSearch)){
                   solName = "sol" + (file.getName()).substring(4);
                   break;
               }
               fileMaze.close();
           }catch (Exception e){
               e.printStackTrace();
           }
       }
        return solName;
   }

   private Solution searchSolution(File solDir, String solName){
        Solution solution = null;
        if (solDir != null && solName != null) {
            File[] solutions = solDir.listFiles();
            //List<File> solList = new ArrayList<>();
            List<File> solList = Arrays.asList(solutions);
            String fileSolName;
            for (File file : solList) {
                fileSolName = file.getName();
                if (fileSolName.equals(solName)) {
                    try {
                        ObjectInputStream fileSol = new ObjectInputStream(new FileInputStream(file.getPath()));
                        solution = (Solution) fileSol.readObject();
                        fileSol.close();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return solution;
   }

}

