package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.NetworkManager;
import prr.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

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
                save();
            } catch (MissingFileAssociationException | FileNotFoundException e1) {
                saveAs();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    private void save() throws MissingFileAssociationException, 
      FileNotFoundException, IOException {
        _receiver.save();
    }

    private void saveAs() throws IOException {
        try {
            _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
        } catch (MissingFileAssociationException | FileNotFoundException e) {
            saveAs();
        }
    }

}
