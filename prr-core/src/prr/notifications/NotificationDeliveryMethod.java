package prr.notifications;

import java.io.Serializable;
import java.io.Serial;

public abstract class NotificationDeliveryMethod implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210150055L;

    public abstract void deliver(Notification notification);

}
