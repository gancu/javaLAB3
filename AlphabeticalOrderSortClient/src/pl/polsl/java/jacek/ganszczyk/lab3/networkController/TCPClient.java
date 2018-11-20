package pl.polsl.java.jacek.ganszczyk.lab3.networkController;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Class with communication protocol for client
 *
 * @author Kamil Zietek
 * @version 4.0
 */
public class TCPClient {

    /**
     * Socket, that will be connected to server
     */
    private final Socket clientSocket;

    /**
     * buffered input character stream
     */
    private final BufferedReader readFromServer;
    /**
     * Formatted output character stream
     */
    private final PrintWriter sendToServer;

    /**
     * Creates the client socket
     *
     * @param address server address
     * @param port server port
     * @throws IOException when port is already bind
     */
    public TCPClient(String address, int port) throws IOException {
        clientSocket = new Socket(address, port);
        sendToServer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                clientSocket.getOutputStream())), true);
        readFromServer = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
    }

    /**
     * Sends command to server
     *
     * @param command command from user
     * @return true if success, false if something failed
     */
    public void executeCommand(String command) {
        sendToServer.println(command);
    }


    public LinkedList getDataFromServer(String str) throws IOException {
        String recived;
        LinkedList<String> results = new LinkedList<>();
        executeCommand(str);
        while (!(recived = readFromServer.readLine()).equals("SENDING_ENDED")) {
        results.add(recived);
        }
        return results;
    }


}
