package prr.util;

import prr.clients.Client;
import prr.communications.Communication;
import prr.notifications.Notification;
import prr.terminals.Terminal;

public interface Visitor {

    String visit(Client client);

    String visit(Communication communication);

    String visit(Notification notification);

    String visit(Terminal terminal);

}
