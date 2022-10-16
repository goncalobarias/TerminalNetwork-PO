package prr.terminals;

public class TerminalBusyStatus extends Terminal.Status {

    public TerminalBusyStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStatusType() {
        return "BUSY";
    }

}
