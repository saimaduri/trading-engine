package com.example.tradingengine.models.orderbook;

import com.example.tradingengine.models.instrument.Security;
import com.example.tradingengine.models.matching.MatchingAlgorithm;

public abstract class MatchingOrderbook extends Orderbook {

    private final MatchingAlgorithm matchingAlgorithm;

    public MatchingOrderbook(Security security, MatchingAlgorithm matchingAlgorithm) {
        super(security);
        this.matchingAlgorithm = matchingAlgorithm;
    }

    public abstract MatchResult match();

}
