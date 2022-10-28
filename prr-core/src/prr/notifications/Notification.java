package prr.notifications;

import java.io.Serializable;
import java.io.Serial;

import prr.util.NotificationVisitor;
import prr.terminals.Terminal;

public abstract class Notification implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192311L;

    private Terminal _notifyingTerminal;

    public Notification(Terminal notifyingTerminal) {
        _notifyingTerminal = notifyingTerminal;
    }

    public abstract String getNotificationType();

    public String getNotifyingTerminalId() {
        final Terminal notifyingTerminal = getNotifyingTerminal();
        return notifyingTerminal.getTerminalId();
    }

    public Terminal getNotifyingTerminal() {
        return _notifyingTerminal;
    }

    public String accept(NotificationVisitor visitor) {
        return visitor.visit(this);
    }

}
