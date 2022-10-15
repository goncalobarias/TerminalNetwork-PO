package prr;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public abstract class Terminal implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210150057L;

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
