package prr.exceptions;

/**
 * Exception for entries with an unrecognized length.
 */
public class UnknownEntryLengthException extends Exception {

	/** Class serial number. */
	private static final long serialVersionUID = 202210161157L;

	/** Unrecognized entry length. */
	private int _entryLength;

	/**
	 * @param entryLength
	 */
	public UnknownEntryLengthException(int entryLength) {
		_entryLength = entryLength;
	}

	/**
	 * @param entryLength
	 * @param cause
	 */
	public UnknownEntryLengthException(int entryLength, Exception cause) {
		super(cause);
		_entryLength = entryLength;
	}

	/**
	 * @return the bad entry length.
	 */
	public int getEntryLength() {
		return _entryLength;
	}

}
