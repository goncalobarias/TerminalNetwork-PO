package prr.notifications;

import java.io.Serial;

import prr.terminals.Terminal;

public class OffToSilentNotification extends Notification {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210152343L;

    public OffToSilentNotification(Terminal notifyingTerminal) {
        super(notifyingTerminal);
    }

    @Override
    public String getNotificationType() {
        return "O2S";
    }

}
