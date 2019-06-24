package com.retail.service;


import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.retail.model.Customer;

@Service
public class CustomerService {
	
	private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	@Value("${retail.store.name}")
	String retailStoreName;
	
	@Value("${retail.store.affiliates}")
	String[] affiliateStores;
	
	public boolean isEmployee(Customer customer) {
		if(customer.getEmployedAt().compareTo(retailStoreName)==0) {
			logger.info("Customer is an employee of retailstore");
			return true;
		}
		logger.info("Customer is not an employee of retailstore");
		return false;
	}
	
	public boolean isAffiliate(Customer customer) {
		boolean affiliate = false;
		for(int i = 0; i < affiliateStores.length; i++) {
			if(customer.getEmployedAt().compareTo(affiliateStores[i])==0) {
				logger.info("Customer is an employee of "+customer.getEmployedAt());
				affiliate = true;
				break;
			}
		}
		return affiliate;
	}

	public int yearsAssociatedWith(Customer customer) {
		
		Calendar empCal = Calendar.getInstance();
		empCal.setTime(customer.getJoinedAt());
		
	    Calendar nowCal = Calendar.getInstance();
	    nowCal.setTime(new Date());
	    
	    int diff = nowCal.get(Calendar.YEAR) - empCal.get(Calendar.YEAR);
	    logger.info("Taking only YEARs = "+diff);
	    if(empCal.get(Calendar.MONTH) > nowCal.get(Calendar.MONTH) || 
	    		(empCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH) && empCal.get(Calendar.DATE) > nowCal.get(Calendar.DATE))) {
	    	diff--;
	    	logger.info("Adjust with month & days = "+diff);
	    }
	   
	    return diff;
	}
	
}
