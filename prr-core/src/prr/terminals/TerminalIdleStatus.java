package prr.terminals;

import java.io.Serial;

public class TerminalIdleStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192356L;

    public TerminalIdleStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStatusType() {
        return "IDLE";
    }

}
