package prr.app.main;

import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import prr.exceptions.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

    DoOpenFile(NetworkManager receiver) {
    	super(Label.OPEN_FILE, receiver);
        addStringField("filename", Prompt.openfile());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.load(stringField("filename"));
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        }
    }

}
