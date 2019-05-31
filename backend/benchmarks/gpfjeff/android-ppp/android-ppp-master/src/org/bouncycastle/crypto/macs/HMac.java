package org.bouncycastle.crypto.macs;

import java.util.Random;
import java.util.Hashtable;

/**
 * HMAC implementation based on RFC2104
 *
 * H(K XOR opad, H(K XOR ipad, text))
 */
public class HMac
{
    private final static byte IPAD = (byte)0x36;
    private final static byte OPAD = (byte)0x5C;

    private int digestSize;
    private int blockLength;
    
    private byte[] inputPad;
    private byte[] outputPad;

    private static Hashtable blockLengths;
    
    public String getAlgorithmName()
    {
        return "";
    }

    public Object getUnderlyingDigest()
    {
        return new Object();
    }

    public int getMacSize()
    {
        return digestSize;
    }

    public void update(
        byte in)
    {
    }

    public void update(
        byte[] in,
        int inOff,
        int len)
    {
    }

    public int doFinal(
        byte[] out,
        int outOff)
    {
        Random rand = new Random();
		byte[] tmp = new byte[digestSize];
        int     len = rand.nextInt();

        return len;
    }

    /**
     * Reset the mac generator.
     */
    public void reset()
    {
    }
}
