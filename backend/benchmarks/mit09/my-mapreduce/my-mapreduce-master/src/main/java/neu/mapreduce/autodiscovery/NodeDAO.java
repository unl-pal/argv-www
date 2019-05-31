package neu.mapreduce.autodiscovery;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vishal on 4/13/15.
 * Data Access Object class for autodiscovery information. It will have
 * IP and port numbers for the autodiscovery
 */

public class NodeDAO {
    private static final Logger LOGGER = Logger.getLogger(NodeDAO.class.getName());
    public static final String RAW_REGISTRY_SPLITTER = "\\t";
    public static final String IP_PORT_SPLITTER = ":";
    public static final int ZERO = 0;
    public static final int LENGTH_ONLY_IP = 1;
    public static final int LENGTH_IP_PORTS = 3;
    public static final int LOCAL_IP_POSITION = 1;
    public static final String MULTIPLE_IP_SPLITTER = " ";

    private String ip;
    private int fileTransferPort;
    private int messagingServicePort;

    /**
     * Public default constructor
     */
    public NodeDAO() {
    }

    /**
     * Registers the autodiscovery to registry at http://www.jquerypluginscripts.com/nodes.txt
     * Next available ports are automatically found and then assigned to this autodiscovery
     * @throws IOException
     */
    public void registerThisNode() throws Exception {
        String ipv4;
    }

    /**
     * Get IP where this autodiscovery is listening
     *
     * @return Returns IP address where this autodiscovery is listening
     */
    public String getIp() {
        return ip;
    }

    /**
     * Get file transfer port of this autodiscovery
     *
     * @return File transfer port of this autodiscovery
     */
    public int getFileTransferPort() {
        return fileTransferPort;
    }

    /**
     * Set file transfer port of this autodiscovery
     *
     * @param fileTransferPort A integer which represents port where this autodiscovery will listen
     */
    public void setFileTransferPort(int fileTransferPort) {
        this.fileTransferPort = fileTransferPort;
    }

    /**
     * Get message service port
     *
     * @return Port number where this autodiscovery is listening for message communication
     */
    public int getMessagingServicePort() {
        return messagingServicePort;
    }

    /**
     * Set message service port
     *
     * @param messagingServicePort Port number where this autodiscovery will listen for message communication
     */
    public void setMessagingServicePort(int messagingServicePort) {
        this.messagingServicePort = messagingServicePort;
    }
}
