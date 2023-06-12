package com.example.tradingengine.models.matching;

import java.util.List;

import com.example.tradingengine.models.fills.FillAllocationAlgorithm;
import com.example.tradingengine.models.orderbook.MatchResult;
import com.example.tradingengine.models.orders.OrderbookEntry;
import com.example.tradingengine.models.trades.TradeResult;
import com.example.tradingengine.models.trades.TradeUtil;

public class FifoMatchingAlgorithm implements MatchingAlgorithm {

    @Override
    public MatchResult Match(List<OrderbookEntry> bids, List<OrderbookEntry> asks) {

        MatchResult matchResult = new MatchResult();

        if (bids.isEmpty() || asks.isEmpty()) {
            return matchResult;
        }

        OrderbookEntry bidOrderToMatch = bids.get(0);
        OrderbookEntry askOrderToMatch = bids.get(0);

        while (bidOrderToMatch != null && askOrderToMatch != null
                && bidOrderToMatch.currentOrder.price <= askOrderToMatch.currentOrder.price) {
            long currentBidQuantity = bidOrderToMatch.currentOrder.currentQuantity;
            long currentAskQuantity = bidOrderToMatch.currentOrder.currentQuantity;

            if (currentBidQuantity == 0) {
                bidOrderToMatch = bidOrderToMatch.next;
                continue;
            }

            if (currentAskQuantity == 0) {
                askOrderToMatch = askOrderToMatch.next;
                continue;
            }

            long fillQuantity = Math.min(currentBidQuantity, currentAskQuantity);

            bidOrderToMatch.currentOrder.decreaseQuantity(fillQuantity);
            askOrderToMatch.currentOrder.decreaseQuantity(fillQuantity);

            TradeResult tradeResult = TradeUtil.createTradeAndFills(bidOrderToMatch.currentOrder,
                    askOrderToMatch.currentOrder, fillQuantity, FillAllocationAlgorithm.FIFO);

            matchResult.addTradeResult(tradeResult);

            if (tradeResult.buyFill.isCompleteFill()) {
                bidOrderToMatch = bidOrderToMatch.next;
            }

            if (tradeResult.sellFill.isCompleteFill()) {
                askOrderToMatch = askOrderToMatch.next;
            }
        }

        return matchResult;
    }

}
