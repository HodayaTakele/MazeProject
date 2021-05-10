package test;

import Client.*;
import IO.*;
import Server.*;
import java.util.Scanner;

public class temp_Main {
    public static void main(String[] args) {
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        //Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //Server stringReverserServer = new Server(5402, 1000, new ServerStrategyStringReverser());
        //Starting servers
        /*solveSearchProblemServer.start();*/
        new Thread(()->{
            mazeGeneratingServer.start();
        }).start();

        String operation;
        Scanner scanner = new Scanner(System.in);
        do{
            operation = scanner.nextLine();
        } while (!operation.equalsIgnoreCase("exit"));
        mazeGeneratingServer.stop();
    }
}
