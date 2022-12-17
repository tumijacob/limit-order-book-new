package Main;

import process.limit.order.ProcessLimitOrder;
import service.impl.OrderBookServiceImpl;

import java.util.Scanner;

public class LimitOrderBookApp {

    public static void main(String[] args) {
        OrderBookServiceImpl service = new OrderBookServiceImpl();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("END")) {
                System.out.println(service.toString());
                break;
            }
            String[] splitInput = input.split(" ");

            ProcessLimitOrder processLimitOrder = new ProcessLimitOrder();
            processLimitOrder.parseLimitOrder(splitInput);
        }
    }

}
