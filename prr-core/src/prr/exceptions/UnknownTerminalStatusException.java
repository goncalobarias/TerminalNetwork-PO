package prr.exceptions;

/**
 * Exception for terminal entries with unknown status type.
 */
public class UnknownTerminalStatusException extends Exception {

	/** Class serial number. */
	private static final long serialVersionUID = 202210181717L;

	/** Unrecognized terminal status type. */
	private String _statusType;

	/**
	 * @param statusType
	 */
	public UnknownTerminalStatusException(String statusType) {
		_statusType = statusType;
	}

	/**
	 * @param statusType
	 * @param cause
	 */
	public UnknownTerminalStatusException(String statusType, Exception cause) {
		super(cause);
		_statusType = statusType;
	}

	/**
	 * @return the bad terminal status type.
	 */
	public String getTerminalStatus() {
		return _statusType;
	}

}
