package com.test.config;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Billing;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.CreditCardToken;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.RefundRequest;
import com.paypal.api.payments.Sale;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.HttpMethod;
import com.paypal.base.rest.PayPalRESTException;
import com.test.common.AppConstants;
import com.test.common.MyUtility;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaypalUtils {

	@Autowired
	private MyUtility myUtils;

	private APIContext apiContext;
	private String mode = "sandbox";
	private String clientID = "AcUvCrtMTzyEJmd9MEVyIdqyAWNvKQJZq4";
	private String clientSecret = "EHF-MdoFmp_h3W6btrvunikkIT17KPXX";
	
//	private String mode = "live";
//	private String clientID = "AZRdBpcj-EkDN5MUDeTRNEK61TMC-VQe8TtcU8Z-R0eNcw-B96ruNh07xlWI3F1XWIGdkd8rZ__AaoPd";
//	private String clientSecret = "EPq4lImjRIKKJNQDNlNbxk2VB1GgiX1yCwub2RYA6J8xrbB7QhiSlYXP1Ujhi8Mdqb6FgpJkebaaK7xM";
	
	
	private static String amexRegex = "^3[47][0-9]{13}$";
	private static String dinnerClubRegex = "^3(?:0[0-59]{1}|[689])[0-9]{0,}$";
	private static String discoverRegex = "^6(?:011|5[0-9]{2})[0-9]{12}$";
	private static String jcbRegex = "^(?:2131|1800|35[0-9]{3})[0-9]{11}$";	
	private static String maestroRegex = "^(5[06789]|6)[0-9]{0,}$";
	private static String masterRegex = "^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$";
	private static String rupayRegex = "^6(?:0|521|522[0-9]{12,14})?$";
	private static String visaRegex = "^4[0-9]{12}(?:[0-9]{3})?$";
	private static final Logger LOGGER = LoggerFactory.getLogger(PaypalUtils.class);

	public APIContext getApiContext() {
		if (apiContext == null)
			apiContext = new APIContext(clientID, clientSecret, mode);
		apiContext.setRequestId(null);
		return apiContext;
	}

	public CreditCard createCreditCard(CreditCard creditCard) throws PayPalRESTException {
		APIContext apiContext = getApiContext();
		creditCard.setExpireYear(calculateExpiryYear(creditCard.getExpireYear()));
//		System.out.println("Credit-Card-Created-Response: " + CreditCard.getLastResponse());
		return creditCard.create(apiContext);
	}

	public CreditCard getCreditCardDetails(String cardId) throws PayPalRESTException {
		APIContext apiContext = getApiContext();
		return CreditCard.get(apiContext, cardId);
	}

	public void deleteCreditCard(String cardId) throws PayPalRESTException {
		APIContext apiContext = getApiContext();
		CreditCard creditCard = CreditCard.get(apiContext, cardId);
		creditCard.delete(apiContext);
	}

	public Payment createPaymentFromCreditCard(String cardId, float totalAmount, String desc) throws PayPalRESTException {
		String totalAmountStr = String.format("%.2f", totalAmount);
		APIContext apiContext = getApiContext();
		Amount amount = new Amount();
		amount.setCurrency(AppConstants.CURRENCY);
		amount.setTotal(totalAmountStr);
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(desc);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCardToken(new CreditCardToken(cardId));
		List<FundingInstrument> fundingInstruments = new ArrayList<FundingInstrument>();
		fundingInstruments.add(fundingInstrument);
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstruments);
		payer.setPaymentMethod(AppConstants.PAYMENT_METHOD_CREDIT_CARD);
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
//		System.out.println("Payment: " + Payment.getLastResponse());
		return payment.create(apiContext);
	}

	private void getPaymentHistory() {
		Map<String, String> containerMap = new HashMap<String, String>();
		containerMap.put("count", "10");
		try {
			APIContext apiContext = getApiContext();
			Payment.list(apiContext, containerMap);
//			LOGGER.info("PaymentHistory = " + PaymentHistory.toString());
//			System.out.println("PaymentHistory: " + PaymentHistory.getLastResponse());
		} catch (PayPalRESTException ex) {
			ex.printStackTrace();
		}
	}

	public void getPaymentDetails() throws PayPalRESTException {
		APIContext apiContext = getApiContext();
		Payment.get(apiContext, "PAY-2Y171787N6850670NLYEFWIA");
//		System.out.println("Payment: " + Payment.getLastResponse());
	}

	public Sale getSaleTransactionDetails(String saleTxnId) throws PayPalRESTException {
		APIContext apiContext = getApiContext();
		return Sale.get(apiContext, saleTxnId);
	}

	public Refund refundSaleTransaction(String saleTxnId, String totalAmount) throws PayPalRESTException {
		APIContext apiContext = getApiContext();
		Sale sale = new Sale();
		sale.setId(saleTxnId);
		Amount amount = new Amount();
		amount.setCurrency(AppConstants.CURRENCY);
		amount.setTotal(totalAmount);
		RefundRequest refund = new RefundRequest();
//		Refund refund = new Refund();
		refund.setAmount(amount);
		return sale.refund(apiContext, refund);
	}

	/**
	 * Payment from Paypal-Accout
	 */
	public Payment createdPaymentFromPayalAccount(String totalAmount, String desc) {
		Payment createdPayment = null;
		APIContext apiContext = getApiContext();
		Amount amount = new Amount();
		amount.setCurrency(AppConstants.CURRENCY);
		amount.setTotal(totalAmount);
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(desc);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		Payer payer = new Payer();
		payer.setPaymentMethod(AppConstants.PAYMENT_METHOD_PAYPAL);
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(String.valueOf(myUtils.getBaseURL()) + "/payment/payment-cancel");
		redirectUrls.setReturnUrl(String.valueOf(myUtils.getBaseURL()) + "/payment/payment-success");
		payment.setRedirectUrls(redirectUrls);
		try {
			createdPayment = payment.create(apiContext);
		} catch (PayPalRESTException ex) {
			System.out.println("Payment: " + Payment.getLastRequest() + ": " + ex.getMessage());
		}
		return createdPayment;
	}

	public Payment executePaymentByPaymentIdAndPayerID(String paymentID, String payerID) throws PayPalRESTException {
		APIContext apiContext = getApiContext();
		Payment payment = new Payment();
		payment.setId(paymentID);
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerID);
		return payment.execute(apiContext, paymentExecution);
	}


