package com.muthu.microservice;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeInterface repo;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveValue(@PathVariable String from, @PathVariable String to ) {
		//CurrencyExchange currencyExchange = new CurrencyExchange(1000l, from, to, BigDecimal.valueOf(50));
		
		CurrencyExchange currencyExchange = repo.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find the value"+ "from" + from + "to" + to );
		}
		String property = environment.getProperty("server.port");
		currencyExchange.setEnvironment(property);
		return currencyExchange;
	}
	
	@GetMapping("currency-exchange/all")
	public List<CurrencyExchange> retreiveAll() {
		List<CurrencyExchange> findAll = repo.findAll();
		String port = environment.getProperty("server.port");
		if(findAll == null) {
			throw new RuntimeException("unable connect the find data for all");
		}
		
		if(findAll.size() >= 1) {
			findAll.stream().forEach(Obj -> Obj.setEnvironment(port));
		}
		return findAll;
	}
}
