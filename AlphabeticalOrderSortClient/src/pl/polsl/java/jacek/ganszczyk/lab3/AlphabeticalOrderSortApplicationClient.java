package pl.polsl.java.jacek.ganszczyk.lab3;

import pl.polsl.java.jacek.ganszczyk.lab3.clientTCP.Controller;

import java.io.File;
import java.io.IOException;

/**
 * Entry point
 *
 * @author Jacek Ganszczyk
 * @version 2.0-online
 */
public class AlphabeticalOrderSortApplicationClient {
    /**
     * Main method, read file path from program parameters
     *
     * @param args the command line arguments
     */

    public static void main(String[] args)  {
        if (!new File("config.properties").exists()) {
            Props.setProperties();
            System.out.println("Creating properties file, first server run");
        }
        Props.loadProperties();
        Controller controller = null;
        try {
            controller = new Controller();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Server is not available ");
        }
        try {
            controller.mainControllerMethod(args);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Server is not available ");
        }
    }


}

