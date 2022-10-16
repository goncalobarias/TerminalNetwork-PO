package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {

    private double _duration;

    public InteractiveCommunication(double duration, int id, 
      Terminal terminalReceiver, Terminal terminalSender) {
        super(id, terminalReceiver, terminalSender);
        _duration = duration;
    }

    public abstract String getCommunicationType();

}
