package com.aws.codestar.comein.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/gbpay")
public class GBPayController {
	
	@RequestMapping(method = RequestMethod.POST)
    public ModelAndView initPostGBPay() {
        ModelAndView mav = new ModelAndView("gbpay");
       // mav.addObject("siteName", this.siteName);
        return mav;
    }
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView initGetGBPay() {
		System.out.println("init get method");
        ModelAndView mav = new ModelAndView("gbpay");
       // mav.addObject("siteName", this.siteName);
        return mav;
    }

}
