import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Qnex
 * Date: 01.10.13
 * Time: 12:51
 * To change this template use File | Settings | File Templates.
 */
public class Server {
    private static Server instance;
    private boolean isRunning;
    private int port;
    private ServerSocket serverSocket;
    private Thread threadListener;

    private Server(int port) {
        this.port = port;
    }

    public static Server create(int port) {
        if (instance == null){
            instance = new Server(port);
        }
        return instance;
    }

    public void run() throws Exception {
        try{
            if (!isRunning){
                serverSocket = new ServerSocket(port);
                threadListener = new Thread(new Runnable() {
                    public void run() {
                        try {
                            keepListening();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadListener.run();
                isRunning = true;
            } else {
                throw new Exception("Server is already running");
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public boolean isRunning(){
        return isRunning;
    }

    public boolean isConnectedClient(){
        return (serverSocket == null) ? false : serverSocket.isBound();
    }

    public boolean isCreated(){
        return (instance == null ) ? false : true;
    }

    private void keepListening() throws IOException {
        while (true){
            serverSocket.accept();
        }
    }
}
