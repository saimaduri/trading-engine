package com.example.tradingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

import com.example.tradingengine.controllers.Exchange;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TradingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingEngineApplication.class, args);
	}

}
