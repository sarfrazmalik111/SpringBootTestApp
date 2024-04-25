package com.test.web;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RelatedResources;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.test.common.AppConstants;
import com.test.common.MyUtility;
import com.test.config.PaypalUtils;

@Controller
@RequestMapping({ "/payment" })
public class APaymentController {

	@Autowired
	private PaypalUtils paypalUtils;
	@Autowired
	private MyUtility myUtils;
	
	private String paymentMethodPage = "pages/misc/payment_method";
	private static final Logger LOGGER = LoggerFactory.getLogger(APaymentController.class);
	
	@GetMapping("/payment-from-agreement")
	public String paymentByAgreement() {
		System.out.println("--------------paymentByAgreement-------------");
		String agreementID = "B-8LD05890AF590390D";
		float totalAmount = 3.2369f;
		String desc = "Test payment by billing agreement";
		Payment payment = paypalUtils.paymentByAgreementID(agreementID, totalAmount, desc);

		String state = payment.getState();
		String paymentID = payment.getId();
		String amount = ((Transaction) payment.getTransactions().get(0)).getAmount().getTotal();
		String saleID = ((RelatedResources) ((Transaction) payment.getTransactions().get(0)).getRelatedResources().get(0)).getSale().getId();
		System.out.println("PaymentID: "+ paymentID);
		return "redirect:/login";
	}
	
	@GetMapping("/payment-from-paypal-account")
	public String paypalPayment() {
		String totalAmount = "2.23";
		String desc = "Testing payment from Paypal Account";
		Payment createdPayment = paypalUtils.createdPaymentFromPayalAccount(totalAmount, desc);
		Iterator<Links> links = createdPayment.getLinks().iterator();
		while (links.hasNext()) {
			Links link = (Links) links.next();
			if (link.getRel().equalsIgnoreCase("approval_url"))
				return "redirect:" + link.getHref();
		}
		return "redirect:/paypal/payment-cancel";
	}

	@ResponseBody
	@GetMapping("/payment-cancel")
	public String cancel() {
		System.out.println("--------------payment-cancel-------------");
		JSONObject json = new JSONObject();
		json.put("status", 0);
		json.put("message", "Error");
		return json.toString();
	}
	
	@ResponseBody
	@GetMapping("/payment-success")
	public String success(@RequestParam String paymentId, @RequestParam String PayerID) {
		System.out.println("--------------payment-success-------------");
		JSONObject jsonResponse = new JSONObject();
		try {
			Payment createdPayment = paypalUtils.executePaymentByPaymentIdAndPayerID(paymentId, PayerID);
			System.out.println(Payment.getLastResponse());
			
			jsonResponse.put("status", 1);
			jsonResponse.put("state", createdPayment.getState());
			jsonResponse.put("payment-id", createdPayment.getId());
			jsonResponse.put("creation-time", myUtils.formatLocalDateTimeForUI(createdPayment.getCreateTime()));
			jsonResponse.put("sale-id", ((RelatedResources) ((Transaction) createdPayment.getTransactions().get(0))
					.getRelatedResources().get(0)).getSale().getId());
			jsonResponse.put("amount", ((RelatedResources) ((Transaction) createdPayment.getTransactions().get(0))
					.getRelatedResources().get(0)).getSale().getAmount().getTotal());
		} catch (PayPalRESTException ex) {
			jsonResponse.put("status", 0);
			jsonResponse.put("error_code", ex.getResponsecode());
			jsonResponse.put("error_name", ex.getDetails().getName());
			jsonResponse.put("error_message", ex.getDetails().getMessage());
		}
		return jsonResponse.toString();
	}
	
	/**
	 * ================== Create Agreement ==================
	 * @return
	 */
	@ResponseBody
	@GetMapping("/create-billing-agreement")
	public String getBillingAgreementToken(HttpServletRequest request) {
		System.out.println("--------------create-billing-agreement-------------");
		JSONObject jsonResponse = new JSONObject();
		try {
			String agreementURL = paypalUtils.getBillingAgreementToken();
			if(agreementURL != null) {
				jsonResponse.put("status", 1);
				jsonResponse.put("message", AppConstants.SUCCESS);
				jsonResponse.put("agreementURL", agreementURL);
				
			}else {
				jsonResponse.put("status", 2);
				jsonResponse.put("message", AppConstants.SOMETHING_WRONG_MSG);
			}
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
			jsonResponse.put("status", 2);
			jsonResponse.put("message", AppConstants.SOMETHING_WRONG_MSG);
		}
		return jsonResponse.toString();
	}

	@RequestMapping("/agreement-success")
	public String agreementSuccess(@RequestParam String ba_token, Model model) {
		System.out.println("---------agreement-Success-----------");
		String apiURL = "v1/billing-agreements/agreements";
		try {
			String jsonData = new JSONObject().put("token_id", ba_token).toString();
			Agreement activeAgreement = paypalUtils.executeAgreementCallback(apiURL, jsonData);
//			System.out.println(Agreement.getLastResponse());
			
			String agreement_id = activeAgreement.getId();
			String payer_id = activeAgreement.getPayer().getPayerInfo().getPayerId();
			String payer_email = activeAgreement.getPayer().getPayerInfo().getEmail();
			
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("status", 1);
			jsonResponse.put("message", AppConstants.SUCCESS);
			jsonResponse.put("payer_email", payer_email);
		} catch (PayPalRESTException ex) {
			ex.printStackTrace();
			model.addAttribute("agreementError", AppConstants.SOMETHING_WRONG_MSG);
		}
		return paymentMethodPage;
	}
	
	@RequestMapping("/agreement-cancel")
	public String agreementCancel(Model model) {
		System.out.println("---------agreement-Cancel-----------");
		model.addAttribute("agreementError", AppConstants.SOMETHING_WRONG_MSG);
		return paymentMethodPage;
	}
	
	private void addAgreementToken(Long userId, String agreementURL) {
		String agreementToken = agreementURL.split("ba_token=")[1]+"$$$"+userId;
		AppConstants.userAgreementTokens.add(agreementToken);
	}
	
	private Long getUserIdFromAgreementToken(String agreementToken) {
		Long userId = null;
		String savedToken = null;
		for(String agToken: AppConstants.userAgreementTokens) {
			if(agToken.contains(agreementToken)) {
				userId = Long.parseLong(agToken.split("\\${3}")[1]);
				savedToken = agToken;
				break;
			}
		}
		AppConstants.userAgreementTokens.remove(savedToken);
		return userId;
	}
	
}
