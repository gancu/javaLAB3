package pl.polsl.java.jacek.ganszczyk.lab3.model;

import com.sun.istack.internal.NotNull;
import pl.polsl.java.jacek.ganszczyk.lab3.Props;

import java.io.*;
import java.util.LinkedList;

/**
 * FileActions reads from file given by user
 *
 * @author Jacek Ganszczyk
 * @version 2.0
 */
public class FileActions {


    /**
     * Field keeps name of file
     */
    @NotNull
    private final String path;

    private String fileName;


    /**
     * Main class constructor
     *
     */
    public FileActions() {
        path = Props.getProps("FILES_DIR");
        fileName = " ";
    }

    public FileActions(String fileName) {
        path = Props.getProps("FILES_DIR");
        this.fileName = fileName;
    }

    /**
     * Constructor witch fileName parameter
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    /**
     * Read words line by line from given file
     *
     * @return List with strings
     * @throws java.io.IOException when there is a problem with file.
     */
    public LinkedList<String> readStringsFromFile() throws IOException {
        BufferedReader readBuffer;
        LinkedList<String> stringsFromFile = new LinkedList<>();
        String oneLine;
        readBuffer = new BufferedReader(new FileReader(path + "\\" + fileName));
        do {
            oneLine = readBuffer.readLine();
            if (null != oneLine && oneLine.trim().length() > 0)
                stringsFromFile.add(oneLine);
        }
        while (null != oneLine);
        readBuffer.close();
        return stringsFromFile;
    }

    /**
     * Method saves sorted list to new file with sorted- prefix
     *
     * @param sortedList sorted list to be save
     * @throws IOException throws when file is corrupted
     */
    public void saveSortedListToFile(LinkedList<String> sortedList) throws IOException {
        BufferedWriter writeBuffer;
        writeBuffer = new BufferedWriter(new FileWriter(path + "\\" + fileName, true));
        for (String item : sortedList) {
            writeBuffer.write(item);
            writeBuffer.newLine();
        }
        writeBuffer.close();

    }

}
