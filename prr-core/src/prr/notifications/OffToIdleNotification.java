package prr.notifications;

import java.io.Serial;

import prr.terminals.Terminal;

public class OffToIdleNotification extends Notification {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210152342L;

    public OffToIdleNotification(Terminal notifyingTerminal) {
        super(notifyingTerminal);
    }

    @Override
    public String getNotificationType() {
        return "O2I";
    }

}
