package com.retail.model;

import java.util.Date;

import lombok.Data;

@Data
public class Customer {
		
	String customerId;
	String employedAt;
	Date joinedAt;

}
