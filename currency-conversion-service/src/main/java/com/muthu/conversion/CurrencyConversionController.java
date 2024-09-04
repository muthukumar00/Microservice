package com.muthu.conversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateProcess(
			@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		Map<String, String> Urivariable = new HashMap<>();
		Urivariable.put("from", from);
		Urivariable.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = 
				new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, Urivariable);
		CurrencyConversion body = responseEntity.getBody(); 
		System.out.print("ResponseEntity"+ body);
		
		BigDecimal conversionMultiple = body.getConversionMultiple();
		BigDecimal totalCalculateAmount = conversionMultiple.multiply(quantity);
		return new CurrencyConversion(body.getId(),from,to,quantity,conversionMultiple, totalCalculateAmount, body.getEnvrionment() + " " + " from restController");
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateProcessfeign(
			@PathVariable String from, 
			@PathVariable String to, 
			@PathVariable BigDecimal quantity) {
		
		/*Map<String, String> Urivariable = new HashMap<>();
		Urivariable.put("from", from);
		Urivariable.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = 
				new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, Urivariable);
		CurrencyConversion body = responseEntity.getBody(); 
		System.out.print("ResponseEntity"+ body);
		
		BigDecimal conversionMultiple = body.getConversionMultiple();
		BigDecimal totalCalculateAmount = conversionMultiple.multiply(quantity); */
		
		CurrencyConversion retrieveExchangeValue = proxy.retrieveExchangeValue(from, to);
		System.out.println("Port Number"+retrieveExchangeValue.getEnvrionment());
		System.out.println("GetId"+retrieveExchangeValue.getId());
		BigDecimal conversionMultiple = retrieveExchangeValue.getConversionMultiple();
		BigDecimal totalCalculateAmount = conversionMultiple.multiply(quantity);
		return new CurrencyConversion(retrieveExchangeValue.getId(),from,to,quantity,conversionMultiple, totalCalculateAmount, retrieveExchangeValue.getEnvrionment() + " " + "from feign");
	}
	
	
}
