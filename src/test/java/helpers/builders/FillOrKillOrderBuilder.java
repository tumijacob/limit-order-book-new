package helpers.builders;

import domain.FillOrKillOrder;
import domain.LimitOrder;

public class FillOrKillOrderBuilder extends AbstractBuilder<FillOrKillOrder> {
    private LimitOrder limitOrder;


    public FillOrKillOrderBuilder withFillOrKillOrderBuilder(LimitOrder limitOrder) {
        this.limitOrder = limitOrder;
        return this;
    }

    @Override
    public FillOrKillOrder build() {
        FillOrKillOrder fokOrder = new FillOrKillOrder();
        fokOrder.setLimitOrder(limitOrder);
        return fokOrder;
    }
}
