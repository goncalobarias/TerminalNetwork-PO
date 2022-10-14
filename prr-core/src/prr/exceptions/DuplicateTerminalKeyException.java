package prr.exceptions;

/** 
 * Given terminal id has already been used. 
 */
public class DuplicateTerminalKeyException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202210140736L;

    /** The terminal's key. */
    private final int _key;

    /** @param key */
    public DuplicateTerminalKeyException(int key) {
        _key = key;
    }

    /** @return the key */
    public int getKey() {
        return _key;
    }

}
