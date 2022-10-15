package prr;

import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;

import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.ImportFileException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.IllegalEntryException;

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    /** */
    private Map<String, Client> _clients = new TreeMap<>();

    /** */
    private Map<Integer, Terminal> _terminals = new TreeMap<>();

    /** */
    private Map<Integer, Communication> _communications = new TreeMap<>();

    /** */
    private int _nextCommunicationId = 0;

    /** */
    private double _globalPayments = 0.0;

    /** */
    private double _globalDebts = 0.0;

    /** */
    private boolean _changed = false;

    /** */
    private String _currentEntry = "";

    /** */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /** */
    public boolean hasChanged() {
        return _changed;
    }

    /** */
    public void changed() {
        setChanged(true);
    }

    /** */
    public void setCurrentEntry(String entry) {
        _currentEntry = entry;
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
      IllegalEntryException, UnrecognizedEntryException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String entry;
            while ((entry = reader.readLine()) != null) {
                setCurrentEntry(entry);
                String[] fields = entry.split("\\|");
                registerEntry(fields);
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
    private void registerEntry(String[] fields) throws IllegalEntryException, 
      UnrecognizedEntryException {
        switch (fields[0]) {
            case "CLIENT" -> parseClient(fields);
            case "BASIC", "FANCY" -> parseTerminal(fields);
            case "FRIENDS" -> parseTerminalFriends(fields);
            default -> throw new UnrecognizedEntryException(_currentEntry);
        }
    }

    /** */
    private void assertFieldsLength(String[] fields, int expectedSize) 
      throws UnrecognizedEntryException {
        if (fields.length != expectedSize) {
            throw new UnrecognizedEntryException(_currentEntry);
        }
    }

    /** */
    private void parseClient(String[] fields) throws IllegalEntryException,
      UnrecognizedEntryException {
        assertFieldsLength(fields, 4);

        try {
            registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
        } catch (DuplicateClientKeyException e) {
            throw new IllegalEntryException(fields);
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
    public void assertNewClient(String id) throws DuplicateClientKeyException {
        if (_clients.containsKey(id)) {
            throw new DuplicateClientKeyException(id);
        }
    }

    /** */
    public Client assertClientExists(String id) throws UnknownClientKeyException {
        Client client = _clients.get(id);
        if (client != null) {
            return client;
        }
        throw new UnknownClientKeyException(id);
    }

    /** */
    private void parseTerminal(String[] fields) 
      throws IllegalEntryException, UnrecognizedEntryException {
        assertFieldsLength(fields, 4);

        try {
            registerTerminal(fields[0], 
                            Integer.parseInt(fields[1]), 
                            fields[2], fields[3]);
        } catch (UnknownClientKeyException | InvalidTerminalKeyException | 
          DuplicateTerminalKeyException e) {
            throw new IllegalEntryException(fields);
        }
    }

    /** */
    public void registerTerminal(String type, int idTerminal, String idClient, 
      String status) throws UnknownClientKeyException, 
      InvalidTerminalKeyException, DuplicateTerminalKeyException, 
      UnrecognizedEntryException {
        assertNewTerminal(idTerminal);
        assertClientExists(idClient);

        Terminal terminal = switch(type) {
            case "BASIC" -> new BasicTerminal(idTerminal, idClient, status);
            case "FANCY" -> new FancyTerminal(idTerminal, idClient, status);
            default -> throw new UnrecognizedEntryException(_currentEntry);
            // FIXME disgusting implementation on the terminal status
        };

        _terminals.put(idTerminal, terminal);
        changed();
    }

    /** */
    public void assertNewTerminal(int id) 
      throws InvalidTerminalKeyException, DuplicateTerminalKeyException {
        if (_terminals.containsKey(id)) {
            throw new DuplicateTerminalKeyException(id);
        }
        if (id < 100000 || id > 999999) {
            throw new InvalidTerminalKeyException(id);
        }
    }

    /** */
    public Terminal assertTerminalExists(int id) 
      throws UnknownTerminalKeyException {
        Terminal terminal = _terminals.get(id);
        if (terminal != null) {
            return terminal;
        }
        throw new UnknownTerminalKeyException(id);
    }

    /** */
    private void parseTerminalFriends(String[] fields) 
      throws IllegalEntryException, UnrecognizedEntryException {
        assertFieldsLength(fields, 3);

        try {
            String[] terminalFriendsIds = fields[2].split(",");
            addTerminalFriends(fields[1], terminalFriendsIds);
        } catch (UnknownTerminalKeyException e) {
            throw new IllegalEntryException(fields);
        }
    }

    /** */
    public void addTerminalFriends(String terminalId, 
      String[] terminalFriendsIds) throws UnknownTerminalKeyException {
        Terminal terminal = assertTerminalExists(Integer.parseInt(terminalId));
        for (String terminalFriendId : terminalFriendsIds) {
            assertTerminalExists(Integer.parseInt(terminalFriendId));
            terminal.addFriend(Integer.parseInt(terminalFriendId));
            // FIXME weird implementation
        }
        changed();
    }

}
