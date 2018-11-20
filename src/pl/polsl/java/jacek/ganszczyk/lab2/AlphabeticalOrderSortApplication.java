package pl.polsl.java.jacek.ganszczyk.lab2;

import pl.polsl.java.jacek.ganszczyk.lab2.model.exceptions.NullListException;
import pl.polsl.java.jacek.ganszczyk.lab2.networkController.TCPServer;

import java.io.IOException;

/**
 * Entry Point of application.
 *
 * @author Jacek Ganszczyk
 * @version 1.0
 */
public class AlphabeticalOrderSortApplication {
    /*
      Main method, read file path from program parameters

     */

    /**
     * Main method, read file path from program parameters
     *
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException, NullListException {
        Props.loadProperties();
        TCPServer server = new TCPServer();
        server.startListening();

    }


}

