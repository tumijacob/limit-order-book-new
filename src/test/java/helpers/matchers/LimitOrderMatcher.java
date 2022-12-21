package helpers.matchers;

import domain.LimitOrder;
import org.hamcrest.Description;

import java.util.Objects;

public class LimitOrderMatcher extends AbstractTypeSafeMatcher<LimitOrder> {

    public LimitOrderMatcher(LimitOrder expected) {
        super(expected);
    }

    @Override
    public void appendDescription(Description description, LimitOrder limitOrder) {
        description.appendText("A LimitOrder with the following state:")
                .appendText("\nLimitOrder: ").appendValue(limitOrder.getOrder());

    }

    @Override
    protected boolean matchesSafely(LimitOrder actual) {
        return Objects.equals(actual.getOrder(), expected.getOrder());
    }
}
