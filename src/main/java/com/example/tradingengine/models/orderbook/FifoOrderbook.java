package com.example.tradingengine.models.orderbook;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.tradingengine.models.fills.Fill;
import com.example.tradingengine.models.instrument.Security;
import com.example.tradingengine.models.matching.FifoMatchingAlgorithm;
import com.example.tradingengine.models.orders.CancelOrder;

public class FifoOrderbook extends MatchingOrderbook {

    public FifoOrderbook(Security security) {
        super(security, new FifoMatchingAlgorithm());
    }

    @Override
    public MatchResult match() {

        MatchResult matchResult = matchingAlgorithm.Match(getBuyOrders(), getAskOrders());

        List<Fill> filledOrders = matchResult.getFills().stream().filter(fill -> fill.isCompleteFill)
                .collect(Collectors.toList());

        for (Fill filledOrder : filledOrders) {
            removeOrder(new CancelOrder(filledOrder.order));
        }

        return matchResult;
    }

}
