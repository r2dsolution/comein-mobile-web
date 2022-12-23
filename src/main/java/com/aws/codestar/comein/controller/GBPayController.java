package com.aws.codestar.comein.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aws.codestar.comein.entity.PaymentM;
import com.aws.codestar.comein.entity.payment.CreditCardM;
import com.aws.codestar.comein.repository.jdbc.CreditCardRepository;
import com.aws.codestar.comein.repository.jdbc.PaymentRepository;
import com.aws.codestar.comein.repository.jdbc.TourBookingRepository;
import com.aws.codestar.comein.utils.DateUtils;

@Controller
@RequestMapping("/gbpay")
public class GBPayController {
	
	Logger logger = LoggerFactory.getLogger(GBPayController.class);
	
	public static String CreditCardFullPayment = "C";

    @Autowired
    private PaymentRepository paymentRepo;
    
    @Autowired 
    private CreditCardRepository ccRepository;
    
    @Autowired
    private TourBookingRepository bookingRepo;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView initPostGBPay(@RequestParam Map<String, String> params) {
    	String page = "error";
    	try {
    		String gateway = "gbpay";
	        Set<String> keys = params.keySet();
	        for (String key : keys) {
	            String param = params.get(key);
	            logger.info("key=" + key + " value=" + param);
	        }
	        String resultCode = params.get("resultCode");
	        String amountStr = params.get("amount");
	        String refNo = params.get("referenceNo");
	        String gbpRefNo = params.get("gbpReferenceNo");
	        String currencyCode = params.get("currencyCode");
	        
	        
//	        PaymentM payment = new PaymentM();
//	        payment.setCreateDate(DateUtils.nowDate());
//	        payment.setGateway(gateway);
//	        payment.setGatewayRef(gbpRefNo);
//	        payment.setBookingCode(refNo);
//	        payment.setPaymentNO(UUID.randomUUID().toString());
//	        
//	        payment.setTotalAmt(new BigDecimal(amountStr));
//	       
	        if (resultCode != null && resultCode.trim().equals("00")) {
	            page = "success";
	            int i = bookingRepo.updateGatewayRef(refNo, gbpRefNo, DateUtils.nowDate(),gateway);
	        }
//	        payment.setStatus(page);
	        
	        //paymentRepo.save(payment);
    	} catch(Exception ex) {
    		//ex.printStackTrace();
    		logger.error(ex.getMessage(),ex);
    		page = "error";
    	}
        ModelAndView mav = new ModelAndView(page + "_gbpay");
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView initGetGBPay() {
        logger.info("init get method");
        ModelAndView mav = new ModelAndView("gbpay");
        // mav.addObject("siteName", this.siteName);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info")
    public ModelAndView initBackgroundGBPay(@RequestParam Map<String, String> params) {
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String param = params.get(key);
            logger.info("key=" + key + " value=" + param);
        }
        String resultCode = params.get("resultCode");
        String amountStr = params.get("amount");
        String refNo = params.get("referenceNo");
        String gbpRefNo = params.get("gbpReferenceNo");
        
        String currencyCode = params.get("currencyCode");
        String paymentType = params.get("paymentType");
        String date = params.get("date");
        String time = params.get("time");
        String cardNo = params.get("cardNo");
        String cardHolderName = params.get("cardHolderName");
       // String uuid = UUID.randomUUID().toString();
        logger.info("gbpRefNo="+gbpRefNo);
        logger.info("refNo="+refNo);
        logger.info("paymentType="+paymentType);
        logger.info("date="+date);
        logger.info("time="+time);
        logger.info("cardNo="+cardNo);
        logger.info("cardHolderName="+cardHolderName);
        logger.info("paymentType="+paymentType);
        
        
        String page = "error";
        String gateway = "gbpay";
        
        PaymentM payment = new PaymentM();
        payment.setCreateDate(DateUtils.nowDate());
        payment.setGateway(gateway);
        payment.setGatewayRef(gbpRefNo);
        payment.setBookingCode(refNo);
        payment.setPaymentNO(UUID.randomUUID().toString());
        
        payment.setTotalAmt(new BigDecimal(amountStr));
       
        if (resultCode != null && resultCode.trim().equals("00")) {
            page = "success";
        }
        payment.setStatus(page);
        payment = paymentRepo.save(payment);
        
        Long paymentId = payment.getId();
        if (paymentType!=null && paymentType.trim().equals(CreditCardFullPayment)) {
        	CreditCardM cc = new CreditCardM();
        	cc.setCardNO(cardNo);
        	cc.setHolderName(cardHolderName);
        	cc.setTransactionDate(date);
        	cc.setTransactionTime(time);
        	cc.setCurrency(currencyCode);
        	cc.setPaymentId(paymentId);
        	cc.setStatus(page);
        	cc.setStatusCode(resultCode);
        	cc.setStatusDesc(statusDescMap(resultCode));
        	cc.setRecommendation(statusRecommend(resultCode));
        	cc.setCreateDate( DateUtils.nowDate());
        	ccRepository.save(cc);
        }
        

        ModelAndView mav = new ModelAndView("bg_gbpay",params);
        //mav.addObject("m", payment);
        return mav;
    }
    
    public static String statusDescMap(String key){
    	Map<String,String> map = new HashMap<String,String>();
		map.put("00","Success");
		map.put("01","Refer to Card Issuer");
		map.put("03","Invalid Merchant ID");
		map.put("05","Do Not Honour");
		map.put("12","Invalid Transaction");
		map.put("13","Invalid Amount");
		map.put("14","Invalid Card Number");
		map.put("17","Customer Cancellation");
		map.put("19","Re-enter Transaction");
		map.put("30","Format Error");
		map.put("41","Lost Card -Pick Up");
		map.put("43","Stolen Card -Pick Up");
		map.put("50","Invalid Payment Condition");
		map.put("51","Insufficient Funds");
		map.put("54","Expired Card");
		map.put("55","Wrong Pin");
		map.put("58","Transaction not Permitted to Terminal");
		map.put("68","Response Received Too Late");
		map.put("91","Issuer or Switch is Inoperative");
		map.put("94","Duplicate Transmission");
		map.put("96","System Malfunction");
		map.put("xx","Transaction Timeout");
		return map.get(key);
    }
    
    public static String statusRecommend(String key) {
    	Map<String,String> map = new HashMap<String,String>();
		map.put("00","Payment Completed");
		map.put("01","Give cardholder contacts issuer bank");
		map.put("03","Please contact GBPrimePay");
		map.put("05","Cardholder input invalid card information. Ex. Expiry date, CVV2 or card number. Give cardholder contacts issuer bank.");
		map.put("12","Please contact GBPrimePay");
		map.put("13","Payment amount must more than 0.1");
		map.put("14","Please check all digits of card no.");
		map.put("17","Customers click at cancel button in payment page when they make transaction. Customers have to make new payment transaction.");
		map.put("19","Duplicate payment. Please contact GBPrimePay");
		map.put("30","Transaction format error. Please contact GBPrimePay");
		map.put("41","Lost Card and Cardholder give up.");
		map.put("43","Stolen Card and Cardholder give up.");
		map.put("50","Ex. Session time out or invalid VbV Password : ask cardholders to try ma again and complete transaction within 15 minutes with correct card information.");
		map.put("51","Not enough credit limit to pay. Please contact issuer");
		map.put("54","Cardholder key in invalid expiry date");
		map.put("55","Wrong Pin");
		map.put("58","Issuer does not allow to pay with debit card (Visa Electron, Mastercard Electron)");
		map.put("68","Response Received Too Late");
		map.put("91","Issuer system is not available to authorize payment");
		map.put("94","Please inform GBPrimePay to investigate");
		map.put("96","Issuer bank system can not give a service");
		map.put("xx","Can not receive response code from issuer with in the time limit");
    	return map.get(key);
    }

}
