package com.example.tradingengine.models.fills;

import com.example.tradingengine.models.orders.OrderCore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Fill {

    public OrderCore order;
    public boolean isCompleteFill;
    public long fillQuantity;
    public long fillId;
    public String executionId;
    public FillAllocationAlgorithm fillAllocationAlgorithm;

    public String getFillExecutionId() {
        return String.format("%s-%s", executionId, fillId);
    }

}
