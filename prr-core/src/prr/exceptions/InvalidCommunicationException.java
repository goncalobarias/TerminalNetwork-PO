package prr.exceptions;

import java.io.Serial;

/**
 * Given terminal doesn't have an ongoing communication or is trying to pay for
 * a communication that is not finished, or has not been initiated by itself,
 * or has already been paid for. Given terminal doesn't have an ongoing
 * communication.
 */
public class InvalidCommunicationException extends Exception { // TODO: check if I can compact exceptions into one to save up space

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210301025L;

}
