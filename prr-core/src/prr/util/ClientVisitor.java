package prr.util;

import prr.clients.Client;

public interface ClientVisitor {

    String visit(Client client);

}
