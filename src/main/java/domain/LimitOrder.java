package domain;

import Enums.Side;

import java.math.BigDecimal;


public class LimitOrder extends Order implements Comparable<LimitOrder> {
    private BigDecimal price;

    public LimitOrder(Long id,int quantity,Side side, long timeStamp, BigDecimal price) {
        super(id, quantity, side, timeStamp);
        this.price = price;
    }

    @Override
    public int compareTo(LimitOrder other) {
        assert getSide().equals(other.getSide());

        if (getSide().equals(Side.BUY.toString())) {
            return compareToBuyOrder(other);
        } else {
            return compareToSellOrder(other);
        }
    }

    private int compareToBuyOrder(LimitOrder other) {
        if (price.compareTo(other.price) == -1) {
            return 1;
        } else if (price.compareTo(other.price) == 1) {
            return -1;
        } else {
            return Long.compare(getTimeStamp(), other.getTimeStamp());
        }
    }

    private int compareToSellOrder(LimitOrder other) {
        if (price.compareTo(other.price) == 1) {
            return 1;
        } else if (price.compareTo(other.price) == -1) {
            return -1;
        } else {
            return Long.compare(getTimeStamp(), other.getTimeStamp());
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean isPriceless() {
        return true;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return getQuantity() + "@" + price + "#" + getId();
    }
}
