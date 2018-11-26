package pl.polsl.java.jacek.ganszczyk.lab3.view;

import java.util.LinkedList;
/**
 * Class with console view
 *
 * @author Jacek Ganszczyk
 * @version 2.0-online
 */
public class View {

    /**
     * Push one line of text to console
     *
     * @param message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Push whole liste to console
     * @param list
     */
    public void printList(LinkedList<String> list) {
        for (String message : list) {
            System.out.println(message);
        }
    }
}
