package test.service;

import domain.LimitOrder;
import domain.Order;
import enums.Side;
import helpers.builders.LimitOrderBuilder;
import helpers.builders.OrderBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import service.impl.OrderBookServiceImpl;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderBookServiceImplTest {

    @Spy
    Map<Long, LimitOrder> orders = new HashMap<>();

    @InjectMocks
    private OrderBookServiceImpl orderBookService;

    private long currTime = Instant.now().getNano();


    @Test
    public void testAddLimitOrderWithBuySide_shouldAddLimitOrderToBuyOrderList() {
        //setup
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


        orderBookService.addOrder(limitOrder);

        assertTrue(orderBookService.hasBuyOrder());

    }

    @Test
    public void testAddLimitOrderWithSellSide_shouldAddLimitOrderToSellOrderList() {
        //setup
        Order order = OrderBuilder.anOrderBuilder()
                .withId(1)
                .withSide(Side.SELL)
                .withQuantity(20)
                .withPrice(9)
                .withTimestamp(currTime)
                .build();

        LimitOrder limitOrder = LimitOrderBuilder.aLimitOrder()
                .withOrder(order)
                .build();

        orderBookService.addOrder(limitOrder);

        assertTrue(orderBookService.hasSellOrder());

    }

    @Test
    public void testModifyLimitOrderWithOrderIdNotInOrdersMap_shouldReturnNothing() {
        //setup
        Order order = OrderBuilder.anOrderBuilder()
                .withId(1)
                .withSide(Side.SELL)
                .withQuantity(20)
                .withPrice(9)
                .withTimestamp(currTime)
                .build();

        LimitOrder limitOrder = LimitOrderBuilder.aLimitOrder()
                .withOrder(order)
                .build();

        Map<Long, LimitOrder> orders = new HashMap<>();
        orders.put(2L, limitOrder);


    }

    @Test
    public void testModifyLimitOrder() {
        //setup
        Order order = OrderBuilder.anOrderBuilder()
                .withId(1)
                .withSide(Side.SELL)
                .withQuantity(20)
                .withPrice(9)
                .withTimestamp(currTime)
                .build();

        LimitOrder limitOrder = LimitOrderBuilder.aLimitOrder()
                .withOrder(order)
                .build();

        orders.put(2L, limitOrder);

        // Mocks
        when(orderBookService.orders.get(2L)).thenReturn(limitOrder);

    }
    @Test
    public void testDeleteLimitOrderWithBuySide_shouldRemoveFromList() {
        //setup
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

        when(orders.get(1L)).thenReturn(limitOrder);

        orderBookService.deleteOrder(1L);

        assertFalse(orderBookService.hasBuyOrder());

    }

    @Test
    public void testDeleteLimitOrderWithSellSide_shouldRemoveFromList() {
        //setup
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

        when(orders.get(1L)).thenReturn(limitOrder);

        orderBookService.deleteOrder(1L);

        assertFalse(orderBookService.hasSellOrder());

    }



}
