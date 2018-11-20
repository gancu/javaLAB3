package pl.polsl.java.jacek.ganszczyk.lab2.networkController;

import pl.polsl.java.jacek.ganszczyk.lab2.Props;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Closeable {

    /**
     * port number
     */
    final private int PORT = Integer.parseInt(Props.getProps("PORT"));

    /**
     * field represents the socket waiting for client connections
     */
    private ServerSocket serverSocket;

    private SingleService singleService;

    private Socket socket;
    /**
     * Creates the server socket
     *
     * @throws IOException when prot is already bind
     */
    public TCPServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }
    public void startListening() throws IOException {
        socket = serverSocket.accept();
        singleService = new SingleService(socket);
        singleService.run();
    }
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
