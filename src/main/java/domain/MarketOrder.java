package domain;

import java.io.Serializable;
import java.util.Objects;

public class MarketOrder implements Serializable {
    private static final long serialVersionUID = 5116010699092377941L;
    private Order order;

    public MarketOrder() {
    }

    public MarketOrder(Order order) {
        this.order = order;
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
        if (!(o instanceof MarketOrder)) return false;
        MarketOrder that = (MarketOrder) o;
        return Objects.equals(getOrder(), that.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder());
    }

    @Override
    public String toString() {
        return "MarketOrder{" +
                "order=" + order +
                '}';
    }
}
