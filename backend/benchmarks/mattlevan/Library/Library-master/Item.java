/* Item.java
 *
 * Abstract superclass for all items available for rent
 * from the library (books, magazines, videos, music, etc.).
 *
 * @author Matthew Scott Levan
 * @version 03/20/2015
 * @semester Spring 2015
 */

import java.util.*;
import java.io.*;

public abstract class Item {
    // variables common to all subclasses of Item
    // (all other variables created in subclasses)
    protected String title; // title
    protected String dueDate; // due date
    protected String itemType; // type of item (book, magazine, video ,etc.)
    protected boolean availability; // true if available, false if not
    protected int pages; // number of pages
    protected String author;
    protected String ISBN;

    // abstract methods to be implemented in subclasses
    public abstract void register();

    // SETTERS
    // title setter
    public void setTitle(String title) {
        this.title = title;
    }

    // due date setter
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    // availability setter
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    // page number setter
    public void setPages(int pages) {
        this.pages = pages;
    }

    // item type setter
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    // GETTERS
    // title getter
    public String getTitle() {
        return title;
    }

    // dueDate getter
    public String getDueDate() {
        return dueDate;
    }

    // availability getter
    public boolean getAvailability() {
        return availability;
    }

    // page number getter
    public int getPages() {
        return pages;
    }

    // item type getter
    public String getItemType() {
        return itemType;
    }
}
