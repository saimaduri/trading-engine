package com.example.tradingengine.models.orders;

import lombok.Getter;

@Getter
public class Order extends OrderCore {

    public long price;
    public long initialQuantity;
    public long currentQuantity;
    public boolean isBuySide;

    public Order(OrderCore order, long price, long quantity, boolean isBuySide) {
        super(order);

        this.price = price;
        this.initialQuantity = quantity;
        this.currentQuantity = quantity;
        this.isBuySide = isBuySide;
    }

    public Order(ModifyOrder modifyOrder) {
        this(modifyOrder, modifyOrder.price, modifyOrder.quantity, modifyOrder.isBuySide);
    }

    public void increaseQuantity(long quantityDelta) {
        currentQuantity += quantityDelta;
    }

    public void decreaseQuantity(long quantityDelta) {
        if (currentQuantity < quantityDelta) {
            throw new IllegalStateException(String.format("Quantity Delta > Current Quantity for Order #%d", orderId));
        }
        currentQuantity -= quantityDelta;
    }

}
