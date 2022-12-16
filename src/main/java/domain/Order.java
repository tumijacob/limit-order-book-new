package domain;

import Enums.Side;

import java.util.Objects;

public abstract class Order {
    private Long id;
    private int quantity;
    private Side side;
    private long timeStamp;

    public Order() {
    }

    public Order(Long id, int quantity, Side side, long timeStamp) {
        this.id = id;
        this.quantity = quantity;
        this.side = side;
        this.timeStamp = timeStamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void decreaseQuantity(int qty) {
        quantity -= qty;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public abstract boolean isPriceless();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getQuantity() == order.getQuantity() && timeStamp == order.timeStamp && Objects.equals(getId(), order.getId()) && getSide() == order.getSide();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getSide());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", side=" + side +
                '}';
    }
}
