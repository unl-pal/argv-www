package assignment4;

import java.util.ArrayList;

/**
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 *         <p/>
 *         This sorting utility class provides static methods for recursive sorting
 *         <p/>
 *         Merge sort methods for threshold setting, driving, recursing, and merging Quicksort methods for driving,
 *         recursing, and finding different pivots Input generators for creating ascending, descending, and permuted
 *         lists
 */
public class RecursiveSortingUtility {

    // field that determines size of array partitions from mergesort recursions that will be sorted with insertion sort
    protected static int mergesortThreshold = 0;


    /**
     * Only used for testing purposes in RecursiveSortingUtilityTest class, this method can be commented out.
     *
     * @return current mergesort thresholding setting for the mergeSortRecursive method
     */
    public static int getMergesortThreshold() {
        return mergesortThreshold;
    }

    /**
     * Helper method for setting the switching threshold for merge sort
     *
     * @param desiredThreshold - merge sort switching threshold
     */
    public static void setMergeSortThreshold(int desiredThreshold) {
        mergesortThreshold = desiredThreshold;
    }

    /**
     * Iterative insertion sort method
     *
     * @param list  - input ArrayList of objects, must have a Comparable implementation
     * @param start - start index of the subarray of objects
     * @param end   - end index of the subarray of objects
     */
    private static <T extends Comparable<? super T>> void insertionSortIterative(ArrayList<T> list, int start, int end) {
        // i index is the first element on the unsorted side of list
        // the list section is the portion of list from indexes start to end inclusive.
        for (int i = start + 1; i <= end; i++) {
            // store value of item to insert
            T val = list.get(i);
            int j;
            // copy elements to the right until element is found that is < val
            for (j = i - 1; j >= start && list.get(j).compareTo(val) > 0; j--)
                list.set(j + 1, list.get(j));
            // insert val at one index above the first element that is less than val
            list.set(j + 1, val);
        }
    }

    /**
     * Recursive merge sort driver method
     *
     * @param list - input ArrayList of objects, must have a Comparable implementation
     */
    public static <T extends Comparable<? super T>> void mergeSortDriver(ArrayList<T> list) {
        mergeSortRecursive(list, new ArrayList<T>(list.size()), 0, list.size() - 1);
    }

    /**
     * Recursive merge sort algorithm method
     *
     * @param list  - input ArrayList of T objects that must have a Comparable implementation
     * @param temp  - temporary ArrayList of T objects to help with merging
     * @param start - start index of the subarray of objects
     * @param end   - end index of the subarray of objects
     */
    private static <T extends Comparable<? super T>> void mergeSortRecursive(ArrayList<T> list, ArrayList<T> temp, int start, int end) {

        // handle base case when list portion reaches size of mergsortThreshold.
        if (end - start <= mergesortThreshold && mergesortThreshold > 1) {
            insertionSortIterative(list, start, end);
            return;
        }

        // check this condition if mergesortThreshold is not set above 1
        if (start >= end)
            return;

        // find middle index between start and end to split array portion in half
        int mid = (start + end) / 2;
        // pass new start and end indexes for each half of partitioned array, also pass temp array to aid in merging
        // two arrays, the temp array is initialized to only the size needed.
        mergeSortRecursive(list, temp/*new ArrayList<T>(mid - start + 1)*/, start, mid);  //left half
        mergeSortRecursive(list, temp/*new ArrayList<T>(end - mid)*/, mid + 1, end);  //right half

        mergeSortedPortions(list, temp, start, mid, end);  //merge the two half when returned from above
    }

