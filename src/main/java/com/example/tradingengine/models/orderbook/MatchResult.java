package com.example.tradingengine.models.orderbook;

import java.util.ArrayList;
import java.util.List;

import com.example.tradingengine.models.fills.Fill;
import com.example.tradingengine.models.trades.Trade;
import com.example.tradingengine.models.trades.TradeResult;

public class MatchResult {

    private final List<Fill> fills = new ArrayList<Fill>();
    private final List<Trade> trades = new ArrayList<Trade>();

    public void addTradeResult(TradeResult tradeResult) {
        addFill(tradeResult.buyFill);
        addFill(tradeResult.sellFill);
        addTrade(tradeResult.trade);
    }

    public void addFill(Fill fill) {
        fills.add(fill);
    }

    private void addTrade(Trade trade) {
        trades.add(trade);
    }
}
