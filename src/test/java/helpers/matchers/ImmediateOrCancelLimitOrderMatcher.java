package helpers.matchers;

import domain.ImmediateOrCancelLimitOrder;
import org.hamcrest.Description;

import java.util.Objects;

public class ImmediateOrCancelLimitOrderMatcher extends AbstractTypeSafeMatcher<ImmediateOrCancelLimitOrder> {

    public ImmediateOrCancelLimitOrderMatcher(ImmediateOrCancelLimitOrder expected) {
        super(expected);
    }

    @Override
    public void appendDescription(Description description, ImmediateOrCancelLimitOrder immediateOrCancelLimitOrder) {
        description.appendText("A ImmediateOrCancelLimitOrder with the following state:")
                .appendText("\nImmediateOrCancelLimitOrder: ").appendValue(immediateOrCancelLimitOrder.getLimitOrder());

    }

    @Override
    protected boolean matchesSafely(ImmediateOrCancelLimitOrder actual) {
        return Objects.equals(actual.getLimitOrder(), expected.getLimitOrder());
    }
}
