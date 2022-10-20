package prr.terminals;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.io.Serial;

import prr.util.Visitable;
import prr.util.Visitor;
import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.IllegalTerminalStatusException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable, Visitable /* FIXME maybe addd more interfaces */{

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    private final String _id;
    private Client _owner;
    private Status _status;
    private Communication _ongoingCommunication;
    private double _payments;
    private double _debts;
    private Map<Integer, Communication> _communications;
    private Map<String, Terminal> _terminalFriends;
    private Queue<Client> _clientsToNotify;

    public Terminal(String id, Client owner) {
        _id = id;
        _owner = owner;
        _status = new TerminalIdleStatus(this);
        _ongoingCommunication = null;
        _payments = 0.0;
        _debts = 0.0;
        _communications = new HashMap<Integer, Communication>(); //TODO figure out the data structure for this
        _terminalFriends = new TreeMap<String, Terminal>();
        _clientsToNotify = new LinkedList<Client>();
        _owner.addTerminal(this);
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
        return _terminalFriends.keySet()
                                .stream()
                                .collect(Collectors.joining(","));
    }

    public void setStatus(String status) throws IllegalTerminalStatusException {
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
        return _communications.isEmpty();
    }

    public void addFriend(Terminal terminalFriend) {
        _terminalFriends.put(terminalFriend.getTerminalId(), terminalFriend);
    }

    public void removeFriend(Terminal terminalFriend) {
        _terminalFriends.remove(terminalFriend.getTerminalId());
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public abstract class Status implements Serializable {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210150058L;

        public Terminal getTerminal() {
            return Terminal.this;
        }

        public abstract String getStatusType();

        protected void setStatus(String status)
          throws IllegalTerminalStatusException {
            Terminal.this._status = switch(status) {
                case "ON" -> new TerminalIdleStatus(getTerminal());
                case "OFF" -> new TerminalOffStatus(getTerminal());
                case "SILENCE" -> new TerminalSilentStatus(getTerminal());
                default -> throw new IllegalTerminalStatusException(status);
            };
        }

        protected void updateStatus(Status status) {
            Terminal.this._status = status;
        }

    }

}
