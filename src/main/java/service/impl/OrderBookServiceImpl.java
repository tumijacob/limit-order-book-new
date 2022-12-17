package service.impl;

import Enums.Side;
import domain.LimitOrder;
import service.OrderBookService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class OrderBookServiceImpl implements OrderBookService {
    private final TreeSet<LimitOrder> buyOrderList = new TreeSet<>();
    private final TreeSet<LimitOrder> sellOrderList = new TreeSet<>();
    private final Map<Long, LimitOrder> orders = new HashMap<>();

    public boolean hasBuyOrder() {
        return !buyOrderList.isEmpty();
    }

    public boolean hasSellOrder() {
        return !sellOrderList.isEmpty();
    }

    @Override
    public void addOrder(LimitOrder order) {
        orders.put(order.getId(), order);

        if (order.getSide().toString().equalsIgnoreCase(Side.BUY.toString())) {
            buyOrderList.add(order);
        } else {
            sellOrderList.add(order);
        }
    }

    @Override
    public void modifyOrder(Long orderId, int newQuantity, BigDecimal newPrice) {
        if (!orders.containsKey(orderId)) {
            return;
        }
        LimitOrder currOrder = orders.get(orderId);

        if (newPrice == currOrder.getPrice() && newQuantity < currOrder.getQuantity()) {
            currOrder.decreaseQuantity(currOrder.getQuantity() - newQuantity);
        } else {
            deleteOrder(orderId);

            Side currSide = currOrder.getSide();
            long currTime = Instant.now().getNano();

            LimitOrder newOrder = new LimitOrder(orderId, newQuantity,currSide,currTime,newPrice);

            orders.put(orderId, newOrder);
            addOrder(newOrder);
        }

    }
    @Override
    public void deleteOrder(Long orderId) {
        if (!orders.containsKey(orderId)) {
            return;
        }

        LimitOrder currOrder = orders.get(orderId);
        orders.remove(orderId);

        if (currOrder.getSide().toString().equalsIgnoreCase(Side.BUY.toString())) {
            buyOrderList.remove(currOrder);
        } else {
            sellOrderList.remove(currOrder);
        }
    }

    public void deleteBuyHead() {
        LimitOrder currOrder = buyOrderList.pollFirst();
        orders.remove(currOrder.getId());
    }

    public void deleteRemoveSellHead() {
        LimitOrder currOrder = sellOrderList.pollFirst();
        orders.remove(currOrder.getId());
    }

    public void removeBuyHead() {
        LimitOrder currOrder = buyOrderList.pollFirst();
        orders.remove(currOrder.getId());
    }

    public void removeSellHead() {
        LimitOrder currOrder = sellOrderList.pollFirst();
        orders.remove(currOrder.getId());
    }

    public LimitOrder peekBuyList() {
        assert buyOrderList.size() != 0;

        return buyOrderList.first();
    }

    public LimitOrder peekSellList() {
        assert sellOrderList.size() != 0;

        return sellOrderList.first();
    }

    public boolean isSellOrderFullyExecutable(LimitOrder currOrder) {
        int executableQuantity = 0;

        for (LimitOrder buyOrder : buyOrderList) {
            if (buyOrder.getPrice().compareTo(currOrder.getPrice()) == -1 ) {
                break;
            }

            executableQuantity += buyOrder.getQuantity();

            if (executableQuantity >= currOrder.getQuantity()) {
                return true;
            }
        }

        return false;
    }

    public boolean isBuyOrderFullyExecutable(LimitOrder currOrder) {
        int executableQuantity = 0;

        for (LimitOrder sellOrder : sellOrderList) {
            if (currOrder.getPrice().compareTo(sellOrder.getPrice()) == -1 ) {
                break;
            }

            executableQuantity += sellOrder.getQuantity();

            if (executableQuantity >= currOrder.getQuantity()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Buy:");
        for (LimitOrder order : buyOrderList) {
            sb.append(" " + order.toString());
        }

        sb.append("\n" + "Sell:");
        for (LimitOrder order : sellOrderList) {
            sb.append(" " + order.toString());
        }

        return sb.toString();
    }


}
