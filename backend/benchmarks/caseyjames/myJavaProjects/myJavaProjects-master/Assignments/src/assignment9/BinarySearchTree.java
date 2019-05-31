package assignment9;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * A BST class in which elements are Comparable (necessary for all BSTs) and without duplicates
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 7/16/2014
 */
public class BinarySearchTree<Type> {
    private BinaryNode root;
    private int size;
    private Comparator<? super Type> cmp;

    /**
     * constructs an empty BST
     */
    public BinarySearchTree() {
        size = 0;
        cmp = null;
    }

    /**
     * constructs a BST with an initial root Node
     *
     * @param element initial element to be added to this BST
     */
    public BinarySearchTree(Type element) {
        root = new BinaryNode(element);
        size = 1;
        cmp = null;
    }

    /**
     * constructs a BST and adds all of the items in the specified Collection
     *
     * @param items the specified Collection of items to be added to this BST
     */
    public BinarySearchTree(Collection<? extends Type> items) {
        size = 0;
        cmp = null;
        this.addAll(items);
    }

    /**
     * constructs a BST that takes a specified comparator function object
     *
     * @param c Comparator function object to be used in place of the Comparable method compareTo()
     */
    public BinarySearchTree(Comparator<? super Type> c) {
        cmp = c;
        size = 0;
    }

    /**
     * Internal method for comparing lhs and rhs using Comparator if provided by the
     * user at construction time, or Comparable, if no Comparator was provided.
     */
    @SuppressWarnings("unchecked")
    private int compare(Type lhs, Type rhs) {
        if (cmp == null)
            return ((Comparable<? super Type>) lhs).compareTo(rhs); // safe to ignore warning
        // We won't test your code on non-Comparable types if we didn't supply a Comparator

        return cmp.compare(lhs, rhs);
    }

