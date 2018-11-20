package pl.polsl.java.jacek.ganszczyk.lab2.networkController;

import pl.polsl.java.jacek.ganszczyk.lab2.Props;
import pl.polsl.java.jacek.ganszczyk.lab2.model.FileActions;
import pl.polsl.java.jacek.ganszczyk.lab2.model.ListSorter;
import pl.polsl.java.jacek.ganszczyk.lab2.model.exceptions.NullListException;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * The server class servicing a single connection
 */
class SingleService implements Closeable {

    private final FileActions fileActions;
    /**
     * ListSorter conenction.
     */
    private final ListSorter listSorter;
    /**
     * socket representing connection to the client
     */
    private Socket socket;
    /**
     * buffered readFromClient character stream
     */
    private BufferedReader readFromClient;
    /**
     * Formatted sendToClient character stream
     */
    private PrintWriter sendToClient;
    private boolean activeConnection;
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
        try {
            sendToClient.println("Welcome to Word's sort Java Aplication!");

            while (activeConnection) {
                String str = readFromClient.readLine();
                str = str.toUpperCase();

                switch (str) {
                    case "-SORT":
                        sortStringsFromClient();
                        break;
                    case "-READ":
                        readSortedStringsFromFile();
                        break;
                    case "-SAVE":
                        saveSortedStringsToFile();
                        break;
                    case "-LIST":
                        listSavedFiles();
                        break;
                    case "-Q":
                        activeConnection = false;
                        break;

                }
                System.out.println("Client sent: " + str);
            }
            System.out.println("closing...");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (NullListException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void listSavedFiles() {
        File[] files = new File(Props.getProps("FILES_DIR")).listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.
        for (File file : files) {
            if (file.isFile()) {
                sendToClient.println(file.getName());
            }
        }
    }

    private void readSortedStringsFromFile() throws IOException {
        String fileName;
        fileName = readFromClient.readLine();
        fileActions.setFileName(fileName);
        listFromClient = fileActions.readStringsFromFile();
        sentSortedList(listFromClient);
    }

    private void saveSortedStringsToFile() throws IOException {
        String fileName;
        fileName = readFromClient.readLine();
        fileActions.setFileName(fileName);
        fileActions.saveSortedListToFile(listFromClient);

    }

    private void sortStringsFromClient() throws NullListException, IOException {
        boolean loop = true;
        String inputLine;
        while (loop) {
            inputLine = readFromClient.readLine();
            if (inputLine.compareToIgnoreCase("-s") == 0) {
                loop = false;
            } else if (inputLine.trim().length() > 0) {
                listFromClient.add(inputLine);
            }
        }
        listSorter.sortList(listFromClient);
        sentSortedList(listFromClient);
    }

    /**
     * Sends do View sorted list of words
     *
     * @param sortedList list to be printed to console
     */
    private void sentSortedList(LinkedList sortedList) {
        for (int i = 0; i < sortedList.size(); i++) {
            sendToClient.println(i + ": " + sortedList.get(i).toString());
        }
    }

    @Override
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}