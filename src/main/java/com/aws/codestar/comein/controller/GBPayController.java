package com.aws.codestar.comein.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aws.codestar.comein.entity.PaymentM;
import com.aws.codestar.comein.repository.jdbc.PaymentRepository;
import com.aws.codestar.comein.repository.jdbc.TourBookingRepository;
import com.aws.codestar.comein.utils.DateUtils;

@Controller
@RequestMapping("/gbpay")
public class GBPayController {

    @Autowired
    private PaymentRepository paymentRepo;
    
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
	            System.out.println("key=" + key + " value=" + param);
	        }
	        String resultCode = params.get("resultCode");
	        String amountStr = params.get("amount");
	        String refNo = params.get("referenceNo");
	        String gbpRefNo = params.get("gbpReferenceNo");
	        String currencyCode = params.get("currencyCode");
	        
	        
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
	        int i = bookingRepo.updateGatewayRef(refNo, gbpRefNo, DateUtils.nowDate(),gateway);
	        paymentRepo.save(payment);
    	} catch(Exception ex) {
    		ex.printStackTrace();
    		page = "error";
    	}
        ModelAndView mav = new ModelAndView(page + "_gbpay");
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView initGetGBPay() {
        System.out.println("init get method");
        ModelAndView mav = new ModelAndView("gbpay");
        // mav.addObject("siteName", this.siteName);
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/info")
    public ModelAndView initBackgroundGBPay(@RequestParam Map<String, String> params) {
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String param = params.get(key);
            System.out.println("key=" + key + " value=" + param);
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
        String uuid = UUID.randomUUID().toString();
        System.out.println("gbpRefNo="+gbpRefNo);
        System.out.println("refNo="+refNo);
        System.out.println("paymentType="+paymentType);
        System.out.println("date="+date);
        System.out.println("time="+time);
        System.out.println("cardNo="+cardNo);
        System.out.println("cardHolderName="+cardHolderName);
        System.out.println("paymentType="+paymentType);

        ModelAndView mav = new ModelAndView("bg_gbpay");
        return mav;
    }

}
