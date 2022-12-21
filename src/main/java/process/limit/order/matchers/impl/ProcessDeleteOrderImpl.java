package process.limit.order.matchers.impl;

import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;

public class ProcessDeleteOrderImpl implements Processor {
    private Long orderId;

    public ProcessDeleteOrderImpl(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public void processOrder(OrderBookServiceImpl service) {
        service.deleteOrder(orderId);

    }
}
