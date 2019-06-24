package com.retail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.retail.model.PurchaseBill;
import com.retail.model.PurchaseBillNetAmount;
import com.retail.service.PurchaseBillService;

@RestController
public class PurchaseBillCalculateController {

	@Autowired
	private PurchaseBillService purchaseBillService;
	
	public PurchaseBillCalculateController() {
		
	}
	
	public PurchaseBillCalculateController(PurchaseBillService pService) {
		this.purchaseBillService = pService;
	}
	
	@RequestMapping(value = "/calculateNetAmount", method = RequestMethod.POST)
	@ResponseBody
	public PurchaseBillNetAmount calculateNetBill(@RequestBody PurchaseBill purchaseBill) {
		
		double netAmount = 0.0;
		netAmount = purchaseBillService.calculateNetAmountBill(purchaseBill);
		
		PurchaseBillNetAmount pAmount = new PurchaseBillNetAmount();
		pAmount.setNetAmount(netAmount);
		
		return pAmount;
		
	}
	
}
