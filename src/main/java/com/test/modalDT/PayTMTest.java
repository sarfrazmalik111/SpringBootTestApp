package com.test.modalDT;

import java.util.TreeMap;

import javax.validation.constraints.NotEmpty;

import com.paytm.pg.merchant.PaytmChecksum;

public class PayTMTest {

	@NotEmpty
	private String customerId;
	@NotEmpty
	private String txnAmount;

	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}


	public static void main(String[] args) throws Exception {

		String MID = "bkUVrC96507922892927";
		String MKEY = "sHl74ZPl4eQLo1U9";
		String ORDERID = "bkUVrC96507922892927";
        /* initialize an hash */
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("MID", MID);
        params.put("ORDERID", ORDERID);

        String paytmChecksum = PaytmChecksum.generateSignature(params, MKEY);
        boolean verifySignature = PaytmChecksum.verifySignature(params, MKEY, paytmChecksum);
        System.out.println("generateSignature Returns: " + paytmChecksum);
        System.out.println("verifySignature Returns: " + verifySignature);

        /* initialize JSON String */
        String body = "{\"mid\":\""+MID+"\",\"orderId\":\""+ORDERID+"\"}";

        paytmChecksum = PaytmChecksum.generateSignature(body, MKEY);
        verifySignature = PaytmChecksum.verifySignature(body, MKEY, paytmChecksum);
        System.out.println("generateSignature Returns: " + paytmChecksum);
        System.out.println("verifySignature Returns: " + verifySignature);
    }
}
