package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Class representation of a library (a collection of library books).
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 1.0
 */
public class LibraryGeneric<Type> {

    // sole constructor of this LibraryGeneric<Type> class
    public LibraryGeneric() {
    }

    /**
     * Returns the holder of the library book with the specified ISBN.
     * <p/>
     * If no book with the specified ISBN is in the library, returns null.
     *
     * @param isbn --
     *             ISBN of the book to be looked up
     */
    public Object lookup(long isbn) {
        return new Object();
    }

    /**
     * Unsets the holder and due date of the library book.
     * <p/>
     * If no book with the specified ISBN is in the library, returns false.
     * <p/>
     * If the book with the specified ISBN is already checked in, returns false.
     * <p/>
     * Otherwise, returns true.
     *
     * @param isbn --
     *             ISBN of the library book to be checked in
     */
    public boolean checkin(long isbn) {
        // if the book wasn't found return false
        return false;
    }

    /**
     * Test method which returns a copy of the list of books
     */
    protected Object bookList() {
        return new Object();
    }

    /**
     * Returns the list of library books, sorted by ISBN (smallest ISBN first).
     */
    public Object getInventoryList() {
        return new Object();
    }

    /**
     * Returns the list of library books, sorted by author
     */
    public Object getOrderedByAuthor() {
        return new Object();
    }

    /**
     * Returns the list of library books with due dates older than the input
     * date. The list is sorted by date (oldest first).
     * <p/>
     * If no library books are overdue, returns an empty list.
     */
    public Object getOverdueList(int month, int day, int year) {
        // return books in overdueList after they are sorted.
        return new Object();
    }

    /**
     * Comparator that defines an ordering among library books using the ISBN.
     */
    protected class OrderByIsbn {
    }

    /**
     * Comparator that defines an ordering among library books using the author,  and book title as a tie-breaker.
     */
    protected class OrderByAuthor {
    }

    /**
     * Comparator that defines an ordering among library books using the due date.
     */
    protected class OrderByDueDate {
    }

}

