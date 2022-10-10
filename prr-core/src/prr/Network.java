package prr;

import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.ImportFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;
import prr.exceptions.UnrecognizedEntryException;
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
     * @throws ImportFileException If an error occured while importing the file
     * @throws UnrecognizedEntryException If some entry is not correct
     * @throws IOException If there is an IO error while processing the text file
     */
    void importFile(String filename) /* throws ImportFileException, UnrecognizedEntryException, 
            IOException */ {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                /* try {
                    registerEntry(fields);
                } catch ( e) {
                    e.printStackTrace();
                } */
            }
        }
    }

    /** 
     * Receive fields from an entry line and register it into the network.
     *
     * @param fields Fields that were previously split in order to contain 
     *              information for an entry and are now ready to be registered
     */
    public void registerEntry(String... fields) /* throws */ {
        /* switch (fields[0]) {
            case "CLIENT" -> registerClient(fields);
            case "BASIC", "FANCY" -> registerTerminal(fields);
            case "FRIENDS" -> registerTerminalFriends(fields);
            default -> throw new UnrecognizedEntryException(fields[0]);
        } */
    }

}
