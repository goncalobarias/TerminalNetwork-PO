package prr.communications;

import prr.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

    public VoiceCommunication(double duration, int id, 
      Terminal terminalReceiver, Terminal terminalSender) {
        super(duration, id, terminalReceiver, terminalSender);
    }

    @Override
    public String getCommunicationType() {
        return "VOICE";
    }

}
