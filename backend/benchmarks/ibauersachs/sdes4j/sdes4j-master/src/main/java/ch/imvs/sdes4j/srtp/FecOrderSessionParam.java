/*
 * SDES4J
 * Java implementation of SDES (Security Descriptions for Media Streams,
 * RFC 4568).
 * 
 * Copyright (C) 2011 FHNW
 *   University of Applied Sciences Northwestern Switzerland (FHNW)
 *   School of Engineering
 *   Institute of Mobile and Distributed Systems (IMVS)
 *   http://sdes4j.imvs.ch
 * 
 * Distributable under LGPL license, see terms of license at gnu.org.
 */
package ch.imvs.sdes4j.srtp;

/**
 * FEC_ORDER signals the use of forward error correction for the RTP packets
 * [RFC2733]. The forward error correction values for "order" are FEC_SRTP or
 * SRTP_FEC. FEC_SRTP signals that FEC is applied before SRTP processing by the
 * sender of the SRTP media and after SRTP processing by the receiver of the
 * SRTP media; FEC_SRTP is the default. SRTP_FEC is the reverse processing.
 * 
 * @author Ingo Bauersachs
 */
public class FecOrderSessionParam {
    /**
     * FEC_SRTP signals that FEC is applied before SRTP processing by the sender
     * of the SRTP media and after SRTP processing by the receiver of the SRTP
     * media; FEC_SRTP is the default.
     */
    public final static int FEC_SRTP = 1;

    /**
     * SRTP_FEC signals that SRTP processing is performed before applying FEC by
     * the sender of the SRTP media and after FEC processing by the receiver of
     * the SRTP media.
     */
    public final static int SRTP_FEC = 2;

    private int mode;

    /**
     * Creates a new instance of this class from a known order value.
     * 
     * @param mode {@value #FEC_SRTP} or {@value #SRTP_FEC}
     */
    public FecOrderSessionParam(int mode) {
        if (mode != FEC_SRTP && mode != SRTP_FEC)
            throw new IllegalArgumentException("mode must be one of FEC_SRTP or SRTP_FEC");
        this.mode = mode;
    }

    /**
     * Gets the forward error correction mode.
     * 
     * @return {@value #SRTP_FEC} or {@value #FEC_SRTP}
     */
    public int getMode() {
        return mode;
    }

    public String encode() {
        throw new IllegalArgumentException("invalid mode");
    }

}
