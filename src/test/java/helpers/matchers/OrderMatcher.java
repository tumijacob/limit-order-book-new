package helpers.matchers;

import domain.Order;
import org.hamcrest.Description;

import java.util.Objects;

public class OrderMatcher extends AbstractTypeSafeMatcher<Order> {

    public OrderMatcher(Order expected) {
        super(expected);
    }

    @Override
    public void appendDescription(Description description, Order order) {
        description.appendText("An Order with the following state:")
                .appendText("\nId: ").appendValue(order.getId())
                .appendText("\nQuantity: ").appendValue(order.getQuantity())
                .appendText("\nPrice: ").appendValue(order.getPrice())
                .appendText("\nTimestamp: ").appendValue(order.getTimeStamp())
                .appendText("\nSide Total: ").appendValue(order.getSide());
    }

    @Override
    protected boolean matchesSafely(Order actual) {
        return Objects.equals(actual.getId(), expected.getId()) &&
                Objects.equals(actual.getQuantity(), expected.getQuantity()) &&
                Objects.equals(actual.getPrice(), expected.getPrice()) &&
                Objects.equals(actual.getSide(), expected.getSide()) &&
                Objects.equals(actual.getTimeStamp(), expected.getTimeStamp());
    }
}
