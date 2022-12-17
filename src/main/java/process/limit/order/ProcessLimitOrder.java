package process.limit.order;

import Enums.Side;
import domain.LimitOrder;
import service.impl.OrderBookServiceImpl;

import java.math.BigDecimal;
import java.time.Instant;

public class ProcessLimitOrder implements Processor {
    protected LimitOrder order;

    public ProcessLimitOrder() {
    }

    public ProcessLimitOrder(LimitOrder order) {
        this.order = order;
    }

    @Override
        public void processOrder(OrderBookServiceImpl orderBook) {
        if(order.getSide().toString().equalsIgnoreCase(Side.BUY.toString())) {
            matchBuyOrder(orderBook);
        } else {
            matchSellOrder(orderBook);
        }

    }

    protected void matchBuyOrder(OrderBookServiceImpl orderBook) {
        int executedAmount = 0;
        LimitOrder currOrder = order;

        while (currOrder.getQuantity() > 0 && orderBook.hasSellOrder()
                && orderBook.peekSellList().getPrice().compareTo(currOrder.getPrice()) == -1 ) {

            LimitOrder other = orderBook.peekSellList();
            int executionPrice = Integer.valueOf(String.valueOf(other.getPrice()));
            int executionQuantity = Math.min(currOrder.getQuantity(), other.getQuantity());

            executedAmount += executionPrice * executionQuantity;

            if(executionQuantity == other.getQuantity()) {
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
                && orderBook.peekBuyList().getPrice().compareTo(currOrder.getPrice()) == 1 ) {

            LimitOrder other = orderBook.peekBuyList();
            int executionPrice = Integer.valueOf(String.valueOf(other.getPrice()));
            int executionQuantity = Math.min(currOrder.getQuantity(), other.getQuantity());

            executedAmount += executionPrice * executionQuantity;
            if(executionQuantity == other.getQuantity()) {
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

    public static ProcessLimitOrder parseLimitOrder(String[] splitInput) {
        if (splitInput.length < 6) {
            throw new IllegalArgumentException("Insufficient number or arguments for LimitOrder");
        }

        Side side = parseSide(splitInput[2]);
        String orderId = splitInput[3];
        int quantity = Integer.parseInt(splitInput[4]);
        int price = Integer.parseInt(splitInput[5]);
        long currTime = Instant.now().getNano();

        LimitOrder order = new LimitOrder(Long.valueOf(orderId), quantity, side, currTime, new BigDecimal(price));

        return new ProcessLimitOrder(order);
    }

    private static Side parseSide(String sideStr) {
        switch(sideStr) {
            case "Buy":
                return Side.BUY;
            case "Sell":
                return Side.SELL;
            default:
                throw new IllegalArgumentException("Invalid entry.");
        }
    }


}
