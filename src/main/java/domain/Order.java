package domain;

import enums.Side;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {
    private static final long serialVersionUID = -420323402475932612L;

    private Long id;
    private int quantity;
    private int price;
    private Side side;
    private long timeStamp;

    public Order() {
    }

    public Order(Long id, int quantity, int price, Side side, long timeStamp) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.timeStamp = timeStamp;
    }

    public Order(Long id, int quantity, Side side, long timeStamp) {
        this.id = id;
        this.quantity = quantity;;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
