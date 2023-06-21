package com.example.tradingengine.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CancelOrderRequestBody {
    private long orderId;
    private String username;
    private int securityId;
}