    /**
     * Recursive merge sort helper method
     *
     * @param list   - input ArrayList of T objects that must have a Comparable implementation
     * @param temp   - temporary ArrayList in  which the result will be placed
     * @param start  - start index of the subarray of objects
     * @param middle - middle index of the subarray of objects
     * @param end    - end index of the subarray of objects
     */
    private static <T extends Comparable<? super T>> void mergeSortedPortions(ArrayList<T> list, ArrayList<T> temp, int start, int middle, int end) {
        // set new variables as the end indexes of each half
        int tempEnd = end - start;
        int addIndex = start;
        int rightBegin = middle + 1;

        // compare indexed elements of each array started at beginning  arrays and adding the lesser element to temp
        while (start <= middle && rightBegin <= end) {
            if (list.get(start).compareTo(list.get(rightBegin)) < 0) {
                temp.add(list.get(start++));
            } else if (list.get(start).compareTo(list.get(rightBegin)) > 0)
                temp.add(list.get(rightBegin++));
            else {
                // if elements at each index are equal than add them both
                temp.add(list.get(start++));
                temp.add(list.get(rightBegin++));
            }
        }

        // the following 2 for loops add remaining elements of an array if the other finished first.
        for (int i = start; i <= middle; i++)
            temp.add(list.get(i));

        for (int i = rightBegin; i <= end; i++)
            temp.add(list.get(i));

        // this for loop copies the sorted temp array to correct indices of of list array
        for (T element : temp)
            list.set(addIndex++, element);
        //clear temp elements to avoid conflicts with other mergeSortedPortions processes
        temp.clear();
    }

    /**
     * Recursive quicksort driver method
     *
     * @param list - input ArrayList of T objects that must have a Comparable implementation
     */
    public static <T extends Comparable<? super T>> void quickSortDriver(ArrayList<T> list) {
        quickSortRecursive(list, 0, list.size() - 1);
    }

    /**
     * Recursive quicksort algorithm method
     *
     * @param list  - input ArrayList of T objects that must have a Comparable implementation
     * @param start - start index of the subarray of objects
     * @param end   - end index of the subarray of objects
     */
    private static <T extends Comparable<? super T>> void quickSortRecursive(ArrayList<T> list, int start, int end) {
        // handle base case and trivial sorts, when array partition is of size 1 it is already sorted.
        if ((end - start) < 1)
            return;

        // find an index for the pivot, three options available below, uncomment desired pivotIndex return strategy
//        int mid = goodPivotStrategy(list, start, end);
//        int mid = betterPivotStrategy(list, start, end);
        int mid = bestPivotStrategy(list, start, end);

        // store the value of the pivot element in order to make comparisons
        T pivot = list.get(mid);

        // swap pivot to the end index so it is not in the way.
        swapElements(list, mid, end);

        // initialize the 'left' and 'right' indices to be walked toward each other
        int left = start;
        int right = end - 1;

        // start infinite loop, will be ended with the break statement
        while (true) {

            // increment left index until an element is found that is greater than or equal to the pivot.
            while (list.get(left).compareTo(pivot) < 0)
                left++;

            // decrement right index until element is found that is less than the pivot, or left and right indices cross
            while (right > left && list.get(right).compareTo(pivot) > 0)
                right--;

            // stop moving pointers when 'left' index equals or passes 'right' index, break out of while loop
            if (left >= right)
                break;

            // swap left and right elements if right index is still greater than left index, than adjust indexes
            swapElements(list, left++, right--);
        }

        // swap left and end elements, occurs when left > right index and pivot point is determined
        swapElements(list, end, left);

        /*
        element at left index is now in its final place, split the array here and pass left and right side indices
        of array partitions to two more recursion methods
        */
        quickSortRecursive(list, start, left - 1);
        quickSortRecursive(list, left + 1, end);
    }

    /**
     * Recursive quicksort helper method
     *
     * @param list  - input ArrayList of T objects that must have a Comparable implementation
     * @param start - start index of the subarray  of objects
     * @param end   - end index of the subarray  of objects
     *
     * @return pivot index which is the middle index between the start and end indexes
     */
    public static <T extends Comparable<? super T>> int goodPivotStrategy(ArrayList<T> list, int start, int end) {
        // returns the middle index value between start and end indexes.
        return (int) (start + end) / 2;
    }

    /**
     * Recursive quicksort helper method
     *
     * @param list  - input ArrayList of T objects that must have a Comparable implementation
     * @param start - start index of the subarray  of objects
     * @param end   - end index of the subarray  of objects
     *
     * @return random index as the pivot index, chosen between the index range from the start to end index.
     */
    public static <T extends Comparable<? super T>> int betterPivotStrategy(ArrayList<T> list, int start, int end) {
        // determine range of index values to generate a random index that is valid
        int range = (end - start) + 1;
        // returns random index as pivot index, return value falling between start and end indexes.
        return (int) ((Math.random() * range) + start);
    }

