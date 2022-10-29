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
        terminalSender.addCommunication(this);
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

    public abstract double getUnits();

    public double getPrice() {
        return _price;
    }

    public void setOngoing(boolean isOngoing) {
        _isOngoing = isOngoing;
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

    public String accept(CommunicationVisitor visitor) {
        return visitor.visit(this);
    }

}
