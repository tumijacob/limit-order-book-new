package process.limit.order;

import service.OrderBookService;
import service.impl.OrderBookServiceImpl;

public interface Processor {
    void processOrder(OrderBookServiceImpl orderBook);
}
