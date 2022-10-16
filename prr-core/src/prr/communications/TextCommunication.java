package prr.communications;

import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    private String _message;

    public TextCommunication(String message, int id, 
      Terminal terminalReceiver, Terminal terminalSender) {
        super(id, terminalReceiver, terminalSender);
        _message = message;
    }

    @Override
    public String getCommunicationType() {
        return "TEXT";
    }

}
