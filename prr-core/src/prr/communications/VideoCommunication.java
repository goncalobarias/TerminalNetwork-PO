package prr.communications;

import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {

    public VideoCommunication(double duration, int id, 
      Terminal terminalReceiver, Terminal terminalSender) {
        super(duration, id, terminalReceiver, terminalSender);
    }

    @Override
    public String getCommunicationType() {
        return "VIDEO";
    }

}
