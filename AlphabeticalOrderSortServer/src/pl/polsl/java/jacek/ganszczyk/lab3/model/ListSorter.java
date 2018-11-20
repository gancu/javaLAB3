package pl.polsl.java.jacek.ganszczyk.lab3.model;

import pl.polsl.java.jacek.ganszczyk.lab3.model.exceptions.NullListException;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Class with method to sort strings
 *
 * @author Jacek Ganszczyk
 * @version 1.0
 */
public class ListSorter {

    /**
     * Method sort list of strings
     *
     * @param lList list of strings
     * @throws NullListException when list of strings is empty
     */
    public void sortList(LinkedList<String> lList) throws NullListException {
        if (lList.size() == 0)
            throw new NullListException("Empty list of strings");
        for (int i = 0; i < lList.size(); i++) {
            for (int j = 0; j < (lList.size() - 1); j++) {
                if (lList.get(j).compareToIgnoreCase(lList.get(j + 1)) > 0) {
                    Collections.swap(lList, j, j + 1);
                }
            }
        }
    }
}
