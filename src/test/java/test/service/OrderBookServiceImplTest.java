package test.service;

import domain.LimitOrder;
import domain.Order;
import enums.Side;
import helpers.builders.LimitOrderBuilder;
import helpers.builders.OrderBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.impl.OrderBookServiceImpl;

import java.time.Instant;
import java.util.TreeSet;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class OrderBookServiceImplTest {

    @Mock
    private OrderBookServiceImpl orderBookService;

    private long currTime = Instant.now().getNano();


    @Test
    public void testAddLimitOrderWithBuySide_shouldAddLimitOrderToBuyOrderList() {
        Order order = OrderBuilder.anOrderBuilder()
                .withId(1)
                .withSide(Side.BUY)
                .withQuantity(20)
                .withPrice(9)
                .withTimestamp(currTime)
                .build();

        LimitOrder limitOrder = LimitOrderBuilder.aLimitOrder()
                .withOrder(order)
                .build();

        TreeSet<LimitOrder> buyOrderList = new TreeSet<>();
        buyOrderList.add(limitOrder);

        orderBookService.addOrder(limitOrder);

        assertEquals(false,orderBookService.hasBuyOrder());


    }
}
