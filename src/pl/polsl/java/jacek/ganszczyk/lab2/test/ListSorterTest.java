package pl.polsl.java.jacek.ganszczyk.lab2.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.polsl.java.jacek.ganszczyk.lab2.model.exceptions.NullListException;
import pl.polsl.java.jacek.ganszczyk.lab2.model.ListSorter;

import java.util.LinkedList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * ListSorte class test
 *
 * @author Jacek Ganszczyk
 * @version 1.0
 */
public class ListSorterTest {
    /**
     * Instances of model objects
     */
    ListSorter testListSorter = new ListSorter();
    LinkedList<String> testList = new LinkedList<>();

    /**
     * Initializing test list
     */
    @BeforeEach
    public void setUp() {
        testList.add("Alice");
        testList.add("Zen");
        testList.add("Data");
        testList.add("Ekg");
    }

    /**
     * Test of correct string sort situation
     *
     * @throws NullListException
     */
    @Test
    public void alphabeticalSortTestCorrect() throws NullListException {
        testListSorter.sortList(testList);
        assertEquals(testList.get(0), "Alice");
        assertEquals(testList.get(1), "Data");
        assertEquals(testList.get(2), "Ekg");
        assertEquals(testList.get(3), "Zen");

    }

    /**
     * Test of incorrect string sort situation
     *
     * @throws NullListException
     */
    @Test
    public void alphabeticalSortTestUncorrect() throws NullListException {
        testListSorter.sortList(testList);
        assertNotEquals(testList.get(0), "Zen");
        assertNotEquals(testList.get(1), "Ekg");
        assertNotEquals(testList.get(2), "Data");
        assertNotEquals(testList.get(3), "Alice");

    }

    /**
     * Test when list is empty
     */
    @Test
    public void emptyListSortTest() {
        try {
            tearDown();
            testListSorter.sortList(testList);
            fail("An exception should be thrown when the list is empty");
        } catch (NullListException e) {
            assertEquals(e.getMessage(), "Empty list of strings");
        }
    }

    /***
     * Cleaning after tests
     */
    @AfterEach
    void tearDown() {
        testList.clear();
    }
}
