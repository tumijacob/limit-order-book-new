package process.limit.order.matchers.impl;

import domain.LimitOrder;
import domain.MarketOrder;
import enums.Side;
import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;

public class ProcessMatchMarketOrderImpl implements Processor {
    private MarketOrder marketOrder;

    public ProcessMatchMarketOrderImpl(MarketOrder marketOrder) {
        this.marketOrder = marketOrder;
    }

    @Override
    public void processOrder(OrderBookServiceImpl orderBook) {
        if (marketOrder.getOrder().getSide().equals(Side.BUY)) {
            matchBuyOrder(orderBook);
        } else {
            matchSellOrder(orderBook);
        }

    }

    private void matchBuyOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        MarketOrder currOrder = marketOrder;

        while (currOrder.getOrder().getQuantity() > 0 && orderBook.hasSellOrder()) {
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
    }

    private void matchSellOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        MarketOrder currOrder = marketOrder;

        while (currOrder.getOrder().getQuantity() > 0 && orderBook.hasBuyOrder()) {
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
    }
}
