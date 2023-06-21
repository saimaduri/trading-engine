package com.example.tradingengine.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModifyOrderRequestBody {
    private long orderId;
    private String username;
    private int securityId;
    private long modifyPrice;
    private int modifyQuantity;
    private boolean isBuySide;
}
