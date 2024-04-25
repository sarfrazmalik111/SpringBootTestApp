package com.test.config;
//package com.recharge.atest;
//
//import java.math.BigDecimal;
//
//import com.braintreegateway.BraintreeGateway;
//import com.braintreegateway.ClientTokenRequest;
//import com.braintreegateway.CreditCard;
//import com.braintreegateway.CreditCardRequest;
//import com.braintreegateway.CreditCardVerification;
//import com.braintreegateway.CreditCardVerificationSearchRequest;
//import com.braintreegateway.Customer;
//import com.braintreegateway.CustomerRequest;
//import com.braintreegateway.Environment;
//import com.braintreegateway.PaymentMethod;
//import com.braintreegateway.PaymentMethodNonce;
//import com.braintreegateway.PaymentMethodRequest;
//import com.braintreegateway.ResourceCollection;
//import com.braintreegateway.Result;
//import com.braintreegateway.Subscription;
//import com.braintreegateway.SubscriptionRequest;
//import com.braintreegateway.Transaction;
//import com.braintreegateway.TransactionRequest;
//
//public class BrainTree {
//
//	private static BraintreeGateway gateway = new BraintreeGateway(
//		Environment.SANDBOX,
//		"b5fn86kd56srqnx5sdsfd",
//		"9w6fzrx56vbq5b3gsdfsf",
//		"7918ba5de273cc30f7c29649ac5d3499sdfs"
//	);
//	
//	private static String createCustomer() {
//		System.out.println("--------------create-Customer--------------");
//		CustomerRequest request = new CustomerRequest()
//				.phone("+919756264795")
//				.firstName("Sarfraz malik")
//				.creditCard().number("4111111111111111").expirationDate("06/22").cvv("100")
//			    .options()
//			      .verifyCard(true)
//			      .done()
//			    .done();
//
//		Result<Customer> result = gateway.customer().create(request);
//		System.out.println("Status: "+ result.isSuccess());
//		System.out.println("Message: "+ result.getMessage());
//
//		Customer customer = result.getTarget();
//		String customerId = customer.getId();	//507330813
//		System.out.println(customerId);
//		System.out.println(customer.getPaymentMethods().get(0).getToken());
//		return customerId;
//	}
//	
//	private static void verifyPaymentMethod() {
//		System.out.println("------------------verifyPaymentMethod-------------------");
//		PaymentMethodRequest request = new PaymentMethodRequest()
//				  .customerId("162159256")
//				  .paymentMethodNonce("ca93b8c2-852f-04b5-5f90-bb2bd8f7faea")
//				  .options()
//				    .verifyCard(true)
//				  .done();
//
//		Result<? extends PaymentMethod> result = gateway.paymentMethod().create(request);
//		System.out.println("isSuccess: "+ result.isSuccess());
//		
//		CreditCardVerification verification = result.getCreditCardVerification();
//		System.out.println("getStatus: "+ verification.getStatus());
//		System.out.println("getProcessorResponseType: "+ verification.getGatewayRejectionReason());
//		System.out.println("getProcessorResponseText: "+ verification.getProcessorResponseText());
//	}
//	
//	private static String createPaymentMethodNonce() {
//		Result<PaymentMethodNonce> result = gateway.paymentMethodNonce().create("8b9h3fg");
//		String nonce = result.getTarget().getNonce();
//		System.out.println("NONCE: "+ nonce);
//		return nonce;
//	}
//	
//	private static void createCreditCard() {
//		System.out.println("------------------createCreditCard-------------------");
//		CreditCardRequest request = new CreditCardRequest()
//			.customerId("507330813")
//			.cvv("100")
//			.number("4111111111111111")
//			.expirationDate("1/2022")
//			.options()
//		    	.verifyCard(true)
//		    .done();;
//		Result<CreditCard> result = gateway.creditCard().create(request);
//		System.out.println("getStatus(): "+ result.isSuccess());
//		System.out.println("getMessage(): "+ result.getMessage());
//		CreditCard creditCard = result.getTarget();
//		System.out.println("getCardType(): "+ creditCard.getCardType());
//		System.out.println("getExpirationDate(): "+ creditCard.getExpirationDate());
//		
//	}
//	
//	private static void verifyCreditCard() {
//		CreditCardVerificationSearchRequest request = new CreditCardVerificationSearchRequest()
//				  .customerId().is("507330813");
//		CreditCardVerificationSearchRequest request2 = new CreditCardVerificationSearchRequest()
//				  .creditCardNumber().is("4111111111111111");
//
//		ResourceCollection<CreditCardVerification> collection = gateway.creditCardVerification().search(request);
//		System.out.println(collection.getMaximumSize());
//		for (CreditCardVerification verification : collection) {
//			System.out.println(verification.getStatus());
//		}
//	}
//	
//	private static void getCustomerDetails() {
//		Customer customer = gateway.customer().find("579173386");
//		System.out.println("ID: "+ customer.getId());
//		System.out.println("Phone: "+ customer.getPhone());
//		System.out.println("getPaymentMethods: "+ customer.getPaymentMethods().size());
//		for(PaymentMethod pm: customer.getPaymentMethods()) {
//			System.out.println(pm.getCustomerId() +" : "+ pm.getToken());
//		}
//		System.out.println("getCreditCards: "+ customer.getCreditCards().size());
//		for(CreditCard card: customer.getCreditCards()) {
//			System.out.println(card.getMaskedNumber());
//			System.out.println(card.getUniqueNumberIdentifier());
//		}
//	}
//	
//	private static void deleteCustomer() {
//		Result<Customer> result = gateway.customer().delete("630729713");
//		System.out.println(result.isSuccess());
//	}
//	
//	private static void sale(String pmNonce) {
//		String deviceDataFromTheClient = "d55afc6ec482d4610105d5d21ed2b703";
//		TransactionRequest request = new TransactionRequest()
//			    .amount(new BigDecimal("5.00"))
//			    .paymentMethodNonce(pmNonce)
//			    .deviceData(deviceDataFromTheClient)
//			    .options()
//			      .submitForSettlement(true)
//			      .done();
//
//		  Result<Transaction> result = gateway.transaction().sale(request);
//		  System.out.println("isSuccess: "+ result.isSuccess());
//		  System.out.println("getMessage: "+ result.getMessage());
//		  System.out.println(result.getTarget().getId());
//		  System.out.println(result.getTarget().getCurrencyIsoCode());
//		  System.out.println(result.getTarget().getAmount());
//	}
//	
//	private static void getSaleDetails() {
//		Transaction transaction = gateway.transaction().find("mvac7mpr");
//		System.out.println(transaction.getId());
//		System.out.println(transaction.getPayPalDetails());
//		System.out.println(transaction.getPayPalDetails().getRefundId());
//		System.out.println(transaction.getPayPalDetails().getPaymentId());
//	}
//	
//	private static void refundSaleDetails() {
//		Result<Transaction> result = gateway.transaction().refund("mvac7mpr");
//		System.out.println(result.isSuccess());
//		System.out.println(result.getMessage());
//		System.out.println(result.getTarget());
//		System.out.println(result.getTarget().getId());
//	}
//	
//	private static void createSubscription() {
//		SubscriptionRequest request = new SubscriptionRequest()
//			.paymentMethodToken("gkk2mpw")
//			.planId("RechargeNow");
//		Result<Subscription> result = gateway.subscription().create(request);
//		System.out.println(result.isSuccess());
//		System.out.println(result.getMessage());
//		System.out.println(result.getTarget().getId());
//	}
//	
//	private static void chargeSubscription() {
//		Result<Transaction> retryResult = gateway.subscription().retryCharge("23d4sw", new BigDecimal("2.00"), true);
//		System.out.println(retryResult.isSuccess());
//		System.out.println(retryResult.getMessage());
//		System.out.println(retryResult.getTarget());
//		System.out.println(retryResult.getTarget().getId());
//	}
//	
//	public static void main(String[] args) {
//		String clientToken = gateway.clientToken().generate();
////		System.out.println(clientToken);
//		
////		ClientTokenRequest clientTokenRequest = new ClientTokenRequest().customerId("507330813");
////		String clientToken2 = gateway.clientToken().generate(clientTokenRequest);
////		System.out.println(clientToken2);
//		
////		createCustomer();
////		verifyPaymentMethod();
////		String nonce  = createPaymentMethodNonce();
//		
////		createCreditCard(); 5vzjnb
////		verifyCreditCard();
////		getCustomerDetails(); TxnID: 8eg4vh0s
////		sale(nonce);
////		getSaleDetails();
//		refundSaleDetails();
//		
////		createSubscription();
////		chargeSubscription();
//	}
//
//}
