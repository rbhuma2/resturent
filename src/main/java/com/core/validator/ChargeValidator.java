package com.core.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.core.model.ChargeRequest;
import com.core.model.ChargeRequest.Currency;

@Component
public class ChargeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ChargeRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	ChargeRequest chargeRequest = (ChargeRequest) target;

    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "bad.request.missing.parameter",
                new Object[] { "amount" });
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stripeToken", "bad.request.missing.parameter",
                new Object[] { "stripeToken" });
    	  
        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        formatCurrency(chargeRequest);
        

    }

	private void formatCurrency(ChargeRequest chargeRequest) {
		if(chargeRequest.getCurrency() == null) {
			chargeRequest.setCurrency(Currency.EUR);
		}
		
	}

	
}
