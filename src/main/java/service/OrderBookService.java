package service;

import domain.LimitOrder;

import java.math.BigDecimal;

public interface OrderBookService {

    void addOrder(LimitOrder order);
    void modifyOrder(Long orderId, int newQuantity, int newPrice);
    void deleteOrder(Long orderId);
}
