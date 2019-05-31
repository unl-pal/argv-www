package assignment3;

import java.util.*;

/**
 *  This is a generic class that implements the Collection interface and uses an array as the
 *  base storage for items. This collection does not allow any duplicates in the array.
 *
 * @param <E> - generic type placeholder
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 6/5/2014
 *
 */
public class ArrayBasedCollection<E> implements Collection<E> {
    E data[]; // Storage for the items in the collection
    int size; // Keep track of how many items we hold

    /**
     * Initialize an ArrayBasedCollection<E> Object with a default capacity of 10.
     */
    @SuppressWarnings("unchecked")
    public ArrayBasedCollection() {
        size = 0;
        // There is no clean way to convert between E and Object, the text book discusses this.
        // We can't instantiate an array of unknown type E, so we must create an Object array, and type-cast
        data = (E[]) new Object[10]; // Start with an initial capacity of 10
    }

    /**
     * Initialize an ArrayBasedCollection<E> Object with a specified capacity.
     * @param capacity Initial capacity or length of the array to initialize.
     */
    @SuppressWarnings("unchecked")
    public ArrayBasedCollection(int capacity) {
        size = 0;
        // There is no clean way to convert between E and Object, the text book discusses this.
        // We can't instantiate an array of unknown type E, so we must create an Object array, and type-cast
        data = (E[]) new Object[capacity]; // Start with an initial capacity specified by the parameter
    }

    /**
     * Double the capacity of the current array (maintains the elements and their ordering)
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        // This is a helper function specific to ArrayCollection
        // Doubles the size of the data storage array, retaining its current contents.
        // You will need to use something similar to the code in the constructor above to create a new array.

        // if the size is zero increase the size to 1
        if (data.length == 0) {
            E newData[] = (E[]) new Object[1];
            data = newData;
            return;
        }

        // for normal growth create a new array of twice the capacity
        E newData[] = (E[]) new Object[this.data.length * 2];

        // copy all elements to the new, twice as large array
        for (int i = 0; i < size; i++)
            newData[i] = data[i];

        // swap the smaller array with the substantiated bigger one
        this.data = newData;
    }

    /**
     * Adds the parameter to the next available index in the array.
     *
     * @param arg0 the element to be added
     * @return true iff the ArrayCollection changed after method execution
     */
    public boolean add(E arg0) {
        // check null case
        if (arg0 == null)
            return false;

        // check for a duplicate, return false if item is already in the array.
        if (this.contains(arg0))
            return false;

        // grow if the array is full
        if (size == data.length)
            grow();

        // if the element isn't in the list add it to the end and increment the size
        data[size++] = arg0;
        return true;
    }

    /**
     * Adds all of the elements in the specified collection passed to this collection (optional
     * operation).
     *
     * @param arg0 a Collection of elements to be added
     * @return true iff any elements have been added
     */
    public boolean addAll(Collection<? extends E> arg0) {
        int newSize = this.size; // save the initial size of this collection
        for (E element : arg0) // add each element in the passed Collection
            if (!this.contains(element)) // check if this array already contains the item
                this.add(element);       // add the item if no duplicates

        // if the size changed then this ArrayBasedCollection changed - return as appropriate
        return (newSize != this.size);
    }

    /**
     * Removes all of the elements from this collection (optional operation).
     */
    public void clear() {
        data = (E[]) new Object[10]; // Start with an initial capacity of 10
        size = 0;
    }

    /**
     * Returns true if this collection contains the passed Object parameter.
     *
     * @param arg0 the Object to be found
     * @return true iff arg0 is found in this collection.
     */
    public boolean contains(Object arg0) {
        // handle null case
        if (arg0 == null)
            return false;

        // loop through this.data and check each position against the passed Object
        for (int i = 0; i < size; i++)
            if (data[i].equals(arg0))
                return true; // if they match return true
        return false; // if no matches were found return false
    }

    /**
     * Checks if all the items in the passed Collection parameter are also in this collection.
     *
     * @param arg0 Items to look for in this collection.
     * @return true if every item in arg0 are found in this collection. Otherwise false.
     */
    public boolean containsAll(Collection<?> arg0) {
        // if there are no items to compare return false
        if (arg0.size() == 0)
            return false;

        // enhanced for loop returns false if any item is not found in this collection
        for (Object element : arg0)
            if (!this.contains(element))
                return false;

        // iff every element was found return true
        return true;
    }

    /**
     * Checks if this ArrayBasedCollection is empty.
     *
     * @return true if this collection does not contain any elements, otherwise returns false.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Creates and returns a new ArrayBasedIterator object.
     *
     * @return new ArrayBasedIterator.
     */
    public Iterator<E> iterator() {
        return new ArrayBasedIterator();
    }

    /**
     * Removes from this collection a single element that is equal to the passed item parameter.
     * @param arg0 Item to be found and removed from this collection.
     * @return true if the item is found and removed from this collection,
     *      false if the item is not found in this collection.
     */
    public boolean remove(Object arg0) {
        // set an index for the found object (default to -1 in order to check if it's found) and loop through objects in data
        int objIndex = -1;
        // traditional for loop used to find index of item to be removed.
        for (int i = 0; i < size; i++)
            if (data[i].equals(arg0)) {
                objIndex = i; // if item is found store index, and break from the loop
                break; // only one index needed because duplicates are not allowed
            }

        // if the object wasn't found return false
        if (objIndex == -1)
            return false;

        // cascade items backward to effectively remove the item
        for (int i = objIndex; i < size - 1; i++)
            data[i] = data[i + 1];

        // the last item, which is now a duplicate, is set to null as size decrements.
        data[--size] = null;
        return true;
    }

