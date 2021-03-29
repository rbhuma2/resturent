package com.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.core.model.ChargeRequest;
import com.core.service.StripeService;
import com.core.validator.ChargeValidator;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
@RequestMapping(value = "/v1/charge")
public class ChargeController {
	
	@Autowired
	private StripeService stripeService;
	
	@Autowired
    private ChargeValidator chargeValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(chargeValidator);
    }
	
	@PostMapping
	public HttpEntity<EntityModel<Charge>> chargeData(@Valid @RequestBody ChargeRequest chargeRequest) throws StripeException {
    	
    	Charge charge = stripeService.charge(chargeRequest);
		EntityModel<Charge> chargeResponse = EntityModel.of(charge);
        return new ResponseEntity<>(chargeResponse, HttpStatus.CREATED);
    }

}
