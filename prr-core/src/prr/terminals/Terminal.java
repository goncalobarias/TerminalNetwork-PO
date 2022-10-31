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

import prr.Network;
import prr.util.TerminalVisitor;
import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.InteractiveCommunication;
import prr.communications.VoiceCommunication;
import prr.notifications.Notification;
import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.InvalidCommunicationException;
import prr.exceptions.InvalidFriendException;
import prr.exceptions.NoOngoingCommunicationException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnreachableBusyTerminalException;
import prr.exceptions.UnreachableOffTerminalException;
import prr.exceptions.UnreachableSilentTerminalException;
import prr.exceptions.UnsupportedCommunicationAtOriginException;
import prr.exceptions.UnsupportedCommunicationAtDestinationException;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Comparable<Terminal>, Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    private final String _id;
    private Client _owner;
    private double _payments;
    private double _debts;
    private InteractiveCommunication _ongoingCommunication;
    private Map<Integer, Communication> _communications;
    private Map<String, Terminal> _terminalFriends;
    private Status _status;
    private Queue<Client> _clientsToNotify;

    public Terminal(String id, Client owner) {
        _id = id;
        _owner = owner;
        _payments = 0.0;
        _debts = 0.0;
        _ongoingCommunication = null;
        _communications = new HashMap<Integer, Communication>();
        _terminalFriends = new TreeMap<String, Terminal>();
        _status = new TerminalIdleStatus(this);
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

    public double getPayments() {
        return _payments;
    }

    public double getDebts() {
        return _debts;
    }

    public double getBalance() {
        return getPayments() - getDebts();
    }

    public Communication getOngoingCommunication()
      throws NoOngoingCommunicationException {
        if (_ongoingCommunication == null) {
            throw new NoOngoingCommunicationException();
        }
        return _ongoingCommunication;
    }

    public String getFriendsIds() {
        return _terminalFriends.keySet()
                                .stream()
                                .collect(Collectors.joining(","));
    }

    public String getStatusType() {
        return _status.getStatusType();
    }

    public Terminal.Status getStatus() {
        return _status;
    }

    public void setStatus(String status) throws IllegalTerminalStatusException {
        _status.setStatus(status);
    }

    public void setOnIdle() throws IllegalTerminalStatusException {
        _status.setOnIdle();
    }

    public void setOnSilent() throws IllegalTerminalStatusException {
        _status.setOnSilent();
    }

    public void turnOff() throws IllegalTerminalStatusException {
        _status.turnOff();
    }

    public void setOnBusy() {
        _status.setOnBusy();
    }

    public void unBusy() {
        _status.unBusy();
    }

    public void setOngoingCommunication(
      InteractiveCommunication communication) {
        _ongoingCommunication = communication;
    }

    public boolean hasFriends() {
        return !_terminalFriends.isEmpty();
    }

    public boolean isFriend(Terminal terminal) {
        return _terminalFriends.containsKey(terminal.getTerminalId());
    }

    public boolean isUnused() {
        return _communications.isEmpty();
    }

    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive
     *         communication) and it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        return _ongoingCommunication != null &&
            this.equals(_ongoingCommunication.getTerminalSender());
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
	    return _status.canStartCommunication();
    }

    protected boolean canReceiveTextCommunication() {
        return _status.canReceiveTextCommunication();
    }

    protected boolean canReceiveInteractiveCommunication() {
        return _status.canReceiveInteractiveCommunication();
    }

    public void addFriend(String terminalFriendId, Network context)
      throws UnknownTerminalKeyException, InvalidFriendException {
        Terminal terminalFriend = context.getTerminal(terminalFriendId);
        if (this.equals(terminalFriend) ||
          this.isFriend(terminalFriend)) {
            throw new InvalidFriendException();
        }
        _terminalFriends.put(terminalFriendId, terminalFriend);
        context.changed();
    }

    public void removeFriend(String terminalFriendId, Network context)
      throws UnknownTerminalKeyException, InvalidFriendException {
        Terminal terminalFriend = context.getTerminal(terminalFriendId);
        if (!this.isFriend(terminalFriend)) {
            throw new InvalidFriendException();
        }
        _terminalFriends.remove(terminalFriendId);
        context.changed();
    }

    public void addCommunication(Communication communication) {
        _communications.put(communication.getId(), communication);
    }

    public double endOngoingCommunication(int duration) {
        double communicationPrice = 0.0;
        if (canEndCurrentCommunication()) {
            communicationPrice =
                _ongoingCommunication.stopCommunication(duration);
            getOwner().verifyLevelUpdateConditions();
        }
        return communicationPrice;
    }

    public void sendSMS(String terminalReceiverId, Network context,
      String message) throws UnknownTerminalKeyException,
      UnreachableOffTerminalException {
        if (canStartCommunication()) {
            Terminal receiver = context.getTerminal(terminalReceiverId);
            receiver.receiveSMS(this, context, message);
        }
    }

    private void receiveSMS(Terminal sender, Network context,
      String newMessage) throws UnreachableOffTerminalException {
        if (canReceiveTextCommunication()) {
            int newId = context.getNextCommunicationId();
            TextCommunication communication =
                new TextCommunication(newMessage, newId, this, sender);
            context.registerCommunication(communication);
            sender.getOwner().verifyLevelUpdateConditions();
        } else {
            addToNotify(sender.getOwner());
            throw new UnreachableOffTerminalException();
        }
    }

    public void makeVoiceCall(String terminalReceiverId, Network context)
      throws UnknownTerminalKeyException, UnreachableOffTerminalException,
      UnreachableBusyTerminalException, UnreachableSilentTerminalException,
      InvalidCommunicationException {
        if (terminalReceiverId.equals(getTerminalId())) {
            throw new InvalidCommunicationException();
        }
        if (canStartCommunication()) {
            Terminal receiver = context.getTerminal(terminalReceiverId);
            receiver.receiveVoiceCall(this, context);
        }
    }

    private void receiveVoiceCall(Terminal sender, Network context)
      throws UnreachableOffTerminalException, UnreachableBusyTerminalException,
      UnreachableSilentTerminalException {
        if (canReceiveInteractiveCommunication()) {
            int newId = context.getNextCommunicationId();
            VoiceCommunication communication =
                new VoiceCommunication(newId, this, sender);
            context.registerCommunication(communication);
        } else {
            addToNotify(sender.getOwner());
            // TODO: fix this horrible mess of exceptions
            switch (getStatusType()) {
                case "BUSY" -> throw new UnreachableBusyTerminalException();
                case "OFF" -> throw new UnreachableOffTerminalException();
                case "SILENCE" -> throw new UnreachableSilentTerminalException();
                default -> throw new UnreachableBusyTerminalException();
            }
        }
    }

    public abstract void makeVideoCall(String terminalReceiverId,
      Network context) throws UnsupportedCommunicationAtOriginException,
      UnsupportedCommunicationAtDestinationException, UnknownTerminalKeyException,
      UnreachableOffTerminalException, UnreachableBusyTerminalException,
      UnreachableSilentTerminalException, InvalidCommunicationException;

    protected abstract void receiveVideoCall(Terminal sender, Network context)
      throws UnsupportedCommunicationAtDestinationException,
      UnreachableOffTerminalException, UnreachableBusyTerminalException,
      UnreachableSilentTerminalException;

    protected void addToNotify(Client client) {
        if (!_clientsToNotify.contains(client) &&
          client.hasNotificationsEnabled()) {
            _clientsToNotify.add(client);
        }
    }

    protected void notifyAllClients(Notification notification) {
        Client client = null;
        while ((client = _clientsToNotify.poll()) != null) {
            client.notify(notification);
        }
    }

    public void performPayment(int communicationId, Network context)
      throws InvalidCommunicationException {
        Communication communication = context.getCommunication(communicationId);
        if (!this.equals(communication.getTerminalSender()) ||
          communication.isOngoing() || communication.isPaid()) {
            throw new InvalidCommunicationException();
        }
        double price = communication.pay();
        updateBalance(price);
    }

    public void updateBalance(double delta) {
        if (delta < 0) {
            _debts += (delta * -1);
        } else {
            _debts -= delta;
            _payments += delta;
        }
        _owner.updateBalance(delta);
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

    public String accept(TerminalVisitor visitor) {
        return visitor.visit(this);
    }

    public abstract class Status implements Serializable { // TODO: do visitors for the status (check test server discord for names)

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210150058L;

        protected Terminal getTerminal() {
            return Terminal.this;
        }

        protected abstract String getStatusType();

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

        protected abstract void setOnIdle()
          throws IllegalTerminalStatusException;

        protected abstract void setOnSilent()
          throws IllegalTerminalStatusException;

        protected abstract void turnOff()
          throws IllegalTerminalStatusException;

        protected abstract void setOnBusy();

        protected abstract void unBusy();

        protected abstract boolean canStartCommunication();

        protected abstract boolean canReceiveTextCommunication();

        protected abstract boolean canReceiveInteractiveCommunication();

    }

}
