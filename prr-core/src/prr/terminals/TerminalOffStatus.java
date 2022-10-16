package prr.terminals;

public class TerminalOffStatus extends Terminal.Status {

    public TerminalOffStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStatusType() {
        return "OFF";
    }

}
