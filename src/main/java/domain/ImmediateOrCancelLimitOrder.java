package domain;

import java.io.Serializable;
import java.util.Objects;

public class ImmediateOrCancelLimitOrder implements Serializable {
    private static final long serialVersionUID = 2692558742713255292L;

    private LimitOrder limitOrder;

    public ImmediateOrCancelLimitOrder() {
    }

    public ImmediateOrCancelLimitOrder(LimitOrder limitOrder) {
        this.limitOrder = limitOrder;
    }

    public LimitOrder getLimitOrder() {
        return limitOrder;
    }

    public void setLimitOrder(LimitOrder limitOrder) {
        this.limitOrder = limitOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmediateOrCancelLimitOrder)) return false;
        ImmediateOrCancelLimitOrder that = (ImmediateOrCancelLimitOrder) o;
        return Objects.equals(getLimitOrder(), that.getLimitOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLimitOrder());
    }

    @Override
    public String toString() {
        return "ImmediateOrCancelLimitOrder{" +
                "limitOrder=" + limitOrder +
                '}';
    }
}
