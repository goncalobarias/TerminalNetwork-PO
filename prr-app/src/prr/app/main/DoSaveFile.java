package prr.app.main;

import java.io.IOException;

import prr.NetworkManager;
import prr.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

    DoSaveFile(NetworkManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() {
        try {
            try {
                _receiver.save();
            } catch (MissingFileAssociationException e) {
                _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
