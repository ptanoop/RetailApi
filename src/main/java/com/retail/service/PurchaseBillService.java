package com.retail.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.retail.model.Product;
import com.retail.model.PurchaseBill;
import com.retail.utils.ProductType;

@Service
public class PurchaseBillService {
	
	private final static Logger logger = LoggerFactory.getLogger(PurchaseBillService.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Value("${retail.store.discount.percentage.for.employee}")
	int discountForEmployee;
	
	@Value("${retail.store.discount.percentage.for.affiliate}")
	int discountForAffiliate;

	@Value("${retail.store.discount.percentage.for.customer.more.than.two.years}")
	int discountForCustomerMoreThanTwoYears;
	
	@Value("${retail.store.discount.amount.on.every.hundred.dollar.bill}")
	int discountForEveryHundredDollarBill;
	
	@Value("${retail.store.discount.on.grocery.product.flag}")
	boolean isDiscountAppliedOnGroceryProduct;
	
	@Value("${retail.store.discount.on.non.grocery.product.flag}")
	boolean isDiscountAppliedOnNonGroceryProduct;
	
	public double applyDiscount(List<Product> productList, int discountPercentage) {
		double priceSum = 0.0;
		for(Product product : productList) {
			logger.info("product price = "+product.getPrice());
			if(product.getType()==ProductType.GROCERY) {
				if(isDiscountAppliedOnGroceryProduct)
					priceSum = priceSum + (product.getPrice() - (product.getPrice() * (discountPercentage / 100.0)));
				else
					priceSum = priceSum + product.getPrice();
			}
			if(product.getType()==ProductType.NONGROCERY) {
				if(isDiscountAppliedOnNonGroceryProduct)
					priceSum = priceSum + (product.getPrice() - (product.getPrice() * (discountPercentage / 100.0)));
				else
					priceSum = priceSum + product.getPrice();
			}
			
		}
		logger.info("product price = "+priceSum);
		return priceSum;
	}
	
	public double applyDiscountOnEveryHundred(double amt) {
		return amt - ( ((int)(amt / 100.0)) * discountForEveryHundredDollarBill);
	}
	
	public double calculateNetAmountBill(PurchaseBill purchaseBill) {
		double netAmount = 0.0;
		
		if(customerService.isEmployee(purchaseBill.getCustomer())) {
			logger.info("customer is an employee");
			netAmount = netAmount + applyDiscount(purchaseBill.getProductList(), discountForEmployee);
			logger.info("net amount = "+netAmount);
		}
		else if(customerService.isAffiliate(purchaseBill.getCustomer())) {
			logger.info("customer is an affiliate");
			netAmount = netAmount + applyDiscount(purchaseBill.getProductList(), discountForAffiliate);
			logger.info("net amount = "+netAmount);
		}
		else if(customerService.yearsAssociatedWith(purchaseBill.getCustomer())>=2) {
			logger.info("customer is more than Two years olse");
			netAmount = netAmount + applyDiscount(purchaseBill.getProductList(), discountForCustomerMoreThanTwoYears);
			logger.info("net amount = "+netAmount);
		}
		else {
			for(Product product : purchaseBill.getProductList()) {
				netAmount = netAmount + product.getPrice();
			}
		}
		
		netAmount = applyDiscountOnEveryHundred(netAmount);
		logger.info("After applying discount on every hundred = "+netAmount);
		return netAmount;
	}
	
	public int discountForEmployee() {
		return this.discountForEmployee;
	}
	
	public int discountForAffiliate() {
		return this.discountForAffiliate;
	}
	
	public int discountForCustomerMoreThanTwoYears() {
		return this.discountForCustomerMoreThanTwoYears;
	}
	
	public int discountForEveryHundredDollarBill() {
		return this.discountForEveryHundredDollarBill;
	}
	
}
