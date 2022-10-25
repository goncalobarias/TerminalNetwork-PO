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
    protected String getStatusType() {
        return "BUSY";
    }

    @Override
    protected void setOnIdle() {
        // do nothing
    }

    @Override
    protected void setOnSilent() {
        // do nothing
    }

    @Override
    protected void turnOff() {
        // do nothing
    }

}
