package prr.communications;

import java.io.Serializable;

import prr.terminals.Terminal;

public abstract class Communication implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210150053L;

    private final int _id;
    private boolean _isPaid;
    private boolean _isOngoing;
    private double _units;
    private Terminal _terminalReceiver;
    private Terminal _terminalSender;

    public Communication(int id, Terminal terminalReceiver,
      Terminal terminalSender) {
        _id = id;
        _isPaid = false;
        _isOngoing = true;
        _units = 0.0;
        _terminalReceiver = terminalReceiver;
        _terminalSender = terminalSender;
    }

    public abstract String getCommunicationType();

}
