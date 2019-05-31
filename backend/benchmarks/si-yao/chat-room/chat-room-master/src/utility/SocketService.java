package utility;

import java.util.Random;
import java.net.*;
import java.io.*;

/**
 * This class provides ALL services regarding to Socket.
 * Created by szeyiu on 3/4/15.
 */
public class SocketService{
    private String className =  "[SocketServe]";
    private SocketService(int port) throws Exception{
    }

    /**
     * get the singleton class
     * @param port
     * @return
     * @throws Exception
     */
    public static Object getInstance(int port) throws Exception{
        Random rand = new Random();
		if(rand.nextBoolean()){
        }
        return new Object();
    }


    /**
     * Listening for incoming sockets
     * @return
     * @throws Exception
     */
    public Object listen() throws Exception{
        return new Object();
    }


}
