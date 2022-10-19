package prr.terminals;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.io.Serializable;

import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.UnknownTerminalStatusException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private final String _id;
    private Client _owner;
    private Status _status;
    private Communication _ongoingCommunication;
    private double _payments;
    private double _debts;
    private List<Communication> _communications;
    private List<Terminal> _terminalFriends;
    private Queue<Client> _clientsToNotify;

    public Terminal(String id, Client owner) {
        _id = id;
        _owner = owner;
        _status = new TerminalIdleStatus(this);
        _payments = 0.0;
        _debts = 0.0;
        _communications = new ArrayList<Communication>(); // FIXME figure out the right data structure
        _terminalFriends = new ArrayList<Terminal>(); // FIXME figure out the right data structure
        _clientsToNotify = new LinkedList<Client>(); // FIXME figure out the right data structure
        _owner.addTerminal(this); // FIXME don't know if this is good practice
    }

    public abstract String getTerminalType();

    public String getTerminalId() {
        return _id;
    }

    public String getClientId() {
        return _owner.getId();
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
                                .map(t -> t.getTerminalId())
                                .collect(Collectors.joining(","));
    }

    public void setStatus(String status) throws UnknownTerminalStatusException {
        _status.setStatus(status);
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

    public void addFriend(Terminal terminalFriend) {
        _terminalFriends.add(terminalFriend);
    }

    public void removeFriend(Terminal terminalFriend) {
        _terminalFriends.remove(terminalFriend);
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

        public Terminal getTerminal() {
            return Terminal.this;
        }

        public abstract String getStatusType();

        protected void setStatus(String status) throws UnknownTerminalStatusException {
            Terminal.this._status = switch(status) {
                case "ON" -> new TerminalIdleStatus(getTerminal());
                case "OFF" -> new TerminalOffStatus(getTerminal());
                case "SILENCE" -> new TerminalSilentStatus(getTerminal());
                default -> throw new UnknownTerminalStatusException(status);
            };
        }

        protected void updateStatus(Status status) {
            Terminal.this._status = status;
        }

    }

}
