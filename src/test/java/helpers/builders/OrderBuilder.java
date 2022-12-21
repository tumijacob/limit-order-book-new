package helpers.builders;

import domain.Order;
import enums.Side;

public class OrderBuilder extends AbstractBuilder<Order> {
    private Long id;
    private int quantity;
    private int price;
    private Side side;
    private long timestamp;

    public OrderBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder withPrice(int price) {
        this.price = price;
        return this;
    }

    public OrderBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderBuilder withSide(Side side) {
        this.side = side;
        return this;
    }

    public OrderBuilder withTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public Order build() {
        Order order = new Order();
        order.setId(id);
        order.setQuantity(quantity);
        order.setPrice(price);
        order.setSide(side);
        order.setTimeStamp(timestamp);
        return order;
    }

    public static OrderBuilder anOrderBuilder() {
        return new OrderBuilder();
    }

}
