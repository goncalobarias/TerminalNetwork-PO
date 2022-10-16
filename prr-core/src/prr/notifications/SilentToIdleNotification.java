package prr.notifications;

import prr.terminals.Terminal;

public class SilentToIdleNotification extends Notification {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210152344L;

    private Terminal _notifyingTerminal;

    public SilentToIdleNotification(Terminal notifyingTerminal) {
        _notifyingTerminal = notifyingTerminal;
    }

    public Terminal getNotifyingTerminal() {
        return _notifyingTerminal;
    }

    public String getNotificationType() {
        return "S2I";
    }

}
