package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.search.ISearchingAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configurations {
    private volatile static Configurations instance = null;
    private final Logger log = LogManager.getLogger();

    private Configurations() { }

    public static Configurations getInstance(){
        if (instance == null){
            instance = new Configurations();
        }
        return instance;
    }

    public synchronized void setThreadPoolSize(int num){
        if(num > 0) {
            try{
                String path = (String)System.getProperty("user.dir") + "\\resources\\config.properties";
                InputStream input = new FileInputStream(path);
                Properties prop = new Properties();

                if (input == null) {
                    log.info("Sorry, unable to find config.properties");
                    return;
                }
                //load a properties file from class path, inside static method
                prop.load(input);
                input.close();
                OutputStream output = new FileOutputStream(path);
                if (output == null) {
                    log.info("Sorry, unable to find config.properties");
                    return;
                }
                String threadNumber = Integer.toString(num);
                //set the property value
                prop.setProperty("threadPoolSize", threadNumber);
                prop.store(output, null);
                output.close();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public int getThreadPoolSize(){
        try {
            String path = (String)System.getProperty("user.dir") + "\\resources\\config.properties";
            InputStream input = new FileInputStream(path);
            Properties prop = new Properties();

            if (input == null) {
                log.info("Sorry, unable to find config.properties");
                return 0;
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            String val = prop.getProperty("threadPoolSize");
            int nThread = Integer.parseInt(val);
            input.close();
            return nThread;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public synchronized void setMazeGeneratingAlgorithm(String algorithm){
        if(algorithm != null ) {
            try {
                Class a = Class.forName("algorithms.mazeGenerators." + algorithm);
                java.lang.reflect.Constructor constructor = a.getConstructor();
                Object invoker = constructor.newInstance();
                if (invoker != null && (invoker instanceof IMazeGenerator)) {
                    String path = (String)System.getProperty("user.dir") + "\\resources\\config.properties";
                    InputStream input = new FileInputStream(path);
                    Properties prop = new Properties();
                    prop.load(input);
                    input.close();
                    OutputStream output = new FileOutputStream(path);
                    if (output == null) {
                        log.info("Sorry, unable to find config.properties");
                        return;
                    }
                    //set the property value
                    prop.setProperty("mazeGeneratingAlgorithm", algorithm);
                    prop.store(output, null);
                    output.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public IMazeGenerator getMazeGeneratingAlgorithm(){
        try {
            String path = (String)System.getProperty("user.dir") + "\\resources\\config.properties";
            InputStream input = new FileInputStream(path);
            Properties prop = new Properties();

            if (input == null) {
                log.info("Sorry, unable to find config.properties");
                return null;
            }
            //load a properties file from class path, inside static method
            prop.load(input);
            String algorithmName = prop.getProperty("mazeGeneratingAlgorithm");


            Class a = Class.forName("algorithms.mazeGenerators." +algorithmName);
            java.lang.reflect.Constructor constructor = a.getConstructor();
            Object invoker = constructor.newInstance();

            if (invoker != null && (invoker instanceof IMazeGenerator)) {
                return (IMazeGenerator)invoker;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public synchronized void setMazeSearchingAlgorithm(String algorithm){
        if(algorithm != null ) {
            try {
                Class a = Class.forName("algorithms.search." + algorithm);
                java.lang.reflect.Constructor constructor = a.getConstructor();
                Object invoker = constructor.newInstance();
                if (invoker != null && (invoker instanceof ISearchingAlgorithm)) {
                    String path = (String)System.getProperty("user.dir") + "\\resources\\config.properties";
                    InputStream input = new FileInputStream(path);
                    Properties prop = new Properties();
                    prop.load(input);
                    input.close();
                    OutputStream output = new FileOutputStream(path);
                    if (output == null) {
                        log.info("Sorry, unable to find config.properties");
                        return;
                    }
                    //set the property value
                    prop.setProperty("mazeSearchingAlgorithm", algorithm);
                    prop.store(output, null);
                    output.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public ISearchingAlgorithm getMazeSearchingAlgorithm(){
        try {
            String path = (String)System.getProperty("user.dir") + "\\resources\\config.properties";
            InputStream input = new FileInputStream(path);
            Properties prop = new Properties();

            if (input == null) {
                log.info("Sorry, unable to find config.properties");
                return null;
            }
            //load a properties file from class path, inside static method
            prop.load(input);
            String algorithmName = prop.getProperty("mazeSearchingAlgorithm");


            Class a = Class.forName("algorithms.search." +algorithmName);
            java.lang.reflect.Constructor constructor = a.getConstructor();
            Object invoker = constructor.newInstance();

            if (invoker != null && (invoker instanceof ISearchingAlgorithm)) {
                return (ISearchingAlgorithm) invoker;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
