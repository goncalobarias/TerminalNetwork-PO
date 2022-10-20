package prr.util;

import prr.clients.Client;
import prr.terminals.Terminal;

import java.io.Serializable;

public abstract class Visitor<T> implements Serializable {

    public abstract T visit(Client client);

    public abstract T visit(Terminal terminal);

}
