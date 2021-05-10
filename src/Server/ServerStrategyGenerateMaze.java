package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            //to check
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            int[] mazeSize = (int[])(fromClient.readObject());
            int rows = mazeSize[0];
            int columns = mazeSize[1];
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}