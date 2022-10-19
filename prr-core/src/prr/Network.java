package prr;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;

import prr.clients.Client;
import prr.terminals.Terminal;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.communications.Communication;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.ImportFileException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownEntryTypeException;
import prr.exceptions.UnknownEntryLengthException;
import prr.exceptions.UnknownTerminalStatusException;

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    /** */
    private Map<String, Client> _clients;

    /** */
    private Map<String, Terminal> _terminals;

    /** */
    private Map<Integer, Communication> _communications;

    /** */
    private int _nextCommunicationId;

    /** */
    private double _globalPayments;

    /** */
    private double _globalDebts;

    /** */
    private boolean _changed;

    /** */
    private String _currentEntry;

    /** Default constructor. */
    public Network() {
        _clients = new TreeMap<String, Client>(new NaturalTextComparator());
        _terminals = new TreeMap<String, Terminal>();
        _communications = new TreeMap<Integer, Communication>();
        _nextCommunicationId = 0;
        _globalPayments = 0.0;
        _globalDebts = 0.0;
        _changed = false;
        _currentEntry = "";
    }

    /** */
    private int getNextCommunicationId() {
        return _nextCommunicationId++;
    }

    /** */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /** */
    public void changed() {
        setChanged(true);
    }

    /** */
    private void setCurrentEntry(String entry) {
        _currentEntry = entry;
    }

    /** */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename Name of the text input file
     * @throws ImportFileException        if an error occured while importing
     *                                    the file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException                if there is an IO error while
     *                                    processing the text file
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
     * Receive fields from an entry line and register it into the network.
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

    /** */
    private void assertEntryLength(String[] fields, int expectedSize)
      throws UnknownEntryLengthException {
        if (fields.length != expectedSize) {
            throw new UnknownEntryLengthException(fields.length);
        }
    }

    /** */
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

    /** */
    public void registerClient(String id, String name, int taxId)
      throws DuplicateClientKeyException {
        assertNewClient(id);
        Client client = new Client(id, name, taxId);
        _clients.put(id, client);
        changed();
    }

    /** */
    public void assertNewClient(String id)
      throws DuplicateClientKeyException {
        if (_clients.containsKey(id)) {
            throw new DuplicateClientKeyException(id);
        }
    }

    /** */
    public void assertClientExists(String id)
      throws UnknownClientKeyException {
        if (!_clients.containsKey(id)) {
            throw new UnknownClientKeyException(id);
        }
    }

    /** */
    public Client getClient(String id) throws UnknownClientKeyException {
        return fetchClient(id);
    }

    /** */
    private Client fetchClient(String id) throws UnknownClientKeyException {
        Client client = _clients.get(id);
        if (client == null) {
            throw new UnknownClientKeyException(id);
        }
        return client;
    }

    /** */
    public Collection<Client> getAllClients() {
        return Collections.unmodifiableCollection(_clients.values());
    }

    /** */
    private void parseTerminal(String[] fields)
      throws UnrecognizedEntryException {
        try {
            assertEntryLength(fields, 4);
            Terminal registeredTerminal =
                registerTerminal(fields[0], fields[1], fields[2]);
            registeredTerminal.setStatus(fields[3]);
        } catch (UnknownEntryLengthException | InvalidTerminalKeyException |
          DuplicateTerminalKeyException | UnknownClientKeyException |
          UnknownEntryTypeException | UnknownTerminalStatusException e) {
            throw new UnrecognizedEntryException(_currentEntry, e);
        }
    }

    /** */
    public Terminal registerTerminal(String type, String terminalId,
      String clientId) throws InvalidTerminalKeyException,
      DuplicateTerminalKeyException, UnknownClientKeyException,
      UnknownEntryTypeException {
        assertNewTerminal(terminalId);
        assertClientExists(clientId);

        Terminal terminal = switch(type) {
            case "BASIC" -> new BasicTerminal(terminalId, getClient(clientId));
            case "FANCY" -> new FancyTerminal(terminalId, getClient(clientId));
            default -> throw new UnknownEntryTypeException(type);
        };

        _terminals.put(terminalId, terminal);
        changed();
        return terminal;
    }

    /** */
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

    /** */
    public void assertTerminalExists(String id)
      throws UnknownTerminalKeyException {
        if (!_terminals.containsKey(id)) {
            throw new UnknownTerminalKeyException(id);
        }
    }

    /** */
    public Terminal getTerminal(String id) throws UnknownTerminalKeyException {
        return fetchTerminal(id);
    }

    /** */
    private Terminal fetchTerminal(String id)
      throws UnknownTerminalKeyException {
        Terminal terminal = _terminals.get(id);
        if (terminal == null) {
            throw new UnknownTerminalKeyException(id);
        }
        return terminal;
    }

    /** */
    public Collection<Terminal> getAllTerminals() {
        return Collections.unmodifiableCollection(_terminals.values());
    }

    /** */
    public Collection<Terminal> getUnusedTerminals() {
        return Collections.unmodifiableCollection(
                                getAllTerminals().stream()
                                .filter(t -> t.isUnused())
                                .collect(Collectors.toList()));
    }

    /** */
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

    /** */
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
