package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final Logger log = LogManager.getLogger();



    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        Configurations configurations = Configurations.getInstance();
        this.threadPool = Executors.newFixedThreadPool(configurations.getThreadPoolSize());

    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            log.info("Starting server at - Port = " + port);
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
                    log.info("Client accepted: " + clientSocket.toString());
                    // Insert the new task into the thread pool:
                    threadPool.submit(() -> handleClient(clientSocket) );
                } catch (SocketTimeoutException e) {
                    log.info("Socket Timeout");
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
            serverSocket.close();
            threadPool.shutdownNow();
        }catch (IOException e){
            e.printStackTrace();
        }
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
        log.info("Server shutting down");
        stop = true;
    }
}
