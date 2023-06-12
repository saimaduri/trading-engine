package com.example.tradingengine.models.orderbook;

import com.example.tradingengine.models.matching.MatchingAlgorithm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class MatchingOrderbook implements OrderEntryOrderbook {

    private final MatchingAlgorithm matchingAlgorithm;
    private final RetrievalOrderbook retrievalOrderbook;

    public abstract MatchResult match();

}
