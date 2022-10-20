package prr.communications;

import java.io.Serial;

import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192345L;

    public VideoCommunication(double duration, int id,
      Terminal terminalReceiver, Terminal terminalSender) {
        super(duration, id, terminalReceiver, terminalSender);
    }

    @Override
    public String getCommunicationType() {
        return "VIDEO";
    }

}
