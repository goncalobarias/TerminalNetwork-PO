package prr.notifications;

import prr.terminals.Terminal;

public class OffToIdleNotification extends Notification {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210152342L;

    private Terminal _notifyingTerminal;

    public OffToIdleNotification(Terminal notifyingTerminal) {
        _notifyingTerminal = notifyingTerminal;
    }

    public Terminal getNotifyingTerminal() {
        return _notifyingTerminal;
    }

    public String getNotificationType() {
        return "O2I";
    }

}
