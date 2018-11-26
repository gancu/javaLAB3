package pl.polsl.java.jacek.ganszczyk.lab3.serverTCP;

import pl.polsl.java.jacek.ganszczyk.lab3.Props;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Closeable {

    /**
     * field represents the socket waiting for client connections
     */
    private ServerSocket serverSocket;

    /**
     * Creates the server socket
     *
     * @throws IOException when port is already bind
     */
    public TCPServer() throws IOException {
        /*
      port number from properties
     */ /**
         * port number from properties
         */int PORT = Integer.parseInt(Props.getProps("PORT"));
        serverSocket = new ServerSocket(PORT);
    }

    /**
     * Method starts connection
     *
     * @throws IOException
     */
    public void startListening() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            try (SingleService singleService = new SingleService(socket)) {
                singleService.run();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Closing connection
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
