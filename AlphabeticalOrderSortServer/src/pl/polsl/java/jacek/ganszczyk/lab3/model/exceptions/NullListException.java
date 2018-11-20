package pl.polsl.java.jacek.ganszczyk.lab3.model.exceptions;

/**
 * My own exception, it is throwed when sorted list is empty
 *
 * @author Jacek Ganszczyk
 * @version 1.0
 */
public class NullListException extends Exception {

    /**
     * Default Constructor
     */
    public NullListException() {
    }


    /**
     * Parametrized Constructor
     *
     * @param message display when error occur
     */
    public NullListException(String message) {
        super(message);
    }
}
