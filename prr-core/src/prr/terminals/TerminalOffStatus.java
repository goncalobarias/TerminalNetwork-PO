package prr.terminals;

import java.io.Serial;

public class TerminalOffStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192357L;

    public TerminalOffStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStatusType() {
        return "OFF";
    }

}
