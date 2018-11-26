package pl.polsl.java.jacek.ganszczyk.lab3.test;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.polsl.java.jacek.ganszczyk.lab3.Props;
import pl.polsl.java.jacek.ganszczyk.lab3.model.FileActions;
import pl.polsl.java.jacek.ganszczyk.lab3.model.ListSorter;
import pl.polsl.java.jacek.ganszczyk.lab3.model.NullListException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * FileActions class test
 *
 * @author Jacek Ganszczyk
 * @version 1.0
 */
public class FileActionsTest {
    /**
     * Instances of model objects
     */
    final FileActions fileActions = new FileActions();
    final ListSorter testListSorter = new ListSorter();
    LinkedList<String> testList = new LinkedList<>();
    static String path;

    /**
     * Method used in tests to save multiple string to file
     *
     * @param file - where we saves data
     * @param args - strings to save in file
     * @throws IOException - if file is corrupted
     */
    public static void saveMultipleStringsToFile(BufferedWriter file, String... args) throws IOException {
        for (String stringToSave : args) {
            file.write(stringToSave);
            file.newLine();
        }
    }

    /**
     * Method used to clear files after test
     *
     * @param args - multiple fileNames to delete multiple files
     */
    public static void deleteFiles(String... args) {
        for (String fileToDelete : args) {
            File file = new File( path + "\\" + fileToDelete);
            file.delete();
        }
    }

    /**
     * Initialize for test files
     *
     * @throws IOException
     */
    @BeforeClass
    public static void makeTestFiles() throws IOException {
        Props.loadProperties();
        path = Props.getProps("FILES_DIR");
        BufferedWriter inputData = new BufferedWriter(new FileWriter(path + "\\" + "inputData.txt", true));
        saveMultipleStringsToFile(inputData, "Orange", "Water", "Chips");
        inputData.close();

        BufferedWriter emptyFile = new BufferedWriter(new FileWriter(path + "\\" + "emptyFile.txt", true));
        emptyFile.close();
    }

    /**
     * Deleting files used in tests
     */
    @AfterClass
    public static void cleanUp() {
        deleteFiles("inputData.txt", "sorted-inputData.txt", "emptyFile.txt");
    }

    /**
     * Tests if read from file work fine
     *
     * @throws IOException
     */
    @Test
    public void readStringsFromFileTest() throws IOException {
        fileActions.setFileName("inputData.txt");
        testList = fileActions.readStringsFromFile();
        assertEquals(3, testList.size());
    }

    /**
     * Tests if file is empty, it should not add any elements to list.
     *
     * @throws IOException
     */
    @Test
    public void readStringsFromEmptyFileTest() throws IOException {

        fileActions.setFileName("emptyFile.txt");
        testList = fileActions.readStringsFromFile();
        assertTrue(testList.isEmpty());
    }

    /**
     * Tests if exception will be catched if there is no file
     *
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void readStringsFromNonExistingFileTest() throws IOException {
        fileActions.setFileName("somerandomefile");
        testList = fileActions.readStringsFromFile();
    }

    /**
     * Test looking for exception when field with fileName is empty
     *
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void emptyFileNameField() throws IOException {
        fileActions.readStringsFromFile();
    }

    /**
     * Test try to load data from file, sort it and then save to another file in correct order
     *
     * @throws NullListException
     * @throws IOException
     */
    @Test
    public void saveStringsToFile() throws NullListException, IOException {
        fileActions.setFileName("inputData.txt");
        testList = fileActions.readStringsFromFile();

        testListSorter.sortList(testList);

        fileActions.setFileName("sorted-inputData.txt");
        fileActions.saveSortedListToFile(testList);

        testList = fileActions.readStringsFromFile();
        assertEquals(testList.get(0), "Chips");
        assertEquals(testList.get(1), "Orange");
        assertEquals(testList.get(2), "Water");
    }
}