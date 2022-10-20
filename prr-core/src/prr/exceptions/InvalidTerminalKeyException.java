package prr.exceptions;

import java.io.Serial;

/**
 * Launched when registering an unknown terminal key.
 */
public class InvalidTerminalKeyException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210140738L;

    /** The terminal's key. */
    private final String _key;

    /** @param key */
    public InvalidTerminalKeyException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
