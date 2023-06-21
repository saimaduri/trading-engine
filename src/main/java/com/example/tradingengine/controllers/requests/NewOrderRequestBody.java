package com.example.tradingengine.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewOrderRequestBody {
    private String username;
    private int securityId;
    private long price;
    private int quantity;
    private boolean isBuySide;
}
