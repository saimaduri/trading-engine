package com.example.tradingengine.models.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderCore {

    public long orderId;
    public String username;
    public int securityId;

    public OrderCore(OrderCore order) {
        this.orderId = order.orderId;
        this.username = order.username;
        this.securityId = order.securityId;
    }
}
