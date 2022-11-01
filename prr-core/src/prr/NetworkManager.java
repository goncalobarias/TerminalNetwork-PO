package prr;

import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

    /** The network itself. */
    private Network _network;

    /** The name of the current file storing the network. */
    private String _filename;

    /** Default constructor. */
    public NetworkManager() {
        _network = new Network();
        _filename = null;
    }

    /** @return The current network */
    public Network getNetwork() {
        return _network;
    }

    /**
     * Loads the serialized application's state from a provided file.
     *
     * @param filename Name of the file containing the serialized application's
     *                 state to load
     * @throws UnavailableFileException if the specified file does not exist or
     *                                  it doesn't have the correct binary data
     */
    public void load(String filename) throws UnavailableFileException {
        try (ObjectInputStream ois = new ObjectInputStream(
          new BufferedInputStream(new FileInputStream(filename)))) {
            _network = (Network) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(filename);
        }
        _filename = filename;
        _network.setChanged(false);
    }

    /**
     * Saves the serialized application's state into the file associated to the
     * current network.
     *
     * @throws MissingFileAssociationException if the current network does not
     *                                         have a file
     * @throws FileNotFoundException           if for some reason the file
     *                                         cannot be created or opened
     * @throws IOException                     if there is some error while
     *                                         serializing the state of the
     *                                         network to disk
     */
    public void save() throws MissingFileAssociationException,
      FileNotFoundException, IOException {
        if (_filename == null) {
            throw new MissingFileAssociationException();
        }

        if (_network.hasChanged()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(
              new BufferedOutputStream(new FileOutputStream(_filename)))) {
                oos.writeObject(_network);
            }
            _network.setChanged(false);
        }
    }

    /**
     * Saves the serialized application's state into the specified file. The
     * current network is associated to this file.
     *
     * @param filename The name of the file
     * @throws FileNotFoundException           if for some reason the file
     *                                         cannot be created or opened
     * @throws IOException                     if there is some error while
     *                                         serializing the state of the
     *                                         network to disk
     */
    public void saveAs(String filename) throws FileNotFoundException,
      IOException {
        _filename = filename;
        try {
            save();
        } catch (MissingFileAssociationException e) {
            // this shouldn't happen since we expect the filename to be valid
            // after we prompt the user
            e.printStackTrace();
        }
    }

    /**
     * Read text input file and create domain entities.
     *
     * @param filename Name of the text input file
     * @throws ImportFileException if any error occurs while importing the file
     */
    public void importFile(String filename) throws ImportFileException {
        try {
            _network.importFile(filename);
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
        _network.changed();
    }

}
