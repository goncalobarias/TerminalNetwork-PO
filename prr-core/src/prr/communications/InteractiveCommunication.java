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
    }

    @Override
    public int getUnits() {
        return _duration;
    }

    public void setUnits(int duration) {
        _duration = duration;
    }

    @Override
    public void estabilishCommunication() {
        getTerminalSender().setOnBusy();
        getTerminalSender().addCommunication(this);
        getTerminalSender().setOngoingCommunication(this);
        getTerminalReceiver().setOnBusy();
        getTerminalReceiver().addCommunication(this);
        getTerminalReceiver().setOngoingCommunication(this);
    }

    public void finishInteractiveCommunication(int duration) {
        setProgress(false);
        getTerminalReceiver().setOngoingCommunication(null);
        getTerminalReceiver().unBusy();
        getTerminalSender().setOngoingCommunication(null);
        getTerminalSender().unBusy();
        setUnits(duration);
    }

    public abstract double finishCommunication(int duration);

}
