package com.retail.model;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseBill {
	
	Customer customer;
	List<Product> productList;
	
	
}
