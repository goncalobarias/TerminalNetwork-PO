package prr.exceptions;

import java.io.Serial;

/**
 * Launched when a unknown client key is used.
 */
public class UnknownClientKeyException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210140739L;

    /** The client's key. */
    private final String _key;

    /** @param key */
    public UnknownClientKeyException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
