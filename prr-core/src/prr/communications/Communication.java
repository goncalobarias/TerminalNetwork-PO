package prr.communications;

import java.io.Serializable;
import java.io.Serial;
import prr.util.CommunicationVisitor;
import prr.terminals.Terminal;

public abstract class Communication implements Serializable { // TODO: do visitors for the communication types (check test discord server for names)

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210150053L;

    private final int _id;
    private Terminal _terminalReceiver;
    private Terminal _terminalSender;
    private boolean _isOngoing;
    private double _price;
    private boolean _isPaid;

    public Communication(int id, Terminal terminalReceiver,
      Terminal terminalSender, boolean isOngoing) {
        _id = id;
        _terminalReceiver = terminalReceiver;
        _terminalSender = terminalSender;
        _isOngoing = isOngoing;
        _price = 0.0;
        _isPaid = false;
        estabilishCommunication();
    }

    public abstract String getCommunicationType();

    public int getId() {
        return _id;
    }

    public String getSenderId() {
        return _terminalSender.getTerminalId();
    }

    public Terminal getTerminalSender() {
        return _terminalSender;
    }

    public String getReceiverId() {
        return _terminalReceiver.getTerminalId();
    }

    public Terminal getTerminalReceiver() {
        return _terminalReceiver;
    }

    public double getPrice() {
        return _price;
    }

    public abstract int getUnits();

    public void setProgress(boolean isOngoing) {
        _isOngoing = isOngoing;
    }

    public void setPrice(double price) {
        _price = price;
    }

    public boolean isOngoing() {
        return _isOngoing;
    }

    public boolean isPaid() {
        return _isPaid;
    }

    public abstract void estabilishCommunication();

    public double pay() {
        _isPaid = true;
        return _price;
    }

    public String accept(CommunicationVisitor visitor) {
        return visitor.visit(this);
    }

}
