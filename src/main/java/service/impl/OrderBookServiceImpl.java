package service.impl;

import domain.LimitOrder;
import domain.Order;
import enums.Side;
import service.OrderBookService;

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
    public void addOrder(LimitOrder limitOrder) {
        orders.put(limitOrder.getOrder().getId(), limitOrder);

        if (limitOrder.getOrder().getSide().equals(Side.BUY)) {
            buyOrderList.add(limitOrder);
        } else {
            sellOrderList.add(limitOrder);
        }
    }

    @Override
    public void modifyOrder(Long orderId, int newQuantity, int newPrice) {
        if (!orders.containsKey(orderId)) {
            return;
        }
        LimitOrder currOrder = orders.get(orderId);

        if (newPrice == currOrder.getOrder().getPrice() && newQuantity < currOrder.getOrder().getQuantity()) {
            currOrder.getOrder().decreaseQuantity(currOrder.getOrder().getQuantity() - newQuantity);
        } else {
            deleteOrder(orderId);

            Side currSide = currOrder.getOrder().getSide();
            long currTime = Instant.now().getNano();
            Order order = new Order(orderId, newQuantity, newPrice, currSide, currTime);

            LimitOrder newOrder = new LimitOrder(order);

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

        if (currOrder.getOrder().getSide().toString().equalsIgnoreCase(Side.BUY.toString())) {
            buyOrderList.remove(currOrder);
        } else {
            sellOrderList.remove(currOrder);
        }
    }

    public void removeBuyHead() {
        LimitOrder currOrder = buyOrderList.pollFirst();
        orders.remove(currOrder.getOrder().getId());
    }

    public void removeSellHead() {
        LimitOrder currOrder = sellOrderList.pollFirst();
        orders.remove(currOrder.getOrder().getId());
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
            if (buyOrder.getOrder().getPrice() < currOrder.getOrder().getPrice()) {
                break;
            }

            executableQuantity += buyOrder.getOrder().getQuantity();

            if (executableQuantity >= currOrder.getOrder().getQuantity()) {
                return true;
            }
        }

        return false;
    }

    public boolean isBuyOrderFullyExecutable(LimitOrder currOrder) {
        int executableQuantity = 0;

        for (LimitOrder sellOrder : sellOrderList) {
            if (currOrder.getOrder().getPrice() < sellOrder.getOrder().getPrice()) {
                break;
            }

            executableQuantity += sellOrder.getOrder().getQuantity();

            if (executableQuantity >= currOrder.getOrder().getQuantity()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "OrderBookServiceImpl{" +
                "buyOrderList=" + buyOrderList +
                ", sellOrderList=" + sellOrderList +
                ", orders=" + orders +
                '}';
    }


}
