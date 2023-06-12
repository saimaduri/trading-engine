package com.example.tradingengine.models.orders;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
public class Limit {

    public long price;
    public OrderbookEntry head;
    public OrderbookEntry tail;

    @Setter(AccessLevel.NONE)
    public Side side;

    public Limit(long price) {
        this.price = price;
    }

    public boolean isEmpty() {
        return head == null && tail == null;
    }

    public Side getSide() {
        if (isEmpty()) {
            return Side.UNKNOWN;
        } else {
            return head.currentOrder.isBuySide ? Side.BID : Side.ASK;
        }
    }

    public long getLevelOrderCount() {
        long orderCount = 0;

        OrderbookEntry ptr = head;
        while (ptr != null) {
            if (ptr.currentOrder.currentQuantity != 0) {
                orderCount++;
            }
            ptr = ptr.next;
        }

        return orderCount;
    }

    public long getLevelOrderQuantity() {
        long orderQuantity = 0;

        OrderbookEntry ptr = head;
        while (ptr != null) {
            orderQuantity += ptr.currentOrder.currentQuantity;
            ptr = ptr.next;
        }

        return orderQuantity;
    }

}
