package prr.util;

import prr.clients.Client;

public interface ClientVisitor<T> {

    T visit(Client client);

}
