package com.example.tradingengine.models.orders;

import lombok.Getter;

@Getter
public class ModifyOrder extends OrderCore {

    public long price;
    public long quantity;
    public boolean isBuySide;

    public ModifyOrder(OrderCore order, long modifyPrice, long modifyQuantity, boolean isBuySide) {
        super(order);

        this.price = modifyPrice;
        this.quantity = modifyQuantity;
        this.isBuySide = isBuySide;
    }

    public CancelOrder toCancelOrder() {
        return new CancelOrder(this);
    }

    public Order toNewOrder() {
        return new Order(this);
    }

}
