/**
 * 
 */
package com.santhosh.microservices.currency.conversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Santhosh Surimani
 *
 */
@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean retrieveCurrencyConverter(@PathVariable final String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariablesMap = new HashMap<>();
		uriVariablesMap.put("from", from);
		uriVariablesMap.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariablesMap);
		
		CurrencyConversionBean responseBody = responseEntity.getBody();
		
		return new CurrencyConversionBean(responseBody.getId(), from, to, responseBody.getConversionMultiple(), quantity,
				quantity.multiply(responseBody.getConversionMultiple()), responseBody.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean retrieveCurrencyConverterFeign(@PathVariable final String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean responseBody = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversionBean(responseBody.getId(), from, to, responseBody.getConversionMultiple(), quantity,
				quantity.multiply(responseBody.getConversionMultiple()), responseBody.getPort());
	}
}
