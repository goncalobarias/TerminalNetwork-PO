package prr;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;
//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

    /** The network itself. */
    private Network _network = new Network();

    /** The name of the current file storing the network. */
    private String _filename = "";

    /** */
    public Network getNetwork() {
        return _network;
    }

    /**
     * @param filename Name of the file containing the serialized application's
     *                 state to load.
     * @throws UnavailableFileException if the specified file does not exist or 
     *                                  there is an error while processing this 
     *                                  file.
     */
    public void load(String filename) throws UnavailableFileException {
        if (filename == null || filename.equals("")) {
            throw new UnavailableFileException(filename);
        }

        _filename = filename;
        try (ObjectInputStream ois = new ObjectInputStream(
          new BufferedInputStream(new FileInputStream(_filename)))) {
            _network = (Network) ois.readObject();
            _network.setChanged(false);
        } catch (IOException | ClassNotFoundException e) {
            throw new UnavailableFileException(_filename);
        }
    }

    /**
     * Saves the serialized application's state into the file associated to the 
     * current network.
     *
     * @throws FileNotFoundException           if for some reason the file 
     *                                         cannot be created or opened. 
     * @throws MissingFileAssociationException if the current network does not 
     *                                         have a file.
     * @throws IOException                     if there is some error while 
     *                                         serializing the state of the 
     *                                         network to disk.
     */
    public void save() throws MissingFileAssociationException, 
      FileNotFoundException, IOException {
        if (_filename == null || _filename.equals("")) {
            throw new MissingFileAssociationException();
        }

        if (_network.hasChanged()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(
              new BufferedOutputStream(new FileOutputStream(_filename)))) {
                oos.writeObject(_network);
                _network.setChanged(false);
            }
        }
    }

    /**
     * Saves the serialized application's state into the specified file. The 
     * current network is associated to this file.
     *
     * @param filename The name of the file.
     * @throws FileNotFoundException           if for some reason the file 
     *                                         cannot be created or opened. 
     * @throws MissingFileAssociationException if the current network does not 
     *                                         have a file.
     * @throws IOException                     if there is some error while 
     *                                         serializing the state of the 
     *                                         network to disk.
     */
    public void saveAs(String filename) throws MissingFileAssociationException, 
      FileNotFoundException, IOException {
        _filename = filename;
        save();
    }

    /**
     * Read text input file and create domain entities..
     * 
     * @param filename Name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        try {
            _network.importFile(filename);
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
    }

}
