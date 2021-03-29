package com.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.model.CheckOut;
import com.core.validator.CheckOutValidator;

@RestController
@RequestMapping(value = "/v1/checkout")
public class CheckoutController {
	
	@Value("${STRIPE_PUBLIC_KEY}")
	private String publicKey;
	
	@Autowired
    private CheckOutValidator checkOutValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(checkOutValidator);
    }
	
	@PostMapping
	public HttpEntity<EntityModel<CheckOut>> saveUserData(@Valid @RequestBody CheckOut checkOut) {
    	
    	
		checkOut.setStripePublicKey(publicKey);
		EntityModel<CheckOut> checkOutResponse = EntityModel.of(checkOut);
        
        return new ResponseEntity<>(checkOutResponse, HttpStatus.CREATED);
    }
	

}
