package prr.communications;

import java.io.Serial;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192344L;

    private int _duration;

    public InteractiveCommunication(int id, Terminal terminalReceiver,
      Terminal terminalSender) {
        super(id, terminalReceiver, terminalSender, true);
        _duration = 0;
        terminalSender.setOnBusy();
        terminalSender.setOngoingCommunication(this);
    }

    public abstract String getCommunicationType();

    @Override
    public int getUnits() {
        return _duration;
    }

    public double stopCommunication() {
        getTerminalSender().unBusy();
        setProgress(false);
        return computePrice();
    }

}
