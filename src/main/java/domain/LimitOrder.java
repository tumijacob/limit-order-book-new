package domain;

import enums.Side;

import java.io.Serializable;
import java.util.Objects;


public class LimitOrder implements Comparable<LimitOrder>, Serializable {
    private static final long serialVersionUID = 127385276176906729L;

    private Order order;

    public LimitOrder() {
    }

    public LimitOrder(Order order) {
        this.order = order;
    }

    @Override
    public int compareTo(LimitOrder other) {
        assert order.getSide().equals(other.order.getSide());

        if (other.getOrder().getSide().equals(Side.BUY)) {
            return compareToBuyOrder(other);
        } else {
            return compareToSellOrder(other);
        }
    }

    private int compareToBuyOrder(LimitOrder other) {
        if (order.getPrice() < other.getOrder().getPrice()) {
            return 1;
        } else if (order.getPrice() > other.getOrder().getPrice()) {
            return -1;
        } else {
            return Long.compare(order.getTimeStamp(), other.getOrder().getTimeStamp());
        }
    }

    private int compareToSellOrder(LimitOrder other) {
        if (order.getPrice() > other.getOrder().getPrice()) {
            return 1;
        } else if (order.getPrice() < other.getOrder().getPrice()) {
            return -1;
        } else {
            return Long.compare(order.getTimeStamp(), other.getOrder().getTimeStamp());
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LimitOrder)) return false;
        LimitOrder that = (LimitOrder) o;
        return Objects.equals(getOrder(), that.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder());
    }

    @Override
    public String toString() {
        return "LimitOrder{" +
                "order=" + order +
                '}';
    }
}
