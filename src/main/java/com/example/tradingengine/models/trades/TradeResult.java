package com.example.tradingengine.models.trades;

import com.example.tradingengine.models.fills.Fill;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TradeResult {

    public Trade trade;
    public Fill buyFill;
    public Fill sellFill;

}
