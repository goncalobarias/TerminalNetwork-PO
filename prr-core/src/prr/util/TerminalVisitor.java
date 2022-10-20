package prr.util;

import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;

public interface TerminalVisitor<T> {

    T visit(BasicTerminal terminal);

    T visit(FancyTerminal terminal);

}
