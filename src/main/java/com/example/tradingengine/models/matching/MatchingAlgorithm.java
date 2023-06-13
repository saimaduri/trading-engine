package com.example.tradingengine.models.matching;

import java.util.List;

import com.example.tradingengine.models.orderbook.MatchResult;
import com.example.tradingengine.models.orders.OrderbookEntry;

public interface MatchingAlgorithm {

    MatchResult Match(List<OrderbookEntry> bids, List<OrderbookEntry> asks);

}
