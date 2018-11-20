package pl.polsl.java.jacek.ganszczyk.lab3.networkController;

import pl.polsl.java.jacek.ganszczyk.lab3.Props;
import pl.polsl.java.jacek.ganszczyk.lab3.view.View;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Main Controller of application.
 *
 * @author Jacek Ganszczyk
 * @version 1.0
 */
public class Controller {
    /**
     * Console output.
     */
    private final View mainView;

    /**
     * Console input.
     */
    private final Scanner input;

    private final TCPClient client;

    private boolean activeConnection;

    /**
     * Constructor of Controller
     */
    public Controller() throws IOException {
        activeConnection = true;
        this.mainView = new View();
        this.input = new Scanner(System.in);
        client = new TCPClient(
                Props.getProps("IP_ADRESS"),
                Integer.parseInt(
                        Props.getProps("PORT")));
    }


    /**
     * Main method of controller, you can choose if you want load data from file or from console
     *
     * @param args
     * @throws IOException
     */
    public void mainControllerMethod(String[] args) throws IOException {
        String inputLine;
        if (args.length == 0) {
            mainView.showMessage("Hello! in word's sorting aplication!");
            mainView.showMessage("These are available options: ");
            mainView.showMessage("Type:");
            mainView.showMessage("1. For start typing words to be sorted! ");
            mainView.showMessage("2. For load before saved list of sorted words and see it ");
            mainView.showMessage("3. If you want to save on server your list of words ");

            while (activeConnection) {
                inputLine = input.nextLine();
                switch (inputLine) {
                    case "1":
                        client.executeCommand("-SORT");
                    case "":
                        sortStringsFromConsole();
                        break;
                    case "2":
                        readSortedStringsFromFile();
                        break;
                    case "3":
                        saveSortedStringsToFile();
                        break;
                    case "-Q":
                        activeConnection = false;
                        break;

                }
            }
        }
    }

    private void readSortedStringsFromFile() throws IOException {
        LinkedList<String> results;
        results = client.getDataFromServer("-LIST");
        mainView.printList(results);

        client.executeCommand("-READ");
        results = client.getDataFromServer(input.nextLine());
        mainView.printList(results);
    }

    /**
     * Method sortStringsFromConsole take data from user and sort it
     *-
     */
    public void sortStringsFromConsole() throws IOException {
        boolean loop = true;
        String inputLine = "";
        while (loop) {
            inputLine = input.nextLine();
            if (inputLine.compareToIgnoreCase("-S") == 0) {
                loop = false;
            } else if (inputLine.trim().length() > 0) {
                client.executeCommand(inputLine);
            }
        }
        LinkedList<String> results;
        results = client.getDataFromServer("-s");
        mainView.printList(results);
    }

    public void saveSortedStringsToFile() {
        mainView.showMessage("Type the name for saving file");
        String inputLine;
        inputLine = input.nextLine();
        client.executeCommand("-SAVE");
        client.executeCommand(inputLine);
    }

    /**
     * Sends do View sorted list of words
     *
     * @param sortedList
     */
    public void printSortedList(LinkedList sortedList) {
        for (int i = 0; i < sortedList.size(); i++) {
            mainView.showMessage(i + ": " + sortedList.get(i).toString());
        }
    }
}
