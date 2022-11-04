package prr.notifications;

import java.io.Serializable;
import java.io.Serial;

import prr.util.Visitor;
import prr.util.Visitable;
import prr.terminals.Terminal;

public abstract class Notification implements Serializable, Visitable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192311L;

    private Terminal _notifyingTerminal;

    public Notification(Terminal notifyingTerminal) {
        _notifyingTerminal = notifyingTerminal;
    }

    public abstract String getNotificationType();

    public String getNotifyingTerminalId() {
        return _notifyingTerminal.getTerminalId();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Notification) {
            Notification notification = (Notification) o;
            return getNotificationType().equals(
                notification.getNotificationType()) &&
                getNotifyingTerminalId().equals(
                notification.getNotifyingTerminalId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        String code = getNotificationType() + getNotifyingTerminalId();
        return code.hashCode();
    }

    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
