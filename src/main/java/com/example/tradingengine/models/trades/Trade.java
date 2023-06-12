package com.example.tradingengine.models.trades;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Trade {

    public int securityId;
    public long price;
    public long quantity;
    public String executionId;
    public List<TradeOrderIdEntries> tradeOrderIdEntries;

}
