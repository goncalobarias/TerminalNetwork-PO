package prr;

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
// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

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
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
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
            case "CLIENT" -> registerClient(fields);
            case "BASIC", "FANCY" -> registerTerminal(fields);
            case "FRIENDS" -> registerTerminalFriends(fields);
            default -> throw new UnrecognizedEntryException(String.join("|", fields));
        }
    }

    /** */
    private void registerClient(String[] fields) throws IllegalEntryException,
      UnrecognizedEntryException {
    }

    /** */
    public void addClient() throws DuplicateClientKeyException {
    }

    /** */
    private void registerTerminal(String[] fields) 
      throws IllegalEntryException, UnrecognizedEntryException {
    }

    /** */
    public void addTerminal() throws UnknownClientKeyException, 
      InvalidTerminalKeyException, DuplicateTerminalKeyException {
    }

    /** */
    private void registerTerminalFriends(String[] fields) 
      throws IllegalEntryException, UnrecognizedEntryException {
    }

    /** */
    public void addTerminalFriends(String[] terminalId, 
      String[] terminalFriendsIds) throws UnknownTerminalKeyException {
    }

}
