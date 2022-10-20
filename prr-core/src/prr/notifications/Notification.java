package prr.notifications;

import java.io.Serializable;
import java.io.Serial;

import prr.terminals.Terminal;

public abstract class Notification implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192311L;

    private Terminal _notifyingTerminal;

    public Notification(Terminal notifyingTerminal) {
        _notifyingTerminal = notifyingTerminal;
    }

    public Terminal getNotifyingTerminal() {
        return _notifyingTerminal;
    }

    public abstract String getNotificationType();

}
