package pl.polsl.java.jacek.ganszczyk.lab3.clientTCP;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Class with communication protocol for client
 *
 * @author Jacek Ganszczyk
 * @version 2.0-online
 */
class TCPClient {
    /**
     * Buffered input character stream
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
     * @param port    server port
     * @throws IOException when port is already bind
     */
    public TCPClient(String address, int port) throws IOException {
        Socket clientSocket = new Socket(address, port);
        sendToServer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                clientSocket.getOutputStream())), true);
        readFromServer = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
    }

    /**
     * Sends command to server, without confirmation
     *
     * @param command command from user
     * @return true if success, false if something failed
     */
    public void sendCommand(String command) {
        sendToServer.println(command);
    }

    /**
     * Sends command to server, get data and operation confirmation
     *
     * @param command
     * @return
     * @throws IOException
     */
    public LinkedList getDataFromServer(String command) throws IOException {
        String status;
        String recived;
        LinkedList<String> results = new LinkedList<>();
        sendCommand(command);
        status = readFromServer.readLine();
        if (status.equalsIgnoreCase("202GOOD")) {
            while (!(recived = readFromServer.readLine()).equals("SENDING_ENDED")) {
                results.add(recived);
            }
        } else {
            results.add(status);
        }
        return results;
    }
}
