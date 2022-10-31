package prr.tariffs;

import java.io.Serializable;
import java.io.Serial;

import prr.clients.ClientNormalLevel;
import prr.clients.ClientGoldLevel;
import prr.clients.ClientPlatinumLevel;
import prr.communications.TextCommunication;
import prr.communications.VoiceCommunication;
import prr.communications.VideoCommunication;

public abstract class TariffPlan implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210150056L;

    private boolean _areFriends;

    public boolean areFriends() {
        return _areFriends;
    }

    public void setFriendship(boolean areFriends) {
        _areFriends = areFriends;
    }

    public abstract double computePrice(ClientNormalLevel level,
      TextCommunication communication);

    public abstract double computePrice(ClientNormalLevel level,
      VoiceCommunication communication);

    public abstract double computePrice(ClientNormalLevel level,
      VideoCommunication communication);

    public abstract double computePrice(ClientGoldLevel level,
      TextCommunication communication);

    public abstract double computePrice(ClientGoldLevel level,
      VoiceCommunication communication);

    public abstract double computePrice(ClientGoldLevel level,
      VideoCommunication communication);

    public abstract double computePrice(ClientPlatinumLevel level,
      TextCommunication communication);

    public abstract double computePrice(ClientPlatinumLevel level,
      VoiceCommunication communication);

    public abstract double computePrice(ClientPlatinumLevel level,
      VideoCommunication communication);

}
