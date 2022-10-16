package prr.terminals;

public class TerminalSilentStatus extends Terminal.Status {

    public TerminalSilentStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStatusType() {
        return "SILENCE";
    }

}
