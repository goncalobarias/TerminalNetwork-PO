package prr.exceptions;

/** 
 * Launched when registering an unknown terminal key.
 */
public class InvalidTerminalKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202210140738L;

    /** The terminal's key. */
    private final int _key;

    /** @param key */
    public InvalidTerminalKeyException(int key) {
        _key = key;
    }

    /** @return the key */
    public int getKey() {
        return _key;
    }

}
