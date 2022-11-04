package prr.app.util;

import java.util.Comparator;

import prr.clients.Client;

public class Comparinator {

    public static final Comparator<Client> CLIENT_DEBT_COMPARATOR =
        new ClientDebtComparator();

    private static class ClientDebtComparator implements Comparator<Client> {

        public int compare(Client c1, Client c2) {
            return Comparator
                    .comparing(Client::getDebts, Comparator.reverseOrder())
                    .thenComparing(Client::getId, String.CASE_INSENSITIVE_ORDER)
                    .compare(c1, c2);
        }

    }

}
