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

    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
