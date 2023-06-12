package com.example.tradingengine.models.orders;

import lombok.Getter;

@Getter
public class CancelOrder extends OrderCore {

    public CancelOrder(OrderCore order) {
        super(order);
    }

}
