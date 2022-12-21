package domain;

import java.io.Serializable;
import java.util.Objects;

public class FillOrKillOrder implements Serializable {
    private static final long serialVersionUID = -133241366925439622L;

    private LimitOrder limitOrder;

    public FillOrKillOrder() {
    }

    public FillOrKillOrder(LimitOrder limitOrder) {
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
        if (!(o instanceof FillOrKillOrder)) return false;
        FillOrKillOrder that = (FillOrKillOrder) o;
        return Objects.equals(getLimitOrder(), that.getLimitOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLimitOrder());
    }

    @Override
    public String toString() {
        return "FillOrKillOrder{" +
                "limitOrder=" + limitOrder +
                '}';
    }
}
