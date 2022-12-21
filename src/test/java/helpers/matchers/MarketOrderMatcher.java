package helpers.matchers;

import domain.MarketOrder;
import org.hamcrest.Description;

import java.util.Objects;

public class MarketOrderMatcher extends AbstractTypeSafeMatcher<MarketOrder> {

    public MarketOrderMatcher(MarketOrder expected) {
        super(expected);
    }

    @Override
    public void appendDescription(Description description, MarketOrder marketOrder) {
        description.appendText("A MarketOrder with the following state:")
                .appendText("\nMarketOrder: ").appendValue(marketOrder.getOrder());

    }

    @Override
    protected boolean matchesSafely(MarketOrder actual) {
        return Objects.equals(actual.getOrder(), expected.getOrder());
    }
}
