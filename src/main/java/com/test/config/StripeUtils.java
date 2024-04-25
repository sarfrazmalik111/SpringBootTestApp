package com.test.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccountCollection;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import com.test.common.AppConstants;

@Service
public class StripeUtils {

	private static final String publishableKey = "pk_test_51H3K3EIBdq000dzDhP1c";
	private static final String secretKey = "sk_test_51H3K3EIB2vDJzGvJHpZr00dHfHcRGy";

	static {
		Stripe.apiKey = secretKey;
	}
	private Customer createCustomer(String email) throws StripeException {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		params.put("description", "Card added for powerbank Rental-System");
		return Customer.create(params);
	}

	private Customer getCustomerDetails(String customerID) throws StripeException {
		return Customer.retrieve(customerID);
	}
	private boolean deleteCustomer(String customerID) throws StripeException {
		return Customer.retrieve(customerID).delete().getDeleted();
	}
	
	/**
	 * Credit Card
	 * @throws StripeException 
	 */
	private Card createCreditCard(String customerID) throws StripeException {
		Map<String, Object> cardParams = new HashMap<>();
		cardParams.put("number", "378282246310005");
		cardParams.put("exp_month", "12");
		cardParams.put("exp_year", "25");
		cardParams.put("cvc", "123");

		Map<String, Object> tokenParams = new HashMap<>();
		tokenParams.put("card", cardParams);
		
		Map<String, Object> sourceParams = new HashMap<>();
		sourceParams.put("source", Token.create(tokenParams).getId());
		return (Card)Customer.retrieve(customerID).getSources().create(sourceParams);
	}

	private Card getCardDetails(String customerID, String cardID) throws StripeException {
		Customer customer = Customer.retrieve(customerID);
		return (Card) customer.getSources().retrieve(cardID);
	}

	private Card updateCardDetails(String customerID, String cardID) throws StripeException {
		Customer customer = Customer.retrieve(customerID);
		Card card = (Card)customer.getSources().retrieve(cardID);

		Map<String, Object> params = new HashMap<>();
		params.put("name", "Jenny Rosen");
		return (Card) card.update(params);
	}

	private Card deleteCard(String customerID, String cardID) throws StripeException {
		Customer customer = Customer.retrieve(customerID);
		Card card = (Card)customer.getSources().retrieve(cardID);
		return (Card) card.delete();
	}

	private ExternalAccountCollection getCustomerCards(String customerID) throws StripeException {
		Map<String, Object> params = new HashMap<>();
		params.put("object", "card");
		params.put("limit", 3);
		Customer customer = Customer.retrieve(customerID);
		return customer.getSources().list(params);
	}

	/**
	 * Payment
	 * @throws StripeException 
	 */
	private Charge paymentByCreditCard(String customerID, String cardID) throws StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", 2*100);
		params.put("currency", AppConstants.CURRENCY);
		params.put("customer", customerID);
		params.put("source", cardID);
		return Charge.create(params);
	}
	
	private Charge getPaymentDetails(String paymentID) throws StripeException {
		return Charge.retrieve(paymentID);
	}

	private Refund refundPayment(String paymentID) throws StripeException {
		Map<String, Object> params = new HashMap<>();
		params.put("charge", paymentID);
		return Refund.create(params);
	}

	private Refund getRefundDetails(String refundID) throws StripeException {
		return Refund.retrieve(refundID);
	}

//	private Token getTokenFromCard() throws StripeException {
//		Map<String, Object> card = new HashMap<>();
//		card.put("number", "4242424242424242");
//		card.put("exp_month", 2);
//		card.put("exp_year", 2021);
//		card.put("cvc", "314");
//		Map<String, Object> params = new HashMap<>();
//		params.put("card", card);
//		return Token.create(params);
//	}

//	private Token createPIIToken() throws StripeException {
//		Map<String, Object> pii = new HashMap<>();
//		pii.put("id_number", "000000000");
//		Map<String, Object> params = new HashMap<>();
//		params.put("pii", pii);
//		return Token.create(params);
//	}
	
//	private void getAllCustomers() throws StripeException {
//	Map<String, Object> params = new HashMap<>();
//	params.put("limit", 10);
//	CustomerCollection customers = Customer.list(params);
//	Iterator<Customer> it = customers.autoPagingIterable().iterator();
//	while(it.hasNext()) {
//		Customer customer = it.next();
//		System.out.println(customer.getId()+": "+customer.getEmail());
//	}
//}
	
//	/**
//	 * Payment Method
//	 */
//	private void createPaymentMethod() {
//		Map<String, Object> card = new HashMap<>();
//		card.put("number", "4242424242424242");
//		card.put("exp_month", 2);
//		card.put("exp_year", 2021);
//		card.put("cvc", "314");
//		Map<String, Object> params = new HashMap<>();
//		params.put("type", "card");
//		params.put("card", card);
//		PaymentMethod paymentMethod = PaymentMethod.create(params);
////		PaymentMethod paymentMethod = PaymentMethod.retrieve("pm_1EUq6lJAJfZb9HEBD2w5v8TP");
//	}
//	private void attachPaymentMEthodToCustomer() {
//		PaymentMethod paymentMethod = PaymentMethod.retrieve("pm_123456789");
//		Map<String, Object> params = new HashMap<>();
//		params.put("customer", "cus_BrCcXhbVKiCf0A");
//		PaymentMethod paymentMethod = paymentMethod.attach(params);
//	}
//	private void detachPaymentMEthod() {
//		PaymentMethod paymentMethod = PaymentMethod.retrieve("pm_123456789");
//		PaymentMethod paymentMethod = paymentMethod.detach();
//	}

//	private void createBankAccount() {
//		Customer customer = Customer.retrieve("cus_GnvPPQnSsGBCPI");
//		Map<String, Object> bankParams = new HashMap<>();
//		bankParams.put("source", "bank_account");
//		bankParams.put("country", "US");
//		bankParams.put("currency", "USD");
//		bankParams.put("account_holder_name", "Sarfraz");
//		bankParams.put("account_holder_type", "individual");
//		bankParams.put("account_number", "0001234567890");
//		bankParams.put("routing_number", "110000000");
	
//		Map<String, Object> tokenParams = new HashMap<>();
//		tokenParams.put("bank_account", bankParams);
//		Token token = Token.create(tokenParams);
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("source", token.getId());
//		BankAccount bankAccount = (BankAccount) customer.getSources().create(params);
//	}
//	private BankAccount verifyBankAccount() throws StripeException {
//		Customer customer = Customer.retrieve("cus_GnvPPQnSsGBCPI");
//		ExternalAccount bankAccount = customer.getSources().retrieve("ba_1fdfgsGHFGH344HJH656HJ");
//		List<Object> amounts = new ArrayList<>();
//		amounts.add(32);
//		amounts.add(45);
//		Map<String, Object> params = new HashMap<>();
//		params.put("amounts", amounts);
//		return (BankAccount) bankAccount.verify(params);
//	}
//	private void getBank() {
//		Customer customer = Customer.retrieve("cus_GnvPPQnSsGBCPI");
//		BankAccount bankAccount = (BankAccount) customer.getSources().retrieve("ba_1GGJYYFSy3mGdINTIjm1U5t8");
//	}
	
	public static void main(String[] args) throws StripeException {
		StripeUtils service = new StripeUtils();
		String customerID = "cus_He1M0NlL8SgSLe";
		String cardID = "card_1H4kswIB2nVmgPeChb9Q4VC7";
		String chargedPaymentID = "ch_1H4mcdIB2nVmgPeC9wRQ1G2K";
		Customer card = service.createCustomer(null);
		System.out.println(card);
	}

}
