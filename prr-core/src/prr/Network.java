package prr;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.Serial;
import java.io.IOException;

import prr.clients.Client;
import prr.communications.Communication;
import prr.notifications.Notification;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.terminals.Terminal;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidCommunicationException;
import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.InvalidFriendException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.NotificationsAlreadyToggledException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownEntryLengthException;
import prr.exceptions.UnknownEntryTypeException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnrecognizedEntryException;

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    /** Stores the network's clients, sorted by their id. */
    private Map<String, Client> _clients;

    /** Stores the network's terminals, sorted by their id. */
    private Map<String, Terminal> _terminals;

    /** Stores the network's communications, sorted by their id. */
    private Map<Integer, Communication> _communications;

    /** Contains the ID for the next communication created by the network. */
    private int _nextCommunicationId;

    /** Stores the current entry from the file that is being parsed. */
    private String _currentEntry;

    /** Was the network changed since the last time it was saved or created? */
    private boolean _changed; // TODO: see if I set the network as changed everywhere

    /** Default constructor. */
    public Network() {
        _clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);
        _terminals = new TreeMap<String, Terminal>();
        _communications = new TreeMap<Integer, Communication>();
        _nextCommunicationId = 1;
        _currentEntry = "";
        _changed = true;
    }

    /**
     * Gets the global payments performed in the network.
     *
     * @return the global payments
     */
    public double getGlobalPayments() {
        return _clients.values().stream()
                .mapToDouble(c -> c.getPayments())
                .sum();
    }

    /**
     * Gets the global debts acquired in the network.
     *
     * @return the global debts
     */
    public double getGlobalDebts() {
        return _clients.values().stream()
                .mapToDouble(c -> c.getDebts())
                .sum();
    }

    /**
     * Gets a client by its key. Two clients are considered the same in the
     * network if their keyss are the same, or only differ by their case.
     *
     * @param id The key of the client to get
     * @return The requested {@link Client}
     * @throws UnknownClientKeyException if the client key is not present in the
     *                                   network.
     */
    public Client getClient(String id) throws UnknownClientKeyException {
        return fetchClient(id);
    }

    /**
     * Checks if a client is elicit to be fetched and retrieves it from the
     * network by its key.
     *
     * @param id The key of the client to fetch
     * @return The {@link Client} with the given key that got fetched from the
     *         network
     * @throws UnknownClientKeyException if the client if is not present in the
     *                                   network.
     */
    private Client fetchClient(String id) throws UnknownClientKeyException {
        Client client = _clients.get(id);
        if (client == null) {
            throw new UnknownClientKeyException(id);
        }
        return client;
    }

    /**
     * Gets the payments of a given client.
     *
     * @param id The key of the client
     * @return The given clients payments
     * @throws UnknownClientKeyException if the client id is not present in the
     *                                   network.
     */
    public double getClientPayments(String id) throws UnknownClientKeyException {
        final Client client = getClient(id);
        return client.getPayments();
    }

    /**
     * Gets the debts of a given client.
     *
     * @param id The key of the client
     * @return The given clients debts
     * @throws UnknownClientKeyException if the client key is not present in the
     *                                   network.
     */
    public double getClientDebts(String id) throws UnknownClientKeyException {
        final Client client = getClient(id);
        return client.getPayments();
    }

    /**
     * Collects all the pending notifications for a given client, marking them
     * as read.
     *
     * @param clientId The key of the client that read their notifications
     * @return A {@link Collection} of the unread notifications
     * @throws UnknownClientKeyException if there exists no client with the
     *                                   given key in the network
     */
    public Collection<Notification> getClientNotifications(String clientId)
      throws UnknownClientKeyException {
        final Client client = getClient(clientId);
        return Collections.unmodifiableCollection(client.readNotifications());
    }

    /**
     * Gets all the clients associated to the network, sorted by their
     * case-insensitive key.
     *
     * @return The clients sorted by their key on a {@link Collection}
     */
    public Collection<Client> getAllClients() {
        return Collections.unmodifiableCollection(_clients.values());
    }

    /**
     * Gets all the clients associated to the network that have no debts, sorted
     * by their case-insensitive key.
     *
     * @return The clients with no debts sorted by their key on
     * a {@link Collection}
     */
    public Collection<Client> getClientsWithoutDebts() {
        return Collections.unmodifiableCollection(
            _clients.values().stream()
            .filter(c -> c.getDebts() == 0D)
            .collect(Collectors.toList())
        );
    }

    /**
     * Gets all the clients associated to the network that have acquired debt,
     * sorted by their case-insensitive key.
     *
     * @return The clients with debts sorted by their key on
     * a {@link Collection}
     */
    public Collection<Client> getClientsWithDebts() {
        return Collections.unmodifiableCollection(
            _clients.values().stream()
            .filter(c -> c.getDebts() > 0D)
            .collect(Collectors.toList())
        );
    }

    /**
     * Enables the given client's reception of failed contacts.
     *
     * @param clientId The key of the client to turn notifications on
     * @throws UnknownClientKeyException            if there exists no client
     *                                              with the given key in the
     *                                              network
     * @throws NotificationsAlreadyToggledException if the client already has
     *                                              notifications turned on
     */
    public void enableClientNotifications(String clientId)
      throws UnknownClientKeyException, NotificationsAlreadyToggledException {
        final Client client = getClient(clientId);
        if (client.hasNotificationsEnabled()) {
            throw new NotificationsAlreadyToggledException(true);
        }
        client.setNotificationState(true);
        changed();
    }

    /**
     * Disables the given client's reception of failed contacts.
     *
     * @param clientId The key of the client to turn notifications on
     * @throws UnknownClientKeyException            if there exists no client
     *                                              with the given key in the
     *                                              network
     * @throws NotificationsAlreadyToggledException if the client already has
     *                                              notifications turned on
     */
    public void disableClientNotifications(String clientId)
      throws UnknownClientKeyException, NotificationsAlreadyToggledException {
        final Client client = getClient(clientId);
        if (!client.hasNotificationsEnabled()) {
            throw new NotificationsAlreadyToggledException(false);
        }
        client.setNotificationState(false);
        changed();
    }

    /**
     * Gets a terminal by its key. Two terminals are considered the same in the
     * network if their keys are the same.
     *
     * @param id The key of the terminal to get
     * @return The requested {@link Terminal}
     * @throws UnknownTerminalKeyException if the terminal key is not present in
     *                                     the network.
     */
    public Terminal getTerminal(String id) throws UnknownTerminalKeyException {
        return fetchTerminal(id);
    }

    /**
     * Checks if a terminal is elicit to be fetched and retrieves it from the
     * network by its key.
     *
     * @param id The key of the terminal to fetch
     * @return The {@link Terminal} with the given key that got fetched from the
     *         network
     * @throws UnknownTerminalKeyException if the terminal if is not present in
     *                                     the network.
     */
    private Terminal fetchTerminal(String id)
      throws UnknownTerminalKeyException {
        Terminal terminal = _terminals.get(id);
        if (terminal == null) {
            throw new UnknownTerminalKeyException(id);
        }
        return terminal;
    }

    /**
     * Gets all the terminals associated to the network, sorted by their key.
     *
     * @return The terminals sorted by their key on a {@link Collection}
     */
    public Collection<Terminal> getAllTerminals() {
        return Collections.unmodifiableCollection(_terminals.values());
    }

    /**
     * Gets all the unused terminals associated to the network, sorted by their
     * key. A terminal is considered unused if there are no known communications
     * associated with it.
     *
     * @return The unusued terminals sorted by their key on a {@link Collection}
     */
    public Collection<Terminal> getUnusedTerminals() {
        return Collections.unmodifiableCollection(
            _terminals.values().stream()
            .filter(t -> t.isUnused())
            .collect(Collectors.toList())
        );
    }

    /**
     * Gets all the terminals with a positive balance associated to the network,
     * sorted by their key. The concept of balance is defined as the difference
     * between the payments made and debts acquired by the terminal.
     *
     * @return The terminals with a net positive balance sorted by their key on
     * a {@link Collection}
     */
    public Collection<Terminal> getTerminalsWithPositiveBalance() {
        return Collections.unmodifiableCollection(
            _terminals.values().stream()
            .filter(t -> t.getBalance() > 0)
            .collect(Collectors.toList())
        );
    }

    /**
     * Obtains a new communication ID and increases its value once more. Since
     * every communication requires an unique ID we will always get a new value
     * on each call of this method.
     *
     * @return The next communication ID for a new communication
     */
    public int getNextCommunicationId() {
        return _nextCommunicationId++;
    }

    /**
     * Gets a communication by its key. Two communications are considered the
     * same in the network if their keys are the same.
     *
     * @param id The key of the communication to get
     * @return The requested {@link Communication}
     * @throws UnknownCommunicationKeyException if the communication key is not
     *                                          present in the network.
     */
    public Communication getCommunication(int id)
      throws InvalidCommunicationException {
        return fetchCommunication(id);
    }

    /**
     * Checks if a communication is elicit to be fetched and retrieves it from
     * the network by its key.
     *
     * @param id The key of the communication to fetch
     * @return The {@link communication} with the given key that got fetched
     *         from the network
     * @throws UnknownCommunicationKeyException if the communication key is not
     *                                          present in the network.
     */
    private Communication fetchCommunication(int id)
      throws InvalidCommunicationException {
        Communication communication = _communications.get(id);
        if (communication == null) {
            throw new InvalidCommunicationException();
        }
        return communication;
    }

    /**
     * Gets all the communications associated to the network, sorted by their
     * key.
     *
     * @return The communications sorted by their key on a {@link Collection}
     */
    public Collection<Communication> getAllCommunications() {
        return Collections.unmodifiableCollection(
            _communications.values().stream()
            .collect(Collectors.toList())
        );
    }

    /**
     * Gets all the communications made by a given client on any of their
     * terminals.
     *
     * @param clientId The key of the client
     * @return The communications made by the client, sorted by their key on
     * a {@link Collection}
     * @throws UnknownClientKeyException if the client if is not present in the
     *                                   network.
     */
    public Collection<Communication> getAllCommunicationsMadeByClient(
      String clientId) throws UnknownClientKeyException {
        final Client client = getClient(clientId);

        return Collections.unmodifiableCollection(
            _communications.values().stream()
            .filter(c -> client.isOwnerOf(c.getTerminalSender()))
            .collect(Collectors.toList())
        );
    }

    /**
     * Gets all the communications received by a given client on any of their
     * terminals.
     *
     * @param clientId The key of the client
     * @return The communications received by the client, sorted by their key on
     * a {@link Collection}
     * @throws UnknownClientKeyException if the client if is not present in the
     *                                   network.
     */
    public Collection<Communication> getAllCommunicationsReceivedByClient(
      String clientId) throws UnknownClientKeyException {
        final Client client = getClient(clientId);

        return Collections.unmodifiableCollection(
            _communications.values().stream()
            .filter(c -> client.isOwnerOf(c.getTerminalReceiver()))
            .collect(Collectors.toList())
        );
    }

    /**
     * Sets the current entry being parsed.
     *
     * @param entry The current entry being parsed
     */
    private void setCurrentEntry(String entry) {
        _currentEntry = entry;
    }

    /**
     * Sets the changed flag to the value it receives.
     *
     * @param changed The value of the changed flag
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Turns the change flag on to indicate that the network has changed since
     * it was last saved or created.
     */
    public void changed() {
        setChanged(true);
    }

    /**
     * Indicates whether the network has changed since it was last saved or
     * created.
     *
     * @return The current value of the changed flag.
     */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * Checks if the entry has the expected number of fields.
     *
     * @throws UnknownEntryLengthException if the number of fields doesn't
     *                                     match the expected value
     */
    private void assertEntryLength(String[] fields, int expectedSize)
      throws UnknownEntryLengthException {
        if (fields.length != expectedSize) {
            throw new UnknownEntryLengthException(fields.length);
        }
    }

    /**
     * Checks if a new client is elicit for registeration, meaning that their key
     * must not already be in the network.
     *
     * @param id The client key to check
     * @throws DuplicateClientKeyException if there already exists a client with
     *                                     the same key in the network
     *                                     (case-insensitive)
     */
    private void assertNewClient(String id)
      throws DuplicateClientKeyException {
        if (_clients.containsKey(id)) {
            throw new DuplicateClientKeyException(id);
        }
    }

    /**
     * Checks if a new terminal is elicit for registeration, meaning that their
     * key must not already be in the network.
     *
     * @param id The terminal key to check
     * @param InvalidTerminalKeyException    if the terminal key isn't exactly 6
     *                                       characters long and only made up of
     *                                       the digits 0 through 9.
     * @throws DuplicateTerminalKeyException if there already exists a terminal
     *                                       with the same key in the network
     */
    private void assertNewTerminal(String id)
      throws InvalidTerminalKeyException, DuplicateTerminalKeyException {
        String validTerminalIdRegex = "^\\d{6}$";
        if (!id.matches(validTerminalIdRegex)) {
            throw new InvalidTerminalKeyException(id);
        }
        if (_terminals.containsKey(id)) {
            throw new DuplicateTerminalKeyException(id);
        }
    }

    /**
     * Read plain text input file and create corresponding domain entities
     * (clients, terminals and terminal friends).
     *
     * @param filename Name of the text input file
     * @throws ImportFileException        if any error occured while importing
     *                                    the file, such as the file being a
     *                                    directory
     * @throws UnrecognizedEntryException if some entry (line) is not correct
     * @throws IOException                if there was an IO error while
     *                                    processing the text file.
     */
    void importFile(String filename) throws IOException,
      UnrecognizedEntryException {
        try (BufferedReader reader = new BufferedReader(
          new FileReader(filename))) {
            String entry;
            while ((entry = reader.readLine()) != null) {
                setCurrentEntry(entry);
                String[] fields = entry.split("\\|");
                parseEntry(fields);
            }
        }
    }

    /**
     * Parses an entry line from its fields, making it possible for the network
     * to register it.
     *
     * @param fields Fields that were previously split in order to contain
     *               information for an entry ready to be registered
     * @throws UnrecognizedEntryException if the entry type is unknown by
     *                                    the program
     */
    private void parseEntry(String[] fields) throws UnrecognizedEntryException {
        try {
            switch (fields[0]) {
                case "CLIENT" -> parseClient(fields);
                case "BASIC", "FANCY" -> parseTerminal(fields);
                case "FRIENDS" -> parseTerminalFriends(fields);
                default -> throw new UnknownEntryTypeException(fields[0]);
            }
        } catch (UnknownEntryTypeException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses and imports a client entry from plain text.
     * <p>
     * A correct client entry needs to have the following format:
     * {@code CLIENT|id|name|taxId}
     *
     * @param fields The fields of a client entry that were previously split
     * @throws UnrecognizedEntryException if the entry does not have the
     *                                    correct fields
     */
    private void parseClient(String[] fields)
      throws UnrecognizedEntryException {
        try {
            assertEntryLength(fields, 4);
            registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
        } catch (UnknownEntryLengthException | NumberFormatException |
          DuplicateClientKeyException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses and imports a terminal entry from plain text.
     * <p>
     * A correct terminal entry needs to have the following format:
     * {@code terminal-type|idTerminal|idClient|state}
     *
     * @param fields The fields of a terminal entry that were previously split
     * @throws UnrecognizedEntryException if the entry does not have the
     *                                    correct fields
     */
    private void parseTerminal(String[] fields)
      throws UnrecognizedEntryException {
        try {
            assertEntryLength(fields, 4);
            Terminal registeredTerminal =
                registerTerminal(fields[0], fields[1], fields[2]);
            registeredTerminal.setStatus(fields[3]);
        } catch (UnknownEntryLengthException | UnknownClientKeyException |
          InvalidTerminalKeyException | DuplicateTerminalKeyException |
          UnknownEntryTypeException | IllegalTerminalStatusException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses and imports terminal friends from an entry in a plain text file.
     * <p>
     * A correct terminal friends entry needs to have the following format:
     * {@code FRIENDS|idTerminal|idTerminal1,...,idTerminalN}
     *
     * @param fields The fields of a terminal friends entry that were
     *               previously split
     * @throws UnrecognizedEntryException if the entry does not have the
     *                                    correct fields
     */
    private void parseTerminalFriends(String[] fields)
      throws UnrecognizedEntryException {
        try {
            assertEntryLength(fields, 3);
            String[] terminalFriendsIds = fields[2].split(",");
            registerTerminalFriends(fields[1], terminalFriendsIds);
        } catch (UnknownEntryLengthException | UnknownTerminalKeyException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Registers a new client in the network from the given parameters.
     *
     * @param id    The unique identifier of each client
     * @param name  The name of the client
     * @param taxId The tax id of the client
     * @return The {@link Client} that just got registered
     * @throws DuplicateClientKeyException if there already exists a client
     *                                     with the same key in the network
     *                                     (case-insensitive)
     */
    public Client registerClient(String id, String name, int taxId)
      throws DuplicateClientKeyException {
        assertNewClient(id);
        Client client = new Client(id, name, taxId);
        _clients.put(id, client);
        changed();
        return client;
    }

    /**
     * Registers a new terminal in the network from the given parameters which
     * starts on an idle state.
     *
     * @param type       The terminal type which can be basic or fancy
     * @param terminalId The unique identifier of each terminal
     * @param clientId   The client id of the owner of the terminal
     * @return The {@link Terminal} that just got registered
     * @throws UnknownClientKeyException     if there exists no client with the
     *                                       given key in the network
     * @throws InvalidTerminalKeyException   if the terminal key isn't exactly 6
     *                                       characters long and only made up
     *                                       of the digits 0 through 9.
     * @throws DuplicateTerminalKeyException if there already exists a terminal
     *                                       with the same key in the network
     * @throws UnknownEntryTypeException     if the terminal type isn't exactly
     *                                       equal to BASIC or FANCY
     */
    public Terminal registerTerminal(String type, String terminalId,
      String clientId) throws UnknownClientKeyException,
      InvalidTerminalKeyException, DuplicateTerminalKeyException,
      UnknownEntryTypeException {
        Client client = getClient(clientId);
        assertNewTerminal(terminalId);

        Terminal terminal = switch(type) {
            case "BASIC" -> new BasicTerminal(terminalId, client);
            case "FANCY" -> new FancyTerminal(terminalId, client);
            default -> throw new UnknownEntryTypeException(type);
        };

        _terminals.put(terminalId, terminal);
        changed();
        return terminal;
    }

    /**
     * Registers the given terminals as friends of a specific terminal. The
     * relationship between terminals is asymmetric, meaning A being a friend
     * of B doesn't imply the opposite.
     *
     * @param terminalId         The key of the terminal that is going to
     *                           receive friends
     * @param terminalFriendsIds The keys of the terminal friends
     * @throws UnknownTerminalKeyException if any of the given terminal keys
     *                                     is not connected to a terminal on
     *                                     the network
     */
    private void registerTerminalFriends(String terminalId,
      String[] terminalFriendsIds) throws UnknownTerminalKeyException {
        Terminal terminal = getTerminal(terminalId);
        for (String terminalFriendId : terminalFriendsIds) {
            try {
                terminal.addFriend(terminalFriendId, this);
            } catch (InvalidFriendException e) {
                // do nothing
            }
        }
        changed();
    }

    /**
     * Registers the given communication on the network.
     *
     * @param communication New communication started by a terminal on the
     *                      network.
     */
    public void registerCommunication(Communication communication) {
        _communications.put(communication.getId(), communication);
        changed();
    }

}
