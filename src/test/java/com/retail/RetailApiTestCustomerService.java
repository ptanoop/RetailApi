package com.retail;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.retail.model.Customer;
import com.retail.service.CustomerService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RetailApiTestCustomerService {

	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testIsEmployee() {
		Customer customer = new Customer();
		customer.setCustomerId("100");
		customer.setEmployedAt("main_store");
		customer.setJoinedAt(new Date());
		
		assertThat(customerService.isEmployee(customer)).isEqualTo(true);
		
		customer = new Customer();
		customer.setCustomerId("101");
		customer.setEmployedAt("first_store");
		customer.setJoinedAt(new Date());
		
		assertThat(customerService.isEmployee(customer)).isEqualTo(false);
	}
	
	@Test
	public void testIsAffiliate() {
		Customer customer = new Customer();
		customer.setCustomerId("100");
		customer.setEmployedAt("store1");
		customer.setJoinedAt(new Date());
		
		assertThat(customerService.isAffiliate(customer)).isEqualTo(true);
		
		customer = new Customer();
		customer.setCustomerId("101");
		customer.setEmployedAt("store3");
		customer.setJoinedAt(new Date());
		
		assertThat(customerService.isAffiliate(customer)).isEqualTo(true);
		
		customer = new Customer();
		customer.setCustomerId("102");
		customer.setEmployedAt("store4");
		customer.setJoinedAt(new Date());
		
		assertThat(customerService.isAffiliate(customer)).isEqualTo(false);
	}
	
	@Test
	public void testYearsAssociatedWith() throws ParseException {
		Customer customer = new Customer();
		customer.setCustomerId("100");
		customer.setEmployedAt("main_store");
		Date cusDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01");
		customer.setJoinedAt(cusDate);
		
		assertThat(customerService.yearsAssociatedWith(customer)).isEqualTo(0);
		
		customer = new Customer();
		customer.setCustomerId("101");
		customer.setEmployedAt("first_store");
		cusDate = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01");
		customer.setJoinedAt(cusDate);
		
		assertThat(customerService.yearsAssociatedWith(customer)).isEqualTo(2);
		
		customer = new Customer();
		customer.setCustomerId("102");
		customer.setEmployedAt("store1");
		cusDate = new SimpleDateFormat("yyyy-MM-dd").parse("2017-12-01");
		customer.setJoinedAt(cusDate);
		
		assertThat(customerService.yearsAssociatedWith(customer)).isEqualTo(1);
	}
	
}
