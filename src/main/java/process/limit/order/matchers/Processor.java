package process.limit.order.matchers;

import service.OrderBookService;
import service.impl.OrderBookServiceImpl;

public interface Processor {
    void processOrder(OrderBookServiceImpl orderBook);
}
