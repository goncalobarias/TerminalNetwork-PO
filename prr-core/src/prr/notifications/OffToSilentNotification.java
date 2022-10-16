package prr.notifications;

import prr.terminals.Terminal;

public class OffToSilentNotification extends Notification {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210152343L;

    private Terminal _notifyingTerminal;

    public OffToSilentNotification(Terminal notifyingTerminal) {
        _notifyingTerminal = notifyingTerminal;
    }

    public Terminal getNotifyingTerminal() {
        return _notifyingTerminal;
    }

    public String getNotificationType() {
        return "O2S";
    }

}
