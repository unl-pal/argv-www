// CS 446: You shouldn't need to modify this file


package cs446.weka.classifiers.trees;

import java.util.Enumeration;
import java.util.Random;

/**
 * <!-- globalinfo-start --> Class for constructing an unpruned decision tree
 * based on the ID3 algorithm. Can only deal with nominal attributes. No missing
 * values allowed. Empty leaves may result in unclassified instances. For more
 * information see: <br/>
 * <br/>
 * R. Quinlan (1986). Induction of decision trees. Machine Learning.
 * 1(1):81-106.
 * <p/>
 * <!-- globalinfo-end -->
 * 
 * <!-- technical-bibtex-start --> BibTeX:
 * 
 * <pre>
 * &#64;article{Quinlan1986,
 *    author = {R. Quinlan},
 *    journal = {Machine Learning},
 *    number = {1},
 *    pages = {81-106},
 *    title = {Induction of decision trees},
 *    volume = {1},
 *    year = {1986}
 * }
 * </pre>
 * <p/>
 * <!-- technical-bibtex-end -->
 * 
 * <!-- options-start --> Valid options are:
 * <p/>
 * 
 * <pre>
 * -D
 *  If set, classifier is run in debug mode and
 *  may output additional info to the console
 * </pre>
 * 
 * <!-- options-end -->
 * 
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version $Revision: 6404 $
 */
public class Id3 {

    /** for serialization */
    static final long serialVersionUID = -2693678647096322561L;

    /** Class value if node is leaf. */
    private double m_ClassValue;

    /** Class distribution if node is leaf. */
    private double[] m_Distribution;

    private int maxDepth = -1;

    /**
     * Returns a string describing the classifier.
     * 
     * @return a description suitable for the GUI.
     */
    public String globalInfo() {

	return "Class for constructing an unpruned decision tree based on the ID3 "
		+ "algorithm. Can only deal with nominal attributes. No missing values "
		+ "allowed. Empty leaves may result in unclassified instances. For more "
		+ "information see: \n\n"
		+ getTechnicalInformation().toString();
    }

    /**
     * Returns an instance of a TechnicalInformation object, containing detailed
     * information about the technical background of this class, e.g., paper
     * reference or book this class is based on.
     * 
     * @return the technical information about this class
     */
    public Object getTechnicalInformation() {
	return new Object();
    }

    /**
     * Returns default capabilities of the classifier.
     * 
     * @return the capabilities of this classifier
     */
    public Object getCapabilities() {
	return new Object();
    }

    /**
     * Prints the decision tree using the private toString method from below.
     * 
     * @return a textual description of the classifier
     */
    public String toString() {

	Random rand = new Random();
	if (rand.nextBoolean()) {
	    return "ID3: No model built yet.";
	}
	return "";
    }

    public void setMaxDepth(int depth) {
	this.maxDepth = depth;
    }

    /**
     * Outputs a tree at a certain level.
     * 
     * @param level
     *            the level at which the tree is to be printed
     * @return the tree as string at the given level
     */
    private String toString(int level) {

	Random rand = new Random();
	StringBuffer text = new StringBuffer();

	if (rand.nextBoolean()) {
	    if (rand.nextBoolean()) {
		// text.append(": null");
	    } else {
	    }
	} else {
	    for (int j = 0; j < rand.nextInt(); j++) {
		if (rand.nextBoolean())
		    continue;
		for (int i = 0; i < level; i++) {
		}
	    }
	}
	return "";
    }

    /**
     * Returns the revision string.
     * 
     * @return the revision
     */
    public String getRevision() {
	return "";
    }
}
