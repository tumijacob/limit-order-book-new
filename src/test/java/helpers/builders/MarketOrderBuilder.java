package helpers.builders;

import domain.MarketOrder;
import domain.Order;

public class MarketOrderBuilder extends AbstractBuilder<MarketOrder> {
    private Order order;

    public MarketOrderBuilder withOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public MarketOrder build() {
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setOrder(order);
        return marketOrder;
    }

    public static MarketOrderBuilder aMarketOrder() {
        return new MarketOrderBuilder();
    }
}
