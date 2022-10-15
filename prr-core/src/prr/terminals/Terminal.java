package prr.terminals;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private int _idTerminal;
    private String _idClient;
    private Status _status;
    private List<Terminal> _terminalFriends = new ArrayList<>();

    public Terminal(int idTerminal, String idClient, String status) {
        _idTerminal = idTerminal;
        _idClient = idClient;
        _status = switch (status) {
            case "ON" -> new TerminalIdleStatus(this);
            case "OFF" -> new TerminalOffStatus(this);
            case "SILENCE" -> new TerminalSilentStatus(this);
            default -> new TerminalIdleStatus(this);
        };
    }

    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     *          it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        // FIXME add implementation code
        return false;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        // FIXME add implementation code
	    return false;
    }


    public void setStatus(Status status) {
        _status = status;
    }

    public void addFriend(int terminalFriendId) {
    }

    public abstract class Status implements Serializable {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202210150058L;

    }
}
