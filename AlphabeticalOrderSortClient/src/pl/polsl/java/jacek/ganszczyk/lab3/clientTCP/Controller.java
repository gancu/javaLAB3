package pl.polsl.java.jacek.ganszczyk.lab3.clientTCP;

import pl.polsl.java.jacek.ganszczyk.lab3.Props;
import pl.polsl.java.jacek.ganszczyk.lab3.view.View;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Main Controller of application.
 *
 * @author Jacek Ganszczyk
 * @version 2.0-online
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
    /**
     * Field binds client
     */
    private final TCPClient client;
    /**
     * Field for menu activation
     */
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
     * Main method of controller, you can choose if you want load data from file or from client
     *
     * @param args
     * @throws IOException
     */
    public void mainControllerMethod(String[] args) throws IOException {
        String inputLine;
        mainView.showMessage("Hello! in word's sorting aplication!");
        mainView.showMessage("These are available options: ");
        mainView.showMessage("Type:");
        mainMenuOptions();
        if (args.length == 0) {
            while (activeConnection) {
                inputLine = input.nextLine();
                switch (inputLine) {
                    case "1":
                        sortStringsFromConsole();
                        mainMenuOptions();
                        break;
                    case "2":
                        readSortedStringsFromFile();
                        mainMenuOptions();
                        break;
                    case "3":
                        saveSortedStringsToFile();
                        mainMenuOptions();
                        break;
                    case "4":
                        activeConnection = false;
                        client.sendCommand("-Q");
                        mainView.showMessage("Closing connection");
                        break;
                }
            }
        }
    }

    /**
     * Prints menu options to console
     */
    private void mainMenuOptions() {
        mainView.showMessage(" 1. For start typing words to be sorted! ");
        mainView.showMessage(" 2. For load before saved list of sorted words and see it ");
        mainView.showMessage(" 3. If you want to save on server your list of words ");
        mainView.showMessage(" 4. to exit");
    }

    /**
     * Method allows to list on server file and read exact one on console
     * @throws IOException
     */
    private void readSortedStringsFromFile() throws IOException {
        LinkedList<String> results;
        results = client.getDataFromServer("-LIST");
        mainView.printList(results);

        mainView.showMessage("Type name of file you want to load and present, and confirm by enter (ex. data.txt)");
        client.sendCommand("-READ");
        results = client.getDataFromServer(input.nextLine());
        mainView.printList(results);
    }

    /**
     * Method sortStringsFromConsole take data from client and sorts it
     *
     */
    private void sortStringsFromConsole() throws IOException {
        client.sendCommand("-SORT");
        mainView.showMessage("Type your words and submit each by pressing enter, if you ready type -S for start sorting");
        boolean loop = true;
        String inputLine;
        while (loop) {
            inputLine = input.nextLine();
            if (inputLine.compareToIgnoreCase("-S") == 0) {
                loop = false;
            } else if (inputLine.trim().length() > 0) {
                client.sendCommand(inputLine);
            }
        }
        LinkedList<String> results;
        results = client.getDataFromServer("-S");
        mainView.printList(results);
    }

    /**
     * Method allows to save sorted words on serverside
     */
    private void saveSortedStringsToFile() {
        mainView.showMessage("Type the name for saving file");
        String inputLine;
        inputLine = input.nextLine();
        client.sendCommand("-SAVE");
        client.sendCommand(inputLine);
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
