package prr.communications;

import java.io.Serial;

import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210190315L;

    private String _message;

    public TextCommunication(String message, int id,
      Terminal terminalReceiver, Terminal terminalSender) {
        super(id, terminalReceiver, terminalSender, false);
        _message = message;
        computePrice();
    }

    @Override
    public String getCommunicationType() {
        return "TEXT";
    }

    @Override
    public int getUnits() {
        return _message.length();
    }

}
