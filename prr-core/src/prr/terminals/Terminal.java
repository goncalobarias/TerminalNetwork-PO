package prr.terminals;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.Serializable;

import prr.clients.Client;
import prr.communications.Communication;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private final int _idTerminal;
    private Client _owner;
    private Status _status;
    private Communication _ongoingCommunication;
    private double _payments;
    private double _debts;
    private List<Communication> _communications = new ArrayList<>(); // FIXME figure out the right data structure
    private List<Terminal> _terminalFriends = new ArrayList<>(); // FIXME figure out the right data structure
    private List<Client> _clientsToNotify = new ArrayList<>(); // FIXME figure out the right data structure

    public Terminal(int idTerminal, Client owner, String status) {
        _idTerminal = idTerminal;
        _owner = owner;
        _status = switch (status) {
            case "ON" -> new TerminalIdleStatus(this);
            case "OFF" -> new TerminalOffStatus(this);
            case "SILENCE" -> new TerminalSilentStatus(this);
            default -> new TerminalIdleStatus(this);
        };
        _owner.addTerminal(this);
    }

    public abstract String getTerminalType();

    public int getTerminalId() {
        return _idTerminal;
    }

    public String getClientId() {
        return _owner.getClientId();
    }

    public String getStatusType() {
        return _status.getStatusType();
    }

    public double getPayments() {
        return _payments;
    }

    public double getDebts() {
        return _debts;
    }

    public String getFriendsIds() {
        return _terminalFriends.stream()
                                .map(t -> Integer.toString(t.getTerminalId()))
                                .collect(Collectors.joining(","));
    }

    public boolean hasFriends() {
        return !_terminalFriends.isEmpty();
    }

    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     *          it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        // FIXME add implementation code
        return false;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        // FIXME add implementation code
	    return true;
    }

    public boolean isUnused() {
        return getPayments() == 0 && getDebts() == 0;
    }

    public void setStatus(Status status) {
        _status = status;
    }

    public void addFriend(int terminalFriendId) {
    }

    @Override
    public String toString() {
        return getTerminalType() + "|" + getTerminalId() + "|" + 
                getClientId() + "|" + getStatusType() + "|" + 
                Math.round(getPayments()) + "|" + Math.round(getDebts()) + 
                (hasFriends() ? "|" + getFriendsIds() : "");
    }

    public abstract class Status implements Serializable {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202210150058L;

        public abstract String getStatusType();

    }

}
