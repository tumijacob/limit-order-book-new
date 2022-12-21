package process.limit.order.matchers.impl;

import domain.LimitOrder;
import enums.Side;
import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;

public class ProcessMatchLimitOrderImpl implements Processor {
    private LimitOrder limitOrder;

    public ProcessMatchLimitOrderImpl(LimitOrder limitOrder) {
        this.limitOrder = limitOrder;
    }

    public LimitOrder getLimitOrder() {
        return limitOrder;
    }

    public void setLimitOrder(LimitOrder limitOrder) {
        this.limitOrder = limitOrder;
    }

    @Override
    public void processOrder(OrderBookServiceImpl orderBook) {
        if (limitOrder.getOrder().getSide().equals(Side.BUY)) {
            matchBuyOrder(orderBook);
        } else {
            matchSellOrder(orderBook);
        }

    }

    protected void matchBuyOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = limitOrder;

        while (currOrder.getOrder().getQuantity() > 0 && orderBook.hasSellOrder()
                && orderBook.peekSellList().getOrder().getPrice() <= currOrder.getOrder().getPrice()) {

            LimitOrder other = orderBook.peekSellList();
            int executionPrice = other.getOrder().getPrice();
            int executionQuantity = Math.min(currOrder.getOrder().getQuantity(), other.getOrder().getQuantity());

            executedAmount += executionPrice * executionQuantity;

            if (executionQuantity == other.getOrder().getQuantity()) {
                orderBook.removeSellHead();
            } else {
                other.getOrder().decreaseQuantity(executionQuantity);
            }
            currOrder.getOrder().decreaseQuantity(executionQuantity);
        }

        System.out.println(executedAmount);

        if (currOrder.getOrder().getQuantity() > 0) {
            orderBook.addOrder(currOrder);
        }
    }

    protected void matchSellOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = limitOrder;

        while (currOrder.getOrder().getQuantity() > 0 && orderBook.hasBuyOrder()
                && orderBook.peekBuyList().getOrder().getPrice() >= currOrder.getOrder().getPrice()) {

            LimitOrder other = orderBook.peekBuyList();
            int executionPrice = other.getOrder().getPrice();
            int executionQuantity = Math.min(currOrder.getOrder().getQuantity(), other.getOrder().getQuantity());

            executedAmount += executionPrice * executionQuantity;
            if (executionQuantity == other.getOrder().getQuantity()) {
                orderBook.removeBuyHead();
            } else {
                other.getOrder().decreaseQuantity(executionQuantity);
            }
            currOrder.getOrder().decreaseQuantity(executionQuantity);
        }

        System.out.println(executedAmount);

        if (currOrder.getOrder().getQuantity() > 0) {
            orderBook.addOrder(currOrder);
        }
    }

}
