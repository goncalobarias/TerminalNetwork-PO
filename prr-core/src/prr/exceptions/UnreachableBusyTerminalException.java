package prr.exceptions;

import java.io.Serial;

/**
 * Given terminal is unreachable because it's busy (i.e., it has  an active
 * interactive communication).
 */
public class UnreachableBusyTerminalException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210291844L;

}
