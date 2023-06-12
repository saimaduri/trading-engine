package com.example.tradingengine.models.orderbook;

import java.util.Objects;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public class OrderbookSpread {

    public long bid;
    public long ask;
    public long spread;

    public OrderbookSpread(@Nullable long bid, @Nullable long ask) {
        this.bid = bid;
        this.ask = ask;
    }

    @Nullable
    public Long getSpread() {
        if (!Objects.isNull(bid) && !Objects.isNull(ask)) {
            return ask - bid;
        }
        return null;
    }

}
