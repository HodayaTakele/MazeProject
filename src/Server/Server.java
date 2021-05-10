package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool; // Thread pool
    private int clientCount;


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = Executors.newCachedThreadPool();
        this.clientCount = 0;

    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            new Thread(()-> handleMultipleClients(serverSocket)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleMultipleClients(ServerSocket serverSocket){
        try {
            while (!stop) {
                try {
                    Socket clientSocket;
                    clientSocket = serverSocket.accept();
                    clientCount++;
                    // Insert the new task into the thread pool:
                    threadPool.submit(() -> handleClient(clientSocket) );

                } catch (SocketTimeoutException e) {
                    //System.out.println("SocketTimeoutException");
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //threadPool.shutdown(); // do not allow any new tasks into the thread pool (not doing anything to the current tasks and running threads)
        threadPool.shutdownNow(); // do not allow any new tasks into the thread pool, and also interrupts all running threads (do not terminate the threads, so if they do not handle interrupts properly, they could never stop...)
    }
    private void handleClient(Socket clientSocket) {
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }
}
