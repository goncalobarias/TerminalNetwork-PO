package prr.app.visitors;

import prr.util.NotificationVisitor;
import prr.notifications.Notification;

public class RenderNotification implements NotificationVisitor {

    @Override
    public String visit(Notification notification) {
        return notification.getNotificationType() + "|" +
                notification.getNotifyingTerminalId();
    }

}
