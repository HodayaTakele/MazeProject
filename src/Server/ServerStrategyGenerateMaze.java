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
            System.out.println("Server applied serverStrategy");
            int[] mazeSize = (int[])(fromClient.readObject());
            int rows = mazeSize[0];
            int columns = mazeSize[1];
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(rows, columns);
            System.out.println("Server generate Maze");
            byte[] mazeByte = maze.toByteArray();
            ObjectOutputStream toClient = new ObjectOutputStream(new MyCompressorOutputStream(new BufferedOutputStream(outToClient)));
            toClient.write(mazeByte);
            System.out.println("Server send compressed Maze");
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}