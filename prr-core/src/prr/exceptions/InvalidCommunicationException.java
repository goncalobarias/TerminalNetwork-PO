package prr.exceptions;

import java.io.Serial;

/**
 * Given terminal doesn't have an ongoing communication. Is trying to pay for
 * a communication that is not finished, or has not been initiated by itself,
 * or has already been paid for. Is trying to start an interactive communication
 * with itself.
 */
public class InvalidCommunicationException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210301025L;

}
