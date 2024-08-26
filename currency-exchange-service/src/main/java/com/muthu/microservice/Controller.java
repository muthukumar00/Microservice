package com.muthu.microservice;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveValue(@PathVariable String from, @PathVariable String to ) {
		return new CurrencyExchange(1000l, from, to, BigDecimal.valueOf(50) );
	}
}
