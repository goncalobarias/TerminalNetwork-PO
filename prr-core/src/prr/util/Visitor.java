package prr.util;

import java.io.Serializable;

import prr.clients.Client;
import prr.communications.Communication;
import prr.notifications.Notification;
import prr.terminals.Terminal;

public abstract class Visitor implements Serializable {

    public abstract String visit(Client client);

    public abstract String visit(Communication communication);

    public abstract String visit(Notification notification);

    public abstract String visit(Terminal terminal);

}
