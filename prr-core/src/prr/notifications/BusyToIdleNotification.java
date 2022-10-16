package prr.notifications;

import prr.terminals.Terminal;

public class BusyToIdleNotification extends Notification {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210152341L;

    private Terminal _notifyingTerminal;

    public BusyToIdleNotification(Terminal notifyingTerminal) {
        _notifyingTerminal = notifyingTerminal;
    }

    public Terminal getNotifyingTerminal() {
        return _notifyingTerminal;
    }

    public String getNotificationType() {
        return "B2I";
    }

}
