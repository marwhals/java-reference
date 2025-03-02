package JavaNetworkProgramming.MultiThreadedServer;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiThreadedServer {

    public static void main(String[] args) {
	    try(ServerSocket serverSocket = new ServerSocket(5000)) {
            while(true) {
                new Echoer(serverSocket.accept()).start();
//                Socket socket = serverSocket.accept(); <---- alternative way of writing this function
//                Echoer echoer = new Echoer(socket);
//                echoer.start();
            }


        } catch(IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
