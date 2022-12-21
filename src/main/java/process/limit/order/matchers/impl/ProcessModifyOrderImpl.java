package process.limit.order.matchers.impl;

import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;

public class ProcessModifyOrderImpl implements Processor {
    private Long orderId;
    private int newQuantity;
    private int newPrice;

    public ProcessModifyOrderImpl(Long orderId, int newQuantity, int newPrice) {
        this.orderId = orderId;
        this.newQuantity = newQuantity;
        this.newPrice = newPrice;
    }

    @Override
    public void processOrder(OrderBookServiceImpl orderBook) {
        orderBook.modifyOrder(orderId, newQuantity, newPrice);
    }

}
