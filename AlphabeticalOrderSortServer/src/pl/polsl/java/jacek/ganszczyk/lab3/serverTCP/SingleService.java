package pl.polsl.java.jacek.ganszczyk.lab3.serverTCP;

import pl.polsl.java.jacek.ganszczyk.lab3.Props;
import pl.polsl.java.jacek.ganszczyk.lab3.model.FileActions;
import pl.polsl.java.jacek.ganszczyk.lab3.model.ListSorter;
import pl.polsl.java.jacek.ganszczyk.lab3.model.NullListException;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Objects;

/**
 * The server class servicing a single connection
 */
class SingleService implements Closeable {

    /**
     * FileActions filed
     */
    private final FileActions fileActions;
    /**
     * ListSorter filed.
     */
    private final ListSorter listSorter;
    /**
     * socket representing connection to the client
     */
    private final Socket socket;
    /**
     * buffered readFromClient character stream
     */
    private BufferedReader readFromClient;
    /**
     * Formatted sendToClient character stream
     */
    private PrintWriter sendToClient;
    /**
     * Used for activation mainMenu
     */
    private boolean activeConnection;
    /**
     * List received from client
     */
    private LinkedList<String> listFromClient;

    /**
     * The constructor of instance of the SingleService class. Use the socket as
     * a parameter.
     *
     * @param socket socket representing connection to the client
     */
    public SingleService(Socket socket) throws IOException {
        this.socket = socket;
        sendToClient = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
        readFromClient = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        activeConnection = true;
        listSorter = new ListSorter();
        fileActions = new FileActions();
        listFromClient = new LinkedList<String>();
    }

    /**
     * Realizes the service
     */
    public void run() {
        while (activeConnection) {
            String str = null;
            try {
                str = readFromClient.readLine();
            } catch (IOException e) {
                sendConfirmation(2);
            }
            str = Objects.requireNonNull(str).toUpperCase();
            System.out.println("Client sent: " + str);
            switch (str) {
                case "-SORT":
                    try {
                        sortStringsFromClient();
                    } catch (NullListException e) {
                        sendConfirmation(2);
                    } catch (IOException e) {
                        sendConfirmation(2);
                    }
                    break;
                case "-READ":
                    try {
                        readSortedStringsFromFile();
                    } catch (IOException e) {
                        sendConfirmation(2);
                    }
                    break;
                case "-SAVE":
                    try {
                        saveSortedStringsToFile();
                    } catch (IOException e) {
                        sendConfirmation(2);
                    }
                    break;
                case "-LIST":
                    listSavedFiles();
                    break;
                case "-HELP":
                    help();
                    break;
                case "-Q":
                    activeConnection = false;
                    break;
                default:
                    System.out.println("Cannot recognize typed command: " + str);
                    sendToClient.println("UNKNOWN_COMMAND");
                    break;
            }
        }
        System.out.println("closing...");
    }

    /**
     * Help method, for other than mine client (ex. putty)
     */
    private void help() {
        sendConfirmation(1);
        sendToClient.println("List of available commands: ");
        sendToClient.println("-SORT sorting typed string");
        sendToClient.println("-READ reading typed text file");
        sendToClient.println("-LIST listing saved files");
        sendToClient.println("-Q (for quit)");
        sendToClient.println("SENDING_ENDED");
    }

    /**
     * Method returns to client list of saved files, from directory
     */
    private void listSavedFiles() {
        File[] files = new File(Props.getProps("FILES_DIR")).listFiles();
        sendConfirmation(1);
        for (File file : files) {
            if (file.isFile()) {
                sendToClient.println(file.getName());
            }
        }
        sendToClient.println("SENDING_ENDED");
    }

    /**
     * Method sends to client words from exact given file
     *
     * @throws IOException
     */
    private void readSortedStringsFromFile() throws IOException {
        String fileName;
        fileName = readFromClient.readLine();
        fileActions.setFileName(fileName);
        listFromClient = fileActions.readStringsFromFile();
        if (!listFromClient.isEmpty())
            sendConfirmation(1);
        sentSortedList(listFromClient);
        listFromClient.clear();
        sendToClient.println("SENDING_ENDED");
    }

    /**
     * Method saves to directory file with hand typed list of sorted files (from menu option 1)
     *
     * @throws IOException
     */
    private void saveSortedStringsToFile() throws IOException {
        String fileName;
        fileName = readFromClient.readLine();
        fileActions.setFileName(fileName);
        fileActions.saveSortedListToFile(listFromClient);
    }

    /**
     * First menu option creates list of given by client words and then sorting and returning to the client
     *
     * @throws NullListException
     * @throws IOException
     */
    private void sortStringsFromClient() throws NullListException, IOException {
        boolean loop = true;
        String inputLine;
        while (loop) {
            inputLine = readFromClient.readLine();
            System.out.println(inputLine);
            if (inputLine.toUpperCase().compareTo("-S") == 0) {
                loop = false;
            } else if (inputLine.trim().length() > 0) {
                listFromClient.add(inputLine);
            }
        }
        listSorter.sortList(listFromClient);
        if (!listFromClient.isEmpty())
            sendConfirmation(1);
        sentSortedList(listFromClient);
        sendToClient.println("SENDING_ENDED");
    }

    /**
     * Method used to send confirmation to mine client about actions on serverside
     *
     * @param type
     */
    private void sendConfirmation(int type) {
        if (type == 1) {
            sendToClient.println("202GOOD");
            System.out.println("202GOOD");
        } else {
            sendToClient.println("500WRONG");
            System.out.println("500WRONG");
        }
    }

    /**
     * Sends do client sorted list of words
     *
     * @param sortedList list to be printed to console
     */
    private void sentSortedList(LinkedList sortedList) {
        for (int i = 0; i < sortedList.size(); i++) {
            sendToClient.println(i + ": " + sortedList.get(i).toString());
        }
    }

    /**
     * Closing connection
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}