package prr.exceptions;

import java.io.Serial;

/**
 * Exception for terminal entries with unknown or unwanted status type.
 */
public class IllegalTerminalStatusException extends Exception {

	/** Class serial number. */
    @Serial
	private static final long serialVersionUID = 202210181717L;

	/** Illegal terminal status type. */
	private String _statusType;

	/**
	 * @param statusType
	 */
	public IllegalTerminalStatusException(String statusType) {
		_statusType = statusType;
	}

	/**
	 * @param statusType
	 * @param cause
	 */
	public IllegalTerminalStatusException(String statusType, Exception cause) {
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
