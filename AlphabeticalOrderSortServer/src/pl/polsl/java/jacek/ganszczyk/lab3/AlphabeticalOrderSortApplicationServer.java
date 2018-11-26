package pl.polsl.java.jacek.ganszczyk.lab3;

import pl.polsl.java.jacek.ganszczyk.lab3.serverTCP.TCPServer;

import java.io.File;
import java.io.IOException;

/**
 * Entry Point of application.
 *
 * @author Jacek Ganszczyk
 * @version 2.0-online
 */
public class AlphabeticalOrderSortApplicationServer {

    /**
     * Main method, start server service
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!new File("config.properties").exists()) {
            Props.setProperties();
            System.out.println("Creating properties file, first server run");
        }
        Props.loadProperties();
        TCPServer server = null;
        try {
            server = new TCPServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            server.startListening();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

