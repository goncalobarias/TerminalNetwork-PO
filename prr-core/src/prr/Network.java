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

import prr.util.NaturalTextComparator;
import prr.clients.Client;
import prr.communications.Communication;
import prr.terminals.Terminal;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.ImportFileException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownEntryTypeException;
import prr.exceptions.UnknownEntryLengthException;
import prr.exceptions.IllegalTerminalStatusException;

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

    /** Contains the total value of the payments performed in the network. */
    private double _globalPayments;

    /** Contains the total value of the debts acquired in the network. */
    private double _globalDebts;

    /** Was the network changed since the last time it was saved or created? */
    private boolean _changed;

    /** Stores the current entry from the file that is being parsed. */
    private String _currentEntry;

    /** Default constructor. */
    public Network() {
        _clients = new TreeMap<String, Client>(new NaturalTextComparator()); //TODO figure out the data structure for this
        _terminals = new TreeMap<String, Terminal>();
        _communications = new TreeMap<Integer, Communication>();
        _nextCommunicationId = 0;
        _globalPayments = 0.0;
        _globalDebts = 0.0;
        _changed = false;
        _currentEntry = "";
    }

    /**
     * Obtains a new communication ID and increases its value once more. Since
     * every communication has an unique ID we will always get a new value on
     * each call of this method.
     *
     * @return The next communication ID for a new communication
     */
    private int getNextCommunicationId() {
        return _nextCommunicationId++;
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
     * Sets the current entry being parsed.
     *
     * @param entry The current entry being parsed
     */
    private void setCurrentEntry(String entry) {
        _currentEntry = entry;
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
     * Gets a client by its id. Two clients are considered the same in the
     * network if their ids are the same, or only differ by their case.
     *
     * @param id The id of the client to get
     * @return The requested {@link Client}
     * @throws UnknownClientKeyException if the client id is not present in the
     *                                   network.
     */
    public Client getClient(String id) throws UnknownClientKeyException {
        return fetchClient(id);
    }

    /**
     * Gets all the clients associated to the network, sorted by their
     * case-insensitive id.
     *
     * @return The clients sorted by their id on a {@link Collection}
     */
    public Collection<Client> getAllClients() {
        return Collections.unmodifiableCollection(_clients.values());
    }

    /**
     * Checks if a client is elicit to be fetched and retrieves it from the
     * network by its id.
     *
     * @param id The id of the client to fetch
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
     * Gets a terminal by its id. Two terminals are considered the same in the
     * network if their ids are the same.
     *
     * @param id The id of the terminal to get
     * @return The requested {@link Terminal}
     * @throws UnknownTerminalKeyException if the terminal id is not present in
     *                                     the network.
     */
    public Terminal getTerminal(String id) throws UnknownTerminalKeyException {
        return fetchTerminal(id);
    }

    /**
     * Gets all the terminals associated to the network, sorted by their id.
     *
     * @return The terminals sorted by their id on a {@link Collection}
     */
    public Collection<Terminal> getAllTerminals() {
        return Collections.unmodifiableCollection(_terminals.values());
    }

    /**
     * Gets all the unused terminals associated to the network, sorted by their
     * id. A terminal is considered unused if there are no known communications
     * associated with it.
     *
     * @return The unusued terminals sorted by their id on a {@link Collection}
     */
    public Collection<Terminal> getUnusedTerminals() {
        return Collections.unmodifiableCollection(
                                getAllTerminals().stream()
                                .filter(t -> t.isUnused())
                                .collect(Collectors.toList()));
    }

    /**
     * Checks if a terminal is elicit to be fetched and retrieves it from the
     * network by its id.
     *
     * @param id The id of the terminal to fetch
     * @return The {@link Terminal} with the given id that got fetched from the
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
     * Checks if a new client is elicit for registeration, meaning that their id
     * must not already be in the network.
     *
     * @param id The client id to check
     * @throws DuplicateClientKeyException if there already exists a client with
     *                                     the same id in the network
     *                                     (case-insensitive)
     */
    public void assertNewClient(String id)
      throws DuplicateClientKeyException {
        if (_clients.containsKey(id)) {
            throw new DuplicateClientKeyException(id);
        }
    }

    /**
     * Checks if an id is already associated with a client in the network,
     * making it elicit for retrieval.
     *
     * @param id The client id to check
     * @throws UnknownClientKeyException if the client id is not already
     *                                   present on the network.
     */
    public void assertClientExists(String id)
      throws UnknownClientKeyException {
        if (!_clients.containsKey(id)) {
            throw new UnknownClientKeyException(id);
        }
    }

    /**
     * Checks if a new terminal is elicit for registeration, meaning that their
     * id must not already be in the network.
     *
     * @param id The terminal id to check
     * @param InvalidTerminalKeyException    if the terminal id isn't exactly 6
     *                                       characters long and only made up of
     *                                       the digits 0 through 9.
     * @throws DuplicateTerminalKeyException if there already exists a terminal
     *                                       with the same id in the network
     */
    public void assertNewTerminal(String id) throws InvalidTerminalKeyException,
      DuplicateTerminalKeyException {
        String validTerminalIdRegex = "^\\d{6}$";
        if (!id.matches(validTerminalIdRegex)) {
            throw new InvalidTerminalKeyException(id);
        }
        if (_terminals.containsKey(id)) {
            throw new DuplicateTerminalKeyException(id);
        }
    }

    /**
     * Checks if an id is already associated with a terminal in the network,
     * making it elicit for retrieval.
     *
     * @param id The terminal id to check
     * @throws UnknownTerminalKeyException if the terminal id is not already
     *                                     present on the network.
     */
    public void assertTerminalExists(String id)
      throws UnknownTerminalKeyException {
        if (!_terminals.containsKey(id)) {
            throw new UnknownTerminalKeyException(id);
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
        } catch (UnknownEntryLengthException | InvalidTerminalKeyException |
          DuplicateTerminalKeyException | UnknownClientKeyException |
          UnknownEntryTypeException | IllegalTerminalStatusException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /**
     * Parses and imports a terminal friends entry from plain text.
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
     *                                     with the same id in the network
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
     *                                       given id in the network
     * @throws InvalidTerminalKeyException   if the terminal id isn't exactly 6
     *                                       characters long and only made up
     *                                       of the digits 0 through 9.
     * @throws DuplicateTerminalKeyException if there already exists a terminal
     *                                       with the same id in the network
     * @throws UnknownEntryTypeException     if the terminal type isn't exactly
     *                                       equal to BASIC or FANCY
     */
    public Terminal registerTerminal(String type, String terminalId,
      String clientId) throws UnknownClientKeyException,
      InvalidTerminalKeyException, DuplicateTerminalKeyException,
      UnknownEntryTypeException {
        assertClientExists(clientId);
        assertNewTerminal(terminalId);

        Terminal terminal = switch(type) {
            case "BASIC" -> new BasicTerminal(terminalId, getClient(clientId));
            case "FANCY" -> new FancyTerminal(terminalId, getClient(clientId));
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
     * @param terminalId         The id of the terminal that is going to
     *                           receive friends
     * @param terminalFriendsIds The ids of the terminal friends
     * @throws UnknownTerminalKeyException if any of the given terminal keys
     *                                     is not connected to a terminal on
     *                                     the network
     */
    private void registerTerminalFriends(String terminalId,
      String[] terminalFriendsIds) throws UnknownTerminalKeyException {
        assertTerminalExists(terminalId);
        Terminal terminal = getTerminal(terminalId);
        for (String terminalFriendId : terminalFriendsIds) {
            assertTerminalExists(terminalFriendId);
            terminal.addFriend(getTerminal(terminalFriendId));
        }
        changed();
    }

}
