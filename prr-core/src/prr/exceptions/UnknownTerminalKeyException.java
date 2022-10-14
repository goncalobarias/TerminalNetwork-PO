package prr.exceptions;

/**
 * Launched when a unknown terminal key is used.
 */
public class UnknownTerminalKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202210140740L;

    /** The terminal's key. */
    private final int _key;

    /** @param key */
    public UnknownTerminalKeyException(int key) {
        _key = key;
    }

    /** @return the key */
    public int getKey() {
        return _key;
    }

}
