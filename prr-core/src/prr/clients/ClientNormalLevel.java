package prr.clients;

import java.io.Serial;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.tariffs.TariffPlan;

public class ClientNormalLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192342L;

    public ClientNormalLevel(Client client, double payments, double debts,
      int numberOfConsecutiveTextCommunications,
      int numberOfConsecutiveVideoCommunications, TariffPlan plan) {
        client.super(payments, debts, numberOfConsecutiveTextCommunications,
                    numberOfConsecutiveVideoCommunications, plan);
    }

    @Override
    protected String getLevelType() {
        return "NORMAL";
    }

    @Override
    public double computePrice(TextCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    @Override
    public double computePrice(VoiceCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    @Override
    public double computePrice(VideoCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    @Override
    protected void verifyLevelUpdateConditions() {
        if (getBalance() > 500D) {
            updateLevel(new ClientGoldLevel(getClient(), getPayments(),
                getDebts(), getNumberOfConsecutiveTextCommunications(),
                getNumberOfConsecutiveVideoCommunications(), getTariffPlan()));
        }
    }

}
