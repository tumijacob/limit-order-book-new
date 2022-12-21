package Main.interceptors;

import domain.FillOrKillOrder;
import domain.ImmediateOrCancelLimitOrder;
import domain.LimitOrder;
import domain.MarketOrder;
import enums.Side;
import process.limit.order.matchers.Processor;
import process.limit.order.matchers.impl.*;

import java.time.Instant;

public class MatchingEngineInterceptor {
    public static Processor interceptInput(String input) {
        String[] splitInput = input.split(" ");
        if (splitInput.length < 2) {
            throw new IllegalArgumentException("The number of arguments is insufficient.");
        }

        String command = splitInput[0];
        switch (command) {
            case "SUB":
                return parseOrder(splitInput);
            case "CXL":
                return deleteOrder(splitInput);
            case "CRP":
                return modifyOrder(splitInput);
            default:
                throw new IllegalArgumentException("The command " + command + " is not valid.");
        }
    }

    private static Processor parseOrder(String[] splitInput) {
        String orderType = splitInput[1];

        switch (orderType) {
            case "LO":
                return interceptLimitOrder(splitInput);
            case "MO":
                return interceptMarketOrder(splitInput);
            case "IOC":
                return interceptIOCOrder(splitInput);
            case "FOK":
                return interceptFokOrder(splitInput);
            default:
                throw new IllegalArgumentException("The order type " + orderType + " is invalid.");
        }
    }

    private static ProcessMatchLimitOrderImpl interceptLimitOrder(String[] splitInput) {
        if (splitInput.length < 6) {
            throw new IllegalArgumentException("The number of arguments for LimitOrder is insufficient.");
        }

        Side side = interceptSide(splitInput[2]);
        String orderId = splitInput[3];
        int quantity = Integer.parseInt(splitInput[4]);
        int price = Integer.parseInt(splitInput[5]);
        long currTime = Instant.now().getNano();

        LimitOrder order = new LimitOrder(Long.valueOf(orderId), quantity, side, currTime, price);

        return new ProcessMatchLimitOrderImpl(order);
    }

    private static ProcessMatchMarketOrderImpl interceptMarketOrder(String[] splitInput) {
        if (splitInput.length < 5) {
            throw new IllegalArgumentException("The number of arguments for MarketOrder is insufficient.");
        }

        Side side = interceptSide(splitInput[2]);
        String orderId = splitInput[3];
        int quantity = Integer.parseInt(splitInput[4]);
        long currTime = Instant.now().getNano();

        MarketOrder order = new MarketOrder(Long.valueOf(orderId), quantity, side, currTime);

        return new ProcessMatchMarketOrderImpl(order);
    }

    private static ProcessDeleteOrderImpl deleteOrder(String[] splitInput) {
        String orderId = splitInput[1];
        return new ProcessDeleteOrderImpl(Long.valueOf(orderId));
    }

    private static Side interceptSide(String sideStr) {
        switch (sideStr) {
            case "B":
                return Side.BUY;
            case "S":
                return Side.SELL;
            default:
                throw new IllegalArgumentException("The side parameter is invalid.");
        }
    }

    private static ProcessMatchMatchImmediateOrCancelOrderImpl interceptIOCOrder(String[] splitInput) {
        if (splitInput.length < 6) {
            throw new IllegalArgumentException("The number of arguments for IocOrder is insufficient.");
        }

        Side side = interceptSide(splitInput[2]);
        Long orderId = Long.valueOf(splitInput[3]);
        int quantity = Integer.parseInt(splitInput[4]);
        int price = Integer.parseInt(splitInput[5]);
        long currTime = Instant.now().getNano();

        ImmediateOrCancelLimitOrder order = new ImmediateOrCancelLimitOrder(orderId, quantity, side, currTime, price);

        return new ProcessMatchMatchImmediateOrCancelOrderImpl(order);
    }

    private static ProcessMatchFillOrKillOrderImpl interceptFokOrder(String[] splitInput) {
        if (splitInput.length < 6) {
            throw new IllegalArgumentException("The number of arguments for FokOrder is insufficient.");
        }

        Side side = interceptSide(splitInput[2]);
        Long orderId = Long.valueOf(splitInput[3]);
        int quantity = Integer.parseInt(splitInput[4]);
        int price = Integer.parseInt(splitInput[5]);
        long currTime = Instant.now().getNano();

        FillOrKillOrder order = new FillOrKillOrder(orderId, quantity, side, currTime, price);

        return new ProcessMatchFillOrKillOrderImpl(order);
    }

    private static ProcessModifyOrderImpl modifyOrder(String[] splitInput) {
        if (splitInput.length < 4) {
            throw new IllegalArgumentException("The number of arguments for CRP insufficient.");
        }
        Long orderId = Long.valueOf(splitInput[1]);
        int newQuantity = Integer.parseInt(splitInput[2]);
        int newPrice = Integer.parseInt(splitInput[3]);

        return new ProcessModifyOrderImpl(orderId, newQuantity, newPrice);
    }

}