    /**
     * Recursive quicksort helper method
     *
     * @param list  - input ArrayList of T objects that must have a Comparable implementation
     * @param start - start index of the subarray  of objects
     * @param end   - end index of the subarray  of objects
     *
     * @return Pivot index. Three random non-repeating indexes are generated and the returned index is the median valued
     *         element of the three elements at those indices.
     */
    public static <T extends Comparable<? super T>> int bestPivotStrategy(ArrayList<T> list, int start, int end) {
        // declare 3 int variables to hold the 3 random indices
        int a, b, c;
        // determine range of indexes available for valid index generation
        int range = (end - start) + 1;

        // to handle cases of array partitions of 3 elements or less
        // if the range is only 2 indices or less just return the end index
        if (range <= 2)
            return end;
        // if array section is just 3 indices, assign a,b, & c in order.
        if (range == 3) {
            a = start;
            b = start + 1;
            c = end;
        }

        // assign first random index in valid index range to 'a'
        a = ((int) (Math.random() * range) + start);

        // continue assigning a random index to 'b' until a non duplicate of 'a' is assigned.
        do {
            b = ((int) (Math.random() * range) + start);
        } while (a == b);

        // continue assigning a random index to 'c' until a non duplicate of 'a' or 'b' is assigned.
        do {
            c = ((int) (Math.random() * range) + start);
        } while (c == a || c == b);

        // if statement combinations to determine which index points to the median valued element
        if (list.get(a).compareTo(list.get(b)) >= 0) {    // following conditions if 'a' is greater than 'b'
            if (list.get(b).compareTo(list.get(c)) >= 0)
                return b;
            if (list.get(a).compareTo(list.get(c)) >= 0)
                return c;
            else
                return a;
        }
        if (list.get(a).compareTo(list.get(b)) <= 0) {  // following conditions if 'a' is less than 'b'
            if (list.get(b).compareTo(list.get(c)) <= 0)
                return b;
            if (list.get(a).compareTo(list.get(c)) <= 0)
                return c;
        }
        return a;
    }

    /**
     * Generates an ArrayList of ints from 1 to size in ascending order
     *
     * @param size size of the returned ArrayList
     *
     * @return an ArrayList of integers in sorted, ascending order, of all values from 1 to 'size'
     */
    public static ArrayList<Integer> generateBestCase(int size) {
        // create new ArrayList of size equal to the parameter 'size'
        ArrayList<Integer> arr = new ArrayList<Integer>(size);
        // add values from 1 to size inclusive, in ascending order to arr
        for (int i = 1; i <= size; i++)
            arr.add(i);
        // return ascending ArrayList
        return arr;
    }

    /**
     * Generates an ArrayList of ints from 1 to size in random order
     *
     * @param size the size of the returned ArrayList
     *
     * @return An ArrayList of random integers  valued from 1 to 'size' in permuted order
     */
    public static ArrayList<Integer> generateAverageCase(int size) {
        // create an ascending array then permute each element
        ArrayList<Integer> returnList = generateBestCase(size);
        for (int i = 0; i < returnList.size() * 100; i++)
            swapElements(returnList, (int) (Math.random() * size), (int) (Math.random() * size));
        return returnList;
    }

    /**
     * Generates an ArrayList of ints from 1 to size in ascending order
     *
     * @param size the size of the returned ArrayList
     *
     * @return An ArrayList of all the integers from 'size' to 1 in descending order.
     */
    public static ArrayList<Integer> generateWorstCase(int size) {
        // create new ArrayList of size equal to the parameter 'size'
        ArrayList<Integer> arr = new ArrayList<Integer>(size);
        // add values from size to 1 inclusive, in descending order to arr
        for (int i = size; i > 0; i--)
            arr.add(i);
        // return descending ArrayList
        return arr;
    }

    /**
     * ArrayList elements swapping Helper method, swaps elements in list at the two given indices
     *
     * @param list  - input ArrayList of objects, must have a Comparable implementation
     * @param left  - index of the left element
     * @param right - index of the right element
     */
    public static <T extends Comparable<? super T>> void swapElements(ArrayList<T> list, int left, int right) {
        // stored copy of left indexed element to temp
        T temp = list.get(left);
        // reassign left indexed element to a copy of the the right indexed element
        list.set(left, list.get(right));
        // replace right indexed element with previous value of left index stored in temp
        list.set(right, temp);
    }
}
