package prr.exceptions;

/**
 * Exception for entries of an unexpected type.
 */
public class UnknownEntryTypeException extends Exception {

	/** Class serial number. */
	private static final long serialVersionUID = 202210161154L;

	/** Unrecognized entry type. */
	private String _entryType;

	/**
	 * @param entryType
	 */
	public UnknownEntryTypeException(String entryType) {
		_entryType = entryType;
	}

	/**
	 * @param entryType
	 * @param cause
	 */
	public UnknownEntryTypeException(String entryType, Exception cause) {
		super(cause);
		_entryType = entryType;
	}

	/**
	 * @return the bad entry type.
	 */
	public String getEntryType() {
		return _entryType;
	}

}
