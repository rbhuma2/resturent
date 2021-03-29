package com.core.service;

import com.core.model.ChargeRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface StripeService {
	
	public Charge charge(ChargeRequest chargeRequest) throws StripeException;

}
