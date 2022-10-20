package prr.terminals;

import java.io.Serial;

public class TerminalSilentStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192358L;

    public TerminalSilentStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStatusType() {
        return "SILENCE";
    }

}
