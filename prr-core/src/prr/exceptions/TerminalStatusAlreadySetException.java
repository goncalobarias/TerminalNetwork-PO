package prr.exceptions;

import java.io.Serial;

/**
 * Given terminal status is being updated to a status the terminal already has.
 */
public class TerminalStatusAlreadySetException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210241125L;

    /** The terminal's status. */
    private final String _terminalStatus;

    /** @param notificationState */
    public TerminalStatusAlreadySetException(String terminalStatus) {
        _terminalStatus = terminalStatus;
    }

    /** @return the terminal status */
    public String getTerminalStatus() {
        return _terminalStatus;
    }

}
