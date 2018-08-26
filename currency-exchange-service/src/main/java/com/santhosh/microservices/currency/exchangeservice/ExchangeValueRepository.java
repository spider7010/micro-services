/**
 * 
 */
package com.santhosh.microservices.currency.exchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Santhosh Surimani
 *
 */
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{
	
	public ExchangeValue findByFromAndTo(String from, String to);

}
