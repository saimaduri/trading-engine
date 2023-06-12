package com.example.tradingengine.models.trades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.tradingengine.models.fills.Fill;
import com.example.tradingengine.models.fills.FillAllocationAlgorithm;
import com.example.tradingengine.models.orders.Order;
import com.example.tradingengine.models.orders.OrderCore;

public class TradeUtil {

    public static TradeResult createTradeAndFills(Order bidOrder, Order askOrder, long fillQuantity,
            FillAllocationAlgorithm fillAllocAlgo) {
        LocalDateTime tradeTime = LocalDateTime.now();
        long tradeNumber = TradeIdGenerator.generateTradeId();
        long bidOrderFillId = tradeNumber * 2;
        long askOrderFillId = 1 + tradeNumber * 2;
        String executionId = formatExecutionId(tradeTime, tradeNumber);

        Fill bidFill = new Fill(new OrderCore(bidOrder.orderId, bidOrder.username, bidOrder.securityId),
                bidOrder.currentQuantity == 0,
                fillQuantity, bidOrderFillId, executionId, fillAllocAlgo);

        Fill askFill = new Fill(new OrderCore(askOrder.orderId, askOrder.username, askOrder.securityId),
                askOrder.currentQuantity == 0,
                fillQuantity, askOrderFillId, executionId, fillAllocAlgo);

        List<TradeOrderIdEntries> tradeOrderIdEntries = new ArrayList<TradeOrderIdEntries>();
        tradeOrderIdEntries.add(new TradeOrderIdEntries(bidOrder.orderId, fillQuantity));
        tradeOrderIdEntries.add(new TradeOrderIdEntries(askOrder.orderId, fillQuantity));

        Trade trade = new Trade(bidOrder.securityId, bidOrder.price, fillQuantity, executionId, tradeOrderIdEntries);

        return new TradeResult(trade, bidFill, askFill);
    }

    private static String formatExecutionId(LocalDateTime tradeTime, long tradeNumber) {
        String formattedTradeTime = DateTimeFormatter.ofPattern("yyyyMMdd").format(tradeTime);
        String formattedTradeNumber = String.format("%07d", tradeNumber);
        return String.format("%s:T:%s", formattedTradeTime, formattedTradeNumber);
    }
}
