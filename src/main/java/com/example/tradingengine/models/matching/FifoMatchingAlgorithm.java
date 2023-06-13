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

        int bidOrderToMatchIndex = 0;
        int askOrderToMatchIndex = 0;

        while (bidOrderToMatchIndex < bids.size() && askOrderToMatchIndex < asks.size()) {

            OrderbookEntry bidOrderToMatch = bids.get(bidOrderToMatchIndex);
            OrderbookEntry askOrderToMatch = asks.get(askOrderToMatchIndex);

            if (bidOrderToMatch.currentOrder.price < askOrderToMatch.currentOrder.price) {
                break;
            }

            long currentBidQuantity = bidOrderToMatch.currentOrder.currentQuantity;
            long currentAskQuantity = askOrderToMatch.currentOrder.currentQuantity;

            if (currentBidQuantity == 0) {
                bidOrderToMatchIndex++;
                continue;
            }

            if (currentAskQuantity == 0) {
                askOrderToMatchIndex++;
                continue;
            }

            long fillQuantity = Math.min(currentBidQuantity, currentAskQuantity);

            bidOrderToMatch.currentOrder.decreaseQuantity(fillQuantity);
            askOrderToMatch.currentOrder.decreaseQuantity(fillQuantity);

            TradeResult tradeResult = TradeUtil.createTradeAndFills(bidOrderToMatch.currentOrder,
                    askOrderToMatch.currentOrder, fillQuantity, FillAllocationAlgorithm.FIFO);

            matchResult.addTradeResult(tradeResult);

            if (tradeResult.buyFill.isCompleteFill()) {
                bidOrderToMatchIndex++;
            }

            if (tradeResult.sellFill.isCompleteFill()) {
                askOrderToMatchIndex++;
            }

        }

        return matchResult;
    }

}
