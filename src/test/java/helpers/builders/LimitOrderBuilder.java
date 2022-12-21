package helpers.builders;

import domain.LimitOrder;
import domain.Order;

public class LimitOrderBuilder extends AbstractBuilder<LimitOrder> {
    private Order order;

    public LimitOrderBuilder withOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public LimitOrder build() {
        LimitOrder limitOrder = new LimitOrder();
        limitOrder.setOrder(order);
        return limitOrder;
    }

    public static LimitOrderBuilder aLimitOrder() {
        return new LimitOrderBuilder();
    }
}
