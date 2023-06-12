package com.example.tradingengine.models.orders;

import java.util.Comparator;

public class AskLimitComparer implements Comparator<Long> {

    @Override
    public int compare(Long a, Long b) {
        if (a > b) {
            return 1;
        } else if (a < b) {
            return -1;
        } else {
            return 0;
        }
    }

}