    /**
     * Removes all of this collection's elements that are also contained in the passed collection
     * parameter.
     *
     * @param arg0 Items to find and remove from this collection.
     * @return true if any items were removed from this collection, otherwise returns false.
     */
    public boolean removeAll(Collection<?> arg0) {
        int initialSize = size; // store the initial size (to check once the method completes if items were removed)
        for (Object element : arg0) // remove each item in the passed collection from 'this' object
            this.remove(element);   // can use remove() for all elements because only returns
        // false if the element is not present, which is ignored.
        // if things were removed the size changed, return a boolean appropriately
        return (size != initialSize);
    }

    /**
     * Reduces this collection to only contain the items that both this collection and the passed
     * collection parameter share together.
     *
     * @param arg0 Items to be compared with this collection.
     * @return Returns true if any items were removed, false otherwise.
     */
    public boolean retainAll(Collection<?> arg0) {
        //check for null Collection
        if (arg0.size() == 0)
            return false;
        int initialSize = this.size;    // store current size to check for changes at the end
        for (E element : data)      // loop through this collections items and compare to arg0
            if (!arg0.contains(element)) //if element is not shared by both collections then
                this.remove(element);   //remove element from this collection.
        return (this.size != initialSize); // return a boolean based on the size changing (if elements were removed)
    }

    /**
     * Provides the current amount of items in this collection as an int value.
     *
     * @return Size of this collection array.
     */
    public int size() {
        return size;
    }

    /**
     * Provides the current length or capacity of this collection array as an int value.
     *
     * @return Length or capacity of this array.
     */
    public int length() {
        return data.length;
    }

    /**
     * Copies all the items of this collection and returns them in the same order in a new Object
     * array with the exact length as the number of items copied.
     *
     * @return new Object array with a copy of all the items in this collection. The returned
     *         Object array has the exact length to hold the copied items from this collection.
     */
    public Object[] toArray() {
        // create new array and copy the elements from this.data into the array
        Object[] returnArray = new Object[size];
        for (int i = 0; i < size; i++)
            returnArray[i] = data[i];
        //return the new Object array with the copied items.
        return returnArray;
    }

    /**
     * Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
     */
    public <T> T[] toArray(T[] arg0) {
        /*this method is not implemented. However, its presence is necessary to complete the
        Collection interface*/
        return null;
    }

    /**
     * Creates and returns and ArrayList of this collections elements that are sorted in a manner
     * determined by the comparator object that is passed in as a parameter.
     *
     * @param cmp Comparator object used to sort the elements of this collection.
     * @return sorted ArrayList of this collections elements.
     */
    public ArrayList<E> toSortedList(Comparator<? super E> cmp) {
        // Sorting method specific to ArrayCollection - not part of the Collection interface
        // implements an insertion sort to sort the returned ArrayList

        // copy the elements of this collection to a new ArrayList to be sorted.
        E[] sortedArray = (E[]) this.toArray();

        // implement insertion sort on sortedArray before its returned
        for (int i = 1; i < sortedArray.length; i++) {
            E val = sortedArray[i];
            int j;
            for (j = i - 1; j >= 0 && (cmp.compare(sortedArray[j], val) > 0); j--)
                sortedArray[j + 1] = sortedArray[j];
            sortedArray[j + 1] = val;
        }

        // change the sorted array to a sorted ArrayList and return it
        ArrayList<E> sortedList = new ArrayList<E>(sortedArray.length);
        for (E element : sortedArray)
            sortedList.add(element);
        return sortedList;
    }

    /**
     * Class that defines the iterator used for the ArrayBasedCollection class
     */
    private class ArrayBasedIterator implements Iterator<E> {
        int index;
        boolean canRemove;
        E toRemove;

        /**
         * Sole constructor for the ArrayBasedIterator, initializes index to 0,
         * and canRemove to false which is used to determine whether next() has been called can
         * remove() is then available to use.
         */
        public ArrayBasedIterator() {
            index = 0;
            canRemove = false;
        }

        /**
         * determines if there is an element present on the next iteration.
         * @return true if the iteration has more elements, otherwise returns false.
         */
        public boolean hasNext() {
            return canRemove;
        }

        /**
         * Returns the next element in the iteration, and advances the iterator index.
         *
         * @return the next element in the iteration.
         * @throws NoSuchElementException
         */
        public E next() throws NoSuchElementException {
            // if there is nothing left to iterate and return, throw the exception
            if (index == size)
                throw new NoSuchElementException();

            //set canRemove to true, now remove() for this iterator is allowed once.
            canRemove = true;
            //store value of returned item as a parameter to remove it from array if needed.
            toRemove = data[index];
            return data[index++]; //return next element and increment index.
        }

        /**
         * Removes the last element returned by next() from this collection,
         * can only be used once after each call to next().
         *
         * @throws IllegalStateException
         */
        public void remove() throws IllegalStateException {
            // if canRemove is false, next() has not been called yet for this method call.
            if (!canRemove)
                throw new IllegalStateException();

            // if remove() can be used, call remove() method of this collection and pass in the
            // last item returned by next, which is toRemove, to be removed from this collection.
            ArrayBasedCollection.this.remove(toRemove);
            // reset toRemove and canRemove until after another call to next().
            toRemove = null;
            canRemove = false;
        }
    }
}
