package process.limit.order.matchers.impl;

import domain.LimitOrder;
import enums.Side;
import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;

public class ProcessMatchLimitOrderImpl implements Processor {
    protected LimitOrder order;

    public ProcessMatchLimitOrderImpl(LimitOrder order) {
        this.order = order;
    }

    @Override
    public void processOrder(OrderBookServiceImpl orderBook) {
        if (order.getSide().toString().equalsIgnoreCase(Side.BUY.toString())) {
            matchBuyOrder(orderBook);
        } else {
            matchSellOrder(orderBook);
        }

    }

    protected void matchBuyOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = order;

        while (currOrder.getQuantity() > 0 && orderBook.hasSellOrder()
                && orderBook.peekSellList().getPrice() <= currOrder.getPrice()) {

            LimitOrder other = orderBook.peekSellList();
            int executionPrice = other.getPrice();
            int executionQuantity = Math.min(currOrder.getQuantity(), other.getQuantity());

            executedAmount += executionPrice * executionQuantity;

            if (executionQuantity == other.getQuantity()) {
                orderBook.removeSellHead();
            } else {
                other.decreaseQuantity(executionQuantity);
            }
            currOrder.decreaseQuantity(executionQuantity);
        }

        System.out.println(executedAmount);

        if (currOrder.getQuantity() > 0) {
            orderBook.addOrder(currOrder);
        }
    }

    protected void matchSellOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = order;

        while (currOrder.getQuantity() > 0 && orderBook.hasBuyOrder()
                && orderBook.peekBuyList().getPrice() >= currOrder.getPrice()) {

            LimitOrder other = orderBook.peekBuyList();
            int executionPrice = other.getPrice();
            int executionQuantity = Math.min(currOrder.getQuantity(), other.getQuantity());

            executedAmount += executionPrice * executionQuantity;
            if (executionQuantity == other.getQuantity()) {
                orderBook.removeBuyHead();
            } else {
                other.decreaseQuantity(executionQuantity);
            }
            currOrder.decreaseQuantity(executionQuantity);
        }

        System.out.println(executedAmount);

        if (currOrder.getQuantity() > 0) {
            orderBook.addOrder(currOrder);
        }
    }

}
