package prr.util;

import prr.terminals.Terminal;

public interface TerminalVisitor {

    String visit(Terminal terminal);

}
