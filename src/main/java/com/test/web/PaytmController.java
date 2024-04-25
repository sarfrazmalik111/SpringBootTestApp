package com.test.web;

import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paytm.pg.merchant.PaytmChecksum;
import com.test.common.MyUtility;
import com.test.modalDT.PayTMTest;
//import com.paytm.pg.merchant.CheckSumServiceHelper;

@Controller
@RequestMapping("/paytm")
public class PaytmController {

	@Autowired
	private Environment env;
	@Autowired
	private MyUtility myUtils;
	
	private String paymentPage = "paytm/payment";
	private String reportPage = "paytm/report";

	@GetMapping("/payment")
	public String payment(Model model, PayTMTest paytm) {
		System.out.println("--------------home-------------");
		model.addAttribute("paytm", paytm);
		return paymentPage;
	}

	@PostMapping("/payment")
	public ModelAndView getPayment(@Valid @ModelAttribute("paytm") PayTMTest paytm, BindingResult bindingResult) throws Exception {

		System.out.println("--------------getPayment-------------");
		TreeMap<String, String> parameters = new TreeMap<>();
		parameters.put("MID", env.getProperty("paytm.merchantId"));
		parameters.put("CHANNEL_ID", env.getProperty("paytm.channelId"));
		parameters.put("INDUSTRY_TYPE_ID", env.getProperty("paytm.industryTypeId"));
		parameters.put("WEBSITE", env.getProperty("paytm.website"));
		parameters.put("CALLBACK_URL", env.getProperty("paytm.callbackUrl"));
		
		parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
		parameters.put("EMAIL", env.getProperty("paytm.email"));
		parameters.put("ORDER_ID", myUtils.getOrderId());
		parameters.put("TXN_AMOUNT", paytm.getTxnAmount());
		parameters.put("CUST_ID", paytm.getCustomerId());
		parameters.put("CHECKSUMHASH", getCheckSum(parameters));
		
		ModelAndView modelAndView = new ModelAndView("redirect:" + env.getProperty("paytm.paytmUrl"));
		modelAndView.addAllObjects(parameters);
		return modelAndView;
	}

	@PostMapping("/response")
	public String getResponse(HttpServletRequest request, Model model) {
		System.out.println("--------------getResponse-------------");
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		mapData.forEach((key, val) -> parameters.put(key, val[0]));
		String paytmChecksum = "";
		if (mapData.containsKey("CHECKSUMHASH")) {
			paytmChecksum = mapData.get("CHECKSUMHASH")[0];
		}
		String result;
		boolean isValideChecksum = false;
		System.out.println("RESULT : " + parameters.toString());
		try {
			isValideChecksum = validateCheckSum(parameters, paytmChecksum);
			if (isValideChecksum && parameters.containsKey("RESPCODE")) {
				if (parameters.get("RESPCODE").equals("01")) {
					result = "Payment Successful";
				} else {
					result = "Payment Failed";
				}
			} else {
				result = "Checksum mismatched";
			}
		} catch (Exception e) {
			result = e.toString();
		}
		model.addAttribute("result", result);
		parameters.remove("CHECKSUMHASH");
		model.addAttribute("parameters", parameters);
		return reportPage;
	}

	private boolean validateCheckSum(TreeMap<String, String> params, String paytmChecksum) throws Exception {
		return PaytmChecksum.verifySignature(params, env.getProperty("paytm.merchantKey"), paytmChecksum);
//		return CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(paytmDetails.getMerchantKey(),
//				parameters, paytmChecksum);
	}

	private String getCheckSum(TreeMap<String, String> params) throws Exception {
		return PaytmChecksum.generateSignature(params, env.getProperty("paytm.merchantKey"));
//		return CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(paytmDetails.getMerchantKey(),
//				parameters);
	}
}
