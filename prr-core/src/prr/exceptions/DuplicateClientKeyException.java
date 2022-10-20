package prr.exceptions;

import java.io.Serial;

/**
 * Given client id has already been used.
 */
public class DuplicateClientKeyException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210140735L;

    /** The client's key. */
    private final String _key;

    /** @param key */
    public DuplicateClientKeyException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

}