    /**
     * Ensures that this set contains the specified item.
     *
     * @param item - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if the input item was actually
     * inserted); otherwise, returns false
     * @throws NullPointerException if the item is null
     */
    public boolean add(Type item) {
        // throw exception if passed parameter is null
        if (item == null)
            throw new NullPointerException("Tried adding null");

        // handle adding to null set - update root and increment size
        if (size == 0) {
            root = new BinaryNode(item);
            size++;
            return true;
        }

        // find the Node where the data should be added
        BinaryNode currentNode = root;
        while (!currentNode.isLeaf()) {
            // if the node is found don't change the BST and return false
            if (currentNode.data.equals(item))
                return false;
                // if the item should go to the left either change the current node to the left subtree or create a left node
            else if (compare(item, currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null)
                    currentNode = currentNode.getLeft();
                else break;
            } else {// if the item should go to the right either change the current node to the right subtree or create a right node
                if (currentNode.getRight() != null)
                    currentNode = currentNode.getRight();
                else break;
            }
        }
        // if the data is at the current node don't change anything and return false
        if (compare(item, currentNode.getData()) == 0)
            return false;
            // implement adding to left of currentNode
        else if (compare(item, currentNode.getData()) < 0) {
            currentNode.setLeft(new BinaryNode(item));
            size++;
            return true;
        }
        // implement adding to right of currentNode
        else {
            currentNode.setRight(new BinaryNode(item));
            size++;
            return true;
        }
    }

    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if any item in the input collection
     * was actually inserted); otherwise, returns false
     * @throws NullPointerException if any of the items is null
     */
    public boolean addAll(Collection<? extends Type> items) {
        // handle adding null
        if (items == null)
            throw new NullPointerException("Tried to add a null Collection with addAll");
        // store the current size in order to check for changes to the set when this method returns
        int size = this.size();
        // add each element
        for (Type element : items)
            add(element);
        // return a boolean iff the BST was changed (if the size was changed)
        return (this.size() != size);
    }

    /**
     * Removes all items from this set. The set will be empty after this method call.
     */
    public void clear() {
        // reset fields
        root = null;
        size = 0;
    }

    /**
     * Determines if there is an item in this set that is equal to the specified item.
     *
     * @param item - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item; otherwise, returns false
     * @throws NullPointerException if the item is null
     */
    public boolean contains(Type item) {
        // handle null parameter
        if (item == null)
            throw new NullPointerException("Tried contains with null item");
        // an empty set cannot contain any certain item - return false
        if (isEmpty())
            return false;

        // search the BST for a Node containing the passed data
        BinaryNode currentNode = root; // start from root
        while (!currentNode.isLeaf()) {
            Type data = currentNode.getData();
                /* the if and if else statements that follow simply compare item against the data in the current node
                   and either change the node to iterate further down the BST or return as appropriate */
            if (compare(item, data) == 0)
                return true;
            else if (compare(item, data) < 0) {
                if (currentNode.getLeft() == null)
                    return false;
                currentNode = currentNode.getLeft();
            } else {
                if (currentNode.getRight() == null)
                    return false;
                currentNode = currentNode.getRight();
            }
        }
        // this statement is only reached when the while loop hits a leaf node, in which case only the currentNode's
        //  data has to be checked
        return (compare(item, currentNode.getData()) == 0);
    }

    /**
     * Determines if for each item in the specified collection, there is an item in this set that is equal to it.
     *
     * @param items - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item in this set that is equal to it;
     * otherwise, returns false
     * @throws NullPointerException if any of the items is null
     */
    public boolean containsAll(Collection<? extends Type> items) {
        // every BST contains an empty collection, so if one is passed return true
        if (items.size() == 0)
            return true;
        // an empty BST cannot contain any specific item - return false
        if (isEmpty())
            return false;

        // check if each element in the collection is found in the BST. If not return false
        for (Type element : items)
            if (!contains(element)) // return false if the element isn't found in the BST
                return false;
        // return true iff every element in the passed collection was found in the BST
        return true;
    }

    /**
     * Returns true if this set contains no items.
     */
    public boolean isEmpty() {
        return (root == null);
    }

    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if the input item was actually
     * removed); otherwise, returns false
     * @throws NullPointerException if the item is null
     */
    public boolean remove(Type item) {
        // handle null parameter
        if (item == null)
            throw new NullPointerException("Tried remove with null");
        // if the BST is empty (and the item isn't null) the item cannot exist in the BST, so return false
        if (isEmpty())
            return false;
        // handle removing final node - reset the BST and return
        if (size == 1 && compare(item, root.getData()) == 0) {
            clear();
            return true;
        }
        // handle removing root by removing root's successor and copying the removed node's data to root
        if (compare(item, root.getData()) == 0) {
            Type data = root.getSuccessor().getData();
            remove(data);
            root.setData(data);
            return true;
        }
        // iterate through BST to find a Node with a child containing the specified data (a child is found instead of
        //  the actual node to remove so that the remove methods can have access to the parent of the node to be
        //  removed).
        BinaryNode currentNode = root;
        while (true) {
            // check while loop condition here to avoid null pointer
            // if either child node matches the data we're looking for stop looping and remove the node
            if (currentNode.getLeft() != null && compare(item, currentNode.getLeft().getData()) == 0)
                break;
            if (currentNode.getRight() != null && compare(item, currentNode.getRight().getData()) == 0)
                break;

            // if the data should be left of the current node check the left child
            if (compare(item, currentNode.getData()) < 0) {
                if (currentNode.getLeft() == null) // if no left child then the node doesn't exist and cannot be removed
                    return false;
                if (compare(item, currentNode.getLeft().getData()) == 0) // if the left child matches the data then remove the node
                    break;
                // if a left subtree exists and the root doesn't match the data then continue iterating down the BST
                currentNode = currentNode.getLeft();
            }
            // if the data should be right of the current node check the right child
            else {
                if (currentNode.getRight() == null) // if no right child then the node doesn't exist and cannot be removed
                    return false;
                if (compare(item, currentNode.getRight().getData()) == 0) // if the right child matches the data then store then remove the node
                    break;
                // if a right subtree exists and the root doesn't match the data then continue iterating down the BST
                currentNode = currentNode.getRight();
            }
        }
        // set the direction according to which node matches the data
        int direction = (currentNode.getLeft() != null && compare(item, currentNode.getLeft().getData()) == 0) ? -1 : 1;

        // these statements only execute if the correct node was found, in which case the node should be removed
        //  according to the number of children, the size should decrement, and this method should return true.
        currentNode.remove(direction);
        size--;
        return true;
    }

    /**
     * Ensures that this set does not contain any of the items in the specified collection.
     *
     * @param items - the collection of items whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if any item in the input collection
     * was actually removed); otherwise, returns false
     * @throws NullPointerException if any of the items is null
     */
    public boolean removeAll(Collection<? extends Type> items) {
        // for empty collections simply return false, as nothing needs to happen and the BST will be unaffected
        if (items.size() == 0)
            return false;
        // store the size in order to check for changes to the BST when returning
        int size = size();
        // remove each element in the Collection from the BST, throwing an Exception if any element is null
        for (Type element : items)
            remove(element);
        // return true iff at least one element was removed (the size was changed during method execution)
        return (size != size());
    }

    /**
     * Returns the number of items in this set.
     */
    public int size() {
        return size;
    }

    /**
     * Returns an ArrayList containing all of the items in this set, in sorted order (equivalent to an in-order
     * depth-first-traversal)
     */
    public ArrayList<Type> toArrayList() {
        return new ArrayList<Type>(inOrderDFT());
    }

    /**
     * Returns the first (i.e., smallest) item in this set.
     *
     * @throws java.util.NoSuchElementException if the set is empty
     */
    public Type first() throws NoSuchElementException {
        // throw an exception for an empty set
        if (root == null)
            throw new NoSuchElementException("Tried first with an empty BST");
        // if root is smallest then return root's data
        if (root.getLeft() == null)
            return root.data;
            // otherwise find the largest node (rightmost node) and return its data
        else return root.getLeftmostNode().getData();
    }

    /**
     * Returns the last (i.e., largest) item in this set.
     *
     * @throws java.util.NoSuchElementException if the set is empty
     */
    public Type last() throws NoSuchElementException {
        // throw an exception for an empty set
        if (root == null)
            throw new NoSuchElementException("Tried last with an empty BST");
        // if root is biggest then return root's data
        if (root.getRight() == null)
            return root.data;
            // otherwise find the largest node (rightmost node) and return its data
        else return root.getRightmostNode().getData();
    }

    /**
     * Performs a pre-order depth-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public List<Type> inOrderDFT() {
        ArrayList<Type> returnList = new ArrayList<Type>(size);
        // check if the BST is empty, if so return the empty ArrayList
        if (isEmpty())
            return returnList;

        inOrderDFTRecursive(root, returnList);
        return returnList;
    }

    /**
     * Performs an in-order depth-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public List<Type> preOrderDFT() {
        ArrayList<Type> returnList = new ArrayList<Type>(size);
        // check if the BST is empty, if so return the empty ArrayList
        if (isEmpty())
            return returnList;

        preOrderDFTRecursive(root, returnList);
        return returnList;
    }

    /**
     * Performs a post-order depth-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public List<Type> postOrderDFT() {
        ArrayList<Type> returnList = new ArrayList<Type>(size);
        // check if the BST is empty, if so return the empty ArrayList
        if (isEmpty())
            return returnList;

        postOrderDFTRecursive(root, returnList);
        return returnList;
    }

    /**
     * Performs a level-order breath-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public List<Type> levelOrderBFT() {
        ArrayList<Type> returnList = new ArrayList<Type>(size);
        // check if the BST is empty, if so return the empty ArrayList
        if (isEmpty())
            return returnList;

        levelOrderDFTRecursive(new LinkedList<BinaryNode>(), root, returnList);
        return returnList;
    }

    /**
     * Uses a queue to recursively add each node in the BST to the return list in a level-order traversal
     */
    private void levelOrderDFTRecursive(LinkedList<BinaryNode> queue, BinaryNode n, ArrayList<Type> returnList) {
        // handle null nodes
        if (n == null)
            return;

        // add 'this' object to the return list
        returnList.add(n.getData());

        // push the children to the queue then use the queue to continue level-order traversal
        if (n.getLeft() != null)
            queue.addLast(n.getLeft());
        if (n.getRight() != null)
            queue.addLast(n.getRight());
        if (queue.size() != 0)
            levelOrderDFTRecursive(queue, queue.removeFirst(), returnList);
    }

    /* These recursive methods are nearly identical, and recursively iterate through the BST in pre-, in-, or post-order */
    private void inOrderDFTRecursive(BinaryNode n, ArrayList<Type> returnList) {
        // handle null node
        if (n == null)
            return;
        //first check for leaf node, add to ArrayList if it is a leaf node
        if (n.isLeaf()) {
            returnList.add(n.getData());
            return;
        }

        inOrderDFTRecursive(n.getLeft(), returnList);
        returnList.add(n.getData());
        inOrderDFTRecursive(n.getRight(), returnList);
    }

    private void preOrderDFTRecursive(BinaryNode n, ArrayList<Type> returnList) {
        // handle null node
        if (n == null)
            return;
        //first check for leaf node, add to ArrayList if it is a leaf node
        if (n.isLeaf()) {
            returnList.add(n.getData());
            return;
        }

        returnList.add(n.getData());
        preOrderDFTRecursive(n.getLeft(), returnList);
        preOrderDFTRecursive(n.getRight(), returnList);
    }

    private void postOrderDFTRecursive(BinaryNode n, ArrayList<Type> returnList) {
        // handle null node
        if (n == null)
            return;
        //first check for leaf node, add to ArrayList if it is a leaf node
        if (n.isLeaf()) {
            returnList.add(n.getData());
            return;
        }

        postOrderDFTRecursive(n.getLeft(), returnList);
        postOrderDFTRecursive(n.getRight(), returnList);
        returnList.add(n.getData());
    }

    /* DOT methods taken from BST.java in the week 6 example package */
    // Driver for writing this tree to a dot file

    /**
     * Creates a file with .dot extension to contain information about the tree. The format must be readable by the DOT
     * tool use by graphviz.
     *
     * @param filename - file containing the DOT formated data
     */
    public void writeDot(String filename, ArrayList randList) {
        try {
            // PrintWriter(FileWriter) will write output to a file
            PrintWriter output = new PrintWriter(new FileWriter(filename));
            Integer dl = 1;
            Integer dr = 5000 * (root.height() + 1) * (root.height() + 1);
            ArrayList<String>[] rankArray = new ArrayList[root.height() + 1];
            int depth = 0;

            // print header to graph and set 'strict' which does not allow more than 1 edge between the two same vertices
//            output.println("// " + randList + "\n\n\n");
            output.println("strict digraph BST{\n\n");
            // set attributes
            output.println("center=true");  // centers the graph
            output.println("ranksep=\"1.0\"");   // sets distance between levels to 0.4"
            output.println("outputorder=\"nodefirst\""); // causes nodes to be set first on graph, helps with overlap
            output.println("page=\"8.5,11\""); // causes nodes to be set first on graph, helps with overlap
            output.println("orientation=portrait"); // causes nodes to be set first on graph, helps with overlap
            output.println("margin=0.5"); // causes nodes to be set first on graph, helps with overlap

//            output.println("size=\"10,7.5\""); // sets image ratio (image height/width), 0.5 seems to work good
            output.println("ratio=auto"); // sets image ratio (image height/width), 0.5 seems to work good
            output.println("ordering=out"); // tells dot.exe to keep the left to right child node ordering as in file
            output.println("dpi=300"); // tells dot.exe to keep the left to right child node ordering as in file
            output.println("node [fontsize=18, fontname=Helvetica]"); // tells dot.exe to keep the left to right child node ordering as in file

            // if the BST is not null, call the recursive method to create the .dot file
            if (root != null)
                writeDotRecursive(root, output, dl, dr, rankArray, depth);

            for (int i = 0; i < root.height(); i++) {
                output.print("{ rank=same; ");
                for (String e : rankArray[i])
                    output.print(" \"" + e + "\"; ");
                output.print("}\n");
            }

            // Close the graph
            output.println("}");
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recursive method that writeDot() driver method calls for writing the tree to a dot file
    private void writeDotRecursive(BinaryNode n, PrintWriter output, Integer dl, Integer dr, ArrayList<String>[] rankArray, int depth) throws Exception {
        // these lines set up a gradient color for each level
        int colorMov = 255 / root.height();
        if (colorMov == 0)
            colorMov = 1;
        int rAndB = 255 - (depth * colorMov);
        String RB = Integer.toHexString(rAndB);
        String G = Integer.toHexString(255);
        String RGB = RB + G + RB;
        RGB = RGB.toUpperCase();
        String RGBcolor = "\"#" + RGB + "\"";

        double MAXNodeSep = 2.0;
        double MINNodeSep = 1.0;
        double currNodeSep = (MAXNodeSep - (((MAXNodeSep - MINNodeSep) / root.height()) * depth));

        Integer mid;
        // if the passed BinaryNode is null, return
        if (n == null)
            return;
        if (n.getLeft() == null && n.getRight() == null)
            return;

        // set data values to variables so it's easier to work with
        output.println("{");
        String nData = "" + n.getData() + "";
        output.println("\"" + nData + "\";");
        mid = ((dr - dl) / 2 + dl);
        String DM = "D" + mid.toString();
        output.println(DM + " [style=filled, fontsize=4, fontcolor=white, color=white, fillcolor=white, nodesep=" + currNodeSep + "];");
        String DL = "D" + (mid - 1);
        String DR = "D" + (mid + 1);
        if (n.getLeft() == null)
            output.println(DL + " [style=filled, fontsize=4, fontcolor=white, color=white, fillcolor=white, nodesep=" + currNodeSep + "];");
        if (n.getRight() == null)
            output.println(DR + " [style=filled, fontsize=4, fontcolor=white, color=white, fillcolor=white, nodesep=" + currNodeSep + "];");

        String getL = null, getR = null;
        if (n.getLeft() != null) {     // adds left and right data to easier variables if they are not null
            getL = "" + n.getLeft().getData() + "";
//            output.println(getL + " [style=filled, fillcolor=\"#FF5959\", nodesep=" + currNodeSep+"]");
            output.println(getL + " [style=filled, fillcolor=" + RGBcolor + ", nodesep=" + currNodeSep + "]");
        }
        if (n.getRight() != null) {
            getR = "" + n.getRight().getData() + "";
//            output.println(getR + " [fontcolor=white, style=filled, fillcolor=\"#1F1F1F\", nodesep=" + currNodeSep+"]");
            output.println(getR + " [fontcolor=black, style=filled, fillcolor=" + RGBcolor + ", nodesep=" + currNodeSep + "]");
        }

        if (rankArray[depth] == null)
            rankArray[depth] = new ArrayList<String>();

        /*following 3 if-statements for cases of numChildren = 0, 1, or 2. This allows formatting so that
        if there is only 1 child, the edge will not point straight down but still show left or right*/
        if (n.getLeft() != null && n.getRight() != null) {
            rankArray[depth].add(getL);
            rankArray[depth].add(DM);
            rankArray[depth].add(getR);
            // if both right and left have data, prints node connections first, then calls recursive methods
            output.println(nData + " -> " + getL);
            output.println(nData + " -> " + DM + " [color=white]"); // set edge to white
            output.println(nData + " -> " + getR);
            // tells dot.exe that these 3 nodes belong on the same level or rank
//            output.println("{rank=same; \"" + getL + "\" \"" + DM + "\" \"" + getR + "\" }");
            output.println("}\n\n");
            depth++;
            writeDotRecursive(n.getLeft(), output, dl, mid, rankArray, depth);
            writeDotRecursive(n.getRight(), output, mid, dr, rankArray, depth);
            return;
        }

        if (n.getLeft() != null && n.getRight() == null) {
            rankArray[depth].add(getL);
            rankArray[depth].add(DM);
            rankArray[depth].add(DR);
            // if only the left node has data, sets middle and right nodes to invisible nodes.
            output.println(nData + " -> " + getL);
            output.println(nData + " -> " + DM + " [color=white]");
            output.println(nData + " -> " + DR + " [color=white]");
            // tells dot.exe that these 3 nodes belong on the same level or rank
//            output.println("{rank=same; \"" + getL + "\" \"" + DM + "\" \"" + DR + "\" }");
            output.println("}\n\n");
            depth++;
            writeDotRecursive(n.getLeft(), output, dl, mid, rankArray, depth);
            return;
        }

        if (n.getLeft() == null && n.getRight() != null) {
            rankArray[depth].add(DL);
            rankArray[depth].add(DM);
            rankArray[depth].add(getR);
            // if only right has data, sets left and middle nodes to invisible nodes.
            output.println(nData + " -> " + DL + " [color=white]");
            output.println(nData + " -> " + DM + " [color=white]");
            output.println(nData + " -> " + getR);
            // tells dot.exe that these 3 nodes belong on the same level or rank
//            output.println("{rank=same; \"" + DL + "\" \"" + DM + "\" \"" + getR + "\" }");
            output.println("}\n\n");
            depth++;
            writeDotRecursive(n.getRight(), output, mid, dr, rankArray, depth);
            return;
        }

//        int colorMov = (255 - 16) / root.height();
//        if (colorMov == 0)
//            colorMov = 1;
//        int depth = root.height() - n.height();
//        int rAndB = 255 - (depth * colorMov);
//        String RB = Integer.toHexString(rAndB);
//        String G = Integer.toHexString(255);
//        String RGB = RB + G + RB;
//        RGB = RGB.toUpperCase();
//        String RGBcolor = "\"#" + RGB + "\"";
//        Integer mid;
//        // if the passed BinaryNode is null, return
//        if (n == null)
//            return;
//
//        // set data values to variables so it's easier to work with
//        Integer nHeight = n.height();
//        String nData = "" + n.getData() + "";
//        mid = ((dr - dl) / 2 + dl);
//        String DM = "D" + mid.toString();
//        String DL = "D" + (mid - 1);
//        String DR = "D" + (mid + 1);
//
//        String getL = null, getR = null;
//        if (n.getLeft() != null)      // adds left and right data to easier variables if they are not null
//            getL = "" + n.getLeft().getData() + "";
//        if (n.getRight() != null)
//            getR = "" + n.getRight().getData() + "";
//
//        /*following 3 if-statements for cases of numChildren = 0, 1, or 2. This allows formatting so that
//        if there is only 1 child, the edge will not point straight down but still show left or right*/
//        if (n.getLeft() != null && n.getRight() != null) {
//            // if both right and left have data, prints node connections first, then calls recursive methods
//            output.println(getL + " [style=filled, fillcolor="+RGBcolor+"]");
//            output.println(nData + " -> " + getL + " [minlen=1.5]");
//            output.println(DM + " [style=filled, fontcolor=white, color=white, fillcolor=white]"); //sets blank label and style invisible
//            output.println(nData + " -> " + DM + " [color=white]"); // high weight so edges go left and right
//            output.println(getR + " [style=filled, fillcolor="+RGBcolor+"]");
//            output.println(nData + " -> " + getR + " [minlen=1.5]");
//            // tells dot.exe that these 3 nodes belong on the same level or rank
//            output.println("{rank=same; \"" + getL + "\" \"" + DM + "\" \"" + getR + "\" }");
//
//            // call recursive methods once node connections are printed to file
//            writeDotRecursive(n.getLeft(), output, dl, mid);
//            writeDotRecursive(n.getRight(), output, mid, dr);
//            return;
//        }
//
//        if (n.getLeft() != null && n.getRight() == null) {
//            // if only the left node has data, sets middle and right nodes to invisible nodes.
//            output.println(getL + " [style=filled, fillcolor="+RGBcolor+"]");
//            output.println(nData + " -> " + getL + " [minlen=1.5]");
//            output.println(DM + " [style=filled, fontcolor=white, color=white, fillcolor=white]");
//            output.println(nData + " -> " + DM + " [color=white]");
//            output.println(DR + " [style=filled, fontcolor=white, color=white, fillcolor=white]");
//            output.println(nData + " -> " + DR + " [minlen=1.5, color=white]");
//            // tells dot.exe that these 3 nodes belong on the same level or rank
//            output.println("{rank=same; \"" + getL + "\" \"" + DM + "\" \"" + DR + "\" }");
//
//            // call recursive method once node connections are printed to file
//            writeDotRecursive(n.getLeft(), output, dl, mid);
//            return;
//        }
//
//        if (n.getLeft() == null && n.getRight() != null) {
//            // if only right has data, sets left and middle nodes to invisible nodes.
//            output.println(DL + " [style=filled, fontcolor=white, color=white, fillcolor=white]");
//            output.println(nData + " -> " + DL + " [minlen=1.5, color=white]");
//            output.println(DM + " [style=filled, fontcolor=white, color=white, fillcolor=white]");
//            output.println(nData + " -> " + DM + " [color=white]");
//            output.println(getR + " [style=filled, fillcolor="+RGBcolor+"]");
//            output.println(nData + " -> " + getR + " [minlen=1.5]");
//            // tells dot.exe that these 3 nodes belong on the same level or rank
//            output.println("{rank=same; \"" + DL + "\" \"" + DM + "\" \"" + getR + "\" }");
//
//            // call recursive method once node connections are printed to file
//            writeDotRecursive(n.getRight(), output, mid, dr);
//            return;
//            /*label=\"\", */
//        }

    }

    /**
     * Represents a general binary tree node. Each binary node contains data, a left child, and a right child
     */
    private class BinaryNode {
        // Since the outer BST class declares <Type>, we can use it here without redeclaring it for BinaryNode
        private Type data;
        private BinaryNode left, right;

        /**
         * Construct a new node with known children
         */
        public BinaryNode(Type _data, BinaryNode _left, BinaryNode _right) {
            data = _data;
            left = _left;
            right = _right;
        }

        /**
         * Construct a new node with no children
         */
        public BinaryNode(Type _data) {
            this(_data, null, null);
        }

        /**
         * Getter method.
         *
         * @return the node data.
         */
        public Type getData() {
            return data;
        }

        /**
         * Setter method.
         *
         * @param _data - the node data to be set.
         */
        public void setData(Type _data) {
            data = _data;
        }

        /**
         * Getter method.
         *
         * @return the left child node.
         */
        public BinaryNode getLeft() {
            return left;
        }

        /**
         * Setter method.
         *
         * @param _left - the left child node to be set.
         */
        public void setLeft(BinaryNode _left) {
            left = _left;
        }

        /**
         * Getter method.
         *
         * @return the right child node.
         */
        public BinaryNode getRight() {
            return right;
        }

        /**
         * Setter method.
         *
         * @param _right - the right child node to be set.
         */
        public void setRight(BinaryNode _right) {
            right = _right;
        }

        /**
         * Number of children Use this to help figure out which BST deletion case to perform
         *
         * @return The number of children of this node
         */
        public int numChildren() {
            int numChildren = 0;

            if (getLeft() != null)
                numChildren++;
            if (getRight() != null)
                numChildren++;

            return numChildren;
        }

        /**
         * Returns true iff the BinaryNode is a leaf node (has no children)
         *
         * @return a boolean indicating if the node is a leaf or not
         */
        public boolean isLeaf() {
            return (numChildren() == 0);
        }

        /**
         * @return The leftmost node in the binary tree rooted at this node.
         */
        public BinaryNode getLeftmostNode() {
            // Base case, done for you
            if (left == null)
                return this; // returns "this" node

            // find leftmost Node
            BinaryNode returnNode = this.getLeft();
            while (returnNode.left != null)
                returnNode = returnNode.left;
            return returnNode;
        }

        /**
         * @return The rightmost node in the binary tree rooted at this node.
         */
        public BinaryNode getRightmostNode() {
            // Base case, done for you
            if (getRight() == null)
                return this; // returns "this" node

            // find rightmost Node
            BinaryNode returnNode = this.getRight();
            while (returnNode.getRight() != null)
                returnNode = returnNode.getRight();
            return returnNode;
        }

        /**
         * This method applies to binary search trees only (not general binary trees).
         *
         * @return The successor of this node.
         * @throws java.util.NoSuchElementException if the node has no successor (is a leaf node)
         *                                          <p/>
         *                                          The successor is a node which can replace this node in a case-3 BST deletion.
         *                                          It is either the smallest node in the right subtree, or the largest node in
         *                                          the left subtree.
         */
        public BinaryNode getSuccessor() throws NoSuchElementException {
            // throw an exception if no successor exists (in case of a leaf node)
            if (isLeaf())
                throw new NoSuchElementException("Attempted .getSuccessor on a leaf node!");
            if (right != null)
                return right.getLeftmostNode();
            else
                return left.getRightmostNode();
        }

        /**
         * @return The height of the binary tree rooted at this node. The height of a tree is the length of the longest
         * path to a leaf node. Consider a tree with a single node to have a height of zero.
         * <p/>
         * The height of a tree with more than one node is the greater of its two subtrees' heights, plus 1
         */
        public int height() {
            // handle base case of recursion
            if (isLeaf())
                return 0;

            // find height of left subtree and right subtree, where a null subtree is of height 0)
            int leftHeight = (left == null) ? 0 : left.height();
            int rightHeight = (right == null) ? 0 : right.height();

            // return the correct height for this node, being the max height of the subtrees + 1
            return (Math.max(leftHeight, rightHeight) + 1);
        }

        /**
         * Removes the child of the this node according to the passed direction
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         * @throws java.util.NoSuchElementException if the node doesn't have the indicated child, or the node is null
         */
        public void remove(int direction) {
            // throw Exceptions for every invalid removal case, with a message as appropriate
            if (direction != -1 && direction != 1)
                throw new NoSuchElementException("Tried BinaryNode.remove with the invalid direction " + direction + "!");
            if (direction == -1 && left == null)
                throw new NoSuchElementException("Tried BinaryNode.remove to the left with no left child!");
            if (direction == 1 && right == null)
                throw new NoSuchElementException("Tried BinaryNode.remove to the right with no right child!");

            // implement removal based on the direction and number of children
            if (direction == -1) { // removing the left child
                if (left.isLeaf())
                    remove0(direction);
                else if (left.numChildren() == 1)
                    remove1(direction);
                else remove2(direction);
            } else { // removing the right child
                if (right.isLeaf())
                    remove0(direction);
                else if (right.numChildren() == 1)
                    remove1(direction);
                else remove2(direction);
            }
        }

        /* Remove methods for each possible number of children. Note that input validation happens in the remove method above */

        /**
         * Removes a node with no children
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         */
        private void remove0(int direction) {
            if (direction == -1) // removing the left child
                left = null;
            else // removing the right child
                right = null;
        }

        /**
         * Removes a node with one child
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         */
        private void remove1(int direction) {
            // to remove a node with one child simply set the correct to the non-null subtree
            if (direction == -1) left = (left.left != null) ? left.left : left.right;
            else right = (right.left != null) ? right.left : right.right;
        }

        /**
         * Removes a node with two children
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         */
        private void remove2(int direction) {
            if (direction == -1) { // removing left node
                // if the right node has no left children then it is the successor - copy its data and remove it
                if (left.right.left == null) {
                    left.data = left.right.data;
                    left.remove(1);
                    return;
                }
                // otherwise find the parent of the successor then copy the successor's data and remove it
                BinaryNode parentNode = left.right;
                while (parentNode.left.left != null)
                    parentNode = parentNode.left;
                left.data = parentNode.left.data;
                parentNode.remove(-1);
            } else { // removing right node
                // if the right node has no left children then it is the successor - copy its data and remove it
                if (right.right.left == null) {
                    right.data = right.right.data;
                    right.remove(1);
                    return;
                }
                // otherwise find the parent of the successor then copy the successor's data and remove it
                BinaryNode parentNode = right.right;
                while (parentNode.left.left != null)
                    parentNode = parentNode.left;
                right.data = parentNode.left.data;
                parentNode.remove(-1);
            }
        }
    }
}
