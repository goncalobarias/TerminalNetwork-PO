package prr.terminals;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.Serializable;
import java.io.Serial;

import prr.util.TerminalVisitor;
import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.TerminalStatusAlreadySetException;
import prr.exceptions.NoOngoingCommunicationException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Comparable<Terminal>, Serializable {

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
        _communications = new TreeMap<Integer, Communication>();
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

    public Client getOwner() {
        return _owner;
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

    public double getBalance() {
        return getPayments() - getDebts();
    }

    public String getFriendsIds() {
        return _terminalFriends.keySet()
                                .stream()
                                .collect(Collectors.joining(","));
    }

    public Stream<Communication> getMadeCommunications() {
        return _communications.values().stream()
                .filter(comm -> this.equals(comm.getTerminalSender()));
    }

    public Stream<Communication> getReceivedCommunications() {
        return _communications.values().stream()
                .filter(comm -> this.equals(comm.getTerminalReceiver()));
    }

    public Communication getOngoingCommunication()
      throws NoOngoingCommunicationException {
        if (_ongoingCommunication == null) {
            throw new NoOngoingCommunicationException();
        }
        return _ongoingCommunication;
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

    public boolean isAlreadyFriend(Terminal terminal) {
        return _terminalFriends.containsKey(terminal.getTerminalId());
    }

    public void addFriend(Terminal terminalFriend) {
        if (!this.equals(terminalFriend) &&
          !this.isAlreadyFriend(terminalFriend)) {
            _terminalFriends.put(terminalFriend.getTerminalId(),
                                terminalFriend);
        }
    }

    public void removeFriend(Terminal terminalFriend) {
        if (this.isAlreadyFriend(terminalFriend)) {
            _terminalFriends.remove(terminalFriend.getTerminalId());
        }
    }

    public void setOnIdle() throws TerminalStatusAlreadySetException {
        _status.setOnIdle();
    }

    public void setOnSilent() throws TerminalStatusAlreadySetException {
        _status.setOnSilent();
    }

    public void turnOff() throws TerminalStatusAlreadySetException {
        _status.turnOff();
    }

    @Override
    public int compareTo(Terminal terminal) {
        return getTerminalId().compareTo(terminal.getTerminalId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Terminal) {
            Terminal terminal = (Terminal) o;
            return compareTo(terminal) == 0;
        }
        return false;
    }

    public <T> T accept(TerminalVisitor<T> visitor) {
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

        protected abstract void setOnIdle() throws TerminalStatusAlreadySetException;

        protected abstract void setOnSilent() throws TerminalStatusAlreadySetException;

        protected abstract void turnOff() throws TerminalStatusAlreadySetException;

    }

}
