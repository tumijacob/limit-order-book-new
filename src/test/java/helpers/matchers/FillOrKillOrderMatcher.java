package helpers.matchers;

import domain.FillOrKillOrder;
import org.hamcrest.Description;

import java.util.Objects;

public class FillOrKillOrderMatcher extends AbstractTypeSafeMatcher<FillOrKillOrder> {


    public FillOrKillOrderMatcher(FillOrKillOrder expected) {
        super(expected);
    }

    @Override
    public void appendDescription(Description description, FillOrKillOrder fillOrKillOrder) {
        description.appendText("A fillOrKillOrder with the following state:")
                .appendText("\nfillOrKillOrder: ").appendValue(fillOrKillOrder.getLimitOrder());

    }

    @Override
    protected boolean matchesSafely(FillOrKillOrder actual) {
        return Objects.equals(actual.getLimitOrder(), expected.getLimitOrder());
    }
}
