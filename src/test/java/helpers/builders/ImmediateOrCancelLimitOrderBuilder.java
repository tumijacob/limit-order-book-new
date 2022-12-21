package helpers.builders;

import domain.ImmediateOrCancelLimitOrder;
import domain.LimitOrder;
import domain.Order;

public class ImmediateOrCancelLimitOrderBuilder extends AbstractBuilder<ImmediateOrCancelLimitOrder> {
    private LimitOrder limitOrder;

    public ImmediateOrCancelLimitOrderBuilder withImmediateOrCancelLimitOrderBuilder(LimitOrder limitOrder) {
        this.limitOrder = limitOrder;
        return this;
    }

    @Override
    public ImmediateOrCancelLimitOrder build() {
        ImmediateOrCancelLimitOrder iocOrder = new ImmediateOrCancelLimitOrder();
        iocOrder.setLimitOrder(limitOrder);
        return iocOrder;
    }

    public static ImmediateOrCancelLimitOrderBuilder anImmediateOrCancelLimitOrderBuilder() {
        return new ImmediateOrCancelLimitOrderBuilder();
    }
}
