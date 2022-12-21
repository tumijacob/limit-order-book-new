package process.limit.order.matchers.impl;

import domain.FillOrKillOrder;
import domain.LimitOrder;
import enums.Side;
import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;


public class ProcessMatchFillOrKillOrderImpl implements Processor {
    private FillOrKillOrder fillOrKillOrder;

    public ProcessMatchFillOrKillOrderImpl(FillOrKillOrder fillOrKillOrder) {
        this.fillOrKillOrder = fillOrKillOrder;
    }

    public FillOrKillOrder getFillOrKillOrder() {
        return fillOrKillOrder;
    }

    public void setFillOrKillOrder(FillOrKillOrder fillOrKillOrder) {
        this.fillOrKillOrder = fillOrKillOrder;
    }

    public void matchBuyOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = fillOrKillOrder.getLimitOrder();

        if (orderBook.hasSellOrder() && orderBook.isBuyOrderFullyExecutable(currOrder)) {
            while (currOrder.getOrder().getQuantity() > 0) {
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
        }

        System.out.println(executedAmount);
    }

    public void matchSellOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = fillOrKillOrder.getLimitOrder();

        if (orderBook.hasBuyOrder() && orderBook.isSellOrderFullyExecutable(currOrder)) {
            while (currOrder.getOrder().getQuantity() > 0) {
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
        }

        System.out.println(executedAmount);
    }

    @Override
    public void processOrder(OrderBookServiceImpl orderBook) {
        if (fillOrKillOrder.getLimitOrder().getOrder().getSide().equals(Side.BUY)) {
            matchBuyOrder(orderBook);
        } else {
            matchSellOrder(orderBook);
        }

    }
}
