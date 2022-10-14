package prr.exceptions;

/** 
 * Launched when an entry inside a file contains illegal information in its 
 * fields.
 */
public class IllegalEntryException extends Exception {

    /** Class serial number. */
    private static final long serialVersionUID = 202210140737L;

    /** The illegal fields. */
    private String[] _fields;

    /** @param fields */
    public IllegalEntryException(String[] fields) {
        _fields = fields;
    }

    /**
     * @param fields
     * @param cause
     */
    public IllegalEntryException(String[] fields, Exception cause) {
        super(cause);
        _fields = fields;
    }

    /** @return the illegal fields of an entry */
    public String[] getIllegalFields() {
        return _fields;
    }

}
