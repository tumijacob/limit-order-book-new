package domain;

import enums.Side;

import java.math.BigDecimal;


public class LimitOrder extends Order implements Comparable<LimitOrder> {
    private int price;

    public LimitOrder(Long id, int quantity, Side side, long timeStamp, int price) {
        super(id, quantity, side, timeStamp);
        this.price = price;
    }

    @Override
    public int compareTo(LimitOrder other) {
        assert getSide().equals(other.getSide());

        if (getSide().equals(Side.BUY)) {
            return compareToBuyOrder(other);
        } else {
            return compareToSellOrder(other);
        }
    }

    private int compareToBuyOrder(LimitOrder other) {
        if (price < other.price) {
            return 1;
        } else if (price > other.price) {
            return -1;
        } else {
            return Long.compare(getTimeStamp(), other.getTimeStamp());
        }
    }

    private int compareToSellOrder(LimitOrder other) {
        if (price > other.price) {
            return 1;
        } else if (price < other.price) {
            return -1;
        } else {
            return Long.compare(getTimeStamp(), other.getTimeStamp());
        }
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean isPriceless() {
        return true;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return getQuantity() + "@" + price + "#" + getId();
    }
}
