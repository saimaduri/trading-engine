package com.example.tradingengine.models.orderbook;

import com.example.tradingengine.models.instrument.Security;
import com.example.tradingengine.models.matching.MatchingAlgorithm;

import lombok.Getter;

@Getter
public abstract class MatchingOrderbook extends Orderbook {

    protected final MatchingAlgorithm matchingAlgorithm;

    public MatchingOrderbook(Security security, MatchingAlgorithm matchingAlgorithm) {
        super(security);
        this.matchingAlgorithm = matchingAlgorithm;
    }

    public abstract MatchResult match();

}
