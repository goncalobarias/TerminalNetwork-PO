package prr.notifications;

import java.io.Serial;

import prr.terminals.Terminal;

public class SilentToIdleNotification extends Notification {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210152344L;

    public SilentToIdleNotification(Terminal notifyingTerminal) {
        super(notifyingTerminal);
    }

    @Override
    public String getNotificationType() {
        return "S2I";
    }

}
