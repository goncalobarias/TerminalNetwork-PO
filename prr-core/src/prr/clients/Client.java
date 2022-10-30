package prr.clients;

import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.io.Serializable;
import java.io.Serial;

import prr.util.ClientVisitor;
import prr.util.NaturalTextComparator;
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
    private static final Comparator<String> ID_COMPARATOR = new NaturalTextComparator();
    public static final Comparator<Client> DEBT_COMPARATOR = new DebtComparator();

    private static class DebtComparator implements Comparator<Client>,
      Serializable {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210231030L;

        public int compare(Client c1, Client c2) {
            return Comparator
                    .comparing(Client::getDebts, Comparator.reverseOrder())
                    .thenComparing(Client::getId, ID_COMPARATOR)
                    .compare(c1, c2);
        }

    }

    public Client(String id, String name, int taxId) {
        _id = id;
        _name = name;
        _taxId = taxId;
        _terminals = new HashMap<String, Terminal>(); // TODO: fix this data structure to a map
        _level = new ClientNormalLevel(this, 0.0, 0.0, 0, 0);
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

    public TariffPlan getTariffPlan() {
        return _level.getTariffPlan();
    }

    public Collection<Notification> readNotifications() {
        Collection<Notification> notifications =
            new LinkedList<>(_notifications);
        _notifications.clear();
        return notifications;
    }

    public void setTariffPlan(TariffPlan plan) {
        _level.setTariffPlan(plan);
    }

    public void setNotificationState(boolean notificationState) {
        _receiveNotifications = notificationState;
    }

    public void resetNumberOfConsecutiveCommunications() {
        _level.resetNumberOfConsecutiveCommunications();
    }

    public boolean hasNotificationsEnabled() {
        return _receiveNotifications;
    }

    public boolean isOwnerOf(Terminal terminal) {
        return _terminals.containsKey(terminal.getTerminalId());
    }

    public void increaseNumberOfConsecutiveTextCommunications() {
        _level.increaseNumberOfConsecutiveTextCommunications();
    }

    public void increaseNumberOfConsecutiveVideoCommunications() {
        _level.increaseNumberOfConsecutiveVideoCommunications();
    }

    public void verifyLevelUpdateConditions() {
        _level.verifyLevelUpdateConditions();
    }

    public void addTerminal(Terminal terminal) {
        _terminals.put(terminal.getTerminalId(), terminal);
    }

    public void notify(Notification notification) {
        _deliveryMethod.deliver(notification);
    }

    public void updateBalance(double delta) {
        _level.updateBalance(delta);
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
            _notifications.add(notification);
        }

    }

    public abstract class Level implements Serializable {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210150052L;

        private double _payments;
        private double _debts;
        private int _numberOfConsecutiveTextCommunications;
        private int _numberOfConsecutiveVideoCommunications;
        private TariffPlan _plan;

        public Level(double payments, double debts,
          int numberOfConsecutiveTextCommunications,
          int numberOfConsecutiveVideoCommunications) {
            _payments = payments;
            _debts = debts;
            _numberOfConsecutiveTextCommunications =
                numberOfConsecutiveTextCommunications;
            _numberOfConsecutiveVideoCommunications =
                numberOfConsecutiveVideoCommunications;
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

        protected double getBalance() {
            return getPayments() - getDebts();
        }

        protected int getNumberOfConsecutiveTextCommunications() {
            return _numberOfConsecutiveTextCommunications;
        }

        protected int getNumberOfConsecutiveVideoCommunications() {
            return _numberOfConsecutiveVideoCommunications;
        }

        protected TariffPlan getTariffPlan() {
            return _plan;
        }

        protected void updateLevel(Level level) {
            Client.this._level = level;
        }

        protected void resetNumberOfConsecutiveCommunications() {
            _numberOfConsecutiveTextCommunications = 0;
            _numberOfConsecutiveVideoCommunications = 0;
        }

        protected void setTariffPlan(TariffPlan plan) {
            _plan = plan;
        }

        public void updateBalance(double delta) {
            if (delta < 0) {
                _debts += (delta * -1);
            } else {
                _debts -= delta;
                _payments += delta;
            }
        }

        protected void increaseNumberOfConsecutiveTextCommunications() {
            _numberOfConsecutiveVideoCommunications = 0;
            _numberOfConsecutiveTextCommunications =
                _numberOfConsecutiveTextCommunications % 2 + 1;
        }

        protected void increaseNumberOfConsecutiveVideoCommunications() {
            _numberOfConsecutiveTextCommunications = 0;
            _numberOfConsecutiveVideoCommunications =
                _numberOfConsecutiveVideoCommunications % 5 + 1;
        }

        protected abstract void verifyLevelUpdateConditions();

    }

}
