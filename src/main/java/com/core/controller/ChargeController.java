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

import com.core.exception.application.DataNotFoundException;
import com.core.model.ChargeRequest;
import com.core.model.ChargeResponse;
import com.core.mongo.data.entity.CartData;
import com.core.mongo.data.repository.CartDataRepository;
import com.core.service.StripeService;
import com.core.utils.DateRoutine;
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
	
	@Autowired
	private CartDataRepository cartDataRepository;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(chargeValidator);
    }
	
	@PostMapping
	public HttpEntity<EntityModel<ChargeResponse>> chargeData(@Valid @RequestBody ChargeRequest chargeRequest) throws StripeException {
    	
		CartData existingCartData = cartDataRepository.findById(chargeRequest.getId()).orElse(null);
		if(existingCartData == null ) {
			throw new DataNotFoundException("no.data.found");
		}
		
		Charge charge = stripeService.charge(chargeRequest);
    	
    	
		existingCartData.setProcessed(false);
		existingCartData.setEmail(chargeRequest.getEmail());
		existingCartData.setTransactionId(charge.getId());
		existingCartData.setPaymentDate(DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString(DateRoutine.currentTimestamp()));
		existingCartData.setOrderPlaceDate(DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString(DateRoutine.currentTimestamp()));
		cartDataRepository.save(existingCartData);
    	ChargeResponse chargeResponse = new ChargeResponse();
    	chargeResponse.setTransactionId(charge.getId());;
    	chargeResponse.setAmount(charge.getAmount().doubleValue());
		EntityModel<ChargeResponse> response = EntityModel.of(chargeResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
