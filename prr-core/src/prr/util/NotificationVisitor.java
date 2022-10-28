package prr.util;

import prr.notifications.Notification;

public interface NotificationVisitor {

    String visit(Notification notification);

}