//	====================================================================================
//	================================== Billing-Agreement ===============================
//	====================================================================================
	public String getBillingAgreementToken() throws PayPalRESTException, MalformedURLException {
		String approvedURL = null;
		String apiURL = "v1/billing-agreements/agreement-tokens";
		Agreement agreement = new Agreement();
		agreement.setName(AppConstants.AGREEMENT_TITLE);
		agreement.setDescription(AppConstants.AGREEMENT_TEXT);
		agreement.setStartDate(myUtils.getTodayDate_ForPaypal());
//		agreement.setStartDate("2020-03-25T10:38:17Z");

		Plan plan = new Plan();
		plan.setType("MERCHANT_INITIATED_BILLING");
		MerchantPreferences merchantPreferences = new MerchantPreferences();
		merchantPreferences.setCancelUrl(String.valueOf(myUtils.getBaseURL()) + "/payment/agreement-cancel");
		merchantPreferences.setReturnUrl(String.valueOf(myUtils.getBaseURL()) + "/payment/agreement-success");
		merchantPreferences.setAcceptedPaymentType("INSTANT");
		plan.setMerchantPreferences(merchantPreferences);
		agreement.setPlan(plan);
		
		Payer payer = new Payer();
		payer.setPaymentMethod("PAYPAL");
		agreement.setPayer(payer);
		Agreement agreementCreated = executeAgreementCallback(apiURL, agreement.toJSON());
//		System.out.println(Agreement.getLastResponse());
		for (Links links : agreementCreated.getLinks()) {
			if ("approval_url".equals(links.getRel())) {
				URL url = new URL(links.getHref());
				approvedURL = url.toString();
				break;
			}
		}
		return approvedURL;
	}
	
	public Agreement executeAgreementCallback(String apiURL, String jsonData) throws PayPalRESTException {
		return Agreement.configureAndExecute(getApiContext(), HttpMethod.POST, apiURL, jsonData, Agreement.class);
	}
	
	public Agreement getAgreementDetails(String agreementID) throws PayPalRESTException {
//		String agreementID = "BA-6NV2584913772970S";
		Agreement agreement =  new Agreement();
		agreement.setToken(agreementID);
		Agreement agreementDetails = Agreement.get(getApiContext(), agreement.getToken());
		
//		System.out.println("Agreement-Details:\n" + Agreement.getLastResponse());
		return agreementDetails;
	}
	
	public Payment paymentByAgreementID(String agreementID, float totalAmount, String desc) {
		Payment createdPayment = null;
		String totalAmountStr = String.format("%.2f", totalAmount);
		Amount amount = new Amount();
		amount.setCurrency(AppConstants.CURRENCY);
		amount.setTotal(totalAmountStr);
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(desc);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setBilling(new Billing().setBillingAgreementId(agreementID));
		List<FundingInstrument> fundingInstruments = new ArrayList<FundingInstrument>();
		fundingInstruments.add(fundingInstrument);
		
		Payer payer = new Payer();
		payer.setPaymentMethod(AppConstants.PAYMENT_METHOD_PAYPAL);
		payer.setFundingInstruments(fundingInstruments);
		
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(myUtils.getBaseURL() +"/payment/payment-cancel");
		redirectUrls.setReturnUrl(myUtils.getBaseURL() +"/payment/payment-success");
		payment.setRedirectUrls(redirectUrls);
		try {
			createdPayment = payment.create(getApiContext());
//			System.out.println("Payment-Response:\n" + Payment.getLastResponse());
		} catch (PayPalRESTException ex) {
			System.out.println("ERROR: "+ ex.getMessage());
			System.out.println("Payment-ERROR: "+ Payment.getLastRequest());
		}
		return createdPayment;
	}
	
	public String getCreditCardType(String cardNumber) {
		String result = null;
		if (cardNumber.matches(amexRegex)) {
			result = "amex";
		} else if (cardNumber.matches(dinnerClubRegex)) {
			result = "dinner";
		} else if (cardNumber.matches(discoverRegex)) {
			result = "discover";
		} else if (cardNumber.matches(jcbRegex)) {
			result = "jcb";
		} else if (cardNumber.matches(maestroRegex)) {
			result = "maestro";
		} else if (cardNumber.matches(masterRegex)) {
			result = "mastercard";
		} else if (cardNumber.matches(rupayRegex)) {
			result = "rupay";
		} else if (cardNumber.matches(visaRegex)) {
			result = "visa";
		}
		return result;
	}
	
	private Integer calculateExpiryYear(Integer expiryYear) {
		String expiryYearStr = String.valueOf(expiryYear);
		if(expiryYear<10) {
			expiryYearStr = "0"+expiryYearStr;
		}
		if(expiryYear < 100) {
			String year = LocalDate.now().getYear()+"";
			Integer currCentury = Integer.parseInt(year.substring(0, 2));
			Integer currYear = Integer.parseInt(year.substring(2));
			if(currYear <= expiryYear) {
				expiryYearStr = currCentury + expiryYearStr;
			}else if(currYear+20 < expiryYear) {
				expiryYearStr = "00";
			}else {
				expiryYearStr = currCentury+1+ expiryYearStr;
			}
		}
		return Integer.parseInt(expiryYearStr);
	}
	
	public static void main(String[] args) throws PayPalRESTException {
		PaypalUtils paypal = new PaypalUtils();
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("5503728087510126");
		creditCard.setExpireMonth(12);
		creditCard.setExpireYear(2023);
		creditCard.setType("mastercard");
		creditCard.setCvv2("610");
		
		String cardID = "CARD-89545574WU620321EL3C4THI";

//		CreditCard cc = paypal.createCreditCard(creditCard);
//		System.out.println(cc.getValidUntil());
		
//		paypal.createCreditCard(creditCard);
//		paypal.createBillingPlan();
//		paypal.getBillingPlan();
//		paypal.createBillingAgreement();
		paypal.createPaymentFromCreditCard(cardID, 2.5f, "test");
		
	}
}
