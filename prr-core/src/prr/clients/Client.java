package prr.clients;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.io.Serializable;
import java.io.Serial;

import prr.util.ClientVisitor;
import prr.notifications.NotificationDeliveryMethod;
import prr.notifications.Notification;
import prr.tariffs.BasePlan;
import prr.tariffs.TariffPlan;
import prr.terminals.Terminal;

public class Client implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210150050L;

    private final String _id;
    private String _name;
    private int _taxId;
    private Map<String, Terminal> _terminals;
    private Level _level;
    private boolean _receiveNotifications;
    private Queue<Notification> _notifications;
    private NotificationDeliveryMethod _deliveryMethod;
    public static final Comparator<Client> DEBT_COMPARATOR = new DebtComparator();

    private static class DebtComparator implements Comparator<Client>,
      Serializable {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210231030L;

        public int compare(Client c1, Client c2) {
            return Double.compare(c2.getDebts(), c1.getDebts());
        }

    }

    public Client(String id, String name, int taxId) {
        _id = id;
        _name = name;
        _taxId = taxId;
        _terminals = new HashMap<String, Terminal>(); // TODO: fix this data structure to a map
        _level = new ClientNormalLevel(this);
        _receiveNotifications = true;
        _notifications = new LinkedList<Notification>();
        _deliveryMethod = new DefaultDeliveryMethod();
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getTaxId() {
        return _taxId;
    }

    public int getNumberOfTerminals() {
        return _terminals.size();
    }

    public String getLevelType() {
        return _level.getLevelType();
    }

    public double getPayments() {
        return _level.getPayments();
    }

    public double getDebts() {
        return _level.getDebts();
    }

    public void setNotificationState(boolean notificationState) {
        _receiveNotifications = notificationState;
    }

    public boolean hasNotificationsEnabled() {
        return _receiveNotifications;
    }

    public boolean isOwnerOf(Terminal terminal) {
        return _terminals.containsKey(terminal.getTerminalId());
    }

    public void addTerminal(Terminal terminal) {
        _terminals.put(terminal.getTerminalId(), terminal);
    }

    public String accept(ClientVisitor visitor) {
        return visitor.visit(this);
    }

    public class DefaultDeliveryMethod extends NotificationDeliveryMethod {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210192341L;

        @Override
        public void deliver(Notification notification) {
        }

    }

    public abstract class Level implements Serializable {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210150052L;

        private double _payments;
        private double _debts;
        private TariffPlan _plan;

        public Level() {
            _payments = 0.0;
            _debts = 0.0;
            _plan = new BasePlan();
        }

        protected Client getClient() {
            return Client.this;
        }

        protected abstract String getLevelType();

        protected double getPayments() {
            return _payments;
        }

        protected double getDebts() {
            return _debts;
        }

    }

}
