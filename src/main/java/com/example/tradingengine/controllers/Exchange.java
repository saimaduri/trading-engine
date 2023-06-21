package com.example.tradingengine.controllers;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tradingengine.controllers.requests.CancelOrderRequestBody;
import com.example.tradingengine.controllers.requests.ModifyOrderRequestBody;
import com.example.tradingengine.controllers.requests.NewOrderRequestBody;
import com.example.tradingengine.models.engine.Engine;
import com.example.tradingengine.models.orders.CancelOrder;
import com.example.tradingengine.models.orders.ModifyOrder;
import com.example.tradingengine.models.orders.Order;
import com.example.tradingengine.models.orders.OrderCore;
import com.example.tradingengine.models.orders.OrderIdGenerator;

@RestController
public class Exchange {

    @Autowired
    Engine engine;

    @RequestMapping("/neworder")
    public ResponseEntity processNewOrder(@RequestBody NewOrderRequestBody newOrderRequestBody) {
        engine.processAsync(new Order(
                new OrderCore(OrderIdGenerator.generateOrderId(), newOrderRequestBody.getUsername(),
                        newOrderRequestBody.getSecurityId()),
                newOrderRequestBody.getPrice(), newOrderRequestBody.getQuantity(), newOrderRequestBody.isBuySide()));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/modifyorder")
    public ResponseEntity processModifyOrder(@RequestBody ModifyOrderRequestBody modifyOrderRequestBody) {
        engine.processAsync(new ModifyOrder(
                new OrderCore(modifyOrderRequestBody.getOrderId(), modifyOrderRequestBody.getUsername(),
                        modifyOrderRequestBody.getSecurityId()),
                modifyOrderRequestBody.getModifyPrice(), modifyOrderRequestBody.getModifyQuantity(),
                modifyOrderRequestBody.isBuySide()));
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/cancelorder")
    public ResponseEntity processCancelOrder(@RequestBody CancelOrderRequestBody cancelOrderRequestBody) {
        engine.processAsync(new CancelOrder(new OrderCore(cancelOrderRequestBody.getOrderId(),
                cancelOrderRequestBody.getUsername(), cancelOrderRequestBody.getSecurityId())));

        return new ResponseEntity(HttpStatus.OK);
    }

}
