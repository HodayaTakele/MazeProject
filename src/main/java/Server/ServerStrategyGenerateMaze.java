package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class ServerStrategyGenerateMaze implements IServerStrategy{
    private final Logger log = LogManager.getLogger();
    private Configurations configurations = Configurations.getInstance();

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            //to check
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            int[] mazeSize = (int[])(fromClient.readObject());
            int rows = mazeSize[0];
            int columns = mazeSize[1];
            IMazeGenerator mazeGenerator = configurations.getMazeGeneratingAlgorithm();
            Maze maze = mazeGenerator.generate(rows, columns);
            byte[] mazeByte = maze.toByteArray();
            // create a temporary byteArrayOutputStream that holds the bytes of the compressed maze
            ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
            MyCompressorOutputStream compressedOutput = new MyCompressorOutputStream(byteArrayOutput);
            compressedOutput.write(mazeByte);
            compressedOutput.flush();
            compressedOutput.close();
            // save the compressed maze and sent it to the client
            byte[] compressedMaze = byteArrayOutput.toByteArray();
            toClient.writeObject(compressedMaze);
            toClient.flush();
            byteArrayOutput.close();
            toClient.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }
}