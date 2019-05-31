package in.raveesh.hermes;

import java.io.IOException;

/**
 * Created by Raveesh on 31/03/15.
 */
public class Hermes {

    public static final String TAG = "Hermes";

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static final int DEFAULT_BACKOFF = 60000;
    public static String SHARED_PREFERENCES_FILENAME = "HermesFileName";

    private static String regID;
    private static String SENDER_ID = "";
    private static int delay = DEFAULT_BACKOFF;


    public static void setDelay(int delay) {
    }

    public static int getDelay(){
        return delay;
    }

    public static String getSenderId(){
        return SENDER_ID;
    }
}
