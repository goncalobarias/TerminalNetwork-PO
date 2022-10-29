package prr.clients;

import java.io.Serial;

public class ClientPlatinumLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192343L;

    public ClientPlatinumLevel(Client client, double payments, double debts,
      int numberOfConsecutiveTextCommunications,
      int numberOfConsecutiveVideoCommunications) {
        client.super(payments, debts, numberOfConsecutiveTextCommunications,
                    numberOfConsecutiveVideoCommunications);
    }

    @Override
    protected String getLevelType() {
        return "PLATINUM";
    }

    @Override
    protected void verifyLevelUpdateConditions() {
        if (getBalance() < 0.0) {
            updateLevel(new ClientNormalLevel(getClient(), getPayments(),
                getDebts(), getNumberOfConsecutiveTextCommunications(),
                getNumberOfConsecutiveVideoCommunications()));
        } else if (getNumberOfConsecutiveTextCommunications() == 2) {
            updateLevel(new ClientGoldLevel(getClient(), getPayments(),
                getDebts(), getNumberOfConsecutiveTextCommunications(),
                getNumberOfConsecutiveVideoCommunications()));
        }
    }

}
