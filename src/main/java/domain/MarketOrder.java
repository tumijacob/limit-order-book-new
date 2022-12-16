package domain;

import Enums.Side;


public class MarketOrder extends Order {

    public MarketOrder(Long id,int quantity,Side side, long timeStamp) {
        super(id, quantity, side, timeStamp);
    }

    @Override
    public boolean isPriceless() {
        return true;
    }

}
