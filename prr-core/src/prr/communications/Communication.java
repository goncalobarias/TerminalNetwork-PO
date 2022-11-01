package prr.communications;

import java.io.Serializable;
import java.io.Serial;

import prr.util.Visitor;
import prr.util.Visitable;
import prr.terminals.Terminal;

public abstract class Communication implements Serializable, Visitable {

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
        _price = 0D;
        _isPaid = false;
        estabilishCommunication();
    }

    public abstract String getCommunicationType();

    public int getId() {
        return _id;
    }

    public String getReceiverId() {
        return _terminalReceiver.getTerminalId();
    }

    public Terminal getTerminalReceiver() {
        return _terminalReceiver;
    }

    public String getSenderId() {
        return _terminalSender.getTerminalId();
    }

    public Terminal getTerminalSender() {
        return _terminalSender;
    }

    public boolean isOngoing() {
        return _isOngoing;
    }

    protected void setProgress(boolean isOngoing) {
        _isOngoing = isOngoing;
    }

    public abstract int getUnits();

    public double getPrice() {
        return _price;
    }

    protected void setPrice(double price) {
        _price = price;
    }

    public boolean isPaid() {
        return _isPaid;
    }

    public double pay() {
        _isPaid = true;
        return _price;
    }

    protected abstract void estabilishCommunication();

    protected abstract double computePrice();

    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
