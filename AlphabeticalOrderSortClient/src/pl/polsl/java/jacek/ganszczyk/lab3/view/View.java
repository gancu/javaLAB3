package pl.polsl.java.jacek.ganszczyk.lab3.view;

import java.util.LinkedList;

public class View {

    /**
     * Push one line of text to console
     *
     * @param message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void printList(LinkedList<String> list) {
        for (String message : list) {
            System.out.println(message);
        }
    }
}
