package domain;

import enums.Side;

import java.math.BigDecimal;

public class FillOrKillOrder extends LimitOrder {

    public FillOrKillOrder(Long orderId, int quantity, Side side, Long timestamp, int price) {
        super(orderId, quantity, side, timestamp, price);
    }
}
