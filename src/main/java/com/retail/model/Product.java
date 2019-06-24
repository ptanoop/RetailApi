package com.retail.model;

import com.retail.utils.ProductType;

import lombok.Data;

@Data
public class Product {

	ProductType type;
	double price;
	
}
