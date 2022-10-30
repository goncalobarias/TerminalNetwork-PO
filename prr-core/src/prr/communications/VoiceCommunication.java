package prr.communications;

import java.io.Serial;

import prr.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192347L;

    public VoiceCommunication(int id, Terminal terminalReceiver,
      Terminal terminalSender) {
        super(id, terminalReceiver, terminalSender);
    }

    @Override
    public String getCommunicationType() {
        return "VOICE";
    }

    @Override
    public double stopCommunication() {
        setProgress(false);
        getTerminalReceiver().setOngoingCommunication(null);
        getTerminalReceiver().unBusy();
        getTerminalSender().setOngoingCommunication(null);
        getTerminalSender().unBusy();
        return computePrice();
    }

}
