package uk.ac.man.cs.patterns.battleship.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that represent pop up messages with confirmation or not.
 * @author Guillermo Antonio Toro Bayona
 */
public class MessageDialog {

    /**
     * JButton cancel
     */
    private JButton jButtonCancel;
    private JButton jButtonOk;
    private JLabel jLabelMessage;
    /**
     * Integer Return cancel and OK states
     */
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;
    /**
     * Get the status
     * @return Integer Status
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * Method to set the behaviour on close
     * @param retStatus
     */
    public void doClose(int retStatus) {
        returnStatus = retStatus;
    }
}
