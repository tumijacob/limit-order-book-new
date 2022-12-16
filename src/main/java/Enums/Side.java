package Enums;

public enum Side {
    BUY("Buy"),
    SELL("Sell");
    private final String value;

    Side(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
