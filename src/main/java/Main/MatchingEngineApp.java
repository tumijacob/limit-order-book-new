package Main;

import Main.interceptors.MatchingEngineInterceptor;
import process.limit.order.matchers.Processor;
import service.impl.OrderBookServiceImpl;

import java.util.Scanner;

public class MatchingEngineApp {

    public static void main(String[] args) {
        OrderBookServiceImpl orderBook = new OrderBookServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("END")) {
                System.out.println(orderBook);
                break;
            }
            Processor process = MatchingEngineInterceptor.interceptInput(input);
            process.processOrder(orderBook);
        }
    }

}
