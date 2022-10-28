package prr.exceptions;

import java.io.Serial;

/**
 * Given terminal is trying to add itself as a friend, or is trying to add a
 * terminal with which it is already friends, or is trying to remove a terminal
 * with which it is not friends.
 */
public class InvalidFriendException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210281118L;

}
