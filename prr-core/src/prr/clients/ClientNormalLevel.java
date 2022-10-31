package prr.clients;

import java.io.Serial;

import prr.communications.TextCommunication;
import prr.communications.VoiceCommunication;
import prr.communications.VideoCommunication;

public class ClientNormalLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192342L;

    public ClientNormalLevel(Client client, double payments, double debts,
      int numberOfConsecutiveTextCommunications,
      int numberOfConsecutiveVideoCommunications) {
        client.super(payments, debts, numberOfConsecutiveTextCommunications,
                    numberOfConsecutiveVideoCommunications);
    }

    @Override
    protected String getLevelType() {
        return "NORMAL";
    }

    public double computePrice(TextCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    public double computePrice(VoiceCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    public double computePrice(VideoCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }



    @Override
    protected void verifyLevelUpdateConditions() {
        if (getBalance() > 500.0) {
            updateLevel(new ClientGoldLevel(getClient(), getPayments(),
                getDebts(), getNumberOfConsecutiveTextCommunications(),
                getNumberOfConsecutiveVideoCommunications()));
        }
    }

}
