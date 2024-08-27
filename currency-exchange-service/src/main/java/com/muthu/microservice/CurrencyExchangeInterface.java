package com.muthu.microservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeInterface extends JpaRepository<CurrencyExchange, Long>{
	CurrencyExchange findByFromAndTo(String from, String to);
}
