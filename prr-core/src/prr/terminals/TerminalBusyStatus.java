package prr.terminals;

import java.io.Serial;

public class TerminalBusyStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192354L;

    public TerminalBusyStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStatusType() {
        return "BUSY";
    }

}
