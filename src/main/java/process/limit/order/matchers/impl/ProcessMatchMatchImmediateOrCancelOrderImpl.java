package process.limit.order.matchers.impl;

import domain.ImmediateOrCancelLimitOrder;
import domain.LimitOrder;
import enums.Side;
import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;

public class ProcessMatchMatchImmediateOrCancelOrderImpl implements Processor {
    private ImmediateOrCancelLimitOrder immediateOrCancelLimitOrder;

    public ProcessMatchMatchImmediateOrCancelOrderImpl(ImmediateOrCancelLimitOrder immediateOrCancelLimitOrder) {
        this.immediateOrCancelLimitOrder = immediateOrCancelLimitOrder;
    }

    public ImmediateOrCancelLimitOrder getImmediateOrCancelLimitOrder() {
        return immediateOrCancelLimitOrder;
    }

    public void setImmediateOrCancelLimitOrder(ImmediateOrCancelLimitOrder immediateOrCancelLimitOrder) {
        this.immediateOrCancelLimitOrder = immediateOrCancelLimitOrder;
    }

    public void matchBuyOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = immediateOrCancelLimitOrder.getLimitOrder();

        if (orderBook.hasSellOrder() && orderBook.peekSellList().getOrder().getPrice() <= currOrder.getOrder().getPrice()) {

            LimitOrder other = orderBook.peekSellList();

            int executionPrice = other.getOrder().getPrice();
            int executionQuantity = Math.min(currOrder.getOrder().getQuantity(), other.getOrder().getQuantity());

            executedAmount += executionPrice * executionQuantity;

            if (executionQuantity == other.getOrder().getQuantity()) {
                orderBook.removeSellHead();
            } else {
                other.getOrder().decreaseQuantity(executionQuantity);
            }
        }

        System.out.println(executedAmount);
    }


    public void matchSellOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = immediateOrCancelLimitOrder.getLimitOrder();

        if (orderBook.hasBuyOrder() && orderBook.peekBuyList().getOrder().getPrice() >= currOrder.getOrder().getPrice()) {

            LimitOrder other = orderBook.peekBuyList();
            int executionPrice = other.getOrder().getPrice();
            int executionQuantity = Math.min(currOrder.getOrder().getQuantity(), other.getOrder().getQuantity());

            executedAmount += executionPrice * executionQuantity;

            if (executionQuantity == other.getOrder().getQuantity()) {
                orderBook.removeBuyHead();
            } else {
                other.getOrder().decreaseQuantity(executionQuantity);
            }
        }

        System.out.println(executedAmount);
    }

    @Override
    public void processOrder(OrderBookServiceImpl orderBook) {
        if (immediateOrCancelLimitOrder.getLimitOrder().getOrder().getSide().equals(Side.BUY)) {
            matchBuyOrder(orderBook);
        } else {
            matchSellOrder(orderBook);
        }

    }
}
