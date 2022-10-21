package prr.util;

import prr.terminals.Terminal;

public interface TerminalVisitor<T> {

    T visit(Terminal terminal);

}
