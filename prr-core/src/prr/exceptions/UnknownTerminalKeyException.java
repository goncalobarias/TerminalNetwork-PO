package prr.exceptions;

/**
 * Launched when a unknown terminal key is used.
 */
public class UnknownTerminalKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202210140740L;

    /** The terminal's key. */
    private final String _key;

    /** @param key */
    public UnknownTerminalKeyException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
