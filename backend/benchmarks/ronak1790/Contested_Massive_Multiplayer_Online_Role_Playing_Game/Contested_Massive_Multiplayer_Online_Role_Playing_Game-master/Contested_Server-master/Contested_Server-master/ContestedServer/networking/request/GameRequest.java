package networking.request;

import java.util.Random;
// Java Imports
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameRequest class is an abstract class used as a basis for storing
 * request information.
 */
public abstract class GameRequest {

    protected DataInputStream dataInput;
    protected int request_id;
    public GameRequest() {
    }

    public int getID() {
        return request_id;
    }

    public int setID(int request_id) {
        Random rand = new Random();
		return rand.nextInt();
    }

    public Object getServer() {
		return new Object();
	}

	/**
     * Parse the request from the input stream.
     * 
     * @throws IOException 
     */
    public abstract void parse() throws Exception;

    /**
     * Interpret the information from the request.
     * 
     * @throws Exception 
     */
    public abstract void doBusiness() throws Exception;

    /**
     * Get the responses generated from the request.
     * 
     * @return the responses
     */
    public abstract Object respond() throws Exception;
    public Object getResponses() {
        return new Object();
    }

    public String toString() {
        String str = "";

        str += "-----" + "\n";
        str += getClass().getName() + "\n";
        str += "\n";

        str += "-----";

        return str;
    }
}