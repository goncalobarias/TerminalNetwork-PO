package prr.exceptions;

import java.io.Serial;

/**
 * Given client already has their notifications toggled on or off.
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
