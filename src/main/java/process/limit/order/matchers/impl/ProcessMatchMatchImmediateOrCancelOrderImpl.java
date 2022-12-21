package process.limit.order.matchers.impl;

import domain.ImmediateOrCancelLimitOrder;
import domain.LimitOrder;
import service.impl.OrderBookServiceImpl;

public class ProcessMatchMatchImmediateOrCancelOrderImpl extends ProcessMatchLimitOrderImpl {

    public ProcessMatchMatchImmediateOrCancelOrderImpl(ImmediateOrCancelLimitOrder order) {
        super(order);
    }

    @Override
    protected void matchBuyOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = order;

        if (orderBook.hasSellOrder() && orderBook.peekSellList().getPrice() <= currOrder.getPrice()) {

            LimitOrder other = orderBook.peekSellList();

            int executionPrice = other.getPrice();
            int executionQuantity = Math.min(currOrder.getQuantity(), other.getQuantity());

            executedAmount += executionPrice * executionQuantity;

            if (executionQuantity == other.getQuantity()) {
                orderBook.removeSellHead();
            } else {
                other.decreaseQuantity(executionQuantity);
            }
        }

        System.out.println(executedAmount);
    }


    @Override
    protected void matchSellOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = order;

        if (orderBook.hasBuyOrder() && orderBook.peekBuyList().getPrice() >= currOrder.getPrice()) {

            LimitOrder other = orderBook.peekBuyList();
            int executionPrice = other.getPrice();
            int executionQuantity = Math.min(currOrder.getQuantity(), other.getQuantity());

            executedAmount += executionPrice * executionQuantity;

            if (executionQuantity == other.getQuantity()) {
                orderBook.removeBuyHead();
            } else {
                other.decreaseQuantity(executionQuantity);
            }
        }

        System.out.println(executedAmount);
    }

}
