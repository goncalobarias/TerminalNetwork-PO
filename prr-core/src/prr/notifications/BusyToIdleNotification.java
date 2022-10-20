package prr.notifications;

import java.io.Serial;

import prr.terminals.Terminal;

public class BusyToIdleNotification extends Notification {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210152341L;

    public BusyToIdleNotification(Terminal notifyingTerminal) {
        super(notifyingTerminal);
    }

    @Override
    public String getNotificationType() {
        return "B2I";
    }

}
