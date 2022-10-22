package prr.exceptions;

import java.io.Serial;

/**
 * Given client already has their notifications toggled on or off.
 */
public class NotificationsAlreadyToggledException extends Exception {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202210140735L;

    /** The client's notification state. */
    private final boolean _notificationState;

    /** @param notificationState */
    public NotificationsAlreadyToggledException(boolean notificationState) {
        _notificationState = notificationState;
    }

    /** @return the notification state */
    public boolean getNotificationState() {
        return _notificationState;
    }

}
