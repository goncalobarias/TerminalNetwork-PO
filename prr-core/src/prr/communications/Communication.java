package prr.communications;

import java.io.Serializable;
import java.io.Serial;

import prr.util.CommunicationVisitor;
import prr.terminals.Terminal;

public abstract class Communication implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210150053L;

    private final int _id;
    private boolean _isPaid;
    private boolean _isOngoing;
    private double _price;
    private Terminal _terminalReceiver;
    private Terminal _terminalSender;

    public Communication(int id, Terminal terminalReceiver,
      Terminal terminalSender) {
        _id = id;
        _isPaid = false;
        _isOngoing = true;
        _price = 0.0;
        _terminalReceiver = terminalReceiver;
        _terminalSender = terminalSender;
    }

    public abstract String getCommunicationType();

    public int getId() {
        return _id;
    }

    public String getSenderId() {
        return _terminalSender.getTerminalId();
    }

    public String getReceiverId() {
        return _terminalReceiver.getTerminalId();
    }

    public abstract double getUnits();

    public double getPrice() {
        return _price;
    }

    public boolean isOngoing() {
        return _isOngoing;
    }

    public boolean isPaid() {
        return _isPaid;
    }

    public void computePrice() {
        _price = 0.0; // TODO: update the method to actually compute the costs accordingly
    }

    public <T> T accept(CommunicationVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
