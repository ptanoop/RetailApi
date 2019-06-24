package com.retail;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.retail.controller.PurchaseBillCalculateController;
import com.retail.model.Customer;
import com.retail.model.Product;
import com.retail.model.PurchaseBill;
import com.retail.model.PurchaseBillNetAmount;
import com.retail.service.PurchaseBillService;
import com.retail.utils.ProductType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetailApiTestPurchaseService {
	
	@Autowired
	private PurchaseBillService purchaseBillService;
	
	@Test
	public void testApplyDiscount() {
		
		List<Product> productList = new ArrayList<Product>();
		Product prod1 = new Product();
		prod1.setPrice(100.0);
		prod1.setType(ProductType.GROCERY);
		Product prod2 = new Product();
		prod2.setPrice(200.0);
		prod2.setType(ProductType.NONGROCERY);
		
		productList.add(prod1);
		productList.add(prod2);
		
		double disAmountForEmp = purchaseBillService.applyDiscount(productList, purchaseBillService.discountForEmployee());
		double disAmountForAff = purchaseBillService.applyDiscount(productList, purchaseBillService.discountForAffiliate());
		double disAmountForMoreThanTwoYears = purchaseBillService.applyDiscount(productList, purchaseBillService.discountForCustomerMoreThanTwoYears());
		
		assertThat(disAmountForEmp).isEqualTo(240.0);
		assertThat(disAmountForAff).isEqualTo(280.0);
		assertThat(disAmountForMoreThanTwoYears).isEqualTo(290.0);
		
		assertThat(purchaseBillService.applyDiscountOnEveryHundred(100.0)).isEqualTo(95.0);
		assertThat(purchaseBillService.applyDiscountOnEveryHundred(990.0)).isEqualTo(945.0);
		assertThat(purchaseBillService.applyDiscountOnEveryHundred(99.0)).isEqualTo(99.0);
		
	}
	
	@Test
	public void testApplyDiscountTwo() {
		
		List<Product> productList = new ArrayList<Product>();
		Product prod1 = new Product();
		prod1.setPrice(100.0);
		prod1.setType(ProductType.GROCERY);
		Product prod2 = new Product();
		prod2.setPrice(200.0);
		prod2.setType(ProductType.NONGROCERY);
		Product prod3 = new Product();
		prod3.setPrice(400.0);
		prod3.setType(ProductType.NONGROCERY);
		Product prod4 = new Product();
		prod4.setPrice(50.0);
		prod4.setType(ProductType.GROCERY);
		
		productList.add(prod1);
		productList.add(prod2);
		productList.add(prod3);
		productList.add(prod4);
		
		double disAmountForEmp = purchaseBillService.applyDiscount(productList, purchaseBillService.discountForEmployee());
		double disAmountForAff = purchaseBillService.applyDiscount(productList, purchaseBillService.discountForAffiliate());
		double disAmountForMoreThanTwoYears = purchaseBillService.applyDiscount(productList, purchaseBillService.discountForCustomerMoreThanTwoYears());
		
		assertThat(disAmountForEmp).isEqualTo(570.0);
		assertThat(disAmountForAff).isEqualTo(690.0);
		assertThat(disAmountForMoreThanTwoYears).isEqualTo(720.0);
		
		
	}
	
	@Test
	public void testNetAmountCalculation() {
		
		Customer customer = new Customer();
		customer.setCustomerId("100");
		customer.setEmployedAt("main_store");
		customer.setJoinedAt(new Date());
		
		List<Product> productList = new ArrayList<Product>();
		Product prod1 = new Product();
		prod1.setPrice(100.0);
		prod1.setType(ProductType.GROCERY);
		Product prod2 = new Product();
		prod2.setPrice(200.0);
		prod2.setType(ProductType.NONGROCERY);
		
		productList.add(prod1);
		productList.add(prod2);
		
		PurchaseBill pBill = new PurchaseBill();
		pBill.setCustomer(customer);
		pBill.setProductList(productList);
		
		double netAmt = purchaseBillService.calculateNetAmountBill(pBill);
		
		assertThat(netAmt).isEqualTo(230.0);
	}
	
	@Test
	public void testNetAmountCalculationTwo() {
		
		Customer customer = new Customer();
		customer.setCustomerId("100");
		customer.setEmployedAt("store1");
		customer.setJoinedAt(new Date());
		
		List<Product> productList = new ArrayList<Product>();
		Product prod1 = new Product();
		prod1.setPrice(100.0);
		prod1.setType(ProductType.GROCERY);
		Product prod2 = new Product();
		prod2.setPrice(200.0);
		prod2.setType(ProductType.NONGROCERY);
		
		productList.add(prod1);
		productList.add(prod2);
		
		PurchaseBill pBill = new PurchaseBill();
		pBill.setCustomer(customer);
		pBill.setProductList(productList);
		
		double netAmt = purchaseBillService.calculateNetAmountBill(pBill);
		
		assertThat(netAmt).isEqualTo(270.0);
		
		
		customer = new Customer();
		customer.setCustomerId("100");
		customer.setEmployedAt("store");
		customer.setJoinedAt(new Date());
		
		productList = new ArrayList<Product>();
		prod1 = new Product();
		prod1.setPrice(100.0);
		prod1.setType(ProductType.GROCERY);
		prod2 = new Product();
		prod2.setPrice(200.0);
		prod2.setType(ProductType.NONGROCERY);
		
		productList.add(prod1);
		productList.add(prod2);
		
		pBill = new PurchaseBill();
		pBill.setCustomer(customer);
		pBill.setProductList(productList);
		
		double disAmountOnEveryHundred = purchaseBillService.calculateNetAmountBill(pBill);
		
		assertThat(disAmountOnEveryHundred).isEqualTo(285.0);
	}
	
	
	@Test
	public void testPurchaseBillCalc() throws ParseException {
		
		Customer customer = new Customer();
		customer.setCustomerId("100");
		customer.setEmployedAt("store");
		Date cusDate = new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01");
		customer.setJoinedAt(cusDate);
		
		List<Product> productList = new ArrayList<Product>();
		Product prod1 = new Product();
		prod1.setPrice(100.0);
		prod1.setType(ProductType.GROCERY);
		Product prod2 = new Product();
		prod2.setPrice(200.0);
		prod2.setType(ProductType.NONGROCERY);
		
		productList.add(prod1);
		productList.add(prod2);
		
		PurchaseBill pBill = new PurchaseBill();
		pBill.setCustomer(customer);
		pBill.setProductList(productList);
		
		double netAmt = purchaseBillService.calculateNetAmountBill(pBill);
		
		assertThat(netAmt).isEqualTo(280.0);
		
		PurchaseBillCalculateController pController = new PurchaseBillCalculateController(purchaseBillService);
		PurchaseBillNetAmount pAmount = pController.calculateNetBill(pBill);
		
		assertThat(pAmount.getNetAmount()).isEqualTo(280.0);
		
	}

}
